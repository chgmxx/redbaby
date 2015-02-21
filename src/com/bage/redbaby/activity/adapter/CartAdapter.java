package com.bage.redbaby.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bage.redbaby.R;
import com.bage.redbaby.activity.cart.CartActivity;
import com.bage.redbaby.bean.Cart.Product;
import com.bage.redbaby.util.ImageUtil;
import com.bage.redbaby.util.ImageUtil.ImageCallback;
import com.bage.redbaby.util.myutil.CartUtil;
import com.bage.redbaby.util.myutil.ConstantRedBoy;

/**
 * 适配器
 * 
 * @author baiiu
 * 
 */
public class CartAdapter extends BaseAdapter {

	private CartActivity activity;
	private List<Product> productlist;
	private List<Product> goodslist;// 有货物的商品
	private List<Product> noGoodslist;// 没有货物的商品

	private boolean clickEdit = false;//是否点击编辑按钮
	
	private String count;//变动数量
	
	ImageCallback callback = new ImageCallback() {
		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			notifyDataSetChanged();
		}
	};
	

	public CartAdapter(CartActivity activity, List<Product> productlist) {
		this.activity = activity;
		this.productlist = productlist;
		goodslist = new ArrayList<Product>();
		noGoodslist = new ArrayList<Product>();
		devideList();
	}

	@Override
	public int getCount() {
		return productlist.size() + 1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		final Product product;

		if (position < goodslist.size()) {
			product = goodslist.get(position);
		} else if (position == goodslist.size()) {
			TextView tv = new TextView(activity);
			tv.setTextColor(Color.WHITE);
			tv.setBackgroundColor(Color.GRAY);
			tv.setText("已无货商品");
			return tv;
		} else {
			product = noGoodslist.get(position - goodslist.size() - 1);
		}

		final CartHolder holder;
		if (convertView != null && convertView instanceof RelativeLayout) {
			holder = (CartHolder) convertView.getTag();
		} else {
			convertView = View.inflate(activity,
					R.layout.shopping_car_listitem, null);
			holder = new CartHolder();
			holder.shopcar_item_prodImage_img = (ImageView) convertView
					.findViewById(R.id.shopcar_item_prodImage_img);
			holder.shopcar_item_prodName_text = (TextView) convertView
					.findViewById(R.id.shopcar_item_prodName_text);
			holder.shopcar_item_prodId_text = (TextView) convertView
					.findViewById(R.id.shopcar_item_prodId_text);
			holder.shopcar_item_prodPrice_text = (TextView) convertView
					.findViewById(R.id.shopcar_item_prodPrice_text);
			holder.shopcar_item_prodCount_text = (TextView) convertView
					.findViewById(R.id.shopcar_item_prodCount_text);
			holder.shopcar_item_prodCount_text = (TextView) convertView
					.findViewById(R.id.shopcar_item_prodCount_text);
			holder.shopcar_item_subtotal_text = (TextView) convertView
					.findViewById(R.id.shopcar_item_subtotal_text);
			holder.shopcar_item_delete_text = (TextView) convertView
					.findViewById(R.id.shopcar_item_delete_text);
			holder.shopcar_item_prodCount_edit = (EditText) convertView
					.findViewById(R.id.shopcar_item_prodCount_edit);
			convertView.setTag(holder);
		}

		// 设置图片
		// TODO:图片地址....
		Bitmap bitmap = ImageUtil.loadImageDefault(activity,
				activity.getCacheDir(), ConstantRedBoy.BASEURL + product.pic,
				callback);

		if (bitmap != null) {
			holder.shopcar_item_prodImage_img.setImageBitmap(bitmap);
		} else {
			holder.shopcar_item_prodImage_img
					.setImageResource(R.drawable.product_loading);
		}
		
		holder.shopcar_item_prodName_text.setText(product.name);// 名称
		holder.shopcar_item_prodId_text.setText(product.id);// 编码
		holder.shopcar_item_prodPrice_text.setText(product.price);// 单价
		holder.shopcar_item_prodCount_text.setText(product.prodNum);// 商品数量
		holder.shopcar_item_subtotal_text.setText(product.subtotal);// 商品总计

		if (position < goodslist.size()) {
			convertView.setBackgroundResource(R.anim.shape_rounded_selectable);
			
			if(clickEdit){
				
				//删除
				holder.shopcar_item_prodCount_text.setVisibility(View.GONE);
				holder.shopcar_item_delete_text.setVisibility(View.VISIBLE);
				holder.shopcar_item_delete_text.setText(product.prodNum);
				holder.shopcar_item_delete_text
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								/*
								 * 重新加载数据
								 */
								CartUtil.removeCartItem(activity, product.id);
								activity.initRequestVo();// 重做url
								activity.initData();// 重新初始化数据
							}
						});
				
				//编辑数量
				holder.shopcar_item_prodCount_edit.setVisibility(View.VISIBLE);
				holder.shopcar_item_prodCount_edit.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
					}
					
					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
					}
					
					@Override
					public void afterTextChanged(Editable s) {
						count = s.toString().trim();
						
						int number = Integer.parseInt(product.number);
						if(Integer.parseInt(count) > number){
							Toast.makeText(activity, "上限是" + number, Toast.LENGTH_SHORT).show();
							holder.shopcar_item_prodCount_edit.setText("");
						}else{
							CartUtil.saveCartItem(activity, product.id, count);
						}
					}

				});
			}else{
				holder.shopcar_item_delete_text.setVisibility(View.GONE);
				holder.shopcar_item_prodCount_edit.setVisibility(View.GONE);
				holder.shopcar_item_prodCount_text.setVisibility(View.VISIBLE);
				holder.shopcar_item_prodCount_text.setText(count);
			}
			
			

		} else if (position > goodslist.size()) {
			holder.shopcar_item_delete_text.setVisibility(View.GONE);
			convertView.setBackgroundResource(R.anim.submit_item_selected);
		}

		return convertView;
	}

	@Override
	public Object getItem(int position) {
		if (position < goodslist.size()) {
			return goodslist.get(position);
		} else if (position == goodslist.size()) {
			return null;
		} else {
			return noGoodslist.get(position - goodslist.size() - 1);
		}
		
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 根据是否有货区分集合
	 */
	private void devideList() {
		goodslist.clear();
		noGoodslist.clear();

		Product product = null;
		for (int i = 0; i < productlist.size(); i++) {
			product = productlist.get(i);
			if (Integer.parseInt(product.number) > 0) {
				goodslist.add(product);
			} else {
				noGoodslist.add(product);
			}
		}
		product = null;
	}

	static class CartHolder {
		ImageView shopcar_item_prodImage_img;// 商品图片
		TextView shopcar_item_prodName_text;// 商品名称
		TextView shopcar_item_prodId_text;// 商品id,编码
		TextView shopcar_item_prodPrice_text;// 商品单价
		TextView shopcar_item_prodCount_text;// 商品数量
		TextView shopcar_item_subtotal_text;// 该商品总价
		TextView shopcar_item_delete_text;// 删除
		EditText shopcar_item_prodCount_edit;// 修改商品数量
	}

	/**
	 * 主页面点击编辑按钮
	 * @param b
	 */
	public void setClickEdit(boolean clickEdit) {
		this.clickEdit = clickEdit;
		notifyDataSetChanged();
	}

}
