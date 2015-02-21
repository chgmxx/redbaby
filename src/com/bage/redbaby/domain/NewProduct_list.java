package com.bage.redbaby.domain;

/**
 * 新品上架
 * 
 * @author LiuDong
 * 
 */
public class NewProduct_list {
	private int i;
	private int marketprice;
	private String name;
	private String pic;
	private int price;

	public NewProduct_list() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewProduct_list(int i, int marketprice, String name, String pic,
			int price) {
		super();
		this.i = i;
		this.marketprice = marketprice;
		this.name = name;
		this.pic = pic;
		this.price = price;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
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

	@Override
	public String toString() {
		return "NewProduct_list [i=" + i + ", marketprice=" + marketprice
				+ ", name=" + name + ", pic=" + pic + ", price=" + price + "]";
	}

}
