package com.bage.redbaby.bean;

import java.util.List;

/**
 * 账户中心  地址列表javaBean
 * @author 程王勇
 *
 */
public class AddressList {
	public String response;
	public List<AddressItem> addresslist;
	
	public class AddressItem{
		public String areadetail;
		public String areaid;
		public String cityid;
		public String fixedtel;
		public String id;
		public String name;
		public String phonenumber;
		public String provinceName;
		public String zipcode;
	}
}
