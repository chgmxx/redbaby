package com.bage.redbaby.activity.adapter;

import com.bage.redbaby.MyApp;
import com.bage.redbaby.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=View.inflate(MyApp.myApp.context, R.layout.hot_words__items, null);
		TextView textHotWords=(TextView) view.findViewById(R.id.textHotWords);
		if(position==0){
			textHotWords.setText("adidas");
		}else if(position==1){
			textHotWords.setText("361");
		}else if(position==2){
			textHotWords.setText("nike");
		}
		
		return view;
	}

}
