package com.gj.test.base.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

/**
 * 网络工具类。
 * 
 * @author magang
 * @since 1.0, Sep 12, 2009
 */
public abstract class WebUtils {

	public static final String DEFAULT_CHARSET = "UTF-8";
	private static final String METHOD_POST = "POST";
	private static final String METHOD_GET = "GET";

	public static final int CONNECT_TIMEOUT = 1000 * 100;

	public static final int READ_TIMEOUT = 1000 * 100;

	public static Log log = LogFactory.getLog( WebUtils.class );

	private WebUtils() {
	}

	/**
	 * 执行HTTP POST请求。
	 * 
	 * @param url 请求地址
	 * @param params 请求参数
	 * @return 响应字符串
	 * @throws IOException
	 */
	public static String doPost( String url, Map<String, String> params, int connectTimeout, int readTimeout ) throws IOException {
		return doPost( url, params, DEFAULT_CHARSET, connectTimeout, readTimeout );
	}

	/**
	 * 执行HTTP POST请求。
	 * 
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param charset 字符集，如UTF-8, GBK, GB2312
	 * @return 响应字符串
	 * @throws IOException
	 */
	public static String doPost( String url, Map<String, String> params, String charset, int connectTimeout, int readTimeout ) throws IOException {
		String ctype = "application/x-www-form-urlencoded;charset=" + charset;
		String query = buildQuery( params, charset );
		byte[] content = {};
		if( query != null ) {
			content = query.getBytes( charset );
		}
		return doPost( url, ctype, content, connectTimeout, readTimeout );
	}

