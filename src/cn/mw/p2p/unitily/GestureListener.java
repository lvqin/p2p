﻿package cn.mw.p2p.unitily;

import android.os.Handler;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

public class GestureListener implements OnTouchListener, OnGestureListener, OnDoubleTapListener {
	private GestureDetector mGestureDetector;
	private Handler mHandler;
	private LinearLayout[] ImageLinearLayout = new LinearLayout[4];
	private LinearLayout[] LinearLayout_Group = new LinearLayout[2];
	private String strTarget = "SplitScreenPlayer";
	
	public GestureListener(Handler mHandler,LinearLayout[] ImageLinearLayout,
			LinearLayout[] LinearLayout_Group){
		this.mGestureDetector = new GestureDetector(this);
		this.mHandler = mHandler;
		this.ImageLinearLayout = ImageLinearLayout;
		this.LinearLayout_Group = LinearLayout_Group;
	}
	
	public GestureListener(Handler mHandler){
		this.mGestureDetector = new GestureDetector(this);
		this.mHandler = mHandler;
		strTarget = "FullScreenPlayer";
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
        if (e1.getX()-e2.getX() > 100   
                && Math.abs(velocityX) > 0) {   
            // Fling left   
            mHandler.sendEmptyMessage(MsgEnum.PTZ_LEFT);  
        } else if (e2.getX()-e1.getX() > 100   
                && Math.abs(velocityX) > 0) {   
            // Fling right   
            mHandler.sendEmptyMessage(MsgEnum.PTZ_RIGHT);  
        } 
        
        if(e1.getY()-e2.getY() > 100
        		&& Math.abs(velocityY) > 0){
        	// Fling Up
        	mHandler.sendEmptyMessage(MsgEnum.PTZ_UP);
        }else if( e2.getY() - e1.getY() >100 
        		&& Math.abs(velocityY) > 0){
        	// Fling down
        	mHandler.sendEmptyMessage(MsgEnum.PTZ_DOWN);
        }
        return false; 
	}

	@Override
	public boolean onDoubleTap(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	//没有长按也没有滑动时触发该事件
	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		mHandler.sendEmptyMessage(MsgEnum.SINGLEDOWN);
		return false;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		if (strTarget.equals("SplitScreenPlayer")) {
			LinearLayout_Group[0].setVisibility(View.VISIBLE);
			LinearLayout_Group[1].setVisibility(View.VISIBLE);
			for (int i = 0; i < 4; i++) {
				ImageLinearLayout[i].setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return mGestureDetector.onTouchEvent(event);
	}

}
