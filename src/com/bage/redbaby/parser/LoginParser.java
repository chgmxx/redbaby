package com.bage.redbaby.parser;

import com.bage.redbaby.bean.Login_info;
import com.bage.redbaby.net.BaseParser;
import com.google.gson.Gson;

public class LoginParser extends BaseParser<Login_info> {

	@Override
	public Login_info parseJSON(String str) {
		Gson gson=new Gson();
		return gson.fromJson(str, Login_info.class);
	}

}
