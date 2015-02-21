package com.bage.redbaby.domain;

public class HomeBanner {
	public String id;
	public String pic;
	public String title;
	
	@Override
	public String toString() {
		return "HomeBanner [id=" + id + ", pic=" + pic + ", title=" + title
				+ "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	

}
