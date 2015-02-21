package com.bage.redbaby.parser;

import com.bage.redbaby.domain.VersionCode;
import com.bage.redbaby.net.BaseParser;
import com.google.gson.Gson;



public class VersionPaser extends BaseParser<VersionCode> {

	@Override
	public VersionCode parseJSON(String str) {
		Gson gson = new Gson();
		
		return gson.fromJson(str, VersionCode.class);
	}

}
