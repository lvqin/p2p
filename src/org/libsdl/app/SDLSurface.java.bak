package org.libsdl.app;

import cn.mw.p2p.unitily.MsgEnum;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
SDLSurface. This is what we draw on, so we need to know when it's created
in order to do anything useful. 

Because of this, that's where we set up the SDL thread
*/
public class SDLSurface extends SurfaceView implements SurfaceHolder.Callback, 
View.OnKeyListener, View.OnTouchListener, SensorEventListener, OnGestureListener  {

// Sensors
private static SensorManager mSensorManager;
private static  AudioManager mAudioManager;
private static String ClassName = "SDLSurface";

private GestureDetector mGestureDetector;

// Startup    
public SDLSurface(Context context) {
    super(context);
    getHolder().addCallback(this); 

    setFocusable(true);
    setFocusableInTouchMode(true);
    requestFocus();
    setLongClickable(true);
    setOnKeyListener(this); 
    setOnTouchListener(this);   

    mSensorManager = (SensorManager)context.getSystemService("sensor");  
    mAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    
    mGestureDetector = new GestureDetector(this);
}

// Called when we have a valid drawing surface
public void surfaceCreated(SurfaceHolder holder) {
    Log.v(ClassName, "surfaceCreated()");
    holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
    SDLActivity.createEGLSurface();
    enableSensor(Sensor.TYPE_ACCELEROMETER, true);
}

// Called when we lose the surface
public void surfaceDestroyed(SurfaceHolder holder) {
    Log.v(ClassName, "surfaceDestroyed()");
    SDLActivity.nativePause();
    enableSensor(Sensor.TYPE_ACCELEROMETER, false);
}

// Called when the surface is resized
public void surfaceChanged(SurfaceHolder holder,
                           int format, int width, int height) {
    Log.v(ClassName, "surfaceChanged()");

    int sdlFormat = 0x85151002; // SDL_PIXELFORMAT_RGB565 by default
    switch (format) {
    case PixelFormat.A_8:
        Log.v(ClassName, "pixel format A_8");
        break;
    case PixelFormat.LA_88:
        Log.v(ClassName, "pixel format LA_88");
        break;
    case PixelFormat.L_8:
        Log.v(ClassName, "pixel format L_8");
        break;
    case PixelFormat.RGBA_4444:
        Log.v(ClassName, "pixel format RGBA_4444");
        sdlFormat = 0x85421002; // SDL_PIXELFORMAT_RGBA4444
        break;
    case PixelFormat.RGBA_5551:
        Log.v(ClassName, "pixel format RGBA_5551");
        sdlFormat = 0x85441002; // SDL_PIXELFORMAT_RGBA5551
        break;
    case PixelFormat.RGBA_8888:
        Log.v(ClassName, "pixel format RGBA_8888");
        sdlFormat = 0x86462004; // SDL_PIXELFORMAT_RGBA8888
        break;
    case PixelFormat.RGBX_8888:
        Log.v(ClassName, "pixel format RGBX_8888");
        sdlFormat = 0x86262004; // SDL_PIXELFORMAT_RGBX8888
        break;
    case PixelFormat.RGB_332:
        Log.v(ClassName, "pixel format RGB_332");
        sdlFormat = 0x84110801; // SDL_PIXELFORMAT_RGB332
        break;
    case PixelFormat.RGB_565:
        Log.v(ClassName, "pixel format RGB_565");
        sdlFormat = 0x85151002; // SDL_PIXELFORMAT_RGB565
        break;
    case PixelFormat.RGB_888:
        Log.v(ClassName, "pixel format RGB_888");
        // Not sure this is right, maybe SDL_PIXELFORMAT_RGB24 instead?
        sdlFormat = 0x86161804; // SDL_PIXELFORMAT_RGB888
        break;
    default:
        Log.v(ClassName, "pixel format unknown " + format);
        break;
    }
    SDLActivity.onNativeResize(width, height, sdlFormat);
    Log.v(ClassName, "Window size:" + width + "x"+height);

    SDLActivity.startApp();
}

// unused
public void onDraw(Canvas canvas) {}



// Key events
public boolean onKey(View  v, int keyCode, KeyEvent event) {
	
    Log.v(ClassName, "key down: " + keyCode + "aciton:" + event.getAction());
//    VolumeDown();
//    if (event.getAction() == KeyEvent.ACTION_DOWN) {
//        Log.v(ClassName, "key down: " + keyCode);
//        SDLActivity.onNativeKeyDown(keyCode);
//        return true;
//    }
//    else if (event.getAction() == KeyEvent.ACTION_UP) {
//        //Log.v(ClassName, "key up: " + keyCode);
//        SDLActivity.onNativeKeyUp(keyCode);
//        return true;
//    }else 
    if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
		 VolumeUp();
		 return true;
		 //return false;
	 }
	 else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
		 VolumeDown();
		 return true;
	 }
    
    return false;
}

