package com.bage.redbaby.domain;

import java.util.List;

/**
 * 限时抢购
 * @author LiuDong
 *
 */
public class LimitBuy {
	private String list_count;
	private String response;
	List<LimitBit_pro> productlist;
	public LimitBuy() {
		super();
	}
	public LimitBuy(String list_count, String response,
			List<LimitBit_pro> productlist) {
		super();
		this.list_count = list_count;
		this.response = response;
		productlist = productlist;
	}
	public String getList_count() {
		return list_count;
	}
	public void setList_count(String list_count) {
		this.list_count = list_count;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public List<LimitBit_pro> getproductlist() {
		return productlist;
	}
	public void setproductlist(List<LimitBit_pro> productlist) {
		productlist = productlist;
	}
	@Override
	public String toString() {
		return "LimitBuy [list_count=" + list_count + ", response=" + response
				+ ", productlist=" + productlist + "]";
	}
	
	
}
