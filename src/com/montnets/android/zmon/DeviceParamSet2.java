﻿package com.montnets.android.zmon;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cn.mw.p2p.Request.RebootDevRequest;
import cn.mw.p2p.Request.SetDevKeyRequest;
import cn.mw.p2p.Request.SetDevNameRequest;
import cn.mw.p2p.Request.SetMaxLineNumRequest;
import cn.mw.p2p.Request.StartRecordRequest;
import cn.mw.p2p.Request.StopRecordRequest;
import cn.mw.p2p.unitily.MsgEnum;
import cn.mw.p2p.unitily.P2pBaseUrl;
import cn.mw.p2p.unitily.WSUtils;

public class DeviceParamSet2 extends Activity {
	private TextView tvTimeInterval;
	private EditText etDevName;
	private EditText etPwdNew1;
	private EditText etPwdNew2;
	private EditText etPwdOld;
	private ImageButton btnBack;
	private Button btnSaveName;
	private Button btnSavePwd;
	private Button btnPerson1;
	private Button btnPerson2;
	private Button btnPerson3;
	private Button btnPerson4;
	private Button btnTimeIncrease;
	private Button btnTimeDecrease;
	private Button btnStartTape;
	private Button btnStopTape;
	private Button btnRestartDev;
	private Spinner spinner;
	
	private String Account;
	private String LoginSession;
	private String DevID;
	private String ChannelNO;
	private String baseurlString;
	private String time_tape;
	
	private ProgressDialog pd;
	private static final String[] m={"1小时","2小时","3小时","4小时","5小时","6小时"};
	private ArrayAdapter<String> adapter; 
	
