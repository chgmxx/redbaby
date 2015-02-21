package com.bage.redbaby.activity.cart;

import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bage.redbaby.R;
import com.bage.redbaby.activity.MainActivity;
import com.bage.redbaby.activity.adapter.CartAdapter;
import com.bage.redbaby.activity.payment.PaymentCenterActivity;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.bean.Cart;
import com.bage.redbaby.bean.Cart.Product;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.parser.CartParser;
import com.bage.redbaby.util.ActivityUtils;
import com.bage.redbaby.util.ImageUtil;
import com.bage.redbaby.util.ImageUtil.ImageCallback;
import com.bage.redbaby.util.myutil.CartUtil;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.bage.redbaby.util.myutil.ParamsMapsUtils;
import com.bage.redbaby.util.myutil.SharedPreferencesUtils;
import com.bage.redbaby.view.CartListView;

/**
 * 购物车内商品存放于SharedPerferences中
 * 
 * 购物车商品存储模式：商品ID:数量|商品ID:数量 1200001:3|1200004:2
 * 
 * @author baiiu
 * 
 */
public class CartActivity extends BaseActivity {
	private DataCallBack<Cart> dataCallBack;// 回调方法

	// 头部
	private TextView shopcar_update_text;// 顶部栏目，购物车为空时显示返回，不为空时显示编辑
	private TextView shopcar_toPay_text;// 结算按钮，购物车不为空时显示

	// 显示区域
	private TextView shopcar_toBuy_text;// 购物条目为空，显示
	private ImageView shopcar_default_img;// 购物车
	private TextView shopcar_null_text;// 显示：购物车内没有商品

	private ScrollView shopcar_body_srcoll;// 滚动条
	private TextView shopcar_total_buycount_text_1;// 数量总计
	private TextView shopcar_total_bonus_text_1;// 赠送积分总计
	private TextView shopcar_total_money_text_1;// 金额总计

	private CartListView shopcar_product_list;// ListView

	// 底部
	private TextView shopcar_total_buycount_text_2;// 底部数量总计
	private TextView shopcar_total_bonus_text_2;// 底部赠送积分总计
	private TextView shopcar_total_money_text_2;// 底部金额总计
	private TextView textShopCarNum;// 小红点

	private TextView shopcar_bottom_toPay_text;// 去结算

	// 购物车是否为空
	private boolean haveNoCartItem = false;
	private RequestVo cartVo;// 访问url
	private String requestUrl;// 参数
	private Map<String, String> requestDataMap;
	private CartParser cartParser;

	// 购物车
	private Cart jsonCart;// javabean
	private CartAdapter adapter;// 适配器

	/****************************************************************************************************************/

	@Override
	public void initView() {
		setContentView(R.layout.shopping_car_activity);

		// 头部
		shopcar_update_text = (TextView) findViewById(R.id.shopcar_update_text);
		shopcar_toPay_text = (TextView) findViewById(R.id.shopcar_toPay_text);

		// 中间
		shopcar_toBuy_text = (TextView) findViewById(R.id.shopcar_toBuy_text);
		shopcar_default_img = (ImageView) findViewById(R.id.shopcar_default_img);
		shopcar_null_text = (TextView) findViewById(R.id.shopcar_null_text);

		shopcar_body_srcoll = (ScrollView) findViewById(R.id.shopcar_body_srcoll);
		shopcar_total_buycount_text_1 = (TextView) findViewById(R.id.shopcar_total_buycount_text_1);
		shopcar_total_bonus_text_1 = (TextView) findViewById(R.id.shopcar_total_bonus_text_1);
		shopcar_total_money_text_1 = (TextView) findViewById(R.id.shopcar_total_money_text_1);
		shopcar_product_list = (CartListView) findViewById(R.id.shopcar_product_list);

		// 底部
		shopcar_total_buycount_text_2 = (TextView) findViewById(R.id.shopcar_total_buycount_text_2);
		shopcar_total_bonus_text_2 = (TextView) findViewById(R.id.shopcar_total_bonus_text_2);
		shopcar_total_money_text_2 = (TextView) findViewById(R.id.shopcar_total_money_text_2);
		textShopCarNum = (TextView) findViewById(R.id.textShopCarNum);
		shopcar_bottom_toPay_text = (TextView) findViewById(R.id.shopcar_bottom_toPay_text);
	}

	@Override
	public void initListener() {

		shopcar_update_text.setOnClickListener(this);// 返回、编辑

		if (haveNoCartItem) {
			/*
			 * 购物车为空
			 */
			shopcar_toBuy_text.setOnClickListener(this);// 去逛逛
		} else {
			/*
			 * 不为空
			 */
			shopcar_toPay_text.setOnClickListener(this);// 去结算
			shopcar_bottom_toPay_text.setOnClickListener(this);// 去结算
		}
	}

