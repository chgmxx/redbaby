package com.bage.redbaby.activity;

import java.util.ArrayList;
import java.util.List;

import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.bage.redbaby.MyApp;
import com.bage.redbaby.R;
import com.bage.redbaby.activity.adapter.TopicAdapter;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.domain.Topic;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.parser.TopicParser;
import com.bage.redbaby.util.LogUtils;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.bage.redbaby.util.myutil.ParamsMapsUtils;

public class TopicActivity extends BaseActivity {
	private ListView listView;
	private List<Topic> topics;
	private TopicAdapter adapter;
	private RequestVo topicVo;
	private DataCallBack<?> topicCallback;
	private int pager = 1;

	@Override
	public void initView() {
		setContentView(R.layout.my_limit_activity);
		setBaseView("品牌专区");
		listView = (ListView) findViewById(R.id.mylimitbuy_product_list);
		System.out.println("android.os.Build.VERSION.SDK_INT="
				+ android.os.Build.VERSION.SDK_INT);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void initData() {
		topics = new ArrayList<Topic>();
		getDataForServer(topicVo, topicCallback);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initRequestVo() {
		// 请求的URL地址
		String requestUrl = ConstantRedBoy.BASEURL + "/topic";
		// 设置解析工具类
		TopicParser topicParser = new TopicParser();
		// 设置请求工具类
		topicVo = new RequestVo(requestUrl, this, ParamsMapsUtils.getTopic(
				getApplicationContext(), pager + "", 8 + ""), topicParser);

	}

	@Override
	public void initDataCallBack() {
		topicCallback = new DataCallBack<List<Topic>>() {

			@Override
			public void processData(List<Topic> data) {
				// TODO
				if (data != null) {
					if (topics.size() == 0) {
						topics = data;
						adapter = new TopicAdapter(topics, TopicActivity.this);
						listView.setAdapter(adapter);
					} else {
						topics.addAll(data);
						adapter.notifyDataSetChanged();
					}

				} else {
					LogUtils.toast(context, "服务器数据获取失败");
				}

			}
		};

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub

	}

}
