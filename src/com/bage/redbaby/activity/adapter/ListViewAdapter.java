package com.bage.redbaby.activity.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bage.redbaby.MyApp;
import com.bage.redbaby.R;

public class ListViewAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		return 5;
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
		View view = View.inflate(MyApp.myApp.context, R.layout.home_item, null);
		TextView textContent = (TextView) view.findViewById(R.id.textContent);
		ImageView imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
		ImageView imgArrow = (ImageView) view.findViewById(R.id.imgArrow);

		if (position == 0) {
			textContent.setText("限时抢购");
			imgIcon.setBackgroundResource(R.drawable.home_classify_01);
		} else if (position == 1) {
			textContent.setText("促销快报");
			imgIcon.setBackgroundResource(R.drawable.home_classify_02);
		} else if (position == 2) {
			textContent.setText("新品上架");
			imgIcon.setBackgroundResource(R.drawable.home_classify_03);
		} else if (position == 3) {
			textContent.setText("热门单品");
			imgIcon.setBackgroundResource(R.drawable.home_classify_04);

		} else if (position == 4) {
			textContent.setText("推荐品牌");
			imgIcon.setBackgroundResource(R.drawable.home_classify_05);
		}

		return view;
	}

}
