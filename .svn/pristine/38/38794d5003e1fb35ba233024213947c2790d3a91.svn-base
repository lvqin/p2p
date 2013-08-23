package com.montnets.android.zmon;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import cn.mw.p2p.Request.AccountRequest;
import cn.mw.p2p.Request.MonSetMobileRequest;
import cn.mw.p2p.Request.SetUserKeyRequest;
import cn.mw.p2p.handler.Threadhandler;
import cn.mw.p2p.unitily.ExitApplication;
import cn.mw.p2p.unitily.MsgEnum;
import cn.mw.p2p.unitily.P2pBaseUrl;
import cn.mw.p2p.unitily.WSUtils;
import cn.mw.p2p.unitily.p2punitily;



public class AccountDataUpdate2 extends Activity {
	private String userIDString;//用户名
	private String loginSession;
	private static String oldUserPhone;//旧用户手机号码
	private TextView userAccount;
	private EditText userpwd;
	private EditText userPhone;
	private EditText oldUserPwd;
	private EditText affirmuserpwd;
	
	private Button btnSaveEcloub,btnaccountSave;
	private Threadhandler thd;
	private ProgressDialog dialog;
	private String baseurlString;
	private SharedPreferences sp;
	private ImageButton imgbtnBack;
	private String newMobleTel1 = null;
	private ProgressDialog pd;
	
	private String Account;
	private String LoginSession;
	
