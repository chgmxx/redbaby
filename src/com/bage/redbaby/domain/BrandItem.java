package com.bage.redbaby.domain;
/**
 * 推荐品牌
 * @author LiuDong
 *
 */
public class BrandItem {
private int id;
private String name;
private String pic;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
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
@Override
public String toString() {
	return "BrandItem [id=" + id + ", name=" + name + ", pic=" + pic + "]";
}

}
