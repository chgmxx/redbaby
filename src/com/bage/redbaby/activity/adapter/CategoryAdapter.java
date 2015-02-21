package com.bage.redbaby.activity.adapter;

import java.util.List;

import com.bage.redbaby.bean.Category.CategoryItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 用于Category模块填充ListView的一个工具类
 * 
 * @author mine
 * @param <T>
 * 
 */
public abstract class CategoryAdapter<T> extends BaseAdapter {
	public List<T> list;
	public Context context;

	public CategoryAdapter(List<T> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
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
	public abstract View getView(int position, View convertView,
			ViewGroup parent);

}
