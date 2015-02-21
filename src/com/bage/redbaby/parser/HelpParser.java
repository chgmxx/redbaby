package com.bage.redbaby.parser;

import java.util.List;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.bage.redbaby.domain.HelpCenter;
import com.bage.redbaby.net.BaseParser;

public class HelpParser extends BaseParser<List<HelpCenter>> {
	@Override
	public List<HelpCenter> parseJSON(String str) {
		try {
			JSONObject jsonObject = new JSONObject(str);
			String helpString = jsonObject.getString("help");
			List<HelpCenter> helps = JSON.parseArray(helpString, HelpCenter.class);
			return helps;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

}
