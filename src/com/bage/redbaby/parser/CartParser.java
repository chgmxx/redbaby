package com.bage.redbaby.parser;

import com.bage.redbaby.bean.Cart;
import com.bage.redbaby.net.BaseParser;
import com.bage.redbaby.util.myutil.GsonUtils;
import com.bage.redbaby.util.myutil.ImitateUtil;

public class CartParser extends BaseParser<Cart> {

	@Override
	public Cart parseJSON(String str) {
		/**
		 * 模拟数据
		 */
		str = ImitateUtil.convertToString();

		return GsonUtils.convertJsonToBean(str, Cart.class);
	}

}
