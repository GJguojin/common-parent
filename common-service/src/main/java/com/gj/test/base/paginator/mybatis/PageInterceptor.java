package com.gj.test.base.paginator.mybatis;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.gj.test.base.search.BaseSearch;

/**
 * 为MyBatis提供基于方言(Dialect)的分页查询的插件
 * <p/>
 * 将拦截Executor.query()方法实现分页方言的插入.
 * 
 * @author badqiu
 * @author miemiedev
 */

@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class PageInterceptor implements Interceptor {
	private static Logger logger = Logger.getLogger( PageInterceptor.class );
	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;

	static ExecutorService Pool;
	Dialect dialect;
	String dialectClass;
	boolean asyncTotalCount = false;

	public static Log log = LogFactory.getLog( PageInterceptor.class );

	private PageBounds getPageBounds( Object parameter ) {
		PageBounds pageBounds = new PageBounds();
		if( parameter instanceof Map ) {
			Map map = ( Map )parameter;
			if( map.containsKey( "pageIndex" ) && map.containsKey( "pageSize" ) ) {
				if( ( Integer )map.get( "pageIndex" ) > 0 && ( Integer )map.get( "pageSize" ) > 0 )
					pageBounds = new PageBounds( ( Integer )map.get( "pageIndex" ), ( Integer )map.get( "pageSize" ) );
			}
		} else if( parameter instanceof BaseSearch ) {
			BaseSearch vSearch = ( BaseSearch )parameter;

			if( vSearch.getPage() > 0 && vSearch.getRows() > 0 ) {
				pageBounds = new PageBounds( vSearch.getPage(), vSearch.getRows() );
			}
		}
		return pageBounds;
	}

	public Object intercept( final Invocation invocation ) throws Throwable {
		final Executor executor = ( Executor )invocation.getTarget();
		final Object[] queryArgs = invocation.getArgs();
		final MappedStatement ms = ( MappedStatement )queryArgs[MAPPED_STATEMENT_INDEX];
		final Object parameter = queryArgs[PARAMETER_INDEX];
		final RowBounds rowBounds = ( RowBounds )queryArgs[ROWBOUNDS_INDEX];
		final PageBounds pageBounds = getPageBounds( parameter );// new PageBounds(rowBounds);

		final int offset = pageBounds.getOffset();
		final int limit = pageBounds.getLimit();
		final int page = pageBounds.getPage();

		final BoundSql boundSql = ms.getBoundSql( parameter );
		final StringBuffer bufferSql = new StringBuffer( boundSql.getSql().trim() );
		if( bufferSql.lastIndexOf( ";" ) == bufferSql.length() - 1 ) {
			bufferSql.deleteCharAt( bufferSql.length() - 1 );
		}
		String sql = bufferSql.toString();

		if( pageBounds.getOrders() != null && !pageBounds.getOrders().isEmpty() ) {
			sql = dialect.getSortString( sql, pageBounds.getOrders() );
		}

		Callable<Paginator> countTask = null;

		if( dialect.supportsLimit() && ( offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT ) ) {
			if( pageBounds.isContainsTotalCount() ) {
				countTask = new Callable() {
					public Object call() throws Exception {
						Integer count = null;
						Cache cache = ms.getCache();
						if( cache != null && ms.isUseCache() ) {
							CacheKey cacheKey = executor
											.createCacheKey( ms, parameter, new PageBounds(), copyFromBoundSql( ms, boundSql, bufferSql.toString() ) );
							count = ( Integer )cache.getObject( cacheKey );
							if( count == null ) {
								count = SQLHelp.getCount( bufferSql.toString(), ms, parameter, boundSql, dialect );
								cache.putObject( cacheKey, count );
							}
						} else {
							count = SQLHelp.getCount( bufferSql.toString(), ms, parameter, boundSql, dialect );
						}
						Paginator paginator = new Paginator( page, limit, count );
						return paginator;
					}
				};
			}

			if( dialect.supportsLimitOffset() ) {
				sql = dialect.getLimitString( sql, offset, limit );
			} else {
				sql = dialect.getLimitString( sql, 0, limit );
			}
			queryArgs[ROWBOUNDS_INDEX] = new RowBounds( RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT );
		}

		queryArgs[MAPPED_STATEMENT_INDEX] = copyFromNewSql( ms, boundSql, sql );
		Boolean async = pageBounds.getAsyncTotalCount() == null ? asyncTotalCount : pageBounds.getAsyncTotalCount();
		Future<List> listFuture = call( new Callable<List>() {
			public List call() throws Exception {
				return ( List )invocation.proceed();
			}
		}, async );

		if( countTask != null ) {
			Future<Paginator> countFutrue = call( countTask, async );
			PageList pageList = new PageList( listFuture.get(), countFutrue.get() );
			pageList.setPageIndex( page );
			pageList.setPageSize( limit );
			pageList.setTotalRows( countFutrue.get().getTotalCount() );
			pageList.setTotalPages( countFutrue.get().getTotalPages() );
			return pageList;
		}

		return listFuture.get();
	}

	private <T> Future<T> call( Callable callable, boolean async ) {
		if( async ) {
			return Pool.submit( callable );
		} else {
			FutureTask<T> future = new FutureTask( callable );
			future.run();
			return future;
		}
	}

	private MappedStatement copyFromNewSql( MappedStatement ms, BoundSql boundSql, String sql ) {
		BoundSql newBoundSql = copyFromBoundSql( ms, boundSql, sql );
		return copyFromMappedStatement( ms, new BoundSqlSqlSource( newBoundSql ) );
	}

	private BoundSql copyFromBoundSql( MappedStatement ms, BoundSql boundSql, String sql ) {
		BoundSql newBoundSql = new BoundSql( ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject() );
		for( ParameterMapping mapping : boundSql.getParameterMappings() ) {
			String prop = mapping.getProperty();
			if( boundSql.hasAdditionalParameter( prop ) ) {
				newBoundSql.setAdditionalParameter( prop, boundSql.getAdditionalParameter( prop ) );
			}
		}
		return newBoundSql;
	}

	// see: MapperBuilderAssistant
	private MappedStatement copyFromMappedStatement( MappedStatement ms, SqlSource newSqlSource ) {
		Builder builder = new Builder( ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType() );

		builder.resource( ms.getResource() );
		builder.fetchSize( ms.getFetchSize() );
		builder.statementType( ms.getStatementType() );
		builder.keyGenerator( ms.getKeyGenerator() );
		if( ms.getKeyProperties() != null && ms.getKeyProperties().length != 0 ) {
			StringBuffer keyProperties = new StringBuffer();
			for( String keyProperty : ms.getKeyProperties() ) {
				keyProperties.append( keyProperty ).append( "," );
			}
			keyProperties.delete( keyProperties.length() - 1, keyProperties.length() );
			builder.keyProperty( keyProperties.toString() );
		}

		// setStatementTimeout()
		builder.timeout( ms.getTimeout() );

		// setStatementResultMap()
		builder.parameterMap( ms.getParameterMap() );

		// setStatementResultMap()
		builder.resultMaps( ms.getResultMaps() );
		builder.resultSetType( ms.getResultSetType() );

		// setStatementCache()
		builder.cache( ms.getCache() );
		builder.flushCacheRequired( ms.isFlushCacheRequired() );
		builder.useCache( ms.isUseCache() );

		return builder.build();
	}

	public Object plugin( Object target ) {
		return Plugin.wrap( target, this );
	}

	public void setProperties( Properties properties ) {

		PropertiesHelper propertiesHelper = new PropertiesHelper( properties );

		setAsyncTotalCount( propertiesHelper.getBoolean( "asyncTotalCount", false ) );

		setPoolMaxSize( propertiesHelper.getInt( "poolMaxSize", 0 ) );

	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource( BoundSql boundSql ) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql( Object parameterObject ) {
			return boundSql;
		}
	}

	public void setDialect( Dialect dialect ) {
		logger.debug( "dialectClass: {" + dialect.getClass().getName() + "} " );
		this.dialect = dialect;
	}

	public void setAsyncTotalCount( boolean asyncTotalCount ) {
		logger.debug( "asyncTotalCount: {" + asyncTotalCount + "} " );
		this.asyncTotalCount = asyncTotalCount;
	}

	public void setPoolMaxSize( int poolMaxSize ) {

		if( poolMaxSize > 0 ) {
			logger.debug( "poolMaxSize: {" + poolMaxSize + "} " );
			Pool = Executors.newFixedThreadPool( poolMaxSize );
		} else {
			Pool = Executors.newCachedThreadPool();
		}

	}

	public String getDialectClass() {
		return dialectClass;
	}

	public void setDialectClass( String dialectClass ) {
		this.dialectClass = dialectClass;

		try {
			setDialect( ( Dialect )Class.forName( dialectClass ).newInstance() );
		} catch( Exception e ) {
			log.error( e.getMessage() );
			throw new RuntimeException( "cannot create dialect instance by dialectClass:" + dialectClass, e );
		}
	}
}
