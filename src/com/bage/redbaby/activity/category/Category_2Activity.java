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
import com.bage.redbaby.activity.category.CategoryActivity.Holder;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.bean.Category;
import com.bage.redbaby.bean.Category.CategoryItem;

/**
 * 这是分类浏览的第二个界面
 * 
 * @author mine
 * 
 */
public class Category_2Activity extends BaseActivity {
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
		Category_2Adapter category_2Adapter = new Category_2Adapter(aList,
				context);
		categoryList.setAdapter(category_2Adapter);
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
			startActivity(new Intent(Category_2Activity.this,
					CategoryActivity.class));
			break;

		}
	}

	@Override
	public void initListener() {
		titleBack.setOnClickListener(this);
		categoryList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
//				if (aList.get(position).isleafnode) {
//					startActivity(new Intent(SecondCategoryActivity.this,DisplayGoodsActivity.class));
//					return ;
//				}
				String myId = aList.get(position).id+"";
				String title = aList.get(position).name;
				Intent intent = new Intent(Category_2Activity.this,Category_3Activity.class);
				intent.putExtra("parentId", myId);
				intent.putExtra("title", title);
				startActivity(intent);
			}
		});
	}

	class Category_2Adapter extends CategoryAdapter<CategoryItem> {

		public Category_2Adapter(List<CategoryItem> list, Context context) {
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
