package com.bage.redbaby.parser;

import com.bage.redbaby.bean.SearchBean;
import com.bage.redbaby.net.BaseParser;
import com.google.gson.Gson;

public class searchParser extends BaseParser<SearchBean> {

		@Override
		public SearchBean parseJSON(String str) {
			Gson gson=new Gson();
			return gson.fromJson(str, SearchBean.class);
		}

}
