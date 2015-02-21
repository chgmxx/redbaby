package com.bage.redbaby.activity.payment;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bage.redbaby.R;
import com.bage.redbaby.base.BaseActivity;
import com.bage.redbaby.bean.Checkout;
import com.bage.redbaby.net.RequestVo;
import com.bage.redbaby.parser.PaymentParser;
import com.bage.redbaby.util.ActivityUtils;
import com.bage.redbaby.util.myutil.ConstantRedBoy;
import com.bage.redbaby.util.myutil.ParamsMapsUtils;

/**
 * 结算中心
 * 
 * @author WB
 * 
 */
public class PaymentCenterActivity extends BaseActivity {

	private RequestVo vo;
	private DataCallBack<Checkout> dataCallBack;
	private RelativeLayout payment_address_rel;
	private RelativeLayout payment_payType_rel;
	private RelativeLayout payment_sendTime_rel;
	private RelativeLayout payment_invoice_rel;
	private RelativeLayout payment_remark_rel;
	protected Checkout obj;
	private TextView payment_username_text;
	private TextView payment_addressArea_text;
	private TextView payment_addressDetail_text;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.payment_address_rel:
			ActivityUtils.startActivityForResult(this,
					AdressManageActivity.class, "address", 0);
			break;
		case R.id.payment_payType_rel:
			ActivityUtils.startActivityForResult(this, PayTypeActivity.class,
					"paytype", 0);
			break;
		case R.id.payment_sendTime_rel:
			ActivityUtils.startActivityForResult(this, SendTimeActivity.class,
					"sendtime", 0);
			break;
		case R.id.payment_invoice_rel:
			ActivityUtils.startActivityForResult(this, InvoiceActivity.class,
					"invoice", 0);
			break;
		case R.id.payment_remark_rel:
			ActivityUtils.startActivityForResult(this, RemarkActivity.class,
					"remark", 0);
			break;

		default:
			break;
		}
	}

	@Override
	public void initView() {
		setContentView(R.layout.payment_center_activity);
		payment_address_rel = (RelativeLayout) findViewById(R.id.payment_address_rel);
		payment_payType_rel = (RelativeLayout) findViewById(R.id.payment_payType_rel);
		payment_sendTime_rel = (RelativeLayout) findViewById(R.id.payment_sendTime_rel);
		payment_invoice_rel = (RelativeLayout) findViewById(R.id.payment_invoice_rel);
		payment_remark_rel = (RelativeLayout) findViewById(R.id.payment_remark_rel);

		payment_username_text = (TextView) findViewById(R.id.payment_username_text);
		payment_addressArea_text = (TextView) findViewById(R.id.payment_addressArea_text);
		payment_addressDetail_text = (TextView) findViewById(R.id.payment_addressDetail_text);
	}

	@Override
	public void initListener() {
		payment_address_rel.setOnClickListener(this);
		payment_payType_rel.setOnClickListener(this);
		payment_sendTime_rel.setOnClickListener(this);
		payment_invoice_rel.setOnClickListener(this);
		payment_remark_rel.setOnClickListener(this);
	}

	@Override
	public void initRequestVo() {
		vo = new RequestVo(ConstantRedBoy.BASEURL + "/checkout",
				getApplicationContext(), ParamsMapsUtils.getPaymentParamsMap(
						context, "1200001:3"), new PaymentParser());
	}

	@Override
	public void initDataCallBack() {
		dataCallBack = new DataCallBack<Checkout>() {

			@Override
			public void processData(Checkout obj) {
				PaymentCenterActivity.this.obj = obj;
				if (obj != null) {
					payment_username_text.setText(obj.address_info.getName());
					payment_addressArea_text.setText(obj.address_info
							.getAddress_area());
					payment_addressDetail_text.setText(obj.address_info
							.getAddress_detail());
				}

			}
		};

	}

	@Override
	public void initData() {
		getDataForServer(vo, dataCallBack);
		System.out.println(obj);
	}

}
