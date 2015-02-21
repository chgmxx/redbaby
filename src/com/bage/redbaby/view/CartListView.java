package com.bage.redbaby.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 自定义ListView,实现在ScrollView中完全滚动
 * 
 * @author baiiu
 * 
 */
public class CartListView extends ListView {

	public CartListView(Context context) {
		this(context, null);
	}

	public CartListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);

		super.onMeasure(widthMeasureSpec, expandSpec);
	}
	
}
