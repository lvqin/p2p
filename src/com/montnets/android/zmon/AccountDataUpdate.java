package com.montnets.android.zmon;

import cn.mw.p2p.Request.AccountRequest;
import cn.mw.p2p.handler.Threadhandler;
import cn.mw.p2p.unitily.ExitApplication;
import cn.mw.p2p.unitily.MsgEnum;
import cn.mw.p2p.unitily.P2pBaseUrl;
import cn.mw.p2p.unitily.p2punitily;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AccountDataUpdate extends Activity {
	
	private String userIDString;//用户名
	private String loginSession;
	private static String oldUserPhone;//旧用户手机号码
	private EditText userAccount;
	private EditText userpwd;
	private EditText userPhone;
	private EditText oldUserPwd;
	
	private Button btnSaveEcloub;
	private Threadhandler thd;
	private ProgressDialog dialog;
	private String baseurlString;
	private SharedPreferences sp;
	private ImageButton imgbtnBack;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.userinfoset);
        ExitApplication.getInstance().addActivity(this);
		sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
		userIDString = sp.getString("userInfo", "").split(",")[0];
		loginSession = sp.getString("userInfo", "").split(",")[2];
		InitUI();
		getbAccountInfo();
	}
	
	private void InitUI()
	{
		userAccount = (EditText)findViewById(R.id.UserAccount);
		userAccount.setText(userIDString);
		userpwd = (EditText)findViewById(R.id.newPassword);
		userPhone = (EditText)findViewById(R.id.MobileTel);
		oldUserPwd = (EditText)findViewById(R.id.OldPassword);
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AccountDataUpdate.this.finish();
			}
		});
		btnSaveEcloub = (Button)findViewById(R.id.btnEcloubSave);
		btnSaveEcloub.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				String strAccount = userAccount.getText().toString();
				String strpwd = userpwd.getText().toString().trim();
				String struserPhone = userPhone.getText().toString().trim();
				String strOldpwd = oldUserPwd.getText().toString().trim();
				AccountRequest art = new AccountRequest();
				art.setAccount(userIDString);
				art.setLoginSession(loginSession);
				if(struserPhone.equals(""))
				{
					Toast.makeText(AccountDataUpdate.this, "手机号码不能为空！", 0).show();
					return;
				}
				if (!p2punitily.isMobileNO(struserPhone)) {
					Toast.makeText(AccountDataUpdate.this, "手机号码不合法！", 0).show();
					return;
				}
				art.setMobileTel(oldUserPhone);
				art.setNewMobileTel(struserPhone);
				Log.v("SetUserPhoneReq:O", oldUserPhone); 
				Log.v("SetUserPhoneReq:N", struserPhone);
				// 开启线程
				if(!struserPhone.equals(oldUserPhone))//手机号码发生改变时才执行修改操作
				{
					dialog = ProgressDialog.show(AccountDataUpdate.this, "", "正在提交数据，请等待...", true);
					dialog.setCancelable(true);
					thd = new Threadhandler(mHandler, art, baseurlString, "set");
					thd.start();
				}
				if(strOldpwd.equals(""))
				{
					Toast.makeText(AccountDataUpdate.this, "原始密码不能为空！", 0).show();
					return;
				}
				if(strpwd.equals(""))
				{
					Toast.makeText(AccountDataUpdate.this, "新密码不能为空！", 0).show();
					return;
				}
				art.setPassword(strOldpwd);
				art.setNewPassword(strpwd);
				// 开启线程
				if (dialog != null) {
					dialog.dismiss();
				}
				dialog = ProgressDialog.show(AccountDataUpdate.this, "", "正在获取数据，请等待...", true);
				dialog.setCancelable(true);
				thd = new Threadhandler(mHandler, art, baseurlString, "SetUserKey");
				thd.start();
			}
		});
	}
	
	/**
	 * 获取用户手机号码
	 */
	private void getbAccountInfo()
	{
		AccountRequest art = new AccountRequest();
		art.setAccount(userIDString);
		art.setLoginSession(loginSession);
		dialog = ProgressDialog.show(AccountDataUpdate.this, "", "正在获取数据，请等待...", true);
		dialog.setCancelable(true);
		baseurlString = P2pBaseUrl.BaseUrl(sp);
		// 开启线程
		thd = new Threadhandler(mHandler, art, baseurlString, "get");
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
					oldUserPhone = res;
					userPhone.setText(res);
				}
//				Toast.makeText(AccountDataUpdate.this, "查询手机号码成功！", 0).show();
				break;
			case MsgEnum.SESSION_TIMEOUT:
				Toast.makeText(AccountDataUpdate.this, "SESSION超时！", 0).show();
				break;
			case MsgEnum.ACCOUNT_NOT_EXIST:
				Toast.makeText(AccountDataUpdate.this, "用户不存在！", 0).show();
				break;
			case MsgEnum.OK:
				oldUserPhone = userPhone.getText().toString().trim();
				Toast.makeText(AccountDataUpdate.this, "修改手机号码成功！", 0).show();
				break;
			case MsgEnum.PASSWORD_MODIFY_OK:
				Toast.makeText(AccountDataUpdate.this, "密码修改成功！", 0).show();
				break;
			case MsgEnum.SERVER_ERROR:
				Toast.makeText(AccountDataUpdate.this, "数据处理异常！", 0).show();
				break;
			case MsgEnum.PASSWORD_OLD_REEOR:
				Toast.makeText(AccountDataUpdate.this, "原始密码错误！", 0).show();
				break;
			default:
				break;
			}
    	}
	
    };
    
}
