package com.bage.redbaby.activity.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bage.redbaby.R;
import com.bage.redbaby.domain.ProductDetailBean.Product;
import com.bage.redbaby.util.ImageUtil;
import com.bage.redbaby.util.ImageUtil.ImageCallback;
import com.bage.redbaby.util.myutil.ConstantRedBoy;

/**
 * 轮播图片数据适配器
 * 
 * @author jun
 * 
 */
public class ProductViewPagerAdapter extends PagerAdapter {

	private Context context;
	private Product product;
	public ProductViewPagerAdapter(Context context,Product product) {
		super();
		this.context = context;
		this.product = product;
	}
	/**
     * 图片解析成功回调
     */
	private ImageCallback imageCallback=new ImageCallback() {
		
		@Override
		public void loadImage(Bitmap bitmap, String imagePath) {
		     notifyDataSetChanged();
			
		}
	};
	@Override
	public int getCount() {
		return product.pic.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView=new ImageView(context);
		String picUrl = ConstantRedBoy.BASEURL+"/images/product_04.jpg";//product.pic[0];
		Bitmap localBitmap=ImageUtil.loadImageDefault(context,context.getCacheDir(),picUrl,imageCallback);
		if(localBitmap!=null){
			imageView.setImageBitmap(localBitmap);
		}else{
            Drawable drawable=context.getResources().getDrawable(R.drawable.product_loading);
            BitmapDrawable bd=(BitmapDrawable)drawable;
            imageView.setImageBitmap(bd.getBitmap());
		}
		imageView.setScaleType(ScaleType.FIT_XY);
		  
		ViewPager viewPager = (ViewPager) container;
		viewPager.addView(imageView);
		return imageView;
	}



	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}
	
	@Override
	public int getItemPosition(Object object) {
	        
		return POSITION_NONE;
	}
}
