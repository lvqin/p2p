package com.montnets.android.zmon;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import cn.mw.p2p.Request.GetPwdRequest;
import cn.mw.p2p.Request.MobileRegisterRequest;
import cn.mw.p2p.Request.MonGetAuthCodeRequest;
import cn.mw.p2p.handler.Threadhandler;
import cn.mw.p2p.unitily.ExitApplication;
import cn.mw.p2p.unitily.MsgEnum;
import cn.mw.p2p.unitily.P2pBaseUrl;
import cn.mw.p2p.unitily.WSUtils;
import cn.mw.p2p.unitily.p2punitily;

public class RegActitivty1 extends Activity {
	
	private EditText editTextUserID, editTextUserPwd, editTextDevID,
			editTextDevPwd, editTextUserAccount, editTextCheckCode;
	private Button btnReg, btngetCode;
	private ProgressDialog dialog;
	private Threadhandler thd;
	private SharedPreferences sp;
	private ImageButton imgbtnBack;
	private String strResCheckCode = "";//服务返回的验证码
	private TimeCount time;
	private static String myphone = null;
	private boolean IsCheckCode = false;
	private String baseurlString;
	private String strUserPhone1 = null;
	private String strUserPhone2 = null;
	private String strUserAccount;
	private String strUserPhone;
	private String strUserPWD;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.reg_layout);
        ExitApplication.getInstance().addActivity(this);
        sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
        baseurlString = P2pBaseUrl.BaseUrl(sp);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        initUI();
        if(editTextUserID.getText().toString().trim().equals(myphone)){
        	btngetCode.setVisibility(View.VISIBLE);
			editTextCheckCode.setVisibility(View.VISIBLE);
			((TextView)findViewById(R.id.textView2)).setVisibility(View.VISIBLE);
			IsCheckCode = true;
		}else {
			IsCheckCode = false;
		}
	}
	
	private void initUI()
	{
		/*//获取本机号码
		myphone = p2punitily.getPhoneNumber(RegActitivty1.this);
		 if(myphone!=null && myphone.substring(0, 3).equals("+86")){
			myphone = myphone.substring(3);
		}*/
		
		editTextUserAccount = (EditText)findViewById(R.id.UserAccount);
		editTextUserAccount.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
		editTextUserAccount.setFocusable(true);
		editTextUserAccount.requestFocus();
		//editTextUserAccount.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32)});
		editTextUserAccount.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				String str1 = editTextUserAccount.getText().toString().trim();
				byte[] b = str1.getBytes();
				if(b.length > 32){
					editTextUserAccount.setError("用户账号长度不能超过32位！");
					return;
				}
				
				if(str1.equals(""))
				{
					editTextUserAccount.setError("用户帐号不能为空！");
					return;
				}
				else {
					String strTemp = p2punitily.getInputDataType(str1.substring(0, 1));
					if (strTemp == null) {
						editTextUserAccount.setError("用户帐号必须以字母开头！");
						return;
					}
					if(strTemp.equals("0"))
					{
						editTextUserAccount.setError("用户帐号必须以字母开头！");
						return;
					}
				}
				
				// TODO Auto-generated method stub
				if(editTextUserAccount.hasFocus() != true){
					String strUserAccount = editTextUserAccount.getText().toString().trim();
					editTextUserAccount.setText(strUserAccount);
				}
			}
		});
		editTextUserID = (EditText)findViewById(R.id.MobileTel);
		editTextUserID.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
		editTextUserID.setText(myphone);

		editTextUserPwd = (EditText)findViewById(R.id.TelPassword);
		editTextDevID = (EditText)findViewById(R.id.DeviceID);
		editTextDevPwd = (EditText)findViewById(R.id.DevPassword);
		//验证码
		editTextCheckCode = (EditText)findViewById(R.id.checkCode);
		btnReg = (Button)findViewById(R.id.btnReg);
		btnReg.setOnClickListener(new RegOnClickListener());
		//获取验证码
		btngetCode = (Button)findViewById(R.id.btnGetCode);
		btngetCode.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String strUserPhone = editTextUserID.getText().toString().trim();
				if(strUserPhone.contains("+86")){
					 strUserPhone1 = strUserPhone.substring(3);
					 System.out.println("号码是==============>" + strUserPhone1);
					 
				}else {
					strUserPhone1 = strUserPhone;
				}

				if(strUserPhone1.equals(""))
				{
					//Toast.makeText(RegActitivty1.this, "手机号码不能为空！", 0).show();
					editTextUserID.setError("手机号码不能为空！");
					return;
				}
				if (!p2punitily.isMobileNO(strUserPhone1)) {
					EditText editTextPhone = (EditText)findViewById(R.id.MobileTel);
					editTextPhone.setFocusableInTouchMode(true);
					editTextPhone.requestFocus();
					editTextPhone.setError("手机号码不合法！");
					return;
				}
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						time.start();
						MonGetAuthCodeRequest request = new MonGetAuthCodeRequest();
						request.setMobileTel(strUserPhone1);
		
		                  PropertyInfo pi = new PropertyInfo();
		                  pi.setName("req");
		                  pi.setValue(request);
		                  
		                  Message msg = mHandler1.obtainMessage();
		  				SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "MonGetAuthCode", pi, request, "MonGetAuthCodeRequest");
		  				if (soapObject != null) {
		  					Object objRes = (Object) soapObject.getProperty("Result");
		  					int result = Integer.parseInt(objRes.toString());
		  					if(result == 0){
		  					Object objRes1 = (Object) soapObject.getProperty("AuthCode");
		  					String strAuthCode = objRes1.toString();
		  					Bundle bd = new Bundle();
		  					bd.putString("AuthCode", strAuthCode);
		  					msg.setData(bd);
		  					}
		  					String strReString = objRes.toString();
		  					// 3. send handle msg
		  					msg.what = Integer.parseInt(strReString);
		  					
		  				}else{
		  					msg.what = -1;
		  				}
		  				mHandler1.sendMessage(msg);
		
					}
				}).start();

			}
		});
		
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RegActitivty1.this.finish();
			}
		});
		
	}
	
	private final class RegOnClickListener implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			strUserAccount = editTextUserAccount.getText().toString().trim();
			editTextUserAccount.setText(strUserAccount);
			String strCheckCode = editTextCheckCode.getText().toString().trim();
			byte[] b1 = strUserAccount.getBytes();
			System.out.println("用户账号长度是----->" + b1.length);
			if(b1.length > 32){
				editTextUserAccount.setError("用户账号长度不能超过32位！");
				return;
			}
			/*if(strResCheckCode.equals("")){
				editTextCheckCode.setError("验证码不能为空！");
				return;
			}*/
			if (!strResCheckCode.equals(strCheckCode)) {
				editTextCheckCode.setError("验证码不正确！");
				return;
			}
			if(strUserAccount.equals(""))
			{
				editTextUserAccount.setError("用户帐号不能为空！");
				return;
			}
			else {
				String strTemp = p2punitily.getInputDataType(strUserAccount.substring(0, 1));
				if (strTemp == null) {
					editTextUserAccount.setError("用户帐号必须以字母开头！");
					return;
				}
				if(strTemp.equals("0"))
				{
					editTextUserAccount.setError("用户帐号必须以字母开头！");
					return;
				}
			}
			strUserPhone2 = editTextUserID.getText().toString().trim();
			System.out.println("strUserPhone2---->" + strUserPhone2);
			System.out.println("strUserPhone1---->" + strUserPhone1);
			System.out.println("strUserPhone----->" + strUserPhone);
			if(!strUserPhone2.equals(strUserPhone1)){
				Toast.makeText(getApplicationContext(), "手机号码已更换，请重新获取验证码！", Toast.LENGTH_SHORT).show();
				return;
			}
			if(strUserPhone2.equals("") || strUserPhone2.equals("0"))
			{
				editTextUserID.setError("手机号码不能为空！");
				return;
			}
			if(strResCheckCode.equals("")){
				editTextCheckCode.setError("验证码不能为空！");
				return;
			}
			
			//验证码
			if (IsCheckCode) {
				//String strCheckCode = editTextCheckCode.getText().toString().trim();
				Log.i("输入的验证码是------>", strCheckCode);
				if (strCheckCode.equals("")) {
					editTextCheckCode.setError("验证码不能为空！");
					return;
				} else {
					// 验证码是否正确
					if (!strResCheckCode.equals(strCheckCode)) {
						editTextCheckCode.setFocusableInTouchMode(true);
						editTextCheckCode.requestFocus();
						editTextCheckCode.setError("验证码不正确！");
						return;
					}
				}
			}
			
			 strUserPWD = editTextUserPwd.getText().toString().trim();
			 byte[] b = strUserPWD.getBytes();
			 if(b.length > 16){
				 editTextUserPwd.setError("用户密码不能超过16位！");
				 return;
			 }
			if(strUserPWD.equals(""))
			{
				editTextUserPwd.setError("用户密码不能为空！");
				return;
			}
			dialog = ProgressDialog.show(RegActitivty1.this, "", "正在提交数据，请等待...", true);
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String strDevID = editTextDevID.getText().toString().trim();
					String strDevKey = editTextDevPwd.getText().toString().trim();
					MobileRegisterRequest request = new MobileRegisterRequest();
					request.setAccount(strUserAccount);
					request.setMobileTel(strUserPhone2);
					request.setTelPassword(strUserPWD);
					request.setPwdType(0);
					request.setDevID(strDevID);
					request.setDevKey(strDevKey);
					
					
					PropertyInfo pi = new PropertyInfo();
					pi.setName("req");
					pi.setValue(request);
					
					Message msg = mHandler.obtainMessage();
					SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "MobileRegister", pi, request, "MobileRegisterRequest");
					if (soapObject != null) {
						Object objRes = (Object) soapObject.getProperty("Result");
						
						String strReString = objRes.toString();
						// 3. send handle msg
						msg.what = Integer.parseInt(strReString);
					}else{
						msg.what = -1;
					}
					mHandler.sendMessage(msg);
				}
			}).start();
			
			
			
		}
		
	}
	
	/**
	 * 消息处理
	 */
	private final Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg) {
			String strDevKey = editTextDevPwd.getText().toString().trim();
			if (dialog != null) {
				dialog.dismiss();
			}
			int msgID = msg.what;
			switch (msgID) {
			case -1:
				Toast.makeText(RegActitivty1.this, "连接服务器失败，请检查网络及服务器设置！", Toast.LENGTH_SHORT).show();
				break;
			case 0:
				Toast.makeText(RegActitivty1.this, "用户注册成功", 0).show();
				Intent intent = new Intent(RegActitivty1.this,LoginActivity.class);
				startActivity(intent);
				break;
			case 1:
				Toast.makeText(RegActitivty1.this, "设备不存在", 0).show();
				break;
			case 2:
				if(strDevKey.equals("")){
					Toast.makeText(RegActitivty1.this, "设备验证码不能为空", 0).show();
				}else {
					Toast.makeText(RegActitivty1.this, "设备验证码错误", 0).show();
				}
				break;
			case 3:
				Toast.makeText(RegActitivty1.this, "该用户无法开通该业务", 0).show();
				break;
			case 4:
				Toast.makeText(RegActitivty1.this, "用户已经注册", 0).show();
				break;
			case 5:
				Toast.makeText(RegActitivty1.this, "设备被其他用户绑定", 0).show();
				break;
			case 9:
				Toast.makeText(RegActitivty1.this, "操作失败！", 0).show();
				break;
			}

		}
	};

	
	/* 定义一个倒计时的内部类 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {
			// 计时完毕时触发
			btngetCode.setClickable(true);
			btngetCode.setEnabled(true);
			editTextUserID.setClickable(true);
			editTextUserID.setEnabled(true);
			btngetCode.setText("获取验证码");
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// 计时过程显示
			btngetCode.setClickable(false);
			btngetCode.setEnabled(false);
			editTextUserID.setClickable(false);
			editTextUserID.setEnabled(false);
			btngetCode.setText("获取中(" + (millisUntilFinished / 1000) + ")");
		}
	}
	
	private final Handler mHandler1 = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case -1:
				Toast.makeText(RegActitivty1.this, "连接服务器失败，请检查网络及服务器设置！", Toast.LENGTH_SHORT).show();
				break;
			case 0:
				Toast.makeText(RegActitivty1.this, "短信发送成功！", Toast.LENGTH_SHORT).show();
				strResCheckCode = msg.getData().getString("AuthCode");
				Log.i("短信获取的验证码是------->", strResCheckCode);
				break;
			case 2:
				Toast.makeText(RegActitivty1.this, "短信发送失败！", Toast.LENGTH_SHORT).show();
				break;
				default:
					Toast.makeText(RegActitivty1.this, "服务器异常！", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};
}
