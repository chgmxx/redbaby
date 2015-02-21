package com.bage.redbaby.util.myutil;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.lidroid.xutils.util.LogUtils;

public class GsonUtils {

	private static Gson gson;

	/**
	 * 解析指定的json字符串为javabean对象
	 * 
	 * @param json
	 * @param classOfT
	 * @return
	 * @throws JSONException
	 */
	public static <T> T convertJsonToBean(String json, Class<T> classOfT) {
		try {
			// 检查json语法是否正确
			new JSONObject(json);
			
			if (gson == null) {
				gson = new Gson();
			}
			return gson.fromJson(json, classOfT);
			
		} catch (JSONException e) {
			e.printStackTrace();
			LogUtils.e("json语句格式不正确");
		}
		
		return null;
	}

}
