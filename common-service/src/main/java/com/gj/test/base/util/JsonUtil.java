package com.gj.test.base.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Json和对象转换操作类
 * 
 * @author lx.lin
 */
public class JsonUtil {
	private static Log log = LogFactory.getLog( JsonUtil.class );

	/**
	 * 从json字符串创建对象
	 * 
	 * @param json
	 * @param valueType
	 * @return
	 */
	public static <T> T json2Object( String json, Class<T> valueType ) {
		return _json2Object( json, valueType, false );
	}

	/**
	 * 从json字符串创建对象,但忽略未识别的属性
	 * 
	 * @param json
	 * @param valueType
	 * @return
	 */
	public static <T> T json2SimpleObject( String json, Class<T> valueType ) {
		return _json2Object( json, valueType, true );
	}

	/**
	 * 从json字符串创建对象
	 * 
	 * @param json
	 * @param valueType
	 * @return
	 */
	private static <T> T _json2Object( String json, Class<T> valueType, boolean disableUnknownProps ) {
		if( StringUtils.isEmpty( json ) ) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		if( disableUnknownProps ) {
			mapper.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES );
		}
		T t = null;
		try {
			t = mapper.readValue( json, valueType );
		} catch( IOException e ) {
			log.error( "====解析json字符串错误====" + e );
		}
		return t;
	}

	/**
	 * 从java对象解析json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String object2Json( Object obj ) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString( obj );
		} catch( JsonProcessingException e ) {
			log.error( "====解析json对象错误====" + e );
		}
		return json;
	}
}
