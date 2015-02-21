package com.bage.redbaby.domain;

import java.util.List;
/**
 * 推荐品牌
 * @author LiuDong
 *
 */
public class BrandGroup {
private String key;
private List<BrandItem> value;
public String getKey() {
	return key;
}
public void setKey(String key) {
	this.key = key;
}
public List<BrandItem> getValue() {
	return value;
}
public void setValue(List<BrandItem> value) {
	this.value = value;
}
@Override
public String toString() {
	return "BrandGroup [key=" + key + ", value=" + value + "]";
}

}
