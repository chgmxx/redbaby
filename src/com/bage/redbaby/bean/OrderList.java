package com.bage.redbaby.bean;

import java.util.List;

/**
 * 账户中心的我的订单 javaBean
 * @author 程王勇
 *
 */
public class OrderList {
	public String response;
	public List<OrderItem> orderlist;
	
	public class OrderItem{
		public String flag;
		public String orderid;
		public String price;
		public String status;
		public String time;
	}
}
