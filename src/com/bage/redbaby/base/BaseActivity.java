package com.bage.redbaby.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bage.redbaby.MyApp;
import com.bage.redbaby.R;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.net.ThreadPoolManager;
import com.bage.redbaby.util.NetUtil;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.lidroid.xutils.util.LogUtils;

public abstract class BaseActivity extends Activity implements OnClickListener {

	protected ProgressDialog progressDialog;
	protected ThreadPoolManager threadPoolManager;
	protected SharedPreferences sp;
	protected Editor edit;
	protected Context context;
	private TextView textTitle;
	private TextView backTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=this;
		// 去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 删除窗口背景
		getWindow().setBackgroundDrawable(null);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		// 定义弹出进度
		progressDialog = new ProgressDialog(this, R.style.Progressdialog);
		// 如果弹出进度被手动取消,则关闭当前Activity
		progressDialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				finish();
			}
		});

		threadPoolManager = MyApp.myApp.threadPoolManager;
		sp = MyApp.myApp.sp;
		edit = MyApp.myApp.edit;
		
		initView();
		initListener();
		initRequestVo();
		initDataCallBack();
		initData();
	}

	/**
	 * 加载公共的view
	 * 
	 * @param title
	 */
	protected void setBaseView(String title) {
		textTitle = (TextView) findViewById(R.id.textTitle);
		backTv = (TextView) findViewById(R.id.backTv);
		textTitle.setText(title);
		backTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	/**
	 * TODO 显示提示框
	 */
	protected void showProgressDialog() {
		if ((!isFinishing()) && (this.progressDialog == null)) {
			this.progressDialog = new ProgressDialog(this);
		}

		// this.progressDialog.setTitle(getString(R.string.loadTitle));
		this.progressDialog.setMessage(getString(R.string.LoadContent));
		this.progressDialog.show();
	}

	/**
	 * TODO 关闭提示框
	 */
	protected void closeProgressDialog() {
		if (this.progressDialog != null && progressDialog.isShowing())
			this.progressDialog.dismiss();
	}

	/**
	 * TODO 显示自定义内容提示框
	 */
	protected void showProgressDialog(String message) {
		if ((!isFinishing()) && (this.progressDialog == null)) {
			this.progressDialog = new ProgressDialog(this);
		}
		if (!progressDialog.isShowing()) {
			// this.progressDialog.setTitle(getString(R.string.loadTitle));
			this.progressDialog.setMessage(message);
			this.progressDialog.show();
		}
	}

	/**
	 * TODO 给sp存String型数据
	 */
	protected void editPutString(String key, String value) {
		edit.putString(key, value);
		edit.commit();
	}

	/**
	 * TODO 给sp存boolean型数据
	 */
	protected void editPutBoolean(String key, Boolean value) {
		edit.putBoolean(key, value);
		edit.commit();
	}

	/**
	 *  初始化布局
	 */
	public abstract void initView();

	/**
	 * 初始化监听器
	 */
	public abstract void initListener();

	/**
	 * 初始化请求工具类
	 */
	public abstract void initRequestVo();

	/**
	 * 初始化回调方法
	 */
	public abstract void initDataCallBack();

	/**
	 * 初始化数据
	 */
	public abstract void initData();

	/**
	 * 从服务器获取数据
	 * 
	 * @param vo
	 *            请求的工具类
	 * @param dataCallBack
	 *            回调接口
	 */
	protected void getDataForServer(RequestVo vo, DataCallBack<?> dataCallBack) {
		/**
		 * 需要一个handler.<BR>
		 * 需要一个子线程.<BR>
		 * 将请求参数封装
		 */
		if (vo.isShowDialog()) {
			showProgressDialog();
		}
		// 向连接池中添加一个线程任务
		threadPoolManager.addTask(new BaseRunnable(
				new BaseHandler(dataCallBack), vo));
	}

	/**
	 * 回调的接口
	 * 
	 * @author Administrator
	 * @param <T>
	 */
	public interface DataCallBack<T> {
		public abstract void processData(T obj);
	}

	class BaseHandler extends Handler {
		private DataCallBack dataCallBack;

		public BaseHandler(DataCallBack dataCallBack) {
			super();
			this.dataCallBack = dataCallBack;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			closeProgressDialog();
			switch (msg.what) {
			case ConstantRedBoy.NET_SUCCESS:
				// 数据请求成功
				if (dataCallBack != null) {
					dataCallBack.processData(msg.obj);
				}
				break;
			case ConstantRedBoy.NET_FAIL:
				// 数据请求失败
				LogUtils.i("sorry,数据请求失败");
				break;
			case ConstantRedBoy.NO_NET:
				// 没网络
				LogUtils.i("亲,请检查您的网络连接");
				break;
			}
			super.handleMessage(msg);
		}
	}

	protected class BaseRunnable implements Runnable {
		private BaseHandler baseHandler;
		private RequestVo vo;

		public BaseRunnable(BaseHandler baseHandler, RequestVo vo) {
			super();
			this.baseHandler = baseHandler;
			this.vo = vo;
		}

		@Override
		public void run() {
			Message msg = Message.obtain();
			try {
				if (NetUtil.hasConnectedNetwork(getApplicationContext())) {
					// 说明能上网
					Object obj=null;
					if(vo.getType().equals("get")){
						obj = NetUtil.get(vo);
					}else{
						obj = NetUtil.post(vo);
					}
					if (obj != null) {
						msg.obj = obj;
						msg.what = ConstantRedBoy.NET_SUCCESS;
					}else{
						msg.what = ConstantRedBoy.NET_FAIL;
					}
				} else {
					msg.what = ConstantRedBoy.NO_NET;
				}
			} catch (Exception e) {
				msg.what = ConstantRedBoy.NET_FAIL;
				e.printStackTrace();
			}
			baseHandler.sendMessage(msg);
		}

	}
}
