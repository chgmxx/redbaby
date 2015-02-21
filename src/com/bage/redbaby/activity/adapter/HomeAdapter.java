package com.bage.redbaby.activity.adapter;

import java.util.List;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bage.redbaby.R;
import com.bage.redbaby.activity.HomeActivity;
import com.bage.redbaby.domain.HomeBanner;
import com.bage.redbaby.util.ImageUtil;
import com.bage.redbaby.util.ImageUtil.ImageCallback;
import com.bage.redbaby.util.myutil.ConstantRedBoy;

public class HomeAdapter extends PagerAdapter {
	List<HomeBanner> list;
	private HomeActivity activity;
	
	
	public HomeAdapter(List<HomeBanner> paramObject,HomeActivity activity) {
		list= paramObject;
		this.activity=activity;
	}
	
	@Override
	public int getCount() {
		if(list!=null){
			return list.size();
		}else{
			return 0;
		}
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		final HomeBanner limitbuy=list.get(position);
		Bitmap mbitmap=ImageUtil.loadImageDefault(activity, activity.getCacheDir(), ConstantRedBoy.BASEURL+"/"+limitbuy.getPic(), callback);
		ImageView imageView=new ImageView(activity);
		if (mbitmap!=null) {
			imageView.setImageBitmap(mbitmap);
		}else{
			imageView.setImageResource(R.drawable.product_loading);
		}
		imageView.setScaleType(ScaleType.FIT_XY);
		
		final ViewPager viewPager=(ViewPager) container;
		viewPager.addView(imageView);
		
		
		
		
		
		
		return imageView;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager)container).removeView((View)object);
	}
	
	
	ImageCallback callback=new ImageCallback() {
		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
			notifyDataSetChanged();
		}
	};
	
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	};

}
