package com.bage.redbaby.bean;

import java.util.List;

public class Cart {

	public MCart cart;
	public String response;
	
	public class MCart{
		public AddUp cart_addup;//购物车总计
		public List<String> cart_prom;//享受促销信息
		public List<Product> productlist; // 商品列表
	}
	
	public class AddUp {
		public String total_count;//商品数量总计
		public String total_point;//商品积分总计
		public String total_price;//商品金额总计
	}

	public class Product {
		public String id;//商品id
		public String name;//商品名称
		public String pic;//商品图片URL
		public String price;//商品售价
		public String prodNum;//商品数量
		public String subtotal;//商品金额小计
		public String number;//商品库存数量
		public String uplimit;//商品购买数量上限
		public String color;
		public String size;
	}

}
