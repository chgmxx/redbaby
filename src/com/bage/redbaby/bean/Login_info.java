package com.bage.redbaby.bean;

import java.util.List;

public class Login_info {

	public String response;
	public List<user> userinfo; 
	
	/**
	 * 从服务器返回的用户信息
	 */
	public class user{
		public String userId;
		public String usersession;
	}
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public List<user> getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(List<user> userinfo) {
		this.userinfo = userinfo;
	}
	
}
