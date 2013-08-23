﻿package com.montnets.android.zmon;

import org.libsdl.app.SDLActivity;
import org.libsdl.app.SDLSurface;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.mw.p2p.unitily.ExitApplication;

public class LocalPlayer extends Activity {
	public final static String URL = "URL";
	private SDLActivity mSoftPlayer = null;
	private FrameLayout frameContainer;
	private TextView mTotalTime;
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if (!Thread.currentThread().isInterrupted()) {
				System.out.println("receive msg : " + msg.what);
				
				switch (msg.what) {
				case FullScreenPlayer2.MSG_OPEN_OK:
					break;
				case FullScreenPlayer2.MSG_OPEN_ERROR:
					break;
				case FullScreenPlayer2.MSG_LOAD_FINISHED:
					break;
				case FullScreenPlayer2.MSG_LOAD_UNFINISHED:
					Toast.makeText(LocalPlayer.this, "播放完毕", Toast.LENGTH_LONG).show();
					mSoftPlayer.nativePause();
					break;
				default:
					break;
				}
			}
			super.handleMessage(msg);
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);         
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.yb_local_player);
		ExitApplication.getInstance().addActivity(this);
		
		initUI();
		
		Intent i = this.getIntent();
		String struri = i.getExtras().getString(URL);
		startPlayVideo(struri);
	}

	private void initUI() {
		frameContainer = (FrameLayout)findViewById(R.id.framecontainer);
	}

	/**
	 * TODO 播放视频
	 */
	private void startPlayVideo(final String struri)
	{	
		mSoftPlayer = new SDLActivity(getApplication(), handler, struri);
		SDLSurface surface = mSoftPlayer.getSDLSurface();

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		params.gravity = Gravity.CENTER;
		surface.setLayoutParams(params);
		frameContainer.addView(surface);
		
	}
	
	protected void onDestroy() {
		super.onDestroy();
		mSoftPlayer.onDestroy();
	}
}