	private final Handler mHandler = new Handler()
    {
    	public void handleMessage(Message msg)
    	{
			dialog.dismiss();
			int msgID = msg.what;
			
			if(msgID == MsgEnum.SUCCESS) {
				String res = msg.getData().getString("ParamResult").toString();
				if(res!="" && res!=null)
				{
					oldUserPhone = res;
					userPhone.setText(res);
				}
			}
			}
    	};
    
	
	private final Handler mHandler1 = new Handler(){
		public void handleMessage(Message msg){
			pd.dismiss();
			switch(msg.what){
			case -1:
				Toast.makeText(AccountDataUpdate2.this, "连接服务器失败，请检查网络环境", Toast.LENGTH_SHORT).show();
				break;
			case 0:
				Toast.makeText(AccountDataUpdate2.this, "修改手机号码成功！", Toast.LENGTH_SHORT).show();
				oldUserPhone = userPhone.getText().toString().trim();
				break;
			case 1:
				Toast.makeText(AccountDataUpdate2.this, "修改失败，用户名不存在", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(AccountDataUpdate2.this, "修改失败，会话超时，请重新登录", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(AccountDataUpdate2.this, "原手机号码有误", Toast.LENGTH_SHORT).show();
				break;
			case 11:
				Toast.makeText(AccountDataUpdate2.this, "用户不可用", Toast.LENGTH_SHORT).show();
				break;
				default:
					Toast.makeText(AccountDataUpdate2.this, "修改失败，未定义错误", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};
	
	// start videotape
	private final Handler mHandler2 = new Handler(){
		public void handleMessage(Message msg){
			pd.dismiss();
			switch(msg.what){
			case -1:
				Toast.makeText(AccountDataUpdate2.this, "连接服务器失败，请检查网络环境", Toast.LENGTH_SHORT).show();
				break;
			case 0:
				Toast.makeText(AccountDataUpdate2.this, "修改密码成功", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(AccountDataUpdate2.this, "修改失败，用户名不存在", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(AccountDataUpdate2.this, "修改失败，会话超时，请重新登录", Toast.LENGTH_SHORT).show();
				break;
			case 10:
				Toast.makeText(AccountDataUpdate2.this, "原始密码错误", Toast.LENGTH_SHORT).show();
				break;
			case 11:
				Toast.makeText(AccountDataUpdate2.this, "开启失败，设备属于其他用户", Toast.LENGTH_SHORT).show();
				break;
				default:
					Toast.makeText(AccountDataUpdate2.this, "用户不可用", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};
	
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
		setDefaultValue();
		getbAccountInfo();

	}
	
	private void InitUI()
	{
		userAccount = (TextView)findViewById(R.id.UserAccount);
		userAccount.setText(userIDString);
		userpwd = (EditText)findViewById(R.id.newPassword);
		userpwd.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				affirmuserpwd.setError(null);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		affirmuserpwd = (EditText) findViewById(R.id.affirmnewPassword);
		affirmuserpwd.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				affirmuserpwd.setError(null);
			}
		});
		userPhone = (EditText)findViewById(R.id.MobileTel);
		oldUserPwd = (EditText)findViewById(R.id.OldPassword);
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
        imgbtnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AccountDataUpdate2.this.finish();
			}
		});
        btnSaveEcloub = (Button)findViewById(R.id.btnEcloubSave);
        btnSaveEcloub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String strpwd = userpwd.getText().toString().trim();
				byte[] b1 = strpwd.getBytes();
				String strOldpwd = oldUserPwd.getText().toString().trim();
				byte[] b2 = strOldpwd.getBytes();
				String straffirmpwd = affirmuserpwd.getText().toString().trim();
				byte[] b = straffirmpwd.getBytes();
				if(b2.length>16){
					oldUserPwd.setError("密码长度不能超过16位！　");
					return;
				}
				if(strOldpwd.equals(""))
				{
					oldUserPwd.setError("原始密码不能为空！　");
					//Toast.makeText(AccountDataUpdate2.this, "原始密码不能为空！", 0).show();
					return;
				}
				if(b1.length>16){
					userpwd.setError("密码长度不能超过16位！");
					return;
				}
				if(strpwd.equals(""))
				{
					userpwd.setError("新密码不能为空！");
					//Toast.makeText(AccountDataUpdate2.this, "新密码不能为空！", 0).show();
					return;
				}
				if(b.length>16){
					affirmuserpwd.setError("密码长度不能超过16位！");
					return;
				}
				if(straffirmpwd.equals("")){
					affirmuserpwd.setError("请输入新密码！");
					return;
				}
				if(!straffirmpwd.equals(strpwd)){
					affirmuserpwd.setError("您输入的两个新密码不一样！");
					//Toast.makeText(AccountDataUpdate2.this, "您输入的两个新密码不一样！", Toast.LENGTH_SHORT).show();
					return;
				}
				
				progressSetPassword();
			}
		});
		
        btnaccountSave = (Button) findViewById(R.id.btnaccountSave);
        btnaccountSave.setOnClickListener(new OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//String oldUserPhone = userPhone.getText().toString().trim();
				String newMobleTel = userPhone.getText().toString().trim();
				
				System.out.println("新手机号码是：" + "----->" + newMobleTel);
				if(newMobleTel.contains("+86")){
					newMobleTel1 = newMobleTel.substring(3);
					System.out.println("处理+86后的手机号码是！---------->" + newMobleTel1);
				}else{
					newMobleTel1 = newMobleTel;
				}
				if(newMobleTel1.equals(""))
				{
					userPhone.setError("手机号码不能为空！");
					//Toast.makeText(AccountDataUpdate2.this, "手机号码不能为空！", 0).show();
					return;
				}
				if (!p2punitily.isMobileNO(newMobleTel1)) {
					userPhone.setError("手机号码不合法！");
					//Toast.makeText(AccountDataUpdate2.this, "手机号码不合法！", 0).show();
					return;
				}
				
				if(!newMobleTel1.equals(oldUserPhone)){
					progressSetMobile();
				}else {
					//userPhone.setError("请输入新的手机号码！");
					Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
				}
				//getbAccountInfo();
			}
		});
	}
	
	private void setDefaultValue() {
		SharedPreferences sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
        Account = sp.getString("userInfo", "").split(",")[0];
        LoginSession = sp.getString("userInfo", "").split(",")[2];
        baseurlString = P2pBaseUrl.BaseUrl(sp);
	}
	
	/**
	 * 获取用户手机号码
	 */
	private void getbAccountInfo()
	{
		AccountRequest art = new AccountRequest();
		art.setAccount(userIDString);
		art.setLoginSession(loginSession);
		dialog = ProgressDialog.show(AccountDataUpdate2.this, "", "正在获取数据，请等待...", true);
		dialog.setCancelable(true);
		baseurlString = P2pBaseUrl.BaseUrl(sp);
		// 开启线程
		thd = new Threadhandler(mHandler, art, baseurlString, "get");
		thd.start();
	}
	
	protected void progressSetMobile() {
		pd = ProgressDialog.show(AccountDataUpdate2.this, "", "发送命令中...", true);
		pd.setCancelable(true);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				//RebootDevRequest request = new RebootDevRequest();
				String newMobleTel = userPhone.getText().toString().trim();
				MonSetMobileRequest request = new MonSetMobileRequest();
				request.setAccount(Account);
				request.setLoginSession(LoginSession);
				request.setNewMobileTel(newMobleTel1);
				request.setMobileTel(oldUserPhone);
				
				PropertyInfo pi = new PropertyInfo();
				pi.setName("req");
				pi.setValue(request);
				
				Message msg = mHandler1.obtainMessage();
				SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "MonSetMobile", pi, request, "MonSetMobileRequest");
				if (soapObject != null) {
					Object objRes = (Object) soapObject.getProperty("Result");
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
	
	protected void progressSetPassword() {
		pd = ProgressDialog.show(AccountDataUpdate2.this, "", "发送命令中...", true);
		pd.setCancelable(true);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				//RebootDevRequest request = new RebootDevRequest();
				String strpwd = userpwd.getText().toString().trim();
				String strOldpwd = oldUserPwd.getText().toString().trim();
				
				SetUserKeyRequest request = new SetUserKeyRequest();
				request.setAccount(Account);
				request.setLoginSession(LoginSession);
				request.setNewPassword(strpwd);
				request.setPassword(strOldpwd);
				
				PropertyInfo pi = new PropertyInfo();
				pi.setName("req");
				pi.setValue(request);
				
				Message msg = mHandler2.obtainMessage();
				SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "SetUserKey", pi, request, "SetUserKeyRequest");
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
	}
	
}


	