package com.gj.test.base.paginator.mybatis;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 包含“分页”信息的List
 * <p/>
 * <p>
 * 要得到总页数请使用 toPaginator().getTotalPages();
 * </p>
 *
 * @author badqiu
 * @author miemiedev
 */
public class PageList<E> extends ArrayList<E> implements Serializable {

	private static final long serialVersionUID = 1412759446332294208L;

	private Paginator paginator;

	private PageInfo pageInfo;

	private int pageSize;

	private int pageIndex;

	private int totalRows;

	private int totalPages;

	/**
	 * 传入的其他搜素参数
	 */
	protected Map<String, String> params;

	protected String filterParamReg = "[\\<\\>\\\"\\'　\\ ]";// 需要过了掉param值中的字符

	/**
	 * 获取带查询参数的map对象。start：查询起始位。limit：查询多少条
	 * 
	 * @return
	 */
	public Map<String, Object> getSearchParamsMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		if( this.params != null ) {
			for( String key : params.keySet() ) {
				String value = params.get( key );
				if( !StringUtils.isEmpty( value ) ) {
					value = value.trim().replaceAll( filterParamReg, "" );
					params.put( key, value );
				}
			}
			map.putAll( params );
		}
		return map;
	}

	public PageList() {
	}

	public PageList( Collection<? extends E> c ) {
		super( c );
	}

	public PageList( Collection<? extends E> c, Paginator p ) {
		super( c );
		this.paginator = p;
	}

	public PageList( Paginator p ) {
		this.paginator = p;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize( int pageSize ) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex( int pageIndex ) {
		this.pageIndex = pageIndex;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows( int totalRows ) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages( int totalPages ) {
		this.totalPages = totalPages;
	}

	public PageInfo<E> getPageInfo() {
		PageInfo<E> pageObj = new PageInfo<E>();
		pageObj.setPageIndex( this.getPageIndex() );
		pageObj.setPageSize( this.getPageSize() );
		pageObj.setTotalPages( this.getTotalPages() );
		pageObj.setTotalRows( this.getTotalRows() );
		return pageObj;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams( Map<String, String> params ) {
		this.params = params;
	}

}
