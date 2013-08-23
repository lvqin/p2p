﻿package com.montnets.android.zmon;

import cn.mw.p2p.unitily.ExitApplication;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	
	private static String userInfo;//用户信息
	private TabHost localTabHost;
	private TabWidget tabWidget;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.tabmain_layout);
        ExitApplication.getInstance().addActivity(this);
        SharedPreferences sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
        userInfo = sp.getString("userInfo", "");
		System.out.println("用户信息：" + userInfo);
		initTab();
    }
	
	/**
	 * 初始化标签页
	 */
	  private void initTab()
	  {
		localTabHost = getTabHost();
		tabWidget = localTabHost.getTabWidget(); 
	    setTabItem(localTabHost, R.drawable.video, "视频播放", "VIDEO_PALY", GroupDeviceListActitviy1.class);
	    setTabItem(localTabHost, R.drawable.ecloud, "设备录像", "YUNVIDEO", DeviceListActitviy.class);
	    setTabItem(localTabHost, R.drawable.guanli, "本地图像", "PIC_MANAGER", FileExplorer.class);
	    setTabItem(localTabHost, R.drawable.setting, "设置", "SET", Setting.class);
	    localTabHost.setCurrentTab(0);
	    //设置E存储选项不可点击
//	    getTabWidget().getChildTabViewAt(2).setClickable(false);

	    View view = tabWidget.getChildAt(0); 
	    if(localTabHost.getCurrentTab()==0){ 
	    	view.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_check_bg)); 
	    }

	    
	    /** 
	     * 当点击tab选项卡的时候，更改当前的背景 
	     */ 
		localTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				for (int i = 0; i < tabWidget.getChildCount(); i++) {
//					if (i == 2)
//						continue;
					View v = tabWidget.getChildAt(i);
					if (localTabHost.getCurrentTab() == i) {
						v.setBackgroundDrawable(getResources().getDrawable(
								R.drawable.btn_check_bg));
					} else {
						v.setBackgroundDrawable(getResources().getDrawable(
								R.drawable.bottom_bar_bg));
					}
				}
			}
		});
	  }
	  
	  private void setTabItem(TabHost paramTabHost, int paramInt, String paramString1, String paramString2, Class<?> paramClass)
	  {
		    String JumpType;//跳转类型
			View localView = initView(paramInt, paramString1);
			Intent localIntent = new Intent(this, paramClass);
			if(paramString2.equals("YUNVIDEO"))
			{
				JumpType = "DEVRECORD";
			}
			else{
				JumpType = "DEVRLIST";
			}
			localIntent.putExtra("JumpType", JumpType);
			localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			paramTabHost.addTab(paramTabHost.newTabSpec(paramString2)
					.setIndicator(localView).setContent(localIntent));
	  }
	  
	  private View initView(int paramInt, String paramString)
	  {
		    View localView = View.inflate(this, R.layout.tab_layout, null);
		    ((ImageView)localView.findViewById(R.id.tab_imageview_icon)).setBackgroundResource(paramInt);
		    ((TextView)localView.findViewById(R.id.tab_textview)).setText(paramString);
		    return localView;
	  }
	  
	  protected void onDestroy()
	  {
	    super.onDestroy();
	  }

	  protected void onStop()
	  {
	    super.onStop();
	  }

}
