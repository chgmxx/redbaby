package com.bage.redbaby;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bage.redbaby.net.ThreadPoolManager;

public class MyApp extends Application {
	public ThreadPoolManager threadPoolManager;
	public static MyApp myApp;
	public Context context;
	public SharedPreferences sp;
	public Editor edit;
	public Object object;
	
	@Override
	public void onCreate() {
		super.onCreate();
		myApp = this;
		context = getApplicationContext();
		sp=context.getSharedPreferences("config",  MODE_PRIVATE);
		edit=sp.edit();
		threadPoolManager = ThreadPoolManager.getInstance();
	}
	@Override
	public void onTerminate() {
		super.onTerminate();
		myApp = null;
	}

}
