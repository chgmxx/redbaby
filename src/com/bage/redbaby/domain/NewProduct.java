package com.bage.redbaby.domain;

import java.util.List;
/**
 * 新品上架
 * 
 * @author LiuDong
 * 
 */
public class NewProduct {
	private int list_count;
	private List<NewProduct_list> productlist;
	private String response;
	public NewProduct(int list_count, List<NewProduct_list> productlist,
			String response) {
		super();
		this.list_count = list_count;
		this.productlist = productlist;
		this.response = response;
	}
	public int getList_count() {
		return list_count;
	}
	public void setList_count(int list_count) {
		this.list_count = list_count;
	}
	@Override
	public String toString() {
		return "NewProduct [list_count=" + list_count + ", response="
				+ response + "]";
	}
	public List<NewProduct_list> getProductlist() {
		return productlist;
	}
	public void setProductlist(List<NewProduct_list> productlist) {
		this.productlist = productlist;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public NewProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
