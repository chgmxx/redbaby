package com.bage.redbaby.parser;

import org.json.JSONException;
import org.json.JSONObject;

import com.bage.redbaby.bean.ProductList;
import com.bage.redbaby.net.BaseParser;
import com.google.gson.Gson;

public class ProductListParser extends BaseParser<ProductList> {

	@Override
	public ProductList parseJSON(String str) {
		Gson gson = new Gson();
		
		System.out.println(str);
		
		try {
			new JSONObject(str);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("hahahh");
			e.printStackTrace();
		}
		
		ProductList productList = gson.fromJson(str, ProductList.class);

		return productList;
	}

}
