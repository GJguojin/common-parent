package com.gj.test.user.beans;

import java.io.Serializable;
import java.util.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User implements Serializable{
	private Long id;//  id
	private String userName;//  userName
	private java.util.Date userBirth;//  userBirth
	private String userEmail;//  userEmail

	public User(){
	}

	public User(Long id){
		this.id = id;
	}

		/**
		 * id
		 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * id
	 */
	public Long getId() {
		return this.id;
	}
		/**
		 * userName
		 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * userName
	 */
	public String getUserName() {
		return this.userName;
	}
		/**
		 * userBirth
		 */
	public void setUserBirth(java.util.Date userBirth) {
		this.userBirth = userBirth;
	}

	/**
	 * userBirth
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public java.util.Date getUserBirth() {
		return this.userBirth;
	}
		/**
		 * userEmail
		 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	/**
	 * userEmail
	 */
	public String getUserEmail() {
		return this.userEmail;
	}

}

