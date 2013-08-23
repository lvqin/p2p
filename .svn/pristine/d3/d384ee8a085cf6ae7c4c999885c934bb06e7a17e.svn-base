package com.montnets.android.zmon;

import cn.mw.p2p.unitily.ExitApplication;
import cn.mw.p2p.unitily.MsgEnum;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends Activity {

	private ImageButton imgbtnBack;
	private TextView videoCelue;//录像策略
	private TextView safeCelue;//安全策略
	private TextView logCelue;//日志查询
	private String loginType;
	private RelativeLayout lineDev,linezxing,lineuserset,lineAboutus,lineHelp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(1);
		setContentView(R.layout.setting_layout);
		ExitApplication.getInstance().addActivity(this);
		SharedPreferences sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
		loginType = sp.getString("loginType", "0");
		initUI();
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new backOnClickListener());
		//设备管理
		lineDev = (RelativeLayout)findViewById(R.id.lineDev);
		lineDev.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Setting.this, DevListActivity.class);
				startActivity(intent);
			}
		});
		//录像策略
		videoCelue = (TextView)findViewById(R.id.videoCelue);
		videoCelue.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Setting.this, RecordActivity.class);
				intent.putExtra("SkipType", MsgEnum.SKIPTYPE_RECORDS);
				startActivity(intent);
			}
		});
		//安全策略
		safeCelue = (TextView)findViewById(R.id.safeCelue);
		safeCelue.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Setting.this, RecordActivity.class);
				intent.putExtra("SkipType", MsgEnum.SKIPTYPE_SAFE);
				startActivity(intent);
			}
		});
		//用户设置
		lineuserset = (RelativeLayout)findViewById(R.id.lineuserset);
		lineuserset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(loginType.equals("1"))
				{
					Toast.makeText(Setting.this, "设备登录无法执行此操作", 0).show();
					return;
				}
				Intent intent = new Intent(Setting.this, AccountDataUpdate2.class);
				startActivity(intent);
			}
		});
		//日志查询
		logCelue = (TextView)findViewById(R.id.logCelue);
		logCelue.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Setting.this, SysLog.class);
				startActivity(intent);
			}
		});
		//关于
		lineAboutus = (RelativeLayout)findViewById(R.id.lineAboutus);
		lineAboutus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Setting.this, About.class);
				startActivity(intent);
			}
		});
		//帮助
		lineHelp = (RelativeLayout)findViewById(R.id.lineHelp);
		lineHelp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Setting.this, Help.class);
				startActivity(intent);
			}
		});
		//生成二维码
		linezxing = (RelativeLayout)findViewById(R.id.linezxing);
		linezxing.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Setting.this, ZxingActivity.class);
				startActivity(intent);
			}
		});
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
			Setting.this.finish();
		}
	}

}