	/**
	 * 执行HTTP POST请求。
	 * 
	 * @param url 请求地址
	 * @param ctype 请求类型
	 * @param content 请求字节数组
	 * @return 响应字符串
	 * @throws IOException
	 */
	public static String doPost( String url, String ctype, byte[] content, int connectTimeout, int readTimeout ) throws IOException {
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			try {
				conn = getConnection( new URL( url ), METHOD_POST, ctype );
				conn.setConnectTimeout( connectTimeout );
				conn.setReadTimeout( readTimeout );
			} catch( IOException e ) {
				log.error( e.getMessage() );
				// Map<String, String> map=getParamsFromUrl(url);
				// TaobaoLogger.logCommError(e,
				// url,map.get("app_key"),map.get("method"), content);
				throw e;
			}
			try {
				out = conn.getOutputStream();
				out.write( content );
				rsp = getResponseAsString( conn );
			} catch( IOException e ) {
				log.error( e.getMessage() );
				// Map<String, String> map=getParamsFromUrl(url);
				// TaobaoLogger.logCommError(e,
				// conn,map.get("app_key"),map.get("method"), content);
				throw e;
			}

		} finally {
			if( out != null ) {
				out.close();
			}
			if( conn != null ) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	/**
	 * 执行HTTP GET请求。
	 * 
	 * @param url 请求地址
	 * @param params 请求参数
	 * @return 响应字符串
	 * @throws IOException
	 */
	public static String doGet( String url, Map<String, String> params ) throws IOException {
		return doGet( url, params, DEFAULT_CHARSET );
	}

	/**
	 * 执行HTTP GET请求。
	 * 
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param charset 字符集，如UTF-8, GBK, GB2312
	 * @return 响应字符串
	 * @throws IOException
	 */
	public static String doGet( String url, Map<String, String> params, String charset ) throws IOException {
		HttpURLConnection conn = null;
		String rsp = null;

		try {
			String ctype = "application/x-www-form-urlencoded;charset=" + charset;
			String query = buildQuery( params, charset );
			try {
				conn = getConnection( buildGetUrl( url, query ), METHOD_GET, ctype );
			} catch( IOException e ) {
				log.error( e.getMessage() );
				// Map<String, String> map=getParamsFromUrl(url);
				// TaobaoLogger.logCommError(e,
				// url,map.get("app_key"),map.get("method"), params);
				throw e;
			}

			try {
				rsp = getResponseAsString( conn );
			} catch( IOException e ) {
				log.error( e.getMessage() );
				// Map<String, String> map=getParamsFromUrl(url);
				// TaobaoLogger.logCommError(e,
				// conn,map.get("app_key"),map.get("method"), params);
				throw e;
			}

		} finally {
			if( conn != null ) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	private static HttpURLConnection getConnection( URL url, String method, String ctype ) throws IOException {
		HttpURLConnection conn = ( HttpURLConnection )url.openConnection();
		conn.setRequestMethod( method );
		conn.setDoInput( true );
		conn.setDoOutput( true );
		conn.setRequestProperty( "Accept", "text/xml,text/javascript,text/html" );
		conn.setRequestProperty( "User-Agent", "top-sdk-java" );
		conn.setRequestProperty( "Content-Type", ctype );
		return conn;
	}

	private static URL buildGetUrl( String strUrl, String query ) throws IOException {
		URL url = new URL( strUrl );
		if( StringUtils.isEmpty( query ) ) {
			return url;
		}

		if( StringUtils.isEmpty( url.getQuery() ) ) {
			if( strUrl.endsWith( "?" ) ) {
				strUrl = strUrl + query;
			} else {
				strUrl = strUrl + "?" + query;
			}
		} else {
			if( strUrl.endsWith( "&" ) ) {
				strUrl = strUrl + query;
			} else {
				strUrl = strUrl + "&" + query;
			}
		}

		return new URL( strUrl );
	}

	public static String buildQuery( Map<String, String> params, String charset ) throws IOException {
		if( params == null || params.isEmpty() ) {
			return null;
		}

		StringBuilder query = new StringBuilder();
		Set<Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;

		for( Entry<String, String> entry : entries ) {
			String name = entry.getKey();
			String value = entry.getValue();
			// 忽略参数名或参数值为空的参数
			if( StringUtils.isNotEmpty( value ) && StringUtils.isNotEmpty( name ) ) {
				if( hasParam ) {
					query.append( "&" );
				} else {
					hasParam = true;
				}
				// UTF-8加密
				// query.append(name).append("=").append(URLEncoder.encode(value,
				// charset));
				// 没加密的
				query.append( name ).append( "=" ).append( value );
			}
		}

		return query.toString();
	}

	protected static String getResponseAsString( HttpURLConnection conn ) throws IOException {
		String charset = getResponseCharset( conn.getContentType() );
		InputStream es = conn.getErrorStream();
		if( es == null ) {
			return getStreamAsString( conn.getInputStream(), charset );
		} else {
			String msg = getStreamAsString( es, charset );
			if( StringUtils.isEmpty( msg ) ) {
				throw new IOException( conn.getResponseCode() + ":" + conn.getResponseMessage() );
			} else {
				throw new IOException( msg );
			}
		}
	}

	private static String getStreamAsString( InputStream stream, String charset ) throws IOException {
		try {
			BufferedReader reader = new BufferedReader( new InputStreamReader( stream, charset ) );
			StringWriter writer = new StringWriter();

			char[] chars = new char[256];
			int count = 0;
			while( ( count = reader.read( chars ) ) > 0 ) {
				writer.write( chars, 0, count );
			}

			return writer.toString();
		} finally {
			if( stream != null ) {
				stream.close();
			}
		}
	}

	private static String getResponseCharset( String ctype ) {
		String charset = DEFAULT_CHARSET;

		if( !StringUtils.isEmpty( ctype ) ) {
			String[] params = ctype.split( ";" );
			for( String param : params ) {
				param = param.trim();
				if( param.startsWith( "charset" ) ) {
					String[] pair = param.split( "=", 2 );
					if( pair.length == 2 ) {
						if( !StringUtils.isEmpty( pair[1] ) ) {
							charset = pair[1].trim();
						}
					}
					break;
				}
			}
		}

		return charset;
	}

	/**
	 * 使用默认的UTF-8字符集反编码请求参数值。
	 * 
	 * @param value 参数值
	 * @return 反编码后的参数值
	 */
	public static String decode( String value ) {
		return decode( value, DEFAULT_CHARSET );
	}

	/**
	 * 使用默认的UTF-8字符集编码请求参数值。
	 * 
	 * @param value 参数值
	 * @return 编码后的参数值
	 */
	public static String encode( String value ) {
		return encode( value, DEFAULT_CHARSET );
	}

	/**
	 * 使用指定的字符集反编码请求参数值。
	 * 
	 * @param value 参数值
	 * @param charset 字符集
	 * @return 反编码后的参数值
	 */
	public static String decode( String value, String charset ) {
		String result = null;
		if( !StringUtils.isEmpty( value ) ) {
			try {
				result = URLDecoder.decode( value, charset );
			} catch( IOException e ) {
				log.error( e.getMessage() );
				throw new RuntimeException( e );
			}
		}
		return result;
	}

	/**
	 * 使用指定的字符集编码请求参数值。
	 * 
	 * @param value 参数值
	 * @param charset 字符集
	 * @return 编码后的参数值
	 */
	public static String encode( String value, String charset ) {
		String result = null;
		if( !StringUtils.isEmpty( value ) ) {
			try {
				result = URLEncoder.encode( value, charset );
			} catch( IOException e ) {
				log.error( e.getMessage() );
				throw new RuntimeException( e );
			}
		}
		return result;
	}

	/**
	 * 从URL中提取所有的参数。
	 * 
	 * @param query URL地址
	 * @return 参数映射
	 */
	public static Map<String, String> splitUrlQuery( String query ) {
		Map<String, String> result = new HashMap<String, String>();

		String[] pairs = query.split( "&" );
		if( pairs != null && pairs.length > 0 ) {
			for( String pair : pairs ) {
				String[] param = pair.split( "=", 2 );
				if( param != null && param.length == 2 ) {
					result.put( param[0], param[1] );
				}
			}
		}

		return result;
	}

	// 去除数组中值为空的元素 类似 输入1,,3,, 输出1,3
	public static String[] emptyArrayNull( String str ) {

		String[] arr = str.split( "," );
		List<String> list = new ArrayList<String>();

		// 用StringBuffer来存放数组中的非空元素，用“;”分隔
		for( int i = 0; i < arr.length; i++ ) {
			if( !"".equals( arr[i] ) ) {

				list.add( arr[i] );
			}
		}
		String[] returnArr = new String[list.size()];
		for( int i = 0; i < list.size(); i++ ) {
			returnArr[i] = list.get( i );
		}
		return returnArr;

	}

}
