package com.bage.redbaby.bean;

import java.util.List;

/**
 * 解析从服务器获取的Json
 * 
 * @author mine
 * 
 */
public class Category {

	 public List<CategoryItem> category;
	 public String response;
	 public String version;
	
	 public class CategoryItem {
	 public int id;
	 public boolean isleafnode;
	 public String name;
	 public int parent_id;
	 public String pic;
	 public String tag;
	 }

//	private int id;
//	private boolean isleafnode;
//	private String name;
//	private int parent_id;
//	private String pic;
//	private String tag;
//
//	public Category() {
//	}
//
//	public Category(int id, boolean isleafnode, String name, int parent_id,
//			String pic, String tag) {
//		this.id = id;
//		this.isleafnode = isleafnode;
//		this.name = name;
//		this.parent_id = parent_id;
//		this.pic = pic;
//		this.tag = tag;
//	}
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public boolean isIsleafnode() {
//		return isleafnode;
//	}
//
//	public void setIsleafnode(boolean isleafnode) {
//		this.isleafnode = isleafnode;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public int getParent_id() {
//		return parent_id;
//	}
//
//	public void setParent_id(int parent_id) {
//		this.parent_id = parent_id;
//	}
//
//	public String getPic() {
//		return pic;
//	}
//
//	public void setPic(String pic) {
//		this.pic = pic;
//	}
//
//	public String getTag() {
//		return tag;
//	}
//
//	public void setTag(String tag) {
//		this.tag = tag;
//	}

}
