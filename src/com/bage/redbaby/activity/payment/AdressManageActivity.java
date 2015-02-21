package com.bage.redbaby.activity.payment;

import android.view.View;
import android.widget.TextView;

import com.bage.redbaby.R;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.util.ActivityUtils;

/**
 * 收货地址管理
 * 
 * @author WB
 * 
 */
public class AdressManageActivity extends BaseActivity {

	private TextView head_back_text;
	private TextView address_manager_add_text;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.head_back_text:
			finish();
			break;
		case R.id.address_manager_add_text:
			ActivityUtils.startActivityForResult(this, AddAddressActivity.class, "addaddress", 0);
			break;

		default:
			break;
		}
	}

	@Override
	public void initView() {
		setContentView(R.layout.address_manage_activity);
		head_back_text = (TextView) findViewById(R.id.head_back_text);
		address_manager_add_text = (TextView) findViewById(R.id.address_manager_add_text);
	}

	@Override
	public void initListener() {
		head_back_text.setOnClickListener(this);
		address_manager_add_text.setOnClickListener(this);
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
