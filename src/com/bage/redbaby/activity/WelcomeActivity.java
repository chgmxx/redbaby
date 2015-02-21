package com.bage.redbaby.activity;
import java.io.File;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import com.bage.redbaby.R;
import com.bage.redbaby.activity.custome.AccountCenterActivity;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.domain.VersionCode;
import com.bage.redbaby.domain.VersionCode.Version;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.parser.VersionPaser;
import com.bage.redbaby.util.LogUtils;
import com.bage.redbaby.util.NetUtil;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.bage.redbaby.util.myutil.ParamsMapsUtils;

/**
 *项目的主页面
 * @author 杜磊
 */
public class WelcomeActivity extends BaseActivity {
	private RequestVo vo;

	/**
	 * 消息处理机制(很重要)
	 */
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Intent intent = new Intent(WelcomeActivity.this, AccountCenterActivity.class);
			startActivity(intent);
			finish();
			switch (msg.what) {
			case ConstantRedBoy.NET_SUCCESS://请求成功
				intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				intent.addCategory("android.intent.category.DEFAULT");
				intent.setDataAndType(Uri.fromFile(new File(Environment
						.getExternalStorageDirectory(), "temp.apk")),
						"application/vnd.android.package-archive");
				startActivityForResult(intent, 0);

				break;
			case ConstantRedBoy.NET_FAIL://请求失败
				LogUtils.toast(WelcomeActivity.this, "下载失败");
				loadMainActivity();
				break;
			case ConstantRedBoy.NO_NET://没有网络
				closeProgressDialog();
				loadMainActivity();
				break;
			}
			switch (msg.what) {
			case ConstantRedBoy.NET_SUCCESS://请求成功
				intent = new Intent();
				
				intent.setAction("android.intent.action.VIEW");
				intent.addCategory("android.intent.category.DEFAULT");

				intent.setDataAndType(Uri.fromFile(new File(Environment
						.getExternalStorageDirectory(), "temp.apk")),
						"application/vnd.android.package-archive");
				startActivityForResult(intent, 0);

				break;
			case ConstantRedBoy.NET_FAIL://请求失败
				LogUtils.toast(WelcomeActivity.this, "下载失败");
				loadMainActivity();
				break;
			case ConstantRedBoy.NO_NET://没有网络
				closeProgressDialog();
				loadMainActivity();
				break;
			}
		};
	};

	/**
	 * 定义初始事件
	 */
	private long starttime;

	/**
	 * 初始化布局
	 */
	@Override
	public void initView() {
		starttime = System.currentTimeMillis();
		setContentView(R.layout.welcome_activity);

	}

	/**
	 * 请求工具类
	 */
	@Override
	public void initRequestVo() {
		String url = ConstantRedBoy.BASEURL + "version";
		vo = new RequestVo(url, this, ParamsMapsUtils.getString(this),
				new VersionPaser());
	}

	/**
	 * 初始化数据
	 */
	@Override
	public void initData() {
		checkVersion();

	}

	/**
	 * 点击事件
	 */
	@Override
	public void onClick(View v) {

	}



	/**
	 * 检验版本号
	 */
	private void checkVersion() {
		// 请求url
		try {

			PackageInfo packageInfo = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			final String versionName = packageInfo.versionName;
			System.out.println(versionName);
			vo.setShowDialog(false);
			getDataForServer(vo, new DataCallBack<VersionCode>() {
				@Override
				public void processData(VersionCode obj) {
					String version = obj.version.version;
					if (version.equals(versionName)) {
						loadMainActivity();
					} else {
						updateVersion(obj.version);
					}
				}

			});

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		/**
		 * 模拟数据
		 */
		//TODO : 模拟数据
	}

	/**
	 * 更新版本
	 * @param 
	 */
	private void updateVersion(Version info) {
		final String url = ConstantRedBoy.BASEURL + info.url;

		
		Builder builder = new Builder(this);
		builder.setTitle("是否需要升级");
		builder.setMessage("有新的版本 : 亲,您需要去进行更新吗 ? ");
       
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				loadMainActivity();
			}
		});

		builder.setNegativeButton("下次再说,好吗?",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						loadMainActivity();
					}
				});

		builder.setPositiveButton("立刻更新啊",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.out.println("下载：" + url);

						download(url);
					}
				});
		builder.show();
	}

	/**
	 * 去安装相应的应用
	 * 
	 * @param url
	 */
	private void download(final String url) {

		new Thread() {
			public void run() {
				boolean isTrue = NetUtil.urlDownloadToFile(WelcomeActivity.this,
						url, "/mnt/sdcard/temp.apk");

				final Message msg = new Message();

				if (isTrue) {
					msg.what = ConstantRedBoy.NET_SUCCESS;
				} else {
					msg.what = ConstantRedBoy.NET_FAIL;
				}
				handler.sendMessage(msg);
			};
		}.start();
	}

	/**
	 * 跳转到主界面
	 */
	private void loadMainActivity() {
		long endTime = System.currentTimeMillis();
		final long time = endTime - starttime;
		if (time < 3000) {
			new Thread() {
				public void run() {
					SystemClock.sleep(3000 - time);
				};
			}.start();
		}
		
		//ActivityUtils.startActivityAndFinish(this, HelpCenterActivity.class);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		loadMainActivity();
	}


	@Override
	public void initDataCallBack() {

	}

	@Override
	public void initListener() {
		// TODO Auto-generated method stub
		
	}

}
