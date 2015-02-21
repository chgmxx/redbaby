package com.bage.redbaby.domain;

import java.util.List;
/**
 * 品牌商品列表
 * @author LiuDong
 *
 */
public class Brand_pro_list {
	private int list_count;
	private String response;
	private List<Brand_pro_lists> productlist;
	public Brand_pro_list() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Brand_pro_list(int list_count, String response,
			List<Brand_pro_lists> productlist) {
		super();
		this.list_count = list_count;
		this.response = response;
		this.productlist = productlist;
	}
	public int getList_count() {
		return list_count;
	}
	public void setList_count(int list_count) {
		this.list_count = list_count;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public List<Brand_pro_lists> getProductlist() {
		return productlist;
	}
	public void setProductlist(List<Brand_pro_lists> productlist) {
		this.productlist = productlist;
	}
	@Override
	public String toString() {
		return "Brand_pro_list [list_count=" + list_count + ", response="
				+ response + ", productlist=" + productlist + "]";
	}
	
}
