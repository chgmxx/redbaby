package com.bage.redbaby.activity.detail;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bage.redbaby.R;
import com.bage.redbaby.activity.adapter.ProductViewPagerAdapter;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.domain.ProductDetailBean;
import com.bage.redbaby.net.BaseParser;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.util.LogUtils;
import com.bage.redbaby.util.myutil.CartUtil;
import com.bage.redbaby.util.myutil.CommonUtil;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.bage.redbaby.util.myutil.ParamsMapsUtils;
import com.google.gson.Gson;
/**
 * 商品详情页面
 * @author jun
 *
 */
public class ProductDetailActivity extends BaseActivity implements OnPageChangeListener{
	private RequestVo productRequestVo;
	private DataCallBack<?> productCallBack;
	/**商品名称 */
	private TextView textProductName;
	/**商品编号 */
	private TextView textProductIdValue;
	/**市场价*/
	private TextView textOriginalPrice;
	/**商品评分*/
	private TextView textProdGradeValue;
	/**售价 */
	private TextView textPrice;
	/**数量 */
	private TextView textProdNum;
	/**加入购物车 */
	private TextView textPutIntoShopcar;
	/**收藏 */
	private TextView textProdToCollect;
	/**商品描述 */
	private RelativeLayout relDescription;
	/**查看库存控件 */
	private RelativeLayout relProdStock;
	/**库存是否有货 */
	private TextView textProdIsStock;
	/**商品评论控件*/
	private RelativeLayout relProductComment;
	/**购买评论数 */
	private TextView textProductCommentNum;
	/**订购电话*/
	private TextView orderTelTv;
	/**商品轮播图片 */
	private ViewPager productGallery;
	/**商品轮播图片上的点 的位置*/
	private LinearLayout ll_point_group;
	/**轮播图片的url地址集合*/
	private List<String> imageUrlList;
	/**轮播图片选中点,默认值为0*/
	private int prePosition = 0;
	/**
	 * 商品编号id值,传递数据使用
	 */
	protected String textProductIdCode;
	/**商品轮播图片上的点 */
	private View pointView;
	/**
	 * 放置点的集合
	 */
	private List<View> viewPointList = new ArrayList<View>();
	/**返回键*/
	private TextView titleBack;
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.titleBack:
			LogUtils.toast(context, "返回被点击了....");
			break;
		case R.id.textPutIntoShopcar:
			CartUtil.saveCartItem(context,textProductIdCode, String.valueOf(textProdNum.getText()));
			AlertDialog.Builder builder = new Builder(this);
			// 设置对话框的一些参数
			builder.setTitle("提示:");
			builder.setMessage("加入购物车成功,是否要去购物车查看?");
			builder.setPositiveButton("是", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//TODO,跳转到购物车
				}
			});
			builder.setNegativeButton("否", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			builder.show();// 此步操作不可省略
			 LogUtils.toast(context, "加入购物车成功");
			break;
		case R.id.textProdToCollect:
		    LogUtils.toast(context, "收藏成功");
		    break;
		case R.id.relDescription:
		//	LogUtils.toast(context, "商品描述点击了...");
			intent = new Intent(this,ProductDescriptionActivity.class);
			intent.putExtra("textProductIdCode", textProductIdCode);//切换activity时,将商品编号数据携带过去
			startActivity(intent);
			break;
		case R.id.relProdStock:
			LogUtils.toast(context, "查看库存点击了...");
			break;
		case R.id.relProductComment:
		//	LogUtils.toast(context, "购买评论点击了...");
			intent = new Intent(this,ProductCommentActivity.class);
			startActivity(intent);
			break;
		case R.id.orderTelTv:
		//	LogUtils.toast(context, "订购电话点击了...");
			String str = (String) orderTelTv.getText();
			String number = str.substring(str.toString().indexOf("0"));
			intent = new Intent();
			intent.setAction("android.intent.action.CALL");
			intent.setData(Uri.parse("tel:"+number));
    		startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void initView() {
		setContentView(R.layout.product_detail_activity);
		imageUrlList= new ArrayList<String>();
		titleBack = (TextView) findViewById(R.id.titleBack);
		productGallery = (ViewPager) findViewById(R.id.productGallery);
		ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
		textProductName = (TextView) findViewById(R.id.textProductName);
		textProductIdValue = (TextView) findViewById(R.id.textProductIdValue);
		textOriginalPrice = (TextView) findViewById(R.id.textOriginalPrice);
		textProdGradeValue = (TextView) findViewById(R.id.textProdGradeValue);
		textPrice = (TextView) findViewById(R.id.textPrice);
		textProdNum = (TextView) findViewById(R.id.textProdNum);
		textPutIntoShopcar = (TextView) findViewById(R.id.textPutIntoShopcar);
		textProdToCollect = (TextView) findViewById(R.id.textProdToCollect);
		relDescription = (RelativeLayout) findViewById(R.id.relDescription);
		relProdStock = (RelativeLayout) findViewById(R.id.relProdStock);
		textProdIsStock = (TextView) findViewById(R.id.textProdIsStock);
		relProductComment = (RelativeLayout) findViewById(R.id.relProductComment);
		textProductCommentNum = (TextView) findViewById(R.id.textProductCommentNum);
		orderTelTv = (TextView) findViewById(R.id.orderTelTv);
	}


	@Override
	public void initListener() {
		titleBack.setOnClickListener(this);
		textPutIntoShopcar.setOnClickListener(this);
		textProdToCollect.setOnClickListener(this);
		relDescription.setOnClickListener(this);
		relProdStock.setOnClickListener(this);
		relProductComment.setOnClickListener(this);
		orderTelTv.setOnClickListener(this);
		productGallery.setOnPageChangeListener(this);
	}

	@Override
	public void initRequestVo() {
		// 请求的URL地址
		String requestUrl =ConstantRedBoy.BASEURL + "product";
		// 设置请求工具类
		productRequestVo = new RequestVo(requestUrl,this,ParamsMapsUtils.getString(context),new BaseParser<ProductDetailBean>() {

			@Override
			public ProductDetailBean parseJSON(String str) {
				Gson gson = new Gson();
				return gson.fromJson(str, ProductDetailBean.class);
			}
		});

	}

	@Override
	public void initDataCallBack() {
		productCallBack =new DataCallBack<ProductDetailBean>() {

			@Override
			public void processData(ProductDetailBean result) {
				textProductName.setText("商品名称:"+result.product.name);
				textProductIdCode = result.product.id;
				textProductIdValue.setText(result.product.id);
				textOriginalPrice.setText("市场价:"+result.product.marketprice);
				textProdGradeValue.setText(result.product.score);
				textPrice.setText("售价:"+result.product.price);
				textProductCommentNum.setText(result.product.comment_count);
				textProdIsStock.setText("北京仓  (有货)");
				for (int i = 0; i <result.product.pic.length; i++) {
					imageUrlList.add(result.product.pic[i]);
				}
				
				initPoint();
				
				ProductViewPagerAdapter productViewPagerAdapter = new ProductViewPagerAdapter(context, result.product);
				productGallery.setAdapter(productViewPagerAdapter);
			}

		};
	}
	
	@Override
	public void initData() {
		getDataForServer(productRequestVo, productCallBack);
		
	}
	/**
	 * 初始化图片下的点
	 */
	private void initPoint() {
		for(int i=0;i<imageUrlList.size();i++){
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