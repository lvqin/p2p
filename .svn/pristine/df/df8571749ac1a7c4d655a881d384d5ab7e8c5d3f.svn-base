package com.montnets.android.zmon;

import cn.mw.p2p.unitily.ExitApplication;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class TabSysDevset extends TabActivity {
	private TabHost tabhost;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tabs_syset);
		ExitApplication.getInstance().addActivity(this);
		setViews();
	}

	private void setViews() {
		
		tabhost = this.getTabHost();
		tabhost.addTab(tabhost.newTabSpec("Tab1")
				.setIndicator("服务设置", this.getResources().getDrawable(R.drawable.sett1))
				.setContent(new Intent(TabSysDevset.this, Sevrset.class)));
		tabhost.addTab(tabhost.newTabSpec("Tab2")
				.setIndicator("设备配置", this.getResources().getDrawable(R.drawable.download))
				.setContent(new Intent(TabSysDevset.this, ZxingActivity.class)));
	}
}
