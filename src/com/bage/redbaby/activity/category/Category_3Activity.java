package com.bage.redbaby.activity.category;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.bage.redbaby.MyApp;
import com.bage.redbaby.R;
import com.bage.redbaby.activity.adapter.CategoryAdapter;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.bean.Category;
import com.bage.redbaby.bean.Category.CategoryItem;

/**
 * 这是分类浏览的第三个界面
 * 
 * @author mine
 * 
 */
public class Category_3Activity extends BaseActivity {
	private List<CategoryItem> list;
	private String parentId;
	private List<CategoryItem> aList = new ArrayList<Category.CategoryItem>();
	private TextView titleBack;
	private TextView categoryTitle;
	private ListView categoryList;

	@Override
	public void initView() {
		setContentView(R.layout.category_child_activity);
		titleBack = (TextView) findViewById(R.id.titleBack);
		categoryTitle = (TextView) findViewById(R.id.categoryTitle);
		categoryList = (ListView) findViewById(R.id.categoryList);
	}

	@Override
	public void initData() {
		list = (List<CategoryItem>) MyApp.myApp.object;
		Intent intent = getIntent();
		parentId = intent.getStringExtra("parentId");
		for (int i = 0; i < list.size(); i++) {
			CategoryItem categoryItem = list.get(i);
			if (parentId.equals(categoryItem.parent_id + "")) {
				aList.add(list.get(i));
			}
		}
		String title = intent.getStringExtra("title");
		categoryTitle.setText(title);
		Category_3Adapter category_3Adapter = new Category_3Adapter(aList,
				context);
		categoryList.setAdapter(category_3Adapter);
	}

	@Override
	public void initRequestVo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initDataCallBack() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titleBack:
//			Intent intent = new Intent(Category_3Activity.this, Category_2Activity.class);
//			startActivity(intent);
			finish();
		}
	}

	@Override
	public void initListener() {
		titleBack.setOnClickListener(this);
		categoryList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Category_3Activity.this,CategoryListActivity.class);
//				intent.putExtra("parentId", myId);
//				intent.putExtra("title", title);
				startActivity(intent);
			}
		});
	}

	class Category_3Adapter extends CategoryAdapter<CategoryItem> {

		public Category_3Adapter(List<CategoryItem> list, Context context) {
			super(list, context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.category_child_item, null);
				holder = new ViewHolder();
				holder.textContent = (TextView) convertView
						.findViewById(R.id.textContent);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.textContent.setText(aList.get(position).name);
			return convertView;
		}

	}

	class ViewHolder {
		TextView textContent;
	}
}
