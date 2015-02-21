package com.bage.redbaby.bean;

import java.util.List;

/**
 * 订单物流的javaBean
 * @author 程王勇
 *
 */
public class OrderLogistics {
	public String response;
	public Logistics logistics;
	
	public class Logistics{
		public String expressway;
		public String logisticscorp;
		public String logisticsid;
		public List<String> list;
	}
}
