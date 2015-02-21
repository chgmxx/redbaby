package com.bage.redbaby.activity.custome;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bage.redbaby.R;
import com.bage.redbaby.activity.adapter.CategoryAdapter;
import com.bage.redbaby.activity.adapter.MyListViewAdapter;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.bean.AddressList;
import com.bage.redbaby.bean.AddressList.AddressItem;
import com.bage.redbaby.net.BaseParser;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.bage.redbaby.util.myutil.ParamsMapsUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.util.LogUtils;

/**
 * 账户中心 地址管理
 * 
 * @author Administrator
 * 
 */
public class MyAddressManager extends BaseActivity {
	private static String username;
	private static String userID;

	/**
	 * 跳转到当前页面必须调用的方法 请不要使用intent
	 * 
	 * @param accountCenterActivity
	 *            当前activity
	 * @param username
	 *            用户名
	 * @param userID
	 *            用户ID
	 */
	public static void actionStart(Activity accountCenterActivity,
			String username, String userID) {
		Intent intent = new Intent(accountCenterActivity,
				MyAddressManager.class);
		MyAddressManager.username = username;
		MyAddressManager.userID = userID;
		accountCenterActivity.startActivity(intent);
	}

	private TextView head_back_text;
	private TextView address_manager_add_text;
	private TextView address_manager_null_text;
	private ListView address_manage_list;
	private RequestVo vo;
	private DataCallBack<AddressList> dataCallBack;

	@Override
	public void initView() {
		setContentView(R.layout.address_manage_activity);

		head_back_text = (TextView) findViewById(R.id.head_back_text);
		address_manager_add_text = (TextView) findViewById(R.id.address_manager_add_text);
		address_manager_null_text = (TextView) findViewById(R.id.address_manager_null_text);
		address_manage_list = (ListView) findViewById(R.id.address_manage_list);
	}

	@Override
	public void initListener() {
		head_back_text.setOnClickListener(this);
		address_manager_add_text.setOnClickListener(this);
	}

	@Override
	public void initRequestVo() {
		vo = new RequestVo(ConstantRedBoy.BASEURL + "/addresslist", this,
				ParamsMapsUtils.getString(this), new BaseParser<AddressList>() {

					@Override
					public AddressList parseJSON(String str) {
						Gson gson = new Gson();
						return gson.fromJson(str, AddressList.class);
					}
				});
	}

	private MyAdapter adapter;

	@Override
	public void initDataCallBack() {
		dataCallBack = new DataCallBack<AddressList>() {

			@Override
			public void processData(AddressList addressList) {
				if (addressList == null || addressList.addresslist.isEmpty()) {
					address_manager_null_text.setVisibility(View.VISIBLE);
					address_manage_list.setVisibility(View.GONE);
					return;
				}
				address_manager_null_text.setVisibility(View.GONE);
				address_manage_list.setVisibility(View.VISIBLE);

				if (adapter == null) {
					adapter = new MyAdapter(addressList.addresslist,
							MyAddressManager.this);
					address_manage_list.setAdapter(adapter);
				} else {
					adapter.notifyDataSetChanged();
				}

			}
		};
	}

	@Override
	public void initData() {
		getDataForServer(vo, dataCallBack);
	}

	class MyAdapter extends MyListViewAdapter<AddressItem> {

		public MyAdapter(List<AddressItem> list, Context context) {
			super(list, context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.address_manage_listitem, null);
				holder = new ViewHolder();
				holder.address_listitem_receiver_text = (TextView) convertView
						.findViewById(R.id.address_listitem_receiver_text);
				holder.address_arror_img = (ImageView) convertView
						.findViewById(R.id.address_arror_img);
				holder.address_listitem_phone_text = (TextView) convertView
						.findViewById(R.id.address_listitem_phone_text);
				holder.address_listitem_ads_text = (TextView) convertView
						.findViewById(R.id.address_listitem_ads_text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			AddressItem addressItem = list.get(position);
			holder.address_listitem_receiver_text.setText(addressItem.name);
			// TODO 这里还没有处理默认收件人地址的问题
			if (position==0) {
				holder.address_arror_img.setVisibility(View.VISIBLE);
			}
			holder.address_listitem_phone_text.setText(addressItem.phonenumber);
			holder.address_listitem_ads_text.setText(addressItem.areadetail);
			return convertView;
		}
	}

	class ViewHolder {
		TextView address_listitem_receiver_text;
		ImageView address_arror_img;
		TextView address_listitem_phone_text;
		TextView address_listitem_ads_text;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.head_back_text:
			finish();
			return;
		case R.id.address_manager_add_text:
			LogUtils.i("添加地址列表被点击");
			//跳转
			MyAddressAddActivity.actionStart(MyAddressManager.this,
					username,userID);
			break;
		}
	}

}
