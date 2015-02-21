package com.bage.redbaby.activity.custome;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bage.redbaby.MyApp;
import com.bage.redbaby.R;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.base.BaseActivity.DataCallBack;
import com.bage.redbaby.bean.OrderList;
import com.bage.redbaby.bean.OrderList.OrderItem;
import com.bage.redbaby.net.BaseParser;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.bage.redbaby.util.myutil.ParamsMapsUtils;
import com.bage.redbaby.view.RefreshView;
import com.bage.redbaby.view.RefreshView.onRefreshingListener;
import com.google.gson.Gson;
import com.lidroid.xutils.util.LogUtils;

/**
 * 我的订单模块
 * 
 * @author 程王勇
 * 
 */
public class MyOrderActivity extends BaseActivity implements
		OnItemClickListener {

	private static String username;
	private static String userID;

	/**
	 * 跳转到此页面时调用的方法 请不要用Intent
	 * 
	 * @param context
	 *            上下文
	 * @param username
	 *            用户名
	 * @param userID
	 *            用户ID
	 */
	public static void actionStart(Context context, String username,
			String userID) {
		Intent intent = new Intent(context, MyOrderActivity.class);
		MyOrderActivity.username = username;
		MyOrderActivity.userID = userID;
		context.startActivity(intent);
	}

	private TextView head_back_text;
	private TextView my_order_all;
	private TextView my_order_notsend;
	private RefreshView my_order_list;
	private TextView my_order_month;
	private RequestVo vo;
	private DataCallBack<OrderList> dataCallBack;
	private MyAdapter adapter;
	private String type = "1";
	private int page = 1;
	private TextView my_order_null_text;

	@Override
	public void initView() {
		setContentView(R.layout.my_order_activity);
		head_back_text = (TextView) findViewById(R.id.head_back_text);
		my_order_month = (TextView) findViewById(R.id.my_order_month);
		my_order_all = (TextView) findViewById(R.id.my_order_all);
		my_order_notsend = (TextView) findViewById(R.id.my_order_notsend);
		my_order_list = (RefreshView) findViewById(R.id.my_order_list);
		//没有订单
		my_order_null_text=(TextView)findViewById(R.id.my_order_null_text);
	}

	@Override
	public void initListener() {
		head_back_text.setOnClickListener(this);
		my_order_month.setOnClickListener(this);
		my_order_all.setOnClickListener(this);
		my_order_notsend.setOnClickListener(this);
		my_order_list.setOnItemClickListener(this);
		// 设置下拉刷新和上拉加载更多的监听器
		my_order_list.setOnRefreshingListener(new onRefreshingListener() {

			@Override
			public void onPullDownRefresh() {
				LogUtils.i("下拉刷新");

				initData();
				my_order_list.onRefreshFinsh();
			}

			@Override
			public void onLoadMore() {
				LogUtils.i("上拉加载更多刷新");
				// 下拉刷新时 加载下一页
				page++;
				initRequestVo();
				initData();
				for (OrderItem item : orderList.orderlist) {
					itemList.add(item);
				}
				LogUtils.i("集合长度" + itemList.size());
				// 把头布局隐藏掉
				my_order_list.onRefreshFinsh();
			}
		});
	}

	@Override
	public void initRequestVo() {
		Map<String, String> map = ParamsMapsUtils.getString(this);
		map.put("type", type);
		map.put("page", String.valueOf(page));
		map.put("pageNum", "10");

		vo = new RequestVo(ConstantRedBoy.BASEURL + "/orderlist", this, map,
				new BaseParser<OrderList>() {

					@Override
					public OrderList parseJSON(String str) {
						LogUtils.i("str==" + str);
						try {
							Gson gson = new Gson();
							return gson.fromJson(str, OrderList.class);
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}

					}
				});
	}

	private static OrderList orderList;
	private boolean flag = true;
	private List<OrderItem> itemList;

	@Override
	public void initDataCallBack() {
		dataCallBack = new DataCallBack<OrderList>() {
			
			@Override
			public void processData(OrderList orderList) {
				if (orderList == null) {
					Toast.makeText(getApplicationContext(), "没有更多数据了",0).show();
					
					return;
				}
				
				closeProgressDialog();
				MyOrderActivity.orderList = orderList;

				if (flag) {
					// 保证方法只被调用一次
					itemList = new ArrayList<OrderItem>();
					for (OrderItem item : orderList.orderlist) {
						itemList.add(item);
					}
					LogUtils.i("集合长度" + itemList.size());
					flag = false;
				}
				
				if (itemList==null || itemList.isEmpty()) {
					//说明集合中无数据
					my_order_list.setVisibility(View.GONE);
					my_order_null_text.setVisibility(View.VISIBLE);
					return;
				}
				my_order_list.setVisibility(View.VISIBLE);
				my_order_null_text.setVisibility(View.GONE);

				if (adapter == null) {
					adapter = new MyAdapter();
					my_order_list.setAdapter(adapter);
				} else {
					adapter.notifyDataSetChanged();
					LogUtils.i("适配器刷新");
				}

			}
		};
	}

	@Override
	public void initData() {
		getDataForServer(vo, dataCallBack);
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return itemList.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.my_order_listitem, null);
				holder = new ViewHolder();
				holder.orderId_text = (TextView) convertView
						.findViewById(R.id.orderId_text);
				holder.orderPayment_text = (TextView) convertView
						.findViewById(R.id.orderPayment_text);
				holder.orderPrice_text = (TextView) convertView
						.findViewById(R.id.orderPrice_text);
				holder.orderState_text = (TextView) convertView
						.findViewById(R.id.orderState_text);
				holder.orderTime_text = (TextView) convertView
						.findViewById(R.id.orderTime_text);
				holder.textCancelOrder = (TextView) convertView
						.findViewById(R.id.textCancelOrder);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final OrderItem orderItem = itemList.get(position);
			holder.orderId_text.setText(orderItem.orderid);
			holder.orderPrice_text.setText(orderItem.price);
			// TODO 这里有问题 返回的是flag 1=>可删除可修改 2=>不可修改 3=>已完成 我们需要的却是是支付方式 大坑
			holder.orderPayment_text.setText(orderItem.flag);
			holder.orderState_text.setText(orderItem.status);
			holder.orderTime_text.setText(orderItem.time);
			//取消订单
			holder.textCancelOrder.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					LogUtils.i("取消订单");
					
					cacelOrder(orderItem.orderid);
				}
			});
			return convertView;
		}

		@Override
		public Object getItem(int position) {
			return orderList.orderlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
	}

	public class ViewHolder {
		TextView orderId_text;
		TextView orderPrice_text;
		TextView orderTime_text;
		TextView orderState_text;
		TextView orderPayment_text;
		TextView textCancelOrder;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.head_back_text:
			LogUtils.i("返回键被点击啦");
			finish();
			break;
		case R.id.my_order_month:
			LogUtils.i("当月订单");
			
			my_order_month.setBackgroundResource(R.drawable.segment_selected_1_bg);
			my_order_all.setBackgroundResource(R.drawable.segment_normal_2_bg);
			my_order_notsend.setBackgroundResource(R.drawable.segment_normal_3_bg);
			
			type="1";
			initRequestVo();
			initData();
			break;
		case R.id.my_order_all:
			LogUtils.i("所有订单");
			
			my_order_month.setBackgroundResource(R.drawable.segment_normal_1_bg);
			my_order_all.setBackgroundResource(R.drawable.segment_selected_2_bg);
			my_order_notsend.setBackgroundResource(R.drawable.segment_normal_3_bg);
			
			type="1";
			initRequestVo();
			initData();
			break;
		case R.id.my_order_notsend:
			LogUtils.i("已取消订单");
			
			my_order_month.setBackgroundResource(R.drawable.segment_normal_1_bg);
			my_order_all.setBackgroundResource(R.drawable.segment_normal_2_bg);
			my_order_notsend.setBackgroundResource(R.drawable.segment_selected_3_bg);
			
			type="3";
			initRequestVo();
			initData();
			break;

		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//跳转
		MyApp.myApp.object=this;
		MyOrderDetailActivity.actionStart(this, itemList.get(position).orderid,username,userID);
	}
	
	/**
	 * 取消订单
	 */
	public void cacelOrder(String orderId) {
		Map<String, String> map = ParamsMapsUtils.getString(this);
		map.put("orderId", orderId);
		vo=new RequestVo(ConstantRedBoy.BASEURL+"/ordercancel", this, map, new BaseParser<String>() {

			@Override
			public String parseJSON(String str) {
				//LogUtils.i("请求取消订单str=="+str);
				return str;
			}
		});
		getDataForServer(vo, new DataCallBack<String>() {

			@Override
			public void processData(String obj) {
				Toast.makeText(getApplicationContext(), "订单取消成功", Toast.LENGTH_SHORT).show();
				MyOrderActivity.actionStart(MyOrderActivity.this, username, userID);
				finish();
			}
		});
	}

}
