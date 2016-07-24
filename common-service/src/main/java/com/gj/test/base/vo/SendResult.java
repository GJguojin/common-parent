package com.gj.test.base.vo;

import java.io.Serializable;

/**
 * 发送结果
 * 
 * @author tang
 */
public class SendResult implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 状态码 由具体服务提供方决定
	 */
	private Integer code;
	/**
	 * 状态描述
	 */
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode( Integer code ) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg( String msg ) {
		this.msg = msg;
	}

}
