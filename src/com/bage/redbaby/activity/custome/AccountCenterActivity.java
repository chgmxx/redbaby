package com.bage.redbaby.activity.custome;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bage.redbaby.R;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.bean.Userinfo;
import com.bage.redbaby.net.BaseParser;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.parser.StringParser;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.bage.redbaby.util.myutil.ParamsMapsUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.util.LogUtils;

/**
 * 账号管理<br/>
 * 测试用,项目上线前还需接受用户数据进行处理
 * @author 程王勇
 */
public class AccountCenterActivity extends BaseActivity {

	private TextView head_more_text;
	private TextView head_quit_text;
	private TextView tv_account_username;
	private TextView tv_account_grad;
	private TextView tv_account_score;
	private LinearLayout my_order;
	private LinearLayout my_address;
	private LinearLayout my_pref;
	private LinearLayout my_collect;
	private TextView my_order_count;
	private TextView my_pref_count;
	private TextView my_collect_count;
	
	private RequestVo vo;
	private DataCallBack<Userinfo> dataCallBack;

	private static String username = "老大";
	private static String userID;

	@Override
	public void initView() {
		setContentView(R.layout.account_center_activity);
		closeProgressDialog();
		head_more_text = (TextView) findViewById(R.id.head_more_text);
		head_quit_text = (TextView) findViewById(R.id.head_quit_text);
		tv_account_username = (TextView) findViewById(R.id.tv_account_username);
		tv_account_grad = (TextView) findViewById(R.id.tv_account_grad);
		tv_account_score = (TextView) findViewById(R.id.tv_account_score);
		my_order = (LinearLayout) findViewById(R.id.my_order);
		my_address = (LinearLayout) findViewById(R.id.my_address);
		my_pref = (LinearLayout) findViewById(R.id.my_pref);
		my_collect = (LinearLayout) findViewById(R.id.my_collect);
		my_order_count = (TextView) findViewById(R.id.my_order_count);
		my_pref_count = (TextView) findViewById(R.id.my_pref_count);
		my_collect_count = (TextView) findViewById(R.id.my_collect_count);
	}

	/**
	 * 跳转到此页面时调用的方法 请不要用Intent
	 * 
	 * @param context
	 *            上下文
	 * @param username
	 *            用户名
	 * @param password
	 *            用户ID
	 */
	public static void actionStart(Context context, String username,
			String userID) {
		Intent intent = new Intent(context, AccountCenterActivity.class);
		AccountCenterActivity.username = username;
		AccountCenterActivity.userID = userID;
		context.startActivity(intent);
	}

	@Override
	public void initListener() {
		my_order.setOnClickListener(this);
		my_address.setOnClickListener(this);
		my_pref.setOnClickListener(this);
		my_collect.setOnClickListener(this);
		head_more_text.setOnClickListener(this);
		head_quit_text.setOnClickListener(this);
	}

	@Override
	public void initRequestVo() {
		//LogUtils.i(ConstantRedBoy.BASEURL + "/userinfo");
		vo = new RequestVo(ConstantRedBoy.BASEURL + "/userinfo", this,
				ParamsMapsUtils.getString(this), new BaseParser<Userinfo>() {

					@Override
					public Userinfo parseJSON(String str) {
						//LogUtils.i("获取到的JSON为" + str);
						closeProgressDialog();
						Gson gson = new Gson();
						Userinfo userInfo = gson.fromJson(str, Userinfo.class);
						return userInfo;
					}
				});
	}

	@Override
	public void initDataCallBack() {
		dataCallBack = new DataCallBack<Userinfo>() {

			@Override
			public void processData(Userinfo userInfo) {
				// 获取到json对象拉
				tv_account_username.setText("用户名 : " + username);
				tv_account_score.setText("会员等级 : " + userInfo.userinfo.bonus);
				tv_account_grad.setText("总积分 : " + userInfo.userinfo.level);
				my_order_count.setText("我的订单 ("+userInfo.userinfo.ordercount+")");
				my_collect_count.setText("收藏夹 ("+userInfo.userinfo.favoritescount+")");
				
				//TODO 这里接口文档中未提供数据 10为自己模拟的数据
				my_pref_count.setText("优惠券/礼品卡 ("+10+")");
			}
		};
	}

	@Override
	public void initData() {
		getDataForServer(vo, dataCallBack);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.my_order:
			// 说明用户点击了我的订单
			LogUtils.i("my_order");
			MyOrderActivity.actionStart(this, username, userID);
			break;
		case R.id.my_address:
			// 说明用户点击了地址管理
			LogUtils.i("my_address");
			MyAddressManager.actionStart(this, username, userID);
			break;
		case R.id.my_pref:
			// 说明用户点击了我的优惠券
			LogUtils.i("my_pref");
			break;
		case R.id.my_collect:
			// 说明用户点击了收藏夹
			LogUtils.i("my_collect");
			my_collection_activity.actionStart(this, username, userID);
			break;
		case R.id.head_more_text:
			// 说明用户点击了更多  返回上一级
			LogUtils.i("更多");
			finish();
			return;
		case R.id.head_quit_text:
			// 说明用户点击了退出登陆  注销账号
			LogUtils.i("返回");
			vo=new RequestVo(ConstantRedBoy.BASEURL+"/logout", this, ParamsMapsUtils.getString(this), new StringParser());
			getDataForServer(vo, new DataCallBack<String>() {

				@Override
				public void processData(String obj) {
					Toast.makeText(getApplicationContext(), "退出登陆成功", Toast.LENGTH_SHORT).show();
				}
			});
			finish();
			
		}
	}

}
