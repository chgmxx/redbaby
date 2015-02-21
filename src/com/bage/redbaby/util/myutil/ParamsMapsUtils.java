package com.bage.redbaby.util.myutil;

import java.util.Map;

import android.content.Context;

import com.bage.redbaby.util.BaseParamsMapUtil;

/**
 * 请求网络参数设置的工具类
 * 
 * @author Administrator
 * 
 */
public class ParamsMapsUtils extends BaseParamsMapUtil {
	public static Map<String, String> getString(Context context) {
		Map<String, String> paramsMap = getParamsMap(context);
		// TODO 这里可以增加参数进map
		return paramsMap;
	}

	/**
	 * 结算中心
	 * 
	 * @param context
	 * @param string
	 * @return
	 */
	public static Map<String, String> getPaymentParamsMap(Context context,
			String string) {
		Map<String, String> paramsMap = getParamsMap(context);
		// TODO 这里可以增加参数进map
		paramsMap.put("sku", string);
		return paramsMap;
	}

	/**
	 * 获取
	 * 
	 * @param cartActivity
	 * @param data
	 * @return
	 */
	public static Map<String, String> getCartString(Context context, String data) {
		Map<String, String> paramsMap = getParamsMap(context);
		paramsMap.put("sku", data);
		return paramsMap;
	}
	/**
	 * 帮助
	 * @param context
	 * @param string
	 * @return
	 */
	public static Map<String, String> getHelp(Context context,String string) {
		Map<String, String> paramsMap = getParamsMap(context);
		paramsMap.put("version", string);
		return paramsMap;
	}
	//版本升级
	public static Map<String, String> getVersion(Context context) {
		Map<String, String> paramsMap = getParamsMap(context);
		return paramsMap;
	}

	public static Map<String, String> getTopic(Context context, String string,
			String string2) {
		Map<String, String> paramsMap = getParamsMap(context);
		paramsMap.put("page", string);
		paramsMap.put("pageNum", string2);

		return paramsMap;
	}
}
