package com.bage.redbaby.domain;

import java.util.List;
/**
 * 推荐品牌
 * @author LiuDong
 *
 */
public class Brand {
	private List<BrandGroup> brand;
	private String response;

	public List<BrandGroup> getBrand() {
		return brand;
	}

	public void setBrand(List<BrandGroup> brand) {
		this.brand = brand;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "Brand [brand=" + brand + ", response=" + response + "]";
	}

}
