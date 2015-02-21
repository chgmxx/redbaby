package com.bage.redbaby.activity;

import android.os.Bundle;
import android.view.View;

import com.bage.redbaby.R;
import com.bage.redbaby.base.BaseActivity;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Intent intent = new Intent(getApplicationContext(), CartActivity.class);
//		startActivity(intent);
		finish();
	}

	@Override
	public void initView() {
		setContentView(R.layout.home_activity);
	}

	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void initListener() {
		
	}

	@Override
	public void initRequestVo() {
		
	}

	@Override
	public void initDataCallBack() {
		
	}

	@Override
	public void initData() {
		
	}


}
