package com.montnets.android.zmon;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.mw.p2p.unitily.ExitApplication;

import com.broov.player.AudioThread;
import com.broov.player.DemoRenderer;
import com.broov.player.GLSurfaceView_SDL;
import com.broov.player.Globals;

public class LocalPlayer3 extends Activity {
	// variable member
	private String mURL;
	private AudioThread mAudioThread;
	private DemoRenderer mRenderer;
	private TextView tvstateRemark1;
	
	// Debug member
	private int dProgress;
	
	// UI member
	private GLSurfaceView_SDL mSurfaceView;
	
	@Override
	public void onBackPressed() {
		if(mRenderer != null){
			mRenderer.exitApp();
		}
	}
	
	@Override
	protected void onStop(){
		Toast.makeText(LocalPlayer3.this, "录像播放完成", Toast.LENGTH_SHORT).show();
		super.onStop();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);         
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, 
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.localplayer3);
		tvstateRemark1 = (TextView) findViewById(R.id.tv_stateRemark1);
		ExitApplication.getInstance().addActivity(this);
		
		initMember();
		initSDL();
	}
	
	private void initMember() {
		Intent i = getIntent();
		mURL = i.getExtras().getString("URL");
		mSurfaceView = (GLSurfaceView_SDL)findViewById(R.id.glsurfaceview);
	}

	private void initSDL() {
    	Globals.setNativeVideoPlayer(false);
    	
    	//Native libraries loading code
    	Globals.LoadNativeLibraries();
    	Globals.setFileName(mURL);
    	System.out.printf("%d. native libraries loaded", dProgress++);
    	
    	//Audio thread initializer
    	mAudioThread = new AudioThread(this);
    	System.out.printf("%d. Audio thread initialized", dProgress++);
    	
    	//Custom Renderer
    	mRenderer = new DemoRenderer(this, handler5);
    	mSurfaceView.setRenderer(mRenderer);
    	System.out.printf("%d. Set the surface view renderer\n", dProgress++);
    	
    	//Get SurfaceHolder
    	SurfaceHolder sh = mSurfaceView.getHolder();
    	sh.addCallback(mSurfaceView);
    	System.out.printf("%d. Added the holder callback", dProgress++);
	}
	private Handler handler5 = new Handler(){
    	public void handleMessage(Message msg){
    		switch(msg.what){
    		case 1:
    			//progressBar.setVisibility(ProgressBar.GONE);
    			System.out.println("============>" + "这里调用了函数！");
    			break;
    		case 2:
    			Bundle b = msg.getData();
    			tvstateRemark1.setText(b.getString("stateRemark"));
    			break;
    			default:
    				break;
    		}
    	}
    };
}
