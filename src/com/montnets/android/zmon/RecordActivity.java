﻿package com.montnets.android.zmon;

import java.util.ArrayList;

import cn.mw.p2p.adpter.DeviceAdapter;
import cn.mw.p2p.adpter.RecordActivityAdapter;
import cn.mw.p2p.bean.VedioPointBean;
import cn.mw.p2p.handler.MethodListCount;
import cn.mw.p2p.unitily.ExitApplication;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class RecordActivity extends Activity {

	private static String userIDString;//用户名
	private ImageButton imgbtnBack;
	private ListView lvRecList;
	private ArrayList<VedioPointBean> videoPoints = null;
	private DeviceAdapter deviceAdapter;
	private Cursor cursor;
	private ProgressDialog dialog;
	private static String strSkipType;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.recordactivity);
        ExitApplication.getInstance().addActivity(this);
		SharedPreferences sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
		userIDString = sp.getString("userInfo", "").split(",")[0];
		System.out.println("RecordActivity-用户ID：" + userIDString);
		strSkipType = getIntent().getStringExtra("SkipType");
		initUI();
    }

	/**
	 * 初始化数据
	 */
	private void initUI() {
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new backOnClickListener());
		lvRecList = (ListView)findViewById(R.id.lvRecList);
		this.lvRecList.setAdapter(new RecordActivityAdapter(this, getDataSqlite(),strSkipType));
		deviceAdapter.closeDB(cursor);
		deviceAdapter.closeDB();
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
			RecordActivity.this.finish();
		}
	}
	
	/**
	 * 获取设备列表
	 * @return
	 */
	private ArrayList<VedioPointBean> getDataSqlite()
	{
		deviceAdapter = new DeviceAdapter(RecordActivity.this);
		dialog = ProgressDialog.show(RecordActivity.this, "", "获取数据中，请等待...", true);
		videoPoints = MethodListCount.getDataArrayListForSqlite(cursor, deviceAdapter, RecordActivity.this);
		dialog.dismiss();
		return videoPoints;

	}

}
