package com.bage.redbaby.domain;

import java.util.List;
/**
 * 热门单品
 * @author LiuDong
 *
 */
public class HotProduct {
	private int list_count;
	private String response;
	private List<HotProduct_pro> produclist;
	public HotProduct() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HotProduct(int list_count, String response,
			List<HotProduct_pro> produclist) {
		super();
		this.list_count = list_count;
		this.response = response;
		this.produclist = produclist;
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
	public List<HotProduct_pro> getProduclist() {
		return produclist;
	}
	public void setProduclist(List<HotProduct_pro> produclist) {
		this.produclist = produclist;
	}
	@Override
	public String toString() {
		return "HotProduct [list_count=" + list_count + ", response="
				+ response + ", produclist=" + produclist + "]";
	}
	
}
