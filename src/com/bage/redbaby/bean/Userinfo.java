package com.bage.redbaby.bean;

import java.util.List;

/**
 * 账户中心 用户信息的javabean
 * @author 程王勇
 *
 */
public class Userinfo {
	public String response;
	public Info userinfo;
	
	public class Info{
		public int bonus;
		public String favoritescount;
		public String level;
		public String ordercount;
		public String userId;
		public String usersession;
	}
}
