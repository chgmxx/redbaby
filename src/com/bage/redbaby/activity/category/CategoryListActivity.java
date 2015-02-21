package com.bage.redbaby.activity.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bage.redbaby.MyApp;
import com.bage.redbaby.R;
import com.bage.redbaby.activity.adapter.CategoryAdapter;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.bean.ProductList;
import com.bage.redbaby.bean.ProductList.Productlist;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.parser.ProductListParser;
import com.bage.redbaby.util.BaseParamsMapUtil;
import com.bage.redbaby.util.ImageUtil;
import com.bage.redbaby.util.ImageUtil.ImageCallback;
import com.bage.redbaby.util.myutil.ConstantRedBoy;

public class CategoryListActivity extends BaseActivity {
	private TextView textTitle;
	private TextView backTv;
	private Button productlist_sift;
	private TextView textRankSale;
	private TextView textRankPrice;
	private TextView textRankGood;
	private TextView textRankTime;
	private ListView productLists;
	private TextView textNull;
	private ProductAdapter adapter;
	// 商品列表集合
	private List<Productlist> productlists;
	// 需要显示的集合
	private List<Productlist> aProductlist = new ArrayList<ProductList.Productlist>();
	private RequestVo productVo;
	private DataCallBack<ProductList> dataCallback;

	@Override
	public void initView() {
		setContentView(R.layout.product_list_activity);
		textTitle = (TextView) findViewById(R.id.textTitle);
		backTv = (TextView) findViewById(R.id.backTv);
		productlist_sift = (Button) findViewById(R.id.productlist_sift);
		productLists = (ListView) findViewById(R.id.productList);
		textNull = (TextView) findViewById(R.id.textNull);
		textNull.setVisibility(View.INVISIBLE);
	}

	@Override
	public void initData() {
		backTv.setText("返回");
		textTitle.setText("商品列表");
		getDataForServer(productVo, dataCallback);
	}

	/**
	 * 这个类里面返回一个对应请求参数的Map
	 * 
	 * @author mine
	 * 
	 */
	static class CategoryParams extends BaseParamsMapUtil {
		public static Map<String, String> getProduct(Context context,
				String page, String pageNum, String cId, String orderby,
				String filter) {
			Map<String, String> paramsMap = getParamsMap(context);
			paramsMap.put("page", page);
			paramsMap.put("pageNum", pageNum);
			paramsMap.put("cId", cId);
			paramsMap.put("orderby", orderby);
			paramsMap.put("filter", filter);
			return paramsMap;
		}
	}

	@Override
	public void initRequestVo() {
		// 请求url地址
		String requestUrl = ConstantRedBoy.BASEURL + "productlist";
		// 设置解析工具类
		ProductListParser productListParser = new ProductListParser();
		Map<String, String> requestDataMap = CategoryParams.getProduct(context,
				1 + "", 8 + "", 1010101 + "", "price_down", "c182d2");
		// 设置请求工具类
		productVo = new RequestVo(requestUrl, this, requestDataMap,
				productListParser);
		// 缓存相关
		productVo.isSaveLocal = true;
	}

	@Override
	public void initDataCallBack() {
		dataCallback = new DataCallBack<ProductList>() {
			@Override
			public void processData(ProductList productList) {
				CategoryParams.getProduct(context, 1 + "", 8 + "",
						1010101 + "", "price_down", "c182d2");
				// 先清除
				aProductlist.clear();
				productlists = productList.productlist;
				for (int i = 0; i < productlists.size(); i++) {
					if (productlists.get(i).id == 101) {
						aProductlist.add(productlists.get(i));
					}
				}
				adapter = new ProductAdapter(aProductlist,
						CategoryListActivity.this);
				productLists.setAdapter(adapter);
			}
		};
	}

	@Override
	public void initListener() {
		backTv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backTv:
			finish();
			break;

		}
	}

	ImageCallback callback = new ImageCallback() {
		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			adapter.notifyDataSetChanged();
		}
	};

	/**
	 * ListView数据填充器
	 */
	class ProductAdapter extends CategoryAdapter<Productlist> {
		public ProductAdapter(List<Productlist> list, Context context) {
			super(list, context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.product_list_items, null);
				holder = new ViewHolder();
				holder.goodsIconIv = (ImageView) convertView
						.findViewById(R.id.goodsIconIv);
				holder.textClothesName = (TextView) convertView
						.findViewById(R.id.textClothesName);
				holder.textClothesPrice = (TextView) convertView
						.findViewById(R.id.textClothesPrice);
				holder.textMarketPrice = (TextView) convertView
						.findViewById(R.id.textMarketPrice);
				holder.textProductCommentNum = (TextView) convertView
						.findViewById(R.id.textProductCommentNum);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.textClothesName.setText(aProductlist.get(position).name);
			holder.textClothesPrice.setText(aProductlist.get(position).price
					+ "");
			holder.textMarketPrice
					.setText(aProductlist.get(position).marketprice + "");
			holder.textProductCommentNum
					.setText(aProductlist.get(position).comment_count + "");
			String pic = aProductlist.get(position).pic;
			Bitmap bitmap = ImageUtil.loadImageDefault(context,
					context.getCacheDir(), ConstantRedBoy.BASEURL + pic,
					callback);
			if (bitmap != null) {
				holder.goodsIconIv.setImageBitmap(bitmap);
			} else {
				holder.goodsIconIv.setImageResource(R.drawable.product_loading);
			}
			return convertView;
		}
	}

	class ViewHolder {
		// 商品图片
		ImageView goodsIconIv;
		// 商品名
		TextView textClothesName;
		// 商品价格
		TextView textClothesPrice;
		// 商品市场价
		TextView textMarketPrice;
		// 商品评价数
		TextView textProductCommentNum;
	}

}
