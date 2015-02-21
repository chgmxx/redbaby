package com.bage.redbaby.activity.custome;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.bage.redbaby.R;
import com.bage.redbaby.activity.adapter.MyListViewAdapter;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.bean.Favorites;
import com.bage.redbaby.bean.Favorites.Product;
import com.bage.redbaby.net.BaseParser;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.bage.redbaby.util.myutil.ParamsMapsUtils;
import com.bage.redbaby.view.RefreshView;
import com.bage.redbaby.view.RefreshView.onRefreshingListener;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.util.LogUtils;

/**
 * 我的收藏
 * 
 * @author Administrator
 * 
 */
public class my_collection_activity extends BaseActivity implements
		OnItemClickListener {

	private static String username;
	private static String userID;
	private TextView head_quite_text;
	private TextView head_clear_text;
	private RefreshView iv_collection;
	private int page = 1;
	private int pageNum = 10;
	private RequestVo vo;
	private DataCallBack<Favorites> dataCallBack;

	public static void actionStart(Context context, String username,
			String userID) {
		Intent intent = new Intent(context, my_collection_activity.class);
		my_collection_activity.username = username;
		my_collection_activity.userID = userID;
		context.startActivity(intent);
	}

	@Override
	public void initView() {
		setContentView(R.layout.my_collection_activity);
		head_quite_text = (TextView) findViewById(R.id.head_quite_text);
		head_clear_text = (TextView) findViewById(R.id.head_clear_text);
		iv_collection = (RefreshView) findViewById(R.id.iv_collection);
	}

	@Override
	public void initListener() {
		head_clear_text.setOnClickListener(this);
		head_quite_text.setOnClickListener(this);
		iv_collection.setOnItemClickListener(this);
		iv_collection.setOnRefreshingListener(new onRefreshingListener() {

			@Override
			public void onPullDownRefresh() {
				initData();
			}

			@Override
			public void onLoadMore() {
				page++;
				initData();
			}
		});
	}

	@Override
	public void initRequestVo() {
		Map<String, String> map = ParamsMapsUtils.getString(this);
		map.put("page", String.valueOf(page));
		map.put("pageNum", String.valueOf(pageNum));
		vo = new RequestVo(ConstantRedBoy.BASEURL + "/favorites", this, map,
				new BaseParser<Favorites>() {

					@Override
					public Favorites parseJSON(String str) {
						LogUtils.i("我的收藏str==" + str);
						Gson gson = new Gson();
						return gson.fromJson(str, Favorites.class);
					}
				});
	}

	private MyAdapter adapter;

	@Override
	public void initDataCallBack() {
		dataCallBack = new DataCallBack<Favorites>() {

			@Override
			public void processData(Favorites fav) {
				List<Product> list = fav.productlist;
				if (adapter == null) {
					adapter = new MyAdapter(list, getApplicationContext());
					iv_collection.setAdapter(adapter);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.head_clear_text:
			// 用户点击清空按钮
			
			break;
		case R.id.head_quite_text:
			// 用户点击退出按钮
			finish();
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	private class MyAdapter extends MyListViewAdapter<Product> {

		public MyAdapter(List<Product> list, Context context) {
			super(list, context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.my_collection_list, null);
				holder = new ViewHolder();
				holder.limitbuyrelImage = (ImageView) convertView
						.findViewById(R.id.limitbuyPicIv);
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.tv_name);
				holder.tv_price = (TextView) convertView
						.findViewById(R.id.tv_price);
				holder.tv_oldprivce = (TextView) convertView
						.findViewById(R.id.tv_oldprivce);
				holder.tv_comment = (TextView) convertView
						.findViewById(R.id.tv_comment);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Product product = list.get(position);
			BitmapUtils bitmapUtils = new BitmapUtils(getApplicationContext());
			bitmapUtils.display(holder.limitbuyrelImage, product.pic);
			holder.tv_name.setText(product.name);
			holder.tv_price.setText("￥" + product.price);
			holder.tv_oldprivce.setText("￥" + product.marketprice);
			holder.tv_comment.setText("已有" + product.id + "人评价");
			return convertView;
		}

	}

	class ViewHolder {
		ImageView limitbuyrelImage;
		TextView tv_name;
		TextView tv_price;
		TextView tv_oldprivce;
		TextView tv_comment;
	}

}
