package com.bage.redbaby.activity.payment;

import android.view.View;
import android.widget.TextView;

import com.bage.redbaby.R;
import com.bage.redbaby.base.BaseActivity;

/**
 * 支付方式
 * 
 * @author WB
 * 
 */
public class PayTypeActivity extends BaseActivity {

	private TextView head_back_text;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.head_back_text:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void initView() {
		setContentView(R.layout.paytype_activity);
		head_back_text = (TextView)findViewById(R.id.head_back_text);
	}

	@Override
	public void initListener() {
		head_back_text.setOnClickListener(this);
	}

	@Override
	public void initRequestVo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initDataCallBack() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

}
