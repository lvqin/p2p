﻿package com.montnets.android.zmon;

import cn.mw.p2p.unitily.ExitApplication;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class SafepolicyList extends Activity {
	private static String userIDString;//用户名
	private ImageButton imgbtnBack;
	private ListView lvSafepolicy;
	private String DevID;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.safepolicylist_layout);
        ExitApplication.getInstance().addActivity(this);
		SharedPreferences sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
		userIDString = sp.getString("userInfo", "").split(",")[0];
		System.out.println("RecpolicyList-用户ID：" + userIDString);
		DevID = getIntent().getStringExtra("DevID");
		initUI();
	}

	private void initUI() {
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new backOnClickListener());
		lvSafepolicy = (ListView)findViewById(R.id.lvSafepolicyList);
		
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
			SafepolicyList.this.finish();
		}
	}
	
	public static final int ADDPOLICY = android.view.Menu.FIRST;

	
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		menu.add(0, ADDPOLICY, 0, "添加安全策略");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case ADDPOLICY:
			Intent intent = new Intent(SafepolicyList.this, SafepolicyListSet.class);
			intent.putExtra("DevID", DevID);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
