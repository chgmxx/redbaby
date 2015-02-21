package com.bage.redbaby.domain;

import java.util.List;

/**
 * 专题商品列表
 * @author LiuDong
 *
 */
public class Topic_pro_list {
	private int list_count;
	private String response;
	@Override
	public String toString() {
		return "Topic_pro_list [list_count=" + list_count + ", response="
				+ response + ", productlist=" + productlist + "]";
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
	public List<Topic_pro_lists> getProductlist() {
		return productlist;
	}
	public void setProductlist(List<Topic_pro_lists> productlist) {
		this.productlist = productlist;
	}
	public Topic_pro_list(int list_count, String response,
			List<Topic_pro_lists> productlist) {
		super();
		this.list_count = list_count;
		this.response = response;
		this.productlist = productlist;
	}
	public Topic_pro_list() {
		super();
		// TODO Auto-generated constructor stub
	}
	private List<Topic_pro_lists> productlist;
}