	private final Handler mHandler6 =new Handler(){
		public void handleMessage(Message msg){
			pd.dismiss();
			switch(msg.what){
			case -1:
				Toast.makeText(DeviceParamSet2.this, "连接服务器失败，请检查网络环境", Toast.LENGTH_SHORT).show();
				break;
			case 0:
				Toast.makeText(DeviceParamSet2.this, "停止录像成功", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(DeviceParamSet2.this, "停止录像失败，用户名不存在", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(DeviceParamSet2.this, "停止录像失败，会话超时，请重新登录", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(DeviceParamSet2.this, "停止录像失败，设备不存在", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(DeviceParamSet2.this, "停止录像失败，设备属于其他用户", Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(DeviceParamSet2.this, "停止录像失败，设备不在线", Toast.LENGTH_SHORT).show();
				break;
			case 6:
				Toast.makeText(DeviceParamSet2.this, "请求发送失败", Toast.LENGTH_SHORT).show();
				break;
			case 7:
				Toast.makeText(DeviceParamSet2.this, "操作超时", Toast.LENGTH_SHORT).show();
				break;
			case 8:
				Toast.makeText(DeviceParamSet2.this, "录像未启动", Toast.LENGTH_SHORT).show();
				break;
			case 9:
				Toast.makeText(DeviceParamSet2.this, "操作失败", Toast.LENGTH_SHORT).show();
				break;
			case 10:
				Toast.makeText(DeviceParamSet2.this, "用户不可用", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	
	};
	
	private final Handler mHandler5 = new Handler(){
		public void handleMessage(Message msg){
			pd.dismiss();
			switch(msg.what){
			case -1:
				Toast.makeText(DeviceParamSet2.this, "连接服务器失败，请检查网络环境", Toast.LENGTH_SHORT).show();
				break;
			case 0:
				Toast.makeText(DeviceParamSet2.this, "重启成功", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(DeviceParamSet2.this, "重启失败，用户名不存在", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(DeviceParamSet2.this, "重启失败，会话超时，请重新登录", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(DeviceParamSet2.this, "重启失败，设备不存在", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(DeviceParamSet2.this, "重启失败，设备属于其他用户", Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(DeviceParamSet2.this, "重启失败，设备不在线", Toast.LENGTH_SHORT).show();
				break;
			case 6:
				Toast.makeText(DeviceParamSet2.this, "重启失败，请求发送失败", Toast.LENGTH_SHORT).show();
				break;
			case 7:
				Toast.makeText(DeviceParamSet2.this, "重启失败，操作超时", Toast.LENGTH_SHORT).show();
				break;
			case 8:
				Toast.makeText(DeviceParamSet2.this, "重启失败，参数无效", Toast.LENGTH_SHORT).show();
				break;
			case 9:
				Toast.makeText(DeviceParamSet2.this, "重启失败，未定义错误", Toast.LENGTH_SHORT).show();
				break;
			case 11:
				Toast.makeText(DeviceParamSet2.this, "重启失败，用户已注销", Toast.LENGTH_SHORT).show();
				break;
				default:
					Toast.makeText(DeviceParamSet2.this, "重启失败，未定义错误", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};
	
	// start videotape
	private final Handler mHandler4 = new Handler(){
		public void handleMessage(Message msg){
			pd.dismiss();
			switch(msg.what){
			case -1:
				Toast.makeText(DeviceParamSet2.this, "连接服务器失败，请检查网络环境", Toast.LENGTH_SHORT).show();
				break;
			case 0:
				Toast.makeText(DeviceParamSet2.this, "开启成功", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(DeviceParamSet2.this, "开启失败，用户名不存在", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(DeviceParamSet2.this, "开启失败，会话超时，请重新登录", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(DeviceParamSet2.this, "开启失败，设备不存在", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(DeviceParamSet2.this, "开启失败，设备属于其他用户", Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(DeviceParamSet2.this, "开启失败，设备不在线", Toast.LENGTH_SHORT).show();
				break;
			case 6:
				Toast.makeText(DeviceParamSet2.this, "开启失败，请求发送失败", Toast.LENGTH_SHORT).show();
				break;
			case 7:
				Toast.makeText(DeviceParamSet2.this, "开启失败，操作超时", Toast.LENGTH_SHORT).show();
				break;
			case 8:
				Toast.makeText(DeviceParamSet2.this, "开启失败，录像已启动", Toast.LENGTH_SHORT).show();
				break;
			case 9:
				Toast.makeText(DeviceParamSet2.this, "开启失败，操作失败", Toast.LENGTH_SHORT).show();
				break;
			case 11:
				Toast.makeText(DeviceParamSet2.this, "开启失败，用户已注销", Toast.LENGTH_SHORT).show();
				break;
				default:
					Toast.makeText(DeviceParamSet2.this, "开启失败，未定义的错误", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};
	
	// change persons
	private final Handler mHandler3 = new Handler(){
		public void handleMessage(Message msg){
			pd.dismiss();
			switch(msg.what){
			case -1:
				Toast.makeText(DeviceParamSet2.this, "连接服务器失败，请检查网络环境", Toast.LENGTH_SHORT).show();
				break;
			case 0:
				switch (msg.arg1) {
				case 1:
					Toast.makeText(DeviceParamSet2.this, "修改成功，最大直连数为1个", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(DeviceParamSet2.this, "修改成功，最大直连数为2个", Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(DeviceParamSet2.this, "修改成功，最大直连数为3个", Toast.LENGTH_SHORT).show();
					break;
				case 4:
					Toast.makeText(DeviceParamSet2.this, "修改成功，最大直连数为4个", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
				break;
			case 1:
				Toast.makeText(DeviceParamSet2.this, "修改失败，用户名不存在", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(DeviceParamSet2.this, "修改失败，会话超时，请重新登录", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(DeviceParamSet2.this, "修改失败，设备不存在", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(DeviceParamSet2.this, "修改失败，设备属于其他用户", Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(DeviceParamSet2.this, "修改失败，设备不在线", Toast.LENGTH_SHORT).show();
				break;
			case 6:
				Toast.makeText(DeviceParamSet2.this, "修改失败，请求发送失败", Toast.LENGTH_SHORT).show();
				break;
			case 7:
				Toast.makeText(DeviceParamSet2.this, "修改失败，操作超时", Toast.LENGTH_SHORT).show();
				break;
			case 8:
				Toast.makeText(DeviceParamSet2.this, "修改失败，超过视频上传路数上限", Toast.LENGTH_SHORT).show();
				break;
			case 9:
				Toast.makeText(DeviceParamSet2.this, "修改失败，用户已注销", Toast.LENGTH_SHORT).show();
				break;
			case 11:
				Toast.makeText(DeviceParamSet2.this, "修改失败，未定义的错误", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	
	// change pwd
	/*private final Handler mHandler2 = new Handler(){
		public void handleMessage(Message msg){
			pd.dismiss();
			switch(msg.what){
			case -1:
				Toast.makeText(DeviceParamSet2.this, "连接服务器失败，请检查网络环境", Toast.LENGTH_SHORT).show();
				break;
			case 0:
				Toast.makeText(DeviceParamSet2.this, "修改成功", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(DeviceParamSet2.this, "修改失败，用户名不存在", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(DeviceParamSet2.this, "修改失败，会话超时，请重新登录", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(DeviceParamSet2.this, "修改失败，设备不存在", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(DeviceParamSet2.this, "修改失败，设备属于其他用户", Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(DeviceParamSet2.this, "修改失败，设备不在线", Toast.LENGTH_SHORT).show();
				break;
			case 6:
				Toast.makeText(DeviceParamSet2.this, "修改失败，请求发送失败", Toast.LENGTH_SHORT).show();
				break;
			case 7:
				Toast.makeText(DeviceParamSet2.this, "修改失败，操作超时", Toast.LENGTH_SHORT).show();
				break;
			case 8:
				Toast.makeText(DeviceParamSet2.this, "修改失败，原始验证码错误", Toast.LENGTH_SHORT).show();
				break;
			case 9:
				Toast.makeText(DeviceParamSet2.this, "修改失败，未定义错误", Toast.LENGTH_SHORT).show();
				break;
			case 11:
				Toast.makeText(DeviceParamSet2.this, "修改失败，用户已注销", Toast.LENGTH_SHORT).show();
				break;
				default:
					Toast.makeText(DeviceParamSet2.this, "修改失败，未定义错误", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};*/
	
	// change name
	private final Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			pd.dismiss();
			switch(msg.what){
			case -1:
				Toast.makeText(DeviceParamSet2.this, "连接服务器失败，请检查网络环境", Toast.LENGTH_SHORT).show();
				break;
			case 0:
				Toast.makeText(DeviceParamSet2.this, "修改成功", Toast.LENGTH_SHORT).show();
			break;
			case 1:
				Toast.makeText(DeviceParamSet2.this, "修改失败，用户不存在", Toast.LENGTH_SHORT).show();
			break;
			case 2:
				Toast.makeText(DeviceParamSet2.this, "修改失败，会话超时，请重新登录", Toast.LENGTH_SHORT).show();
			break;
			case 3:
				Toast.makeText(DeviceParamSet2.this, "修改失败，设备不存在", Toast.LENGTH_SHORT).show();
			break;
			case 4:
				Toast.makeText(DeviceParamSet2.this, "修改失败，设备属于其他用户", Toast.LENGTH_SHORT).show();
			break;
			case 11:
				Toast.makeText(DeviceParamSet2.this, "修改失败，用户已注销", Toast.LENGTH_SHORT).show();
			break;
			default:
				Toast.makeText(DeviceParamSet2.this, "修改失败，未定义错误消息", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deviceparamset3);
		initView();
		setListener();
		setDefaultValue();
	}

	private void setDefaultValue() {
		SharedPreferences sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
        Account = sp.getString("userInfo", "").split(",")[0];
        LoginSession = sp.getString("userInfo", "").split(",")[2];
        baseurlString = P2pBaseUrl.BaseUrl(sp);
        
        DevID = getIntent().getStringExtra("DevID").toString();
        ChannelNO = getIntent().getStringExtra("ChannelNO").toString();
        
		etDevName.setText(getIntent().getStringExtra("ChannelName").toString());
	}

	private void setListener() {
		btnBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				DeviceParamSet2.this.finish();
			}
		});
		btnSaveName.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				progressSaveName();
			}
		});
		/*btnSavePwd.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				progressSavePwd();
			}
		});*/
		btnPerson1.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				progressSetPersons(1);
			}
		});
		btnPerson2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				progressSetPersons(2);
			}
		});
		btnPerson3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				progressSetPersons(3);
			}
		});
		btnPerson4.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				progressSetPersons(4);
			}
		});
		/*btnTimeIncrease.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				int interval = Integer.parseInt(tvTimeInterval.getText().toString());
				interval += 1;
				tvTimeInterval.setText(String.valueOf(interval));
			}
		});*/
		/*btnTimeDecrease.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				int interval = Integer.parseInt(tvTimeInterval.getText().toString());
				interval -= 1;
				if(interval>0){
					tvTimeInterval.setText(String.valueOf(interval));
				}else{
					// undo anything
				}
			}
		});*/
		btnStartTape.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				progressStartTape();
			}
		});
		btnStopTape.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				progressStoptTape();
			}
		});
		btnRestartDev.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				progressRestart();
			}
		});
	}

	protected void progressRestart() {
		pd = ProgressDialog.show(DeviceParamSet2.this, "", "发送重启命令中...", true);
		pd.setCancelable(true);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				RebootDevRequest request = new RebootDevRequest();
				request.setAccount(Account);
				request.setLoginSession(LoginSession);
				request.setDevID(DevID);
				
				PropertyInfo pi = new PropertyInfo();
				pi.setName("req");
				pi.setValue(request);
				
				Message msg = mHandler5.obtainMessage();
				SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "RebootDev", pi, request, "RebootDevRequest");
				if (soapObject != null) {
					Object objRes = (Object) soapObject.getProperty("Result");
					String strReString = objRes.toString();
					// 3. send handle msg
					msg.what = Integer.parseInt(strReString);
				}else{
					msg.what = -1;
				}
				mHandler5.sendMessage(msg);
			}
			
		}).start();
	}
	
	protected void progressStoptTape() {
		pd = ProgressDialog.show(DeviceParamSet2.this, "", "发送停止录像命令中...", true);
		pd.setCancelable(true);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				StopRecordRequest request = new StopRecordRequest();
				request.setAccount(Account);
				request.setLoginSession(LoginSession);
				request.setDevID(DevID);
				request.setChannelNo(Integer.parseInt(ChannelNO));
				
				PropertyInfo pi = new PropertyInfo();
				pi.setName("req");
				pi.setValue(request);
				pi.setType(request.getClass());
				
				Message msg = mHandler6.obtainMessage();
				SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "StopRecord", pi, request, "StopRecordRequest");
				if (soapObject != null) {
					Object objRes = (Object) soapObject.getProperty("Result");
					String strReString = objRes.toString();
					// 3. send handle msg
					msg.what = Integer.parseInt(strReString);
				}else{
					msg.what = -1;
				}
				mHandler6.sendMessage(msg);
			}
		}).start();
	}

	protected void progressStartTape() {
		pd = ProgressDialog.show(DeviceParamSet2.this, "", "发送录像命令中...", true);
		pd.setCancelable(true);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				StartRecordRequest request = new StartRecordRequest();
				request.setAccount(Account);
				request.setLoginSession(LoginSession);
				request.setDevID(DevID);
				request.setChannelNo(Integer.parseInt(ChannelNO));
				request.setStreamType(1);
				request.setRecordFlag(0);
				request.setTimeLen(Integer.parseInt(time_tape.substring(0, 1)));
				
				PropertyInfo pi = new PropertyInfo();
				pi.setName("req");
				pi.setValue(request);
				pi.setType(request.getClass());
				
				Message msg = mHandler4.obtainMessage();
				SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "StartRecord", pi, request, "StartRecordRequest");
				if (soapObject != null) {
					Object objRes = (Object) soapObject.getProperty("Result");
					String strReString = objRes.toString();
					// 3. send handle msg
					msg.what = Integer.parseInt(strReString);
				}else{
					msg.what = -1;
				}
				mHandler4.sendMessage(msg);
			}
			
		}).start();
	}

	protected void progressSetPersons(final int i) {
		pd = ProgressDialog.show(DeviceParamSet2.this, "", "设置最大直连数中...", true);
		pd.setCancelable(true);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				SetMaxLineNumRequest quest = new SetMaxLineNumRequest();
				quest.setAccount(Account);
				quest.setLoginSession(LoginSession);
				quest.setDevID(DevID);
				quest.setChannelNo(Integer.parseInt(ChannelNO));
				quest.setMaxLineNum(i);
				Log.v("Max", String.valueOf(i));
				
				PropertyInfo pi = new PropertyInfo();
				pi.setName("req");
				pi.setValue(quest);
				pi.setType(quest.getClass());
				
				Message msg = mHandler3.obtainMessage();
				SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "SetMaxLineNum", pi, quest, "SetMaxLineNumRequest");
				if (soapObject != null) {
					Object objRes = (Object) soapObject.getProperty("Result");
					String strReString = objRes.toString();
					// 3. send handle msg
					msg.what = Integer.parseInt(strReString);
					msg.arg1 = i;
				}else{
					msg.what = -1;
				}
				mHandler3.sendMessage(msg);
				
			}
			
		}).start();
	}

/*	protected void progressSavePwd() {
		// native check
		String new1 = etPwdNew1.getText().toString().trim();
		final String new2 = etPwdNew2.getText().toString().trim();
		final String old = etPwdOld.getText().toString().trim();
		if(old.length() == 0){
			etPwdOld.setError("原始验证码不能为空！");
			return;
		}
		if(new1.length() == 0){
			etPwdNew1.setError("新的验证码不能为空！");
			return;
		}
		if(new2.length() == 0){
			etPwdNew2.setError("确认验证码不能为空！");
			return;
		}
		if(!new1.equals(new2)){
			etPwdNew2.setError("两次输入的新验证码不相同！");
			return;
		}
		
		pd = ProgressDialog.show(DeviceParamSet2.this, "", "更改验证码中...", true);
		pd.setCancelable(true);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				SetDevKeyRequest request = new SetDevKeyRequest();
				request.setAccount(Account);
				request.setLoginSession(LoginSession);
				request.setDevID(DevID);
				request.setDevKey(old);
				request.setNewDevKey(new2);
				
				PropertyInfo pi = new PropertyInfo();
				pi.setName("req");
				pi.setValue(request);
				pi.setType(request.getClass());
				
				Message msg = mHandler2.obtainMessage();
				SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "SetDevKey", pi, request, "SetDevKeyRequest");
				if (soapObject != null) {
					Object objRes = (Object) soapObject.getProperty("Result");
					String strReString = objRes.toString();
					// 3. send handle msg
					msg.what = Integer.parseInt(strReString);
				}else{
					msg.what = -1;
				}
				mHandler2.sendMessage(msg);
			}
			
		}).start();
	}*/

	private void progressSaveName() {
		// native check
				String curDevName = etDevName.getText().toString().trim();
				byte[] b =curDevName.getBytes();
				if(b.length > 32){
					etDevName.setError("设备名称不能超过32位！");
					return;
				}
				if(curDevName.length() == 0){
					etDevName.setError("设备名称不能为空！");
					return;
				}
		
		pd = ProgressDialog.show(DeviceParamSet2.this, "", "正在修改中...", true);
		pd.setCancelable(true);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				SetDevNameRequest req = new SetDevNameRequest();
				req.setAccount(Account);
				req.setLoginSession(LoginSession);
				req.setDevID(DevID);
				req.setChannleNo(Integer.parseInt(ChannelNO));
				req.setChannelName(etDevName.getText().toString());
				
				PropertyInfo pi = new PropertyInfo();
				pi.setName("req");
				pi.setValue(req);
				pi.setType(req.getClass());
				
				Message msg = mHandler.obtainMessage();
				SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "SetDevName", pi, req, "SetDevNameRequest");
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

	private void initView() {
		tvTimeInterval = (TextView) findViewById(R.id.textView3);
		
		etDevName = (EditText) findViewById(R.id.EditText01);
		//etPwdNew1 = (EditText) findViewById(R.id.editText1);
		//etPwdNew2 = (EditText) findViewById(R.id.editText2);
		//etPwdOld = (EditText) findViewById(R.id.editText3);
		
		btnBack = (ImageButton) findViewById(R.id.back_btn);
		btnSaveName = (Button) findViewById(R.id.btnSave);
		//btnSavePwd = (Button) findViewById(R.id.Button01);
		btnPerson1 = (Button) findViewById(R.id.button1);
		btnPerson2 = (Button) findViewById(R.id.Button04);
		btnPerson3 = (Button) findViewById(R.id.Button05);
		btnPerson4 = (Button) findViewById(R.id.Button06);
		/*btnTimeIncrease = (Button) findViewById(R.id.Button03);
		btnTimeDecrease = (Button) findViewById(R.id.Button07);*/
		btnStartTape = (Button) findViewById(R.id.Button08);
		btnRestartDev = (Button) findViewById(R.id.Button02);
		btnStopTape = (Button) findViewById(R.id.Button09);
		spinner = (Spinner) findViewById(R.id.spinner01);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,m);		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		spinner.setAdapter(adapter);   
		spinner.setOnItemSelectedListener(new SpinnerSelectedListener());   
		spinner.setVisibility(View.VISIBLE);   

	}
	    class SpinnerSelectedListener implements OnItemSelectedListener{   
	       public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,   
	               long arg3) {   
	    	   time_tape = String.valueOf(m[arg2]);
	    	   System.out.println("您选择的录像时间是++++++++++++++==>"+time_tape);
	    	   System.out.println("第一个字符是------>" + time_tape.substring(0, 1));
	        }   
	  
	       public void onNothingSelected(AdapterView<?> arg0) {   
	        }   
	    }   


}
