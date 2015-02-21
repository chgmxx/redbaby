package com.bage.redbaby.bean;

import java.util.List;

public class SearchBean {
	public String list_count;
	public List<Product> productlist;
	public String response;
	
	public class Product{
		public String comment_count;
		public String id;
		public String marketprice;
		public String name;
		public String pic;
		public String price;
		
	}
	
	
	@Override
	public String toString() {
		return "SearchBean [list_count=" + list_count + ", productlist="
				+ productlist + ", response=" + response + "]";
	}
	
	
}
