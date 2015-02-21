package com.bage.redbaby.activity.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 * ListView的适配器类  个人使用
 * @author 程王勇
 *
 * @param <T>
 */
public abstract class MyListViewAdapter<T> extends BaseAdapter {

	public List<T> list;
	public Context context;

	public MyListViewAdapter(List<T> list, Context context) {
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
