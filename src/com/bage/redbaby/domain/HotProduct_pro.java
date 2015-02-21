package com.bage.redbaby.domain;
/**
 * 热门单品
 * @author LiuDong
 *
 */
public class HotProduct_pro {
	private int id;
	private int marketprice;
	private String name;
	private String pic;
	private int price;
	@Override
	public String toString() {
		return "HotProduct_pro [id=" + id + ", marketprice=" + marketprice
				+ ", name=" + name + ", pic=" + pic + ", price=" + price + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMarketprice() {
		return marketprice;
	}
	public void setMarketprice(int marketprice) {
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public HotProduct_pro(int id, int marketprice, String name, String pic,
			int price) {
		super();
		this.id = id;
		this.marketprice = marketprice;
		this.name = name;
		this.pic = pic;
		this.price = price;
	}
	public HotProduct_pro() {
		super();
		// TODO Auto-generated constructor stub
	}
}
