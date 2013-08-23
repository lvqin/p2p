package com.montnets.android.zmon;


import java.util.ArrayList;

import cn.mw.p2p.Request.GetSysLogRequest;
import cn.mw.p2p.bean.LogDescBean;
import cn.mw.p2p.handler.MethodListCount;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class SysLog extends Activity {
	private ImageButton imgbtnBack;
	private ListView lvSysLog;
	private String userIDString;//用户名
	private String loginSession = null;
	private ProgressDialog dialog;
	private Threadhandler thd;
	private String baseurlString;
	private ArrayList<LogDescBean> arrLogDescBeans;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(1);
		setContentView(R.layout.syslog_layout);
		ExitApplication.getInstance().addActivity(this);
		SharedPreferences sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
		userIDString = sp.getString("userInfo", "").split(",")[0];
		loginSession = sp.getString("userInfo", "").split(",")[2];
		baseurlString = P2pBaseUrl.BaseUrl(sp);
		initUI();
		getSysLogList();
	}
	
	/**
	 * 初始化UI
	 */
	private void initUI() {
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new backOnClickListener());
		
	}
	
	/**
	 * 返回操作
	 * 
	 * @author Administrator
	 * 
	 */
	private final class backOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			SysLog.this.finish();
		}
	}
	
	private void getSysLogList()
	{
		GetSysLogRequest glr = new GetSysLogRequest();
		glr.setAccount(userIDString);
		glr.setLoginSession(loginSession);
		dialog = ProgressDialog.show(SysLog.this, "", "正在获取录像系统日志", true);
		thd = new Threadhandler(mHandler, glr, baseurlString, "GetSysLog");
		thd.start();
	}
	
	private final Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			dialog.dismiss();
			int msgID = msg.what;
			switch (msgID) {
			case MsgEnum.SUCCESS:
				arrLogDescBeans = MethodListCount.arraySysLogList;
				lvSysLog = (ListView)findViewById(R.id.lvsyslogList);
				break;
			case MsgEnum.USERNAME_NULL:
				Toast.makeText(SysLog.this, "用户不存在！", 0).show();
				break;
			case MsgEnum.SESSION_TIMEOUT:
				Toast.makeText(SysLog.this, "SESSION超时！", 0).show();
				break;
			default:
				Toast.makeText(SysLog.this, "查询失败！", 0).show();
				break;
			}
		}
	};
}
