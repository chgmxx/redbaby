package com.bage.redbaby.parser;

import java.util.List;

import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.bage.redbaby.domain.ProductDetailBean.Product;
import com.bage.redbaby.net.BaseParser;

public class ProductDetailParser extends BaseParser<List<Product>> {

	@Override
	public List<Product> parseJSON(String str) {
		try {
			JSONObject jsonObject = new JSONObject(str);
			String productString = jsonObject.getString("product");
			List<Product> products = JSON.parseArray(productString, Product.class);
			return products;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
