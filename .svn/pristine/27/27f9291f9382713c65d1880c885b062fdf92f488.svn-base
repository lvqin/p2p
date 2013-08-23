package com.montnets.android.zmon;

import cn.mw.p2p.Request.SafeRequest;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class SafepolicyListSet extends Activity {
	
	private String DevID = null;
	private String ChannelNO = null;
	private String Account = null;
	private SharedPreferences sp;
	private ImageButton imgbtnBack;
	private Button btnSave;
	private String loginSession = null;
	private Spinner spinnerSleep;
	private Spinner spinnerPerson;
	private EditText etxtDevKey;
	private CheckBox cbxRestart;
	private Threadhandler thd;
	private ProgressDialog dialog;
	private String baseurlString;
	private EditText etxtNewDeyKey;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.safepolicyset); 
        ExitApplication.getInstance().addActivity(this);
        sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
        Account = sp.getString("userInfo", "").split(",")[0];
        loginSession = sp.getString("userInfo", "").split(",")[2];
        DevID = getIntent().getStringExtra("DevID").toString();
        ChannelNO = getIntent().getStringExtra("ChannelNO").toString();
        initUI();
    }
	private void initUI() {
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new backOnClickListener());
		
		//设备密码
		etxtNewDeyKey = (EditText)findViewById(R.id.hostnewpwd);
		etxtDevKey = (EditText)findViewById(R.id.hostpwd);
		//绑定休眠唤醒状态
		spinnerSleep = (Spinner)findViewById(R.id.spinnerSleep);
		ArrayAdapter<String> adapterSleep = new ArrayAdapter<String>(
				SafepolicyListSet.this, android.R.layout.simple_spinner_item,
				MsgEnum.SLEEP_NAME);
		adapterSleep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerSleep.setAdapter(adapterSleep);
		
		//绑定同时观看人数
		spinnerPerson = (Spinner)findViewById(R.id.spinnerPerson);
		ArrayAdapter<String> adapterPerson = new ArrayAdapter<String>(
				SafepolicyListSet.this, android.R.layout.simple_spinner_item,
				MsgEnum.PERSON);
		adapterPerson.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPerson.setAdapter(adapterPerson);
		
		//重启摄像机
		cbxRestart = (CheckBox)findViewById(R.id.chkrestart);
		
		btnSave = (Button)findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new btnSaveOnClickListener());
	}
	
	//返回
	private final class backOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			SafepolicyListSet.this.finish();
		}
	}
	
	//保存
	private final class btnSaveOnClickListener implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			dialog = ProgressDialog.show(SafepolicyListSet.this, "", "正在提交数据，请等待...", true);
			baseurlString = P2pBaseUrl.BaseUrl(sp);
			SafeRequest sr = new SafeRequest();
			sr.setAccount(Account);
			sr.setLoginSession(loginSession);
			sr.setDevID(DevID);
			//设备密码
			String strDevKey = etxtDevKey.getText().toString().trim();
			String strNewDevKey = etxtNewDeyKey.getText().toString().trim();
			if(!strDevKey.equals("") && !strNewDevKey.equals(""))
			{
				sr.setDevKey(strDevKey);
				sr.setNewDevKey(strNewDevKey);
				// 开启线程
				thd = new Threadhandler(mHandler, sr, baseurlString, "SetDevKey");
				thd.start();
			}
			//同时观看人数
			Long maxlineIndex = spinnerPerson.getSelectedItemId();
			String strPersonValue = MsgEnum.PERSON[maxlineIndex.intValue()];
			if(!strPersonValue.equals("请选择"))
			{
				
				sr.setChannelNo(Integer.parseInt(ChannelNO));
				sr.setMaxLineNum(Integer.parseInt(strPersonValue));
				// 开启线程
				thd = new Threadhandler(mHandler, sr, baseurlString, "SetMaxLineNum");
				thd.start();
			}
			//重启摄像机
			if(cbxRestart.isChecked())
			{
				// 开启线程
				thd = new Threadhandler(mHandler, sr, baseurlString, "RebootDev");
				thd.start();
			}
			
		}
		
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
				Toast.makeText(SafepolicyListSet.this, "设置成功！", 0).show();
				break;
			case MsgEnum.SESSION_TIMEOUT:
				Toast.makeText(SafepolicyListSet.this, "SESSION超时！", 0).show();
				break;
			case MsgEnum.DEVICE_EXISTS_NULL:
				Toast.makeText(SafepolicyListSet.this, "设备不存在！", 0).show();
				break;
			case MsgEnum.ACCOUNT_NOT_EXIST:
				Toast.makeText(SafepolicyListSet.this, "用户不存在！", 0).show();
				break;
			default:
				break;
			}
    	}
	
    };
	
}
