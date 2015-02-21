package com.bage.redbaby.parser;

import java.util.List;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.bage.redbaby.domain.HomeBanner;
import com.bage.redbaby.net.BaseParser;

public class HomeParser extends BaseParser<List<HomeBanner>> {

	@Override
	public List<HomeBanner> parseJSON(String str) {
		try {
			JSONObject jsonObject = new JSONObject(str);
			String home_bannerString = jsonObject.getString("home_banner");
			List<HomeBanner> homeBanners = JSON.parseArray(home_bannerString,
					HomeBanner.class);
			return homeBanners;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}
