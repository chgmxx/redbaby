package com.bage.redbaby.util.myutil;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
	private static SharedPreferences sp;
	private static final String CONFIG="config";
	/**
	 * 获取String值
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getStringData(Context context,String key,String defValue){
		if (sp==null) {
			sp=context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		return sp.getString(key, defValue);
	}
	/**
	 * 保存字符串值
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveStringData(Context context,String key,String value){
		if (sp==null) {
			sp=context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		sp.edit().putString(key, value).commit();
	}
	/**
	 * 获取Boolean值
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static boolean getbooleanData(Context context,String key,boolean defValue){
		if (sp==null) {
			sp=context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, defValue);
	}
	/**
	 * 保存布尔值
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveBooleanData(Context context,String key,boolean value){
		if (sp==null) {
			sp=context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}
	
}
