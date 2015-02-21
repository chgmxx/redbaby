package com.bage.redbaby.domain;
/**
 * 限时抢购
 * @author LiuDong
 *
 */
public class LimitBit_pro {
	private int id;
	private long lefttime;
	private int limitprice;
	private String name;
	private String pic;
	private int price;

	public LimitBit_pro() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LimitBit_pro(int id, long lefttime, int limitprice, String name,
			String pic, int price) {
		super();
		this.id = id;
		this.lefttime = lefttime;
		this.limitprice = limitprice;
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

	public long getLefttime() {
		return lefttime;
	}

	public void setLefttime(long lefttime) {
		this.lefttime = lefttime;
	}

	public int getLimitprice() {
		return limitprice;
	}

	public void setLimitprice(int limitprice) {
		this.limitprice = limitprice;
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
		return "LimitBit_pro [id=" + id + ", lefttime=" + lefttime
				+ ", limitprice=" + limitprice + ", name=" + name + ", pic="
				+ pic + ", price=" + price + "]";
	}

}
