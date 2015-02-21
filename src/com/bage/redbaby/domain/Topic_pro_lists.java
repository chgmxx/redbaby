package com.bage.redbaby.domain;
/**
 * 专题商品列表
 * @author LiuDong
 *
 */
public class Topic_pro_lists {
	private int comment_count;
	private int id;
	private double marketprice;
	private String name;
	private String pic;
	private double price;
	public Topic_pro_lists(int comment_count, int id, double marketprice,
			String name, String pic, double price) {
		super();
		this.comment_count = comment_count;
		this.id = id;
		this.marketprice = marketprice;
		this.name = name;
		this.pic = pic;
		this.price = price;
	}
	@Override
	public String toString() {
		return "Topic_pro_lists [comment_count=" + comment_count + ", id=" + id
				+ ", marketprice=" + marketprice + ", name=" + name + ", pic="
				+ pic + ", price=" + price + "]";
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getMarketprice() {
		return marketprice;
	}
	public void setMarketprice(double marketprice) {
		this.marketprice = marketprice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Topic_pro_lists() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
