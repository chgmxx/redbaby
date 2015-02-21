package com.bage.redbaby.domain;

import java.util.List;
/**
 * 商品详情业务bean
 * @author admin
 *
 */
public class ProductDetailBean {
	public Product product;
	public String response;
	
	public class Product{
		public String[] bigPic;//大图
		public String[] pic;//商品图片
		public String[] productProm;//促销信息
		
		public String available; //是否可售
		public String buyLimit;//单品购买上限
		public String comment_count;//
		public String id;//商品编号
		public String inventory_area;
		public String leftTime; // 限时抢购剩余时间
		public String limitPrice;
		public String marketprice;//市场价格
		public String name;//商品名称
		public String price;  //会员价
		public String score; //评分
	}
}
