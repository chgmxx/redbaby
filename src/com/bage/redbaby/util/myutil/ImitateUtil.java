package com.bage.redbaby.util.myutil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.bage.redbaby.MyApp;

/**
 * 模拟数据工具类
 * 
 * @author baiiu
 * 
 */
public class ImitateUtil {

	public static String convertToString() {

		try {
			InputStream is = MyApp.myApp.context.getAssets().open("cart.json");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line.trim());
			}

			br.close();
			
			String data = sb.toString();
			sb = null;
			return data;

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("抛异常啦");
		}

		return null;
	}

}
