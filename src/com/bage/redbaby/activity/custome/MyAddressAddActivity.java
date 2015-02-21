package com.bage.redbaby.activity.custome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bage.redbaby.R;
import com.bage.redbaby.activity.adapter.MyListViewAdapter;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.bean.AddressArea;
import com.bage.redbaby.bean.AddressArea.Area;
import com.bage.redbaby.bean.AddressArea.City;
import com.bage.redbaby.bean.AddressArea.Province;
import com.bage.redbaby.net.BaseParser;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.parser.StringParser;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.bage.redbaby.util.myutil.ParamsMapsUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

/**
 * 订单中心 地址管理  新增地址
 * @author 程王勇
 *
 */
public class MyAddressAddActivity extends BaseActivity implements OnItemClickListener {
	
	private static String username;
	private static String userID;

	public static void actionStart(Activity myAddressManager,
			String username, String userID) {
		Intent intent = new Intent(myAddressManager,
				MyAddressAddActivity.class);
		MyAddressAddActivity.username = username;
		MyAddressAddActivity.userID = userID;
		myAddressManager.startActivity(intent);
	}

	private TextView head_back_text;
	private TextView address_add_save;
	private EditText add_address_name_edit;
	private EditText add_address_mobile_edit;
	private EditText add_address_tel_edit;
	private TextView add_address_province_name_text;
	private TextView add_address_city_name_text;
	private TextView add_address_area_name_text;
	private EditText add_address_detail_edit;
	private EditText add_address_zipcode_edit;
	private Button save_address_button;
	private Button cancel_address_button;
	private RequestVo vo;
	private DataCallBack<AddressArea> dataCallBack;
	private PopupWindow pw;
	private MyAdapter adapter;
	
	@Override
	public void initView() {
		setContentView(R.layout.add_address_activity);
		//返回键
		head_back_text=(TextView)findViewById(R.id.head_back_text);
		//保存地址
		address_add_save=(TextView)findViewById(R.id.address_add_save);
		//收件人
		add_address_name_edit=(EditText)findViewById(R.id.add_address_name_edit);
		//手机
		add_address_mobile_edit=(EditText)findViewById(R.id.add_address_mobile_edit);
		//固定电话
		add_address_tel_edit=(EditText)findViewById(R.id.add_address_tel_edit);
		//省份
		add_address_province_name_text=(TextView)findViewById(R.id.add_address_province_name_text);
		//城市
		add_address_city_name_text=(TextView)findViewById(R.id.add_address_city_name_text);
		//地区
		add_address_area_name_text=(TextView)findViewById(R.id.add_address_area_name_text);
		//详细地址
		add_address_detail_edit=(EditText)findViewById(R.id.add_address_detail_edit);
		//邮编
		add_address_zipcode_edit=(EditText)findViewById(R.id.add_address_zipcode_edit);
		//保存
		save_address_button=(Button)findViewById(R.id.save_address_button);
		//取消
		cancel_address_button=(Button)findViewById(R.id.cancel_address_button);
	}

	@Override
	public void initListener() {
		head_back_text.setOnClickListener(this);
		address_add_save.setOnClickListener(this);
		add_address_province_name_text.setOnClickListener(this);
		add_address_city_name_text.setOnClickListener(this);
		add_address_area_name_text.setOnClickListener(this);
		save_address_button.setOnClickListener(this);
		cancel_address_button.setOnClickListener(this);
	}

	@Override
	public void initRequestVo() {
		Map<String, String> map = ParamsMapsUtils.getString(this);
		map.put("version", "12");
		 vo=new RequestVo(ConstantRedBoy.BASEURL+"/City.json", this, map, new BaseParser<AddressArea>() {

				@Override
				public AddressArea parseJSON(String str) {
					LogUtils.i("str=="+str);
					Gson gson=new Gson();
					return gson.fromJson(str, AddressArea.class);
				}
			});
	}
	private AddressArea address;
	@Override
	public void initDataCallBack() {
		dataCallBack=new DataCallBack<AddressArea>() {

			@Override
			public void processData(AddressArea obj) {
				LogUtils.i("三级列表封装成功=="+obj.version);
				address=obj;
			}
		};
	}