	@Override
	public void initRequestVo() {
		// 获取存储的商品列表
		String data = SharedPreferencesUtils.getStringData(this, "cartUrl", "");
		
		// 购物车为空
		if (TextUtils.isEmpty(data)) {
			haveNoCartItem = true;
			return;
		}

		haveNoCartItem = false;

		// 购物车商品存储模式：商品ID:数量|商品ID:数量 1200001:3|1200004:2

		if (requestUrl == null || requestDataMap == null || cartParser == null) {
			requestUrl = ConstantRedBoy.BASEURL + "/cart";
			requestDataMap = ParamsMapsUtils.getCartString(this, data);
			cartParser = new CartParser();
		}

		cartVo = new RequestVo(requestUrl, this, requestDataMap, cartParser);
	}

	@Override
	public void initDataCallBack() {
		// 没有购物条目，不用请求数据
		if (haveNoCartItem) {
			return;
		}

		if (cartVo == null) {
			return;
		}

		dataCallBack = new DataCallBack<Cart>() {
			@Override
			public void processData(Cart cart) {
				// 初始化完后再次调用initData()进行初始化
				CartActivity.this.jsonCart = cart;
				fillData();
			}
		};
	}

	@Override
	public void initData() {
		getDataForServer(cartVo, dataCallBack);
	}

	/**
	 * 回调方法中初始化数据
	 */
	private void fillData() {
		if (haveNoCartItem || jsonCart == null) {
			/*
			 * 购物车空
			 */
			textShopCarNum.setBackgroundColor(Color.TRANSPARENT);// 小红点消失
			textShopCarNum.setText("");//设置数目
			
			shopcar_null_text.setVisibility(View.VISIBLE);// 您的购物车中没有商品
			shopcar_toBuy_text.setVisibility(View.VISIBLE);// 去逛逛
			shopcar_default_img.setVisibility(View.VISIBLE);// 购物车图片

			shopcar_body_srcoll.setVisibility(View.GONE);// 购物条目详细

			// 头部
			shopcar_update_text.setText("返回");

		} else {
			/*
			 * 购物车不为空
			 */
			textShopCarNum.setBackgroundResource(R.drawable.number_bg);// 小红点再现
			textShopCarNum.setText(CartUtil.getCartItemCount());//设置数目
			
			shopcar_null_text.setVisibility(View.GONE);// 您的购物车中没有商品
			shopcar_toBuy_text.setVisibility(View.GONE);// 去逛逛
			shopcar_default_img.setVisibility(View.GONE);// 购物车图片

			shopcar_body_srcoll.setVisibility(View.VISIBLE);// 购物条目滚动条显示

			// 头部
			shopcar_update_text.setText("编辑");
			shopcar_toPay_text.setVisibility(View.VISIBLE);

			// 商品统计信息 上
			shopcar_total_buycount_text_1
					.setText(jsonCart.cart.cart_addup.total_count);// 数量总计
			shopcar_total_bonus_text_1
					.setText(jsonCart.cart.cart_addup.total_point);// 赠送积分
			shopcar_total_money_text_1
					.setText(jsonCart.cart.cart_addup.total_price);// 价格总计

			// 商品统计信息 下
			shopcar_total_buycount_text_2
					.setText(jsonCart.cart.cart_addup.total_count);// 数量总计
			shopcar_total_bonus_text_2
					.setText(jsonCart.cart.cart_addup.total_point);// 赠送积分总计
			shopcar_total_money_text_2
					.setText(jsonCart.cart.cart_addup.total_price);// 价格总计

			// 填充ListView
			if (adapter == null) {
				adapter = new CartAdapter(this, jsonCart.cart.productlist);
				shopcar_product_list.setAdapter(adapter);
			} else {
				adapter.notifyDataSetChanged();
			}

			// 滚动到头部，否则会显示listview在屏幕顶端
			shopcar_body_srcoll.post(new Runnable() {
				@Override
				public void run() {
					shopcar_body_srcoll.scrollTo(0, 0);
				}
			});
			
			/*
			 * onItemClick
			 */
			shopcar_product_list
					.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							System.out.println("点击啦" + position);
							//TODO: 跳转到指商品详情页面，传入商品id
						}
					});
			
			
			
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// 返回、编辑
		case R.id.shopcar_update_text:
			String word = shopcar_update_text.getText().toString();
			if ("返回".equals(word)) {
				ActivityUtils.startActivityAndFinish(this, MainActivity.class);
			} else if ("编辑".equals(word)) {
				// TODO:编辑
				adapter.setClickEdit(true);
				shopcar_update_text.setText("完成");
			}else if("完成".equals(word)){
				//向服务器提交数据，刷新整个页面
				adapter.setClickEdit(false);
				initRequestVo();
				initData();
			}
			break;

		// 去逛逛
		case R.id.shopcar_toBuy_text:
			ActivityUtils.startActivityAndFinish(this, MainActivity.class);
			break;

		// 去结算
		case R.id.shopcar_toPay_text:
		case R.id.shopcar_bottom_toPay_text:
			if(TextUtils.isEmpty(SharedPreferencesUtils.getStringData(this, "userId", ""))){
				//TODO:跳转到登录界面
			}else{
			}
			
			ActivityUtils.startActivity(this, PaymentCenterActivity.class);
			
			break;
		}
	}

}
