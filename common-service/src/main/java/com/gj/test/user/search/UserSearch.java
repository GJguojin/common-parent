package com.gj.test.user.search;

import org.apache.ibatis.type.Alias;

import com.gj.test.base.search.BaseSearch;
@Alias("UserSearch")
public class UserSearch extends BaseSearch{
	private Long id; //   id
	private String userName; //   userName
	private java.util.Date userBirth; //   userBirth
	private String userEmail; //   userEmail

	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
	public void setUserName(String value) {
		this.userName = value;
	}
	
	public String getUserName() {
		return this.userName;
	}

	public void setUserBirth(java.util.Date value) {
		this.userBirth = value;
	}
	
	public java.util.Date getUserBirth() {
		return this.userBirth;
	}
	public void setUserEmail(String value) {
		this.userEmail = value;
	}
	
	public String getUserEmail() {
		return this.userEmail;
	}

}

