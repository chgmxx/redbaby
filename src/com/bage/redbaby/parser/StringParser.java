package com.bage.redbaby.parser;

import com.bage.redbaby.net.BaseParser;

public class StringParser extends BaseParser<String> {

	@Override
	public String parseJSON(String str) {
		//TODO  这里写上解析JSON
		return "这是解析后的数据=="+str;
	}

}
