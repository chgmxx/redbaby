package com.bage.redbaby.activity.custome;


import java.util.Map;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bage.redbaby.MyApp;
import com.bage.redbaby.R;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.bean.OrderLogistics;
import com.bage.redbaby.net.BaseParser;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.bage.redbaby.util.myutil.ParamsMapsUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.util.LogUtils;
/**
 * 物流信息
 * @author 程王勇
 */
public class MyOrderLogisticsActivity extends BaseActivity {
	private static String username;
	private static String userID;
	private static String orderId;
	private TextView head_back_text;
	private RequestVo vo;
	private DataCallBack<OrderLogistics> dataCallBack;
	private TextView tv_logistice_method;
	private TextView tv_logistice_number;
	private TextView tv_logistice_company;
	private TextView tv_logistice_movenumber;
	private TextView tv_logistice_address;
	
	public static void actionStart(Context context, String username,
			String userID, String orderId) {
		Intent intent = new Intent(context, MyOrderLogisticsActivity.class);
		MyOrderLogisticsActivity.username = username;
		MyOrderLogisticsActivity.userID = userID;
		MyOrderLogisticsActivity.orderId = orderId;
		context.startActivity(intent);
	}
	
	@Override
	public void initView() {
		setContentView(R.layout.ordr_logistics_activity);
		head_back_text=(TextView)findViewById(R.id.head_back_text);
		//发货方式
		tv_logistice_method=(TextView)findViewById(R.id.tv_logistice_method);
		//物流编号
		tv_logistice_number=(TextView)findViewById(R.id.tv_logistice_number);
		//物流公司
		tv_logistice_company=(TextView)findViewById(R.id.tv_logistice_company);
		//运单号码
		tv_logistice_movenumber=(TextView)findViewById(R.id.tv_logistice_movenumber);
		//物流跟踪
		tv_logistice_address=(TextView)findViewById(R.id.tv_logistice_address);
	}

	@Override
	public void initListener() {
		head_back_text.setOnClickListener(this);
	}

	@Override
	public void initRequestVo() {
		Map<String, String> map = ParamsMapsUtils.getString(context);
		map.put("orderId", orderId);
		vo=new RequestVo(ConstantRedBoy.BASEURL+"/logistics", this, map, new BaseParser<OrderLogistics>() {

			@Override
			public OrderLogistics parseJSON(String str) {
				LogUtils.i("str==="+str);
				Gson gson=new Gson();
				return gson.fromJson(str, OrderLogistics.class);
			}
		});
	}

	@Override
	public void initDataCallBack() {
		dataCallBack=new DataCallBack<OrderLogistics>() {

			@Override
			public void processData(OrderLogistics order) {
				if (order==null) {
					Toast.makeText(getApplicationContext(), "数据解析失败,请检查网络", Toast.LENGTH_SHORT).show();
					return ;
				}
				tv_logistice_method.setText(order.logistics.expressway);
				//TODO 这里服务器没有数据 假数据
				tv_logistice_number.setText(order.logistics.logisticsid);
				tv_logistice_company.setText(order.logistics.logisticscorp);
				tv_logistice_movenumber.setText(order.logistics.logisticsid);
				tv_logistice_address.setText("以下信息由物流公司提供,如有疑问查询"+order.logistics.logisticscorp+"快递官方网站");
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
		case R.id.head_back_text:
			LogUtils.i("点击了返回键");
			finish();
			break;
		}
	}
	
}
