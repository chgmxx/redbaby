package com.bage.redbaby.bean;

import java.util.List;

public class ProductList {
	public List<Category_productlist> category_productlist;
	public List<List_filter> list_filter;
	public List<Productlist> productlist;
	public String response;
	
	public class Category_productlist{
		public String key;
		public List<Value> value;
	}
	public class Value {
		public String id;
		public String name;
	}
	public class List_filter{
		public String key;
		public List<Value> value; 
	}
	public class Productlist{
		public int comment_count;
		public int id;
		public int marketprice;
		public String name;
		public String pic;
		public int price;
	}
}
