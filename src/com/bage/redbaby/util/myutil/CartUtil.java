package com.bage.redbaby.util.myutil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.text.TextUtils;

/**
 * 购物车工具类，使购物条目在map和sp中互相转化<br>
 * sp中存储购物车条目信息，转化为map为了方便删除、添加、修改
 * 
 * @author baiiu
 * 
 */
public class CartUtil {
	/**
	 * 存储购物车
	 */
	private static Map<String, String> cartMap;

	/**
	 * 读取sp中数据，初始化cartMap
	 * 
	 * @param context
	 */
	public static void iniCartMap(Context context) {
		if (cartMap != null) {
			return;
		}

		// 初始化map
		cartMap = new HashMap<String, String>();

		// 获取sp中值
		String data = SharedPreferencesUtils.getStringData(context, "cartUrl",
				"");

		// 购物车为空
		if (TextUtils.isEmpty(data)) {
			return;
		}

		if (data.contains("|")) {
			// 多个购物条目
			String[] keyValues = data.split("\\|");

			for (int i = 0; i < keyValues.length; i++) {
				String[] kv = keyValues[i].split(":");

				cartMap.put(kv[0], kv[1]);
			}
		} else {
			// 只有一个购物条目
			String[] kv = data.split(":");
			cartMap.put(kv[0], kv[1]);
		}
	}

	/**
	 * 获取当前购物车内条目数目
	 * 
	 * @return string 购物车内数目
	 */
	public static String getCartItemCount() {
		if (cartMap == null) {
			return "";
		}

		return cartMap.keySet().size() + "";
	}

	/**
	 * 存储商品购物车条目到sp中
	 * 
	 * @param context
	 *            上下文对象
	 * @param id
	 *            商品id
	 * @param number
	 *            商品数量
	 */
	public static void saveCartItem(Context context, String id, String number) {
		if (cartMap == null) {
			return;
		}

		cartMap.put(id, number);

		SharedPreferencesUtils.saveStringData(context, "cartUrl",
				getCartItems(context));
	}

	/**
	 * 从sp中修改指定购物车条目
	 * 
	 * @param context
	 *            上下文对象
	 * @param id
	 *            商品id
	 * @param number
	 *            商品数量
	 */
	public static void updateCartItem(Context context, String id, String number) {
		if (cartMap == null) {
			return;
		}

		cartMap.put(id, number);

		SharedPreferencesUtils.saveStringData(context, "cartUrl",
				getCartItems(context));
	}

	/**
	 * 从sp中删除指定购物车条目
	 * 
	 * @param context
	 *            上下文对象
	 * @param id
	 *            商品id
	 * @param number
	 *            商品数量
	 */
	public static void removeCartItem(Context context, String id) {
		if (cartMap == null) {
			return;
		}

		cartMap.remove(id);

		SharedPreferencesUtils.saveStringData(context, "cartUrl",
				getCartItems(context));
	}

	/**
	 * 获取所有购物条目，以指定格式返回
	 * 
	 * @param context
	 * @return
	 */
	private static String getCartItems(Context context) {
		if (cartMap == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		Set<String> keySet = cartMap.keySet();
		for (String key : keySet) {
			sb.append(key + ":" + cartMap.get(key) + "|");// 1200001:3|1200004:2
		}

		return sb.toString().substring(0, sb.length() - 1);
	}

}
