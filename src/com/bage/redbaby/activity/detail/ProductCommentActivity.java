package com.bage.redbaby.activity.detail;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bage.redbaby.R;
import com.bage.redbaby.activity.adapter.ProductCommentAdapter;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.domain.ProductCommentBean;
import com.bage.redbaby.domain.ProductCommentBean.Comment;
import com.bage.redbaby.net.BaseParser;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.util.LogUtils;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.bage.redbaby.util.myutil.ParamsMapsUtils;
import com.google.gson.Gson;
/**
 * 购买评论列表activity
 * @author jun
 *
 */
public class ProductCommentActivity extends BaseActivity {
	private RequestVo commentVo;
	private DataCallBack<?> productCommentCallBack;
	private ProductCommentAdapter productCommentAdapter;
	/**
	 * 评论列表集合
	 */
	private List<Comment> commentList;
	/**
	 * 购买评论列表
	 */
	private ListView listComments;
	/**
	 * 商品详情返回键
	 */
	private TextView titleBack;
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this,ProductDetailActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void initView() {
		setContentView(R.layout.prod_comments_activity);
		commentList = new ArrayList<Comment>();
		listComments = (ListView) findViewById(R.id.listComments);
		titleBack = (TextView) findViewById(R.id.titleBack);
	}

	@Override
	public void initListener() {
		titleBack.setOnClickListener(this);
	}

	@Override
	public void initRequestVo() {
		// 请求的URL地址
		String requestUrl =ConstantRedBoy.BASEURL + "product/comment";
		// 设置请求工具类
		commentVo = new RequestVo(requestUrl,this,ParamsMapsUtils.getString(context),new BaseParser<ProductCommentBean>() {

		@Override
		public ProductCommentBean parseJSON(String str) {
				Gson gson = new Gson();
				return gson.fromJson(str, ProductCommentBean.class);
			}
		});
	}

	@Override
	public void initDataCallBack() {
		productCommentCallBack = new DataCallBack<ProductCommentBean>() {

			@Override
			public void processData(ProductCommentBean result) {
				
				if(result != null){
					if(commentList.size() == 0){
						commentList = result.comment;
						productCommentAdapter = new ProductCommentAdapter(context, commentList);
						listComments.setAdapter(productCommentAdapter);
					}else{
						productCommentAdapter.notifyDataSetChanged();
					}
				}else{
					LogUtils.toast(context, "服务器数据获取失败");
				}
			}
		};
	}

	@Override
	public void initData() {
		getDataForServer(commentVo, productCommentCallBack);
	}

}
