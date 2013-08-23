package com.montnets.android.zmon;

import cn.mw.p2p.unitily.ExitApplication;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class FileListActivity extends Activity {
	
	private static String userIDString;//用户名
	private ImageButton imgbtnBack;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.file_list_layout);
        ExitApplication.getInstance().addActivity(this);
		SharedPreferences sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
		userIDString =sp.getString("userInfo", "").split(",")[0];
		System.out.println("FileListActivity-用户ID：" + userIDString);
		initUI();
	}
	
	/**
	 * 初始化UI
	 */
	private void initUI()
	{
		imgbtnBack = (ImageButton)findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new backOnClickListener());
	}
	
	/**
	 * 返回操作
	 * @author Administrator
	 *
	 */
	private final class backOnClickListener implements View.OnClickListener
	{

		@Override
		public void onClick(View v) {
			FileListActivity.this.finish();
		}
	}
}
