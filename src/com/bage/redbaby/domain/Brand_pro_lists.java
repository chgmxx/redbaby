package com.bage.redbaby.domain;
/**
 * 品牌商品列表
 * @author LiuDong
 *
 */
public class Brand_pro_lists {
	private int id;
	private double marketprice;
	private String name;
	private String pic;
	private double price;
	public Brand_pro_lists() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Brand_pro_lists(int id, double marketprice, String name, String pic,
			double price) {
		super();
		this.id = id;
		this.marketprice = marketprice;
		this.name = name;
		this.pic = pic;
		this.price = price;
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
	@Override
	public String toString() {
		return "Brand_pro_lists [id=" + id + ", marketprice=" + marketprice
				+ ", name=" + name + ", pic=" + pic + ", price=" + price + "]";
	}
	
}
