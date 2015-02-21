package com.bage.redbaby.bean;

import java.util.List;


/**
 * 我的收藏
 * @author 程王勇
 *
 */
public class Favorites {
	public String list_count;
	public List<Product> productlist;
	public String response;
	
	public class Product{
		public String id;
		public String marketprice;
		public String name;
		public String pic;
		public String price;
	}
}
