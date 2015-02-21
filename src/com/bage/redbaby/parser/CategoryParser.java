package com.bage.redbaby.parser;



import com.bage.redbaby.bean.Category;
import com.bage.redbaby.net.BaseParser;
import com.google.gson.Gson;

public class CategoryParser extends BaseParser<Category>{

	@Override
	public Category parseJSON(String str) {
//		try {
//			JSONObject jsonObject = new JSONObject(str);
//			String categoryString = jsonObject.getString("category");
//			List<Category> categorys = JSON.parseArray(categoryString, Category.class);
//			return categorys;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
		Gson gson = new Gson();
		Category category = gson.fromJson(str, Category.class);
		return category;
	}

}
