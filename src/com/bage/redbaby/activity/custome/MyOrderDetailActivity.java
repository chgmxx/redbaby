package com.bage.redbaby.activity.custome;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bage.redbaby.MyApp;
import com.bage.redbaby.R;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.bean.Orderdetail;
import com.bage.redbaby.bean.Orderdetail.Productlist;
import com.bage.redbaby.net.BaseParser;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.bage.redbaby.util.myutil.ParamsMapsUtils;
import com.bage.redbaby.view.MyListView;
import com.google.gson.Gson;
import com.lidroid.xutils.util.LogUtils;
/**
 * 订单详情
 * @author 程王勇
 *
 */
public class MyOrderDetailActivity extends BaseActivity {

	private static String orderId;
	private static String username;
	private static String userID;

	/**
	 * 跳转到此页面时调用的方法 请不要用Intent
	 * 
	 * @param context
	 *            上下文
	 * @param orderId
	 *            订单号
	 * @param userID 
	 * @param username 
	 */
	public static void actionStart(Context context, String orderId, String username, String userID) {
		Intent intent = new Intent(context, MyOrderDetailActivity.class);
		MyOrderDetailActivity.orderId=orderId;
		MyOrderDetailActivity.username=username;
		MyOrderDetailActivity.userID=userID;
		context.startActivity(intent);
	}

	private TextView head_back_text;
	private RelativeLayout ordr_logistics_rel;
	private MyListView listdetail;
	private TextView textAdress1;
	private TextView textAdress2;
	private TextView textAdress4;
	private TextView textDetail1;
	private TextView textDetail2;
	private TextView textAdress3;
	private TextView textDetail3;
	private TextView textDetail4;
	private TextView textDetail5;
	private TextView textDetail6;
	private TextView textDetail7;
	private TextView textDetail8;
	private TextView textDetail9;
	private TextView textPrice2;
	private TextView textPrice3;
	private TextView textPrice4;
	private TextView textPrice5;
	private TextView textPrice6;
	private TextView textPrice7;
	private RequestVo vo;
	private DataCallBack<Orderdetail> dataCallBack;
	private TextView cancel_order;
	private MyOrderActivity orderActivity;

	@Override
	public void initView() {
		setContentView(R.layout.my_order_detail_activity);
		//获取对象
		orderActivity = (MyOrderActivity) MyApp.myApp.object;
		
		head_back_text=(TextView)findViewById(R.id.head_back_text);
		//收件人
		textAdress1=(TextView)findViewById(R.id.textAdress1);
		//联系电话
		textAdress2=(TextView)findViewById(R.id.textAdress2);
		//联系地址
		textAdress3=(TextView)findViewById(R.id.textAdress3);
		//邮政编码
		textAdress4=(TextView)findViewById(R.id.textAdress4);
		//订单配送信息
		ordr_logistics_rel=(RelativeLayout)findViewById(R.id.ordr_logistics_rel);
		//订单状态
		textDetail1=(TextView)findViewById(R.id.textDetail1);
		//送货方式
		textDetail2=(TextView)findViewById(R.id.textDetail2);
		//支付方式
		textDetail3=(TextView)findViewById(R.id.textDetail3);
		//订单生成时间
		textDetail4=(TextView)findViewById(R.id.textDetail4);
		
		//发货时间
		textDetail5=(TextView)findViewById(R.id.textDetail5);
		//是否发票
		textDetail6=(TextView)findViewById(R.id.textDetail6);
		//发票抬头
		textDetail7=(TextView)findViewById(R.id.textDetail7);
		//送货要求
		textDetail8=(TextView)findViewById(R.id.textDetail8);
		
		//备注
		textDetail9=(TextView)findViewById(R.id.textDetail9);
		
		//商品清单的ListView
		listdetail=(MyListView)findViewById(R.id.listdetail);
		
		//商品金额总价
		textPrice2=(TextView)findViewById(R.id.textPrice2);
		//优惠金额
		textPrice3=(TextView)findViewById(R.id.textPrice3);
		//运费金额
		textPrice4=(TextView)findViewById(R.id.textPrice4);
		//已支付金额
		textPrice5=(TextView)findViewById(R.id.textPrice5);
		//获得积分
		textPrice6=(TextView)findViewById(R.id.textPrice6);
		//应收款金额
		textPrice7=(TextView)findViewById(R.id.textPrice7);
		//取消订单
		cancel_order=(TextView)findViewById(R.id.cancel_order);
		
		
		
	}

	@Override
	public void initListener() {
		ordr_logistics_rel.setOnClickListener(this);
		head_back_text.setOnClickListener(this);
		//商品详情不需要被点击
		
		cancel_order.setOnClickListener(this);
	}

