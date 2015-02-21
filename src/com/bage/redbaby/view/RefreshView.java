package com.bage.redbaby.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bage.redbaby.R;
public class RefreshView extends ListView {
	private View headView;// 头布局
	private int headHeight;// 头布局的高度

	private ImageView iv_arrow;// 下拉刷新的箭头
	private ProgressBar pb;// 正在刷新的进度条
	private TextView title;// 下拉刷新的文本
	private TextView time;// 下拉刷新的时间

	private int downY;// 按下是Y轴坐标

	private final int Down_PULL = 0;// 头布局状态 下拉刷新
	private final int RELEASE_REFRESH = 1;// 头布局状态 释放刷新
	private final int REFRESHING = 2;// 头布局状态 正在刷新中...

	private int currentState = Down_PULL;// 设置当前头布局的状态为 下拉刷新

	private int footState = Down_PULL;// 设置当前脚布局的状态为正在加载

	private RotateAnimation upAnimation;
	private RotateAnimation downAnimation;
	private onRefreshingListener listener;
	private View footerView;
	private int footerHeight;
	private boolean isLoadMore = false;
	private onClickItemListener itemListener;

	/**
	 * 代码创建ListView 走的方法
	 * 
	 * @param context
	 */
	public RefreshView(Context context) {
		super(context);
		init();
	}

	/**
	 * 框架解析xml文件时走的方法
	 * 
	 * @param context
	 * @param attrs
	 */
	public RefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 初始化的方法
	 */
	private void init() {
		// 初始化头布局
		initHeaderView();
		initFooterView();
	}

	// 加载脚布局
	private void initFooterView() {
		footerView = View.inflate(getContext(), R.layout.listview_footer, null);
		footerView.measure(0, 0);
		footerHeight = footerView.getMeasuredHeight();
		// 先把它给隐藏拉
		footerView.setPadding(0, -footerHeight, 0, 0);
		this.addFooterView(footerView);
	}

	private void initHeaderView() {
		headView = View.inflate(getContext(), R.layout.listview_header, null);
		iv_arrow = (ImageView) headView.findViewById(R.id.iv_listview_header_arrow);
		pb = (ProgressBar) headView.findViewById(R.id.pb_listview_header);
		title = (TextView) headView.findViewById(R.id.tv_listview_header_state);
		time = (TextView) headView.findViewById(R.id.tv_listview_header_last_update_time);
		time.setText("最后刷新的时间为" + getCurrentTime());

		// 让系统框架去帮我们测量headView的宽高
		headView.measure(0, 0);
		headHeight = headView.getMeasuredHeight();
		headView.setPadding(0, -headHeight, 0, 0);
		this.addHeaderView(headView);
		// 初始化动画
		initAnimation();
	}

