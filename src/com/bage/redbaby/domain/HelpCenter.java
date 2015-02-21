package com.bage.redbaby.domain;

import java.util.List;


	public class  HelpCenter{
		private String id ;
		private String title;
		private String detail_url;
		public HelpCenter() {
			super();
			// TODO Auto-generated constructor stub
		}
		public HelpCenter(String id, String title, String detail_url) {
			super();
			this.id = id;
			this.title = title;
			this.detail_url = detail_url;
		}
		@Override
		public String toString() {
			return "HelpCenter [id=" + id + ", title=" + title
					+ ", detail_url=" + detail_url + "]";
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDetail_url() {
			return detail_url;
		}
		public void setDetail_url(String detail_url) {
			this.detail_url = detail_url;
		}
		
	}