	@Override
	public void initRequestVo() {
		Map<String, String> map = ParamsMapsUtils.getString(this);
		map.put("orderId", orderId);
		vo=new RequestVo(ConstantRedBoy.BASEURL+"/Json.json", this, map, new BaseParser<Orderdetail>() {

			@Override
			public Orderdetail parseJSON(String str) {
				//LogUtils.i("str=="+str);
				Gson gson=new Gson();
				return gson.fromJson(str, Orderdetail.class);
			}
		});
	}
	
	private MyAdapter adapter;
	private List<Productlist> list;
	@Override
	public void initDataCallBack() {
		dataCallBack=new DataCallBack<Orderdetail>() {

			

			@Override
			public void processData(Orderdetail order) {
				if (order==null) {
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), "数据解析失败", Toast.LENGTH_SHORT).show();
						}
					});
					return;
				}
				
				LogUtils.i(order.checkout_prom.prom2);
				textAdress1.setText(order.address_info.name);
				//TODO 这里由出乌龙 服务器没有提供电话 此处拼接的是地址簿ID
				textAdress2.setText(order.address_info.id);
				textAdress3.setText(order.address_info.address_area+order.address_info.address_detail);
				//TODO 这里也没有 这里用的还是上面的ID
				textAdress4.setText(order.address_info.id);
				textDetail1.setText(order.order_info.status);
				//TODO 依旧没数据
				textDetail2.setText("京东物流");
				textDetail3.setText(order.payment_info.type.equals("1")?"货到付款 ":(order.payment_info.type.equals("2")?"货到POS机":"支付宝"));
				textDetail4.setText(order.order_info.time);
				//TODO 没数据
				textDetail5.setText("乖乖等着把");
				textDetail6.setText("冒得");
				
				textDetail7.setText(order.invoice_info.title);
				textDetail8.setText(order.delivery_info.type.equals("1")?"周一至周五送货":(order.delivery_info.type.equals("2")?"双休日及公众假期送货":"时间不限，工作日双休日及公众假期均可送货"));
				//TODO 少儿不宜
				textDetail9.setText("xxx-ooo");
				
				textPrice2.setText(order.checkout_addup.total_price);
				textPrice3.setText(order.checkout_addup.prom_cut);
				textPrice4.setText(order.checkout_addup.freight);
				//TODO 没有数据
				textPrice5.setText("0");
				
				textPrice6.setText(order.checkout_addup.total_point);
				textPrice7.setText(""+(Integer.parseInt(order.checkout_addup.total_price)-Integer.parseInt(order.checkout_addup.prom_cut)));
				
				list=order.productlist;
				
				if (adapter==null) {
					adapter=new MyAdapter();
					listdetail.setAdapter(adapter);
				}else {
					adapter.notifyDataSetChanged();
				}
				
				
			}
		};
	}

	@Override
	public void initData() {
		getDataForServer(vo, dataCallBack);
	}

	
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			//LogUtils.i("list的长度"+list.size());
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//LogUtils.i("position="+position);
			ViewHolder holder=null;
			if (convertView==null) {
				convertView=View.inflate(getApplicationContext(), R.layout.orderdatail_item, null);
				holder=new ViewHolder();
				holder.tv_color=(TextView) convertView.findViewById(R.id.tv_color);
				holder.tv_count=(TextView) convertView.findViewById(R.id.tv_count);
				holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
				holder.tv_price=(TextView) convertView.findViewById(R.id.tv_price);
				holder.tv_size=(TextView) convertView.findViewById(R.id.tv_size);
				convertView.setTag(holder);
			}else {
				holder=(ViewHolder) convertView.getTag();
			}
			Productlist productlist = list.get(position);
			holder.tv_name.setText("商品名称: "+productlist.name);
			holder.tv_color.setText("颜色："+productlist.color);
			holder.tv_count.setText("数量："+productlist.number);
			holder.tv_size.setText("尺码："+productlist.size);
			holder.tv_price.setText("价格："+productlist.price);
			return convertView;
		}
		
	}
	private class ViewHolder{
		TextView tv_name;
		TextView tv_color;
		TextView tv_count;
		TextView tv_size;
		TextView tv_price;
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ordr_logistics_rel:
			LogUtils.i("订单配送信息被点击");
			//跳转到相关页面
			MyOrderLogisticsActivity.actionStart(MyOrderDetailActivity.this, username, userID,orderId);
			break;
		case R.id.head_back_text:
			LogUtils.i("返回键被点击");
			finish();
			break;
		case R.id.cancel_order:
			LogUtils.i("取消订单被点击");
			orderActivity.cacelOrder(orderId);
			break;
		}
	}

	
	
}