	@Override
	public void initData() {
		 getDataForServer(vo, dataCallBack);
	}
	
	private List<String> valueList=new ArrayList<String>();
	
	private TextView tv;
	private Map<TextView, Integer> viewMap=new HashMap<TextView, Integer>();
	private boolean flag;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.head_back_text:
			//地址管理
			finish();
			return;
		case R.id.address_add_save:
			//保存收货信息
			saveAddressInfo();
			break;
		case R.id.add_address_province_name_text:
			//省份
			if (address!=null) {
				valueList.clear();
				for (Province pro : address.arealist) {
					valueList.add(pro.value);
				}
				showCityList(add_address_province_name_text);
				LogUtils.i("当前条目是"+viewMap.get(add_address_province_name_text));
				flag=true;
			}
			break;
		case R.id.add_address_city_name_text:
			if (address!=null) {
				valueList.clear();
				add_address_area_name_text.setText("请选择");
				
				Province province = address.arealist.get(viewMap.get(add_address_province_name_text));
				for (City city : province.sub_area) {
					valueList.add(city.value);
				}
				showCityList(add_address_city_name_text);
			}
			//城市
			break;
		case R.id.add_address_area_name_text:
			//市区
			if (address!=null) {
				valueList.clear();
				List<Area> sub_area = address.arealist.get(viewMap.get(add_address_province_name_text)).sub_area.get(viewMap.get(add_address_city_name_text)).sub_area;
				for (Area area : sub_area) {
					valueList.add(area.value);
				}
				showCityList(add_address_area_name_text);
			}
			break;
		case R.id.save_address_button:
			//保存
			saveAddressInfo();
			break;
		case R.id.cancel_address_button:
			//取消
			finish();
			return;
		}
	}
	
	
	
	
	
	
	@SuppressWarnings("deprecation")
	private void showCityList(TextView tv) {
		this.tv=tv;
		// 初始化ListView控件和里边的数据
    	ListView mListView = initListView();
    	
    	// 弹出一个PopupWindow的窗体, 把ListView作为其内容部分显示.
    	pw = new PopupWindow(mListView, tv.getWidth() - 4, 200);
    	
    	// 设置可以使用焦点
    	pw.setFocusable(true);
    	
    	// 设置popupwindow点击外部可以被关闭
    	pw.setOutsideTouchable(true);
    	// 设置一个popupWindow的背景
    	pw.setBackgroundDrawable(new BitmapDrawable());
    	
    	// 把popupwindow显示出来, 显示的位置是: 在输入框的下面, 和输入框是连着的.
    	pw.showAsDropDown(tv, 0, 0); 
	}

	private ListView initListView() {
		ListView mListView = new ListView(this);
		mListView.setDividerHeight(0);
		mListView.setBackgroundResource(R.drawable.listview_background);
		// 去掉右侧垂直滑动条
		mListView.setVerticalScrollBarEnabled(false);
		mListView.setOnItemClickListener(this);
		
		// 设置适配器展示数据
		adapter=new MyAdapter(valueList, context);
		mListView.setAdapter(adapter);
		return mListView;
	}
	
	public class MyAdapter extends MyListViewAdapter<String>{

		public MyAdapter(List<String> list, Context context) {
			super(list, context);
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			NumberHolder mHolder = null;
			if(convertView == null) {
				convertView = View.inflate(MyAddressAddActivity.this, R.layout.listview_item, null);
				mHolder = new NumberHolder(); // 缓存类
				mHolder.tvNumber = (TextView) convertView.findViewById(R.id.tv_listview_item_number);
				// 把Holder类设置给convertView对象.
				convertView.setTag(mHolder);
			} else {
				mHolder = (NumberHolder) convertView.getTag();
			}
			mHolder.tvNumber.setText(list.get(position));
			mHolder.tvNumber.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					tv.setText(list.get(position));
					viewMap.put(tv, position);
					if (viewMap.containsKey(add_address_province_name_text)&& flag) {
						flag=false;
						String city = address.arealist.get(viewMap.get(add_address_province_name_text)).sub_area.get(0).value;
						String area = address.arealist.get(viewMap.get(add_address_province_name_text)).sub_area.get(0).sub_area.get(0).value;
						LogUtils.i("进来拉"+city+"----"+area);
						add_address_city_name_text.setText(city);
						add_address_area_name_text.setText(area);
					}
					pw.dismiss();
				}
			});
			return convertView;
		}
		
	}
	
	class NumberHolder {
		public TextView tvNumber;
	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
	}
	

	/**
	 * 保存用户地址信息
	 */
	private void saveAddressInfo() {
		String name = add_address_name_edit.getText().toString().trim();
		String mobile = add_address_mobile_edit.getText().toString().trim();
		String tel = add_address_tel_edit.getText().toString().trim();
		String province = add_address_province_name_text.getText().toString().trim();
		String city = add_address_city_name_text.getText().toString().trim();
		String area = add_address_area_name_text.getText().toString().trim();
		String detail = add_address_detail_edit.getText().toString().trim();
		String zipcode = add_address_zipcode_edit.getText().toString().trim();
		
		String msg = (name.equals("")?"收件人姓名不能为空":(mobile.equals("")?"手机号不能为空":(tel.equals("")?"固定电话不能为空":
			(province.equals("请选择")?"省份不能为空":(city.equals("请选择")?"城市不能为空":(area.equals("请选择")?"地区不能为空":(detail.equals("")?"详细地址不能为空":(zipcode.equals("")?"邮编不能为空":null))))))));
		if (TextUtils.isEmpty(msg)) {
			//说明用户全都填好拉 请求网络数据并更新
			//LogUtils.i(name+mobile+tel+province+city+area+detail+zipcode);
			getDataFromNet(name, mobile, tel, province, city, area, detail,zipcode);
			
//			HttpUtils http=new HttpUtils();
//			
//			RequestParams params=new RequestParams();
//			params.addQueryStringParameter("name", name);
//			params.addQueryStringParameter("phonenumber", mobile);
//			params.addQueryStringParameter("fixedtel", tel);
//			params.addQueryStringParameter("areaid", province+city+area);
//			params.addQueryStringParameter("areadetail", detail);
//			params.addQueryStringParameter("zipcode", zipcode);
//			
//			http.send(HttpMethod.POST, ConstantRedBoy.BASEURL+"/addresssave", params, new RequestCallBack<String>() {
//
//				@Override
//				public void onFailure(HttpException arg0, String arg1) {
//					LogUtils.i("xutils==失败拉");
//				}
//
//				@Override
//				public void onSuccess(ResponseInfo<String> arg0) {
//					LogUtils.i("xutils=="+arg0.result);
//				}
//			});
			
			
		}else {
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
		}
	}

	private void getDataFromNet(String name, String mobile, String tel,
			String province, String city, String area, String detail,
			String zipcode) {
		//TODO 服务器不了人 咋办
		Map<String, String> map = ParamsMapsUtils.getString(this);
		//map.put("id", "1001");
		map.put("name", name);
		map.put("phonenumber", mobile);
		map.put("fixedtel", tel);
		map.put("areaid", province+","+city+","+area);
		map.put("areadetail", detail);
		map.put("zipcode", zipcode);
		LogUtils.i("map=="+map);
		//vo.setType("post");
		vo=new RequestVo(ConstantRedBoy.BASEURL+"/addresssave", this, map, new BaseParser<String>() {

			@Override
			public String parseJSON(String str) {
				LogUtils.i("str=="+str);
				return str;
			}
		});
		
		
		getDataForServer(vo, new DataCallBack<String>() {

			@Override
			public void processData(String obj) {
				LogUtils.i("obj=="+obj);
			}
		});
	}

	

	
}
