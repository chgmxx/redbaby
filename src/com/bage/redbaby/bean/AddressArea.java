package com.bage.redbaby.bean;

import java.util.List;

/**
 * 账户中心 省市区三级联动javaBean
 * @author 程王勇
 */
public class AddressArea {
	public String response;
	public String version;
	public List<Province> arealist;
	
	public class Province{
		public String id;
		public List<City> sub_area;
		public String value;
	}
	
	public class City{
		public String id;
		public List<Area> sub_area;
		public String value;
	}
	
	public class Area{
		public String id;
		public String value;
	}
}
