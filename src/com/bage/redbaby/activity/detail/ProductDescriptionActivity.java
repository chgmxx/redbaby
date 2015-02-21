package com.bage.redbaby.activity.detail;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bage.redbaby.R;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.util.LogUtils;
import com.bage.redbaby.util.myutil.CartUtil;

public class ProductDescriptionActivity extends BaseActivity {
	/**返回商品详情按钮*/
	private TextView titleBack;
	/**加入购物车按钮*/
	private TextView putIntoShopcar;
//	/**商品详情介绍*/
//	private WebView wv_product_description;
	/**商品详情介绍*/
	private TextView tv_description;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titleBack:
			Intent productDetailIntent = new Intent(this,ProductDetailActivity.class);
			startActivity(productDetailIntent);
			finish();
			break;
		case R.id.putIntoShopcar:
			LogUtils.toast(context, "加入购物车成功");
			//在商品详细介绍页点击加入购物车1次,添加1次商品
			CartUtil.saveCartItem(context,getIntent().getStringExtra("textProductIdCode"), "1");
			break;
		default:
			break;
		}
	}

	@Override
	public void initView() {
		setContentView(R.layout.product_description);
		titleBack = (TextView) findViewById(R.id.titleBack);
		putIntoShopcar = (TextView) findViewById(R.id.putIntoShopcar);
		tv_description = (TextView) findViewById(R.id.tv_description);
	}

	@Override
	public void initListener() {
		titleBack.setOnClickListener(this);
		putIntoShopcar.setOnClickListener(this);

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
		//wv_product_description.setContentDescription("这是商品详细介绍页面");
		tv_description.setText("这是商品详细介绍页面");
	}

}