	private void initAnimation() {
		upAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		upAnimation.setDuration(500);
		upAnimation.setFillAfter(true);// 让动画停留在最终画面上

		downAnimation = new RotateAnimation(-180, -360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		downAnimation.setDuration(500);
		downAnimation.setFillAfter(true);
	}

	/**
	 * 当用户手指移动的时候调用的方法
	 */
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:// 手指按下的时候
			downY = (int) ev.getY();
			if (itemListener != null) {
				// System.out.println("点击被调用啦"+getCheckedItemPosition());
				itemListener.onItemClick(getCheckedItemPosition());
			}
			break;
		case MotionEvent.ACTION_MOVE:// 手指移动的时候
			
			int moveY = (int) ev.getY();
			// 得到手指的偏移距离
			int dY = moveY - downY;
			if (dY > 0) {
				if (currentState == REFRESHING) {// 说明是正在刷新中...
					break;
				}
				// 得到新的top值
				int paddingTop = -headHeight + dY;
				// 如果是第一个条目并且是向下拉的情况下
				if (getFirstVisiblePosition() == 0 && paddingTop > -headHeight) {
					// 加判断 判断各个刷新的状态
					if (paddingTop > 20) {// 防止拖动太多
						paddingTop = 20;
					}
					if (paddingTop > 0 && currentState == Down_PULL) {// 说明是下拉刷新的状态
																		// //
																		// 并且控件已完全显示
						currentState = RELEASE_REFRESH;// 更改状态释放刷新
						refreshHeadViewState();
					} else if (paddingTop < 0
							&& currentState == RELEASE_REFRESH) {// 说明释放刷新的状态
						currentState = Down_PULL;// 更改状态为下拉刷新
						refreshHeadViewState();
					}
					headView.setPadding(0, paddingTop, 0, 0);
				}
			} else {// 说明是从下往上 加载脚布局
					// System.out.println("到了没有");
				if (footState ==REFRESHING) {
					break;
				}
				
				int paddingTop = -footerHeight - dY;
				if ((getLastVisiblePosition() == (getCount() - 1) && paddingTop > -footerHeight)) {
					// System.out.println("paddingTop"+paddingTop);
					
					if (paddingTop > 0 && footState == Down_PULL) {
						footState = RELEASE_REFRESH;
					} else if (paddingTop < 0 && footState == RELEASE_REFRESH) {
						footState = Down_PULL;
					}
					footerView.setPadding(0, 0, 0, paddingTop);
				}
				
			}

			break;
		case MotionEvent.ACTION_UP:// 手指离开的时候
			if (currentState == Down_PULL) {// 说明是下拉刷新放开了,这个时候什么都不做,直接隐藏头布局即可
				headView.setPadding(0, -headHeight, 0, 0);
			} else if (currentState == RELEASE_REFRESH) {// 说明是释放刷新,并且手放开了
															// 这个时候应该刷新姐买呢
				currentState = REFRESHING;
				headView.setPadding(0, 0, 0, 0);// 让头布局完全显示
				refreshHeadViewState();
				if (listener != null) {
					// 这里提供监听接口的方法 让外界调用
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							listener.onPullDownRefresh();
							onRefreshFinsh();
						}
					}, 5000);
				}
			}
			if (footState == Down_PULL) {
				footerView.setPadding(0, -footerHeight, 0, 0);
			} else if (footState == RELEASE_REFRESH) {
				isLoadMore = true;
				footState = REFRESHING;
				footerView.setPadding(0, 0, 0, 0);
				//这个是增强用户体验,让一下拉就让用户看到下拉的布局对象,不然就要自己用手拉
				setSelection(getCount());
				if (listener != null) {
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							listener.onLoadMore();
							onRefreshFinsh();
						}
					}, 5000);
				}
			}
			break;

		default:
			break;
		}

		return super.onTouchEvent(ev);
	}

	/**
	 * 当完成下拉刷新时 调用的方法
	 */
	public void onRefreshFinsh() {
		if (isLoadMore) {
			footState=Down_PULL;
			isLoadMore = false;
			footerView.setPadding(0, -footerHeight, 0, 0);
		} else {
			// 隐藏头布局
			headView.setPadding(0, -headHeight, 0, 0);
			// 将状态复原
			currentState = Down_PULL;
			pb.setVisibility(View.INVISIBLE);
			iv_arrow.setVisibility(View.VISIBLE);
			title.setText("下拉刷新");
			time.setText("最后刷新的时间为" + getCurrentTime());
		}

	}

	/**
	 * 格式化当前时间
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	private String getCurrentTime() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf.format(new Date());
	}

	/**
	 * 更新头布局的UI
	 */
	private void refreshHeadViewState() {
		switch (currentState) {
		case Down_PULL:// 说明时下拉刷新
			iv_arrow.startAnimation(downAnimation);
			title.setText("下拉刷新");
			break;
		case RELEASE_REFRESH:// 说明是松开刷新
			iv_arrow.startAnimation(upAnimation);
			title.setText("松开刷新");
			break;
		case REFRESHING:// 说明是正在刷新中....
			// 先清除自己身上的动画 否则下一次没办法加载
			iv_arrow.clearAnimation();
			// 讲起设置为不可见
			iv_arrow.setVisibility(View.INVISIBLE);
			pb.setVisibility(View.VISIBLE);
			title.setText("正在刷新中");
			break;
		}
	}

	/**
	 * 设置监听器的方法
	 * 
	 * @param listener
	 */
	public void setOnRefreshingListener(onRefreshingListener listener) {
		this.listener = listener;

	}

	public interface onRefreshingListener {
		/**
		 * 加载更多的时候调用的方法<br/>
		 * 此方法已实现在子线程中执行,请不要再开子线程,并且不要使用任何更改UI的操作
		 */
		public void onLoadMore();

		/**
		 * 下拉刷新的时候调用的方法<br/>
		 * 此方法已实现在子线程中执行,请不要再开子线程,并且不要使用任何更改UI的操作
		 */
		public void onPullDownRefresh();
	}

	public interface onClickItemListener {
		public void onItemClick(int position);
	}

	/**
	 * 点击条目的监听器
	 * 
	 * @param itemListener
	 */
	public void setOnClickItemListener(onClickItemListener itemListener) {
		this.itemListener = itemListener;
	}

}