public void VolumeUp(){
	 mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE,   AudioManager.FX_FOCUS_NAVIGATION_UP);
}

public void VolumeDown(){
	 //mAudioManager.adjustVolume(AudioManager.ADJUST_RAISE, 0);
	 mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER,   AudioManager.FX_FOCUS_NAVIGATION_UP);

}



// Touch events
public boolean onTouch(View v, MotionEvent event) {
	return mGestureDetector.onTouchEvent(event);
//    {
//        Log.i("Test", "natvie xxxxxxxxxxxxxxxxxxxxx");
//
//         final int touchDevId = event.getDeviceId();
//         final int pointerCount = event.getPointerCount();
//         // touchId, pointerId, action, x, y, pressure
//         int actionPointerIndex = event.getActionIndex();
//         int pointerFingerId = event.getPointerId(actionPointerIndex);
//         int action = event.getActionMasked();
//
//         float x = event.getX(actionPointerIndex);
//         float y = event.getY(actionPointerIndex);
//         float p = event.getPressure(actionPointerIndex);
//
//         if (action == MotionEvent.ACTION_MOVE && pointerCount > 1) {
//            // TODO send motion to every pointer if its position has
//            // changed since prev event.
//            for (int i = 0; i < pointerCount; i++) {
//                pointerFingerId = event.getPointerId(i);
//                x = event.getX(i);
//                y = event.getY(i);
//                p = event.getPressure(i);
//                SDLActivity.onNativeTouch(touchDevId, pointerFingerId, action, x, y, p);
//            }
//         } else {
//            SDLActivity.onNativeTouch(touchDevId, pointerFingerId, action, x, y, p);
//         }
//    }
//  //修改为false，为了响应上层事件
//  return false;
} 

// Sensor events
public void enableSensor(int sensortype, boolean enabled) {
    // TODO: This uses getDefaultSensor - what if we have >1 accels?
    if (enabled) {
        mSensorManager.registerListener(this, 
                        mSensorManager.getDefaultSensor(sensortype), 
                        SensorManager.SENSOR_DELAY_GAME, null);
    } else {
        mSensorManager.unregisterListener(this, 
                        mSensorManager.getDefaultSensor(sensortype));
    }
}

public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // TODO
}

public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
        SDLActivity.onNativeAccel(event.values[0] / SensorManager.GRAVITY_EARTH,
                                  event.values[1] / SensorManager.GRAVITY_EARTH,
                                  event.values[2] / SensorManager.GRAVITY_EARTH);
    }
}

@Override
public boolean onDown(MotionEvent e) {
	return false;
}

@Override
public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
		float velocityY) {
	 if (e1.getX()-e2.getX() > 100   
             && Math.abs(velocityX) > 0) {   
         // Fling left   
         SDLActivity.gestureMove(SDLActivity.MOVE_LEFT);
     } else if (e2.getX()-e1.getX() > 100   
             && Math.abs(velocityX) > 0) {   
         // Fling right   
         SDLActivity.gestureMove(SDLActivity.MOVE_RIGHT);
     }else if(e1.getY()-e2.getY() > 100
     		&& Math.abs(velocityY) > 0){
     	// Fling Up
     	SDLActivity.gestureMove(SDLActivity.MOVE_UP);
     }else if( e2.getY() - e1.getY() >100 
     		&& Math.abs(velocityY) > 0){
     	// Fling down
     	SDLActivity.gestureMove(SDLActivity.MOVE_DOWN);
     }
	return false;
}

@Override
public void onLongPress(MotionEvent e) {
	Log.v("gestureDetector", "onLongPress");
}

@Override
public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
		float distanceY) {
	return false;
}

@Override
public void onShowPress(MotionEvent e) {
}

@Override
public boolean onSingleTapUp(MotionEvent e) {
	return false;
}

}