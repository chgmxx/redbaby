package com.bage.redbaby.activity.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.KeyEvent.DispatcherState;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bage.redbaby.MyApp;
import com.bage.redbaby.R;
import com.bage.redbaby.activity.adapter.CategoryAdapter;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.bean.Category;
import com.bage.redbaby.bean.Category.CategoryItem;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.parser.CategoryParser;
import com.bage.redbaby.util.BaseParamsMapUtil;
import com.bage.redbaby.util.ImageUtil;
import com.bage.redbaby.util.ImageUtil.ImageCallback;
import com.bage.redbaby.util.myutil.ConstantRedBoy;

/**
 * 这是分类页面的第一级分类页面
 * 
 * @author mine
 * 
 */
public class CategoryActivity extends BaseActivity {
	// 保存分类的数据
	private List<CategoryItem> list;
	// 第一级的集合
	private List<CategoryItem> aList = new ArrayList<Category.CategoryItem>();

	private RequestVo categoryVo;
	private DataCallBack<Category> dataCallback;
	protected Category_1Adapter adapter;

	// 用来填充第一级的内容
	private ListView categoryList;
	// 用来显示分类头部的文字
	private TextView categoryTitle;
	// 请求网络是显示的进度条
	private TextView categoryEmptyListTv;

	/**
	 * 初始化页面
	 */
	@Override
	public void initView() {
		setContentView(R.layout.category_activity);
		categoryTitle = (TextView) findViewById(R.id.categoryTitle);
		categoryList = (ListView) findViewById(R.id.categoryList);
		categoryEmptyListTv = (TextView) findViewById(R.id.categoryEmptyListTv);
		categoryEmptyListTv.setVisibility(View.INVISIBLE);
	}

	/**
	 * 初始化数据
	 */
	@Override
	public void initData() {
		categoryTitle.setText("分类浏览");
		getDataForServer(categoryVo, dataCallback);
	}

	@Override
	public void initRequestVo() {
		// 请求的URL地址
		String requestUrl = ConstantRedBoy.BASEURL + "category";
		// 设置解析工具类
		CategoryParser categoryParser = new CategoryParser();
		// 设置请求工具类
		categoryVo = new RequestVo(requestUrl, this,
				CategoryParams.getVersion(this), categoryParser);
		// 缓存相关
		categoryVo.isSaveLocal = true;

	}

	/**
	 * 为填充ListView做准备，把网络请求的javabean分为一个个的list集合
	 */
	@Override
	public void initDataCallBack() {
		dataCallback = new DataCallBack<Category>() {
			// TODO 缓存可能相关
			@Override
			public void processData(Category category) {
				// 保存version数据
				edit.putString("version", category.version);
				// 先清除
				aList.clear();
				list = category.category;
				// 方法Application中
				MyApp.myApp.object = list;

				for (int i = 0; i < list.size(); i++) {
					CategoryItem categoryItem = list.get(i);
					if (categoryItem.parent_id == 0) {
						aList.add(categoryItem);
					}
				}
				adapter = new Category_1Adapter(aList, CategoryActivity.this);
				// 请求到数据就GONE掉
				categoryEmptyListTv.setVisibility(View.GONE);
				categoryList.setAdapter(adapter);
			}

		};

	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void initListener() {
		categoryList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// if (aList.get(position).isleafnode) {
				// startActivity(new
				// Intent(CategoryActivity.this,DisplayGoodsActivity.class));
				// }
				String parentId = aList.get(position).id + "";
				String title = aList.get(position).name;
				Intent intent = new Intent(CategoryActivity.this,
						Category_2Activity.class);
				intent.putExtra("parentId", parentId);
				intent.putExtra("title", title);
				startActivity(intent);
			}
		});
	}

	/**
	 * 这个类里面返回一个对应请求参数的Map
	 * 
	 * @author mine
	 * 
	 */
	static class CategoryParams extends BaseParamsMapUtil {
		private static SharedPreferences sp = MyApp.myApp.sp;

		public static Map<String, String> getVersion(Context context) {
			Map<String, String> paramsMap = getParamsMap(context);
			// TODO 这个版本号应该存起来,存在sp里,在网络请求后存储
			paramsMap.put("version", sp.getString("version", "0"));
			return paramsMap;
		}

	}

	ImageCallback callback = new ImageCallback() {
		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			adapter.notifyDataSetChanged();
		}
	};

	class Category_1Adapter extends CategoryAdapter<CategoryItem> {

		public Category_1Adapter(List<CategoryItem> list, Context context) {
			super(list, context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.category_item, null);
				holder = new Holder();
				holder.imgIcon = (ImageView) convertView
						.findViewById(R.id.imgIcon);
				holder.textContent = (TextView) convertView
						.findViewById(R.id.textContent);
				holder.item_describe = (TextView) convertView
						.findViewById(R.id.item_describe);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.textContent.setText(aList.get(position).name);
			holder.item_describe.setText(aList.get(position).tag);
			String pic = aList.get(position).pic;
			Bitmap mbitmap = ImageUtil.loadImageDefault(context,
					context.getCacheDir(), ConstantRedBoy.BASEURL + pic,
					callback);
			if (mbitmap != null) {
				holder.imgIcon.setImageBitmap(mbitmap);
			} else {
				holder.imgIcon.setImageResource(R.drawable.product_loading);
			}

			return convertView;
		}

	}

	class Holder {
		public ImageView imgIcon;
		// 填写内容
		public TextView textContent;
		// 填写Tag
		public TextView item_describe;
	}
}
