package com.bage.redbaby.bean;

public class Address_info {
	/**
	 * 地址簿ID
	 */
	private String id;
	/**
	 * 收货人姓名
	 */
	private String name;
	/**
	 * 国家三级地址
	 */
	private String address_area;
	/**
	 * 地址详情
	 */
	private String address_detail;
	public Address_info(String id, String name, String address_area,
			String address_detail) {
		super();
		this.id = id;
		this.name = name;
		this.address_area = address_area;
		this.address_detail = address_detail;
	}
	public Address_info() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress_area() {
		return address_area;
	}
	public void setAddress_area(String address_area) {
		this.address_area = address_area;
	}
	public String getAddress_detail() {
		return address_detail;
	}
	public void setAddress_detail(String address_detail) {
		this.address_detail = address_detail;
	}
	@Override
	public String toString() {
		return "Address_Info [id=" + id + ", name=" + name + ", address_area="
				+ address_area + ", address_detail=" + address_detail + "]";
	}
	
}
