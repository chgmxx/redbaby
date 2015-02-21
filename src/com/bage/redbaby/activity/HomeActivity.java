package com.bage.redbaby.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bage.redbaby.R;
import com.bage.redbaby.activity.adapter.HomeAdapter;
import com.bage.redbaby.activity.adapter.ListViewAdapter;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.domain.HomeBanner;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.parser.HomeParser;
import com.bage.redbaby.util.LogUtils;
import com.bage.redbaby.util.myutil.CommonUtil;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.bage.redbaby.util.myutil.ParamsMapsUtils;

public class HomeActivity extends BaseActivity implements OnPageChangeListener {

	private RequestVo homeVo;
	private DataCallBack<List<HomeBanner>> homeCallback;
	private List<HomeBanner> homeBanners;
	private HomeAdapter adapter;
	public ViewPager vp;
	private ListView custonInfoListView;
	/**请求图片的url地址集合*/
	private List<String> imageList;
	/**图片上点的集合*/
	private List<View> viewPointList;
	/**图片上的点*/
	private View pointView;
	/**图片上点的控件*/
	private LinearLayout ll_point_group;
	/**图片切换的位置变量,默认是0*/
	private int prePosition = 0;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initView() {
		setContentView(R.layout.home_activity);
		vp = (ViewPager) findViewById(R.id.vp);
		custonInfoListView = (ListView) findViewById(R.id.custonInfoListView);
		ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
		imageList = new ArrayList<String>();
		viewPointList =new ArrayList<View>();
	}

	@Override
	public void initRequestVo() {
		// 请求的URL地址
		String requestUrl = ConstantRedBoy.BASEURL + "/home";
		// 设置解析工具类
		HomeParser homeParsre = new HomeParser();
		// 设置请求工具类
		homeVo = new RequestVo(requestUrl, this, ParamsMapsUtils.getString(context), homeParsre);
	}

	@Override
	public void initDataCallBack() {
		homeCallback = new DataCallBack<List<HomeBanner>>() {

			@Override
			public void processData(List<HomeBanner> data) {
				// TODO
				if (data != null) {
					if (homeBanners.size() == 0) {
						homeBanners = data;
						adapter = new HomeAdapter(homeBanners,
								HomeActivity.this);
						vp.setAdapter(adapter);
						for (int i = 0; i < homeBanners.size(); i++) {
							imageList.add(homeBanners.get(1).pic);     
						}
						initPoint();
					} else {
						homeBanners.addAll(data);
						adapter.notifyDataSetChanged();
					}

				} else {
					LogUtils.toast(context, "服务器数据获取失败");
				}
			}
		};
	}

	@Override
	public void initData() {
		homeBanners = new ArrayList<HomeBanner>();
		getDataForServer(homeVo, homeCallback);

		custonInfoListView.setAdapter(new ListViewAdapter());
	}

	@Override
	public void initListener() {
		custonInfoListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					if(position==1){
						Intent intent = new Intent(context,TopicActivity.class);
						startActivity(intent);
					}
			}
		});
		
		vp.setOnPageChangeListener(this);
	}
	/**
	 * 初始化图片下的点
	 */
	private void initPoint() {
		for(int i=0;i<imageList.size();i++){
			pointView = new View(context);
			if(i == 0){
				pointView.setBackgroundResource(R.drawable.point_focus);
			}else{
				pointView.setBackgroundResource(R.drawable.point_normal);
			}
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(CommonUtil.dip2px(context, 6), 
					CommonUtil.dip2px(context, 6));
			pointView.setLayoutParams(params);
			params.setMargins(5, 0, 5, 0);
			ll_point_group.addView(pointView);
			viewPointList.add(pointView);
		}
	}
	/**
	 * 轮播图片切换时调用的方法
	 */
	@Override
	public void onPageSelected(int arg0) {
	
		viewPointList.get(arg0).setBackgroundResource(R.drawable.point_focus);
		viewPointList.get(prePosition).setBackgroundResource(R.drawable.point_normal);
		prePosition = arg0;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
