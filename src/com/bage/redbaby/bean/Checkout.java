package com.bage.redbaby.bean;

import java.util.List;

public class Checkout {
	public Address_info address_info;
	public Checkout_addup checkout_addup;
	public List<String> checkout_prom;
	public Delivery_info delivery_info;
	public Invoice_info invoice_info;
	public Payment_info payment_info;
	public List<String> productlist;
	public String response;
}

class Payment_info{
	public String type;
}

class Invoice_info{
	public String content ;
	public String id;
	public String title;
}
class Delivery_info {
	public String type;
}

class Checkout_addup {
	public String freight;
	public String prom_cut;
	public String total_count;
	public String total_point;
	public String total_price;
}