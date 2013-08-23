﻿package com.montnets.android.zmon;

import cn.mw.p2p.Request.GetEAccountRequest;
import cn.mw.p2p.Request.SetEAccountRequest;
import cn.mw.p2p.handler.Threadhandler;
import cn.mw.p2p.unitily.ExitApplication;
import cn.mw.p2p.unitily.MsgEnum;
import cn.mw.p2p.unitily.P2pBaseUrl;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EcloubAccount extends Activity {
	
	private String userIDString;//用户名
	private String loginSession;
	private EditText etxtEcloubAccount;
	private EditText etxtEcloubPwd;
	private Button btnSaveEcloub;
	private Threadhandler thd;
	private ProgressDialog dialog;
	private String baseurlString;
	private SharedPreferences sp;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.userset);
        ExitApplication.getInstance().addActivity(this);
		sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
		userIDString =sp.getString("userInfo", "").split(",")[0];
		loginSession = sp.getString("userInfo", "").split(",")[2];
		InitUI();
		getEcloubAccount();
	}
	
	private void InitUI()
	{
		etxtEcloubAccount = (EditText)findViewById(R.id.ecloubAccount);
		etxtEcloubPwd = (EditText)findViewById(R.id.ecloubpwd);
		btnSaveEcloub = (Button)findViewById(R.id.btnEcloubSave);
		btnSaveEcloub.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String strECloubAccount = etxtEcloubAccount.getText().toString().trim();
				String strECloubpwd = etxtEcloubPwd.getText().toString().trim();
				if(strECloubAccount.equals("") || strECloubpwd.equals(""))
				{
					Toast.makeText(EcloubAccount.this, "E云帐户和密码不能为空！", 0).show();
					return;
				}
				SetEAccountRequest ser = new SetEAccountRequest();
				ser.setAccount(userIDString);
				ser.setLoginSession(loginSession);
				ser.setEstoreAccount(strECloubAccount);
				ser.setEstorePassword(strECloubpwd);
				// 开启线程
				thd = new Threadhandler(mHandler, ser, baseurlString);
				thd.start();
			}
		});
	}
	
	private void getEcloubAccount()
	{
		GetEAccountRequest ger = new GetEAccountRequest();
		ger.setAccount(userIDString);
		ger.setLoginSession(loginSession);
		dialog = ProgressDialog.show(EcloubAccount.this, "", "正在获取数据，请等待...", true);
		baseurlString = P2pBaseUrl.BaseUrl(sp);
		// 开启线程
		thd = new Threadhandler(mHandler, ger, baseurlString);
		thd.start();
	}
	
    //=======================TODO 线程操作==============================
    private final Handler mHandler = new Handler()
    {
    	public void handleMessage(Message msg)
    	{
			dialog.dismiss();
			int msgID = msg.what;
			switch (msgID) {
			case MsgEnum.SUCCESS:
				String res = msg.getData().getString("ParamResult").toString();
				if(res!="" && res!=null)
				{
					etxtEcloubAccount.setText(res.split(",")[0]);
					etxtEcloubPwd.setText(res.split(",")[1]);
				}
				Toast.makeText(EcloubAccount.this, "查询成功！", 0).show();
				break;
			case MsgEnum.SESSION_TIMEOUT:
				Toast.makeText(EcloubAccount.this, "SESSION超时！", 0).show();
				break;
			case MsgEnum.ACCOUNT_NOT_EXIST:
				Toast.makeText(EcloubAccount.this, "用户不存在！", 0).show();
				break;
			case MsgEnum.OK:
				Toast.makeText(EcloubAccount.this, "修改成功！", 0).show();
				break;
			case MsgEnum.SERVER_ERROR:
				Toast.makeText(EcloubAccount.this, "数据处理异常！", 0).show();
				break;
			case MsgEnum.PASSWORD_OLD_REEOR:
				Toast.makeText(EcloubAccount.this, "E云帐号和密码不匹配！", 0).show();
				break;
			default:
				break;
			}
    	}
	
    };
}
