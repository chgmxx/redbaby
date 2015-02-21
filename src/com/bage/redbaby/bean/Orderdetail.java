package com.bage.redbaby.bean;

import java.util.List;

/**
 * 账户中心  订单详情的javaBean
 * @author Administrator
 *
 */
public class Orderdetail {
	public String response;
	public AddressInfo address_info;
	public CheckoutAddup checkout_addup;
	public CheckoutProm checkout_prom;
	public DeliveryInfo delivery_info;
	public InvoiceInfo invoice_info;
	public OrderInfo order_info;
	public PaymentInfo payment_info;
	public List<Productlist> productlist;
	public class AddressInfo{
		public String address_area;
		public String address_detail;
		public String id;
		public String name;
	}
	public class CheckoutAddup{
		public String freight;
		public String prom_cut;
		public String total_count;
		public String total_point;
		public String total_price;
	}
	public class CheckoutProm{
		public String prom1;
		public String prom2;
	}
	public class DeliveryInfo{
		public String type; 
	}
	public class InvoiceInfo{
		public String content;
		public String id;
		public String title;
	}
	public class OrderInfo{
		public String flag;
		public String orderid;
		public String status;
		public String time;
	}
	public class PaymentInfo{
		public String type;
	}
	public class Productlist{
		public String color;
		public String id;
		public String name;
		public int number;
		public String pic;
		public boolean isgift;
		public String price;
		public String size;
		public String uplimit;
	}
}
