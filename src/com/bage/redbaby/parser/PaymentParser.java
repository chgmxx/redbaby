package com.bage.redbaby.parser;

import com.bage.redbaby.bean.Checkout;
import com.bage.redbaby.net.BaseParser;
import com.bage.redbaby.util.myutil.GsonUtils;

/**
 * 结算中心解析器
 * 
 * @author WB
 * 
 */
public class PaymentParser extends BaseParser<Checkout> {

	@Override
	public Checkout parseJSON(String str) {
		str = "{" + "'address_info': {" + "'address_area': '北京市海淀区',"
				+ "'address_detail': '闵庄路3号'," + "'id': 1001,"
				+ "'name': '肖文'}" + "}";
		return GsonUtils.convertJsonToBean(str, Checkout.class);
	}

}
