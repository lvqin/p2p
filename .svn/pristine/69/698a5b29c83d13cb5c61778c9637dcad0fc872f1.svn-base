package org.libsdl.app;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.*;

import android.app.*;
import android.content.*;
import android.view.*;
import android.os.*;
import android.util.Log;
import android.graphics.*;
import android.text.method.*;
import android.text.*;
import android.media.*;
import android.hardware.*;
import android.content.*;

import java.lang.*;


/**
    SDL Activity
*/
public class SDLActivity  {

    // Main components
    private static SDLActivity mSingleton;
    private static SDLSurface mSurface;
    private static Handler mHandler;

    // This is what SDL runs in. It invokes SDL_main(), eventually
    private static Thread mSDLThread;
    private static boolean mInit = false;

    // Audio
    private static Thread mAudioThread;
    private static AudioTrack mAudioTrack;

    // EGL private objects
    private static EGLContext  mEGLContext;
    private static EGLSurface  mEGLSurface;
    private static EGLDisplay  mEGLDisplay;
    private static EGLConfig   mEGLConfig;
    private static int mGLMajor, mGLMinor;
    private static String mFilename;

    // gesture
    public final static int MOVE_LEFT = 3;
    public final static int MOVE_RIGHT = 4;
    public final static int MOVE_UP = 1;
    public final static int MOVE_DOWN = 2;
    
    // Load the .so
    static {
        System.loadLibrary("ffmpeg");
        System.loadLibrary("SDL");
        System.loadLibrary("main");
    }
    public SDLActivity(Context context, Handler handler,String filename){
    	  mSingleton = this;
    	  mFilename = filename;
    	  mSurface = new SDLSurface(context);
    	  mHandler = handler;
    	  SurfaceHolder holder = mSurface.getHolder();

    }
    
    public SDLSurface getSDLSurface(){
    	return mSurface;
    }
    

	public int getDuration(){
		return this.PlayerGetDuration();
	}
	
	public int getCurrentPosition(){
		return this.PlayergetCurrentPosition();
	}
	
	public void start(){
		if(isPlaying() == false)
			this.PlayerPause();
	}
	
	public void stop(){
		if(isPlaying() == true)
			this.PlayerPause();
		
	}
	
	public boolean isPlaying(){
		return this.PlayerIsPlay()==1?true:false;
	}
	
	//鏄惁鍦ㄧ洃鍚�
	public boolean isSounding(){
		return this.PlayerIsSound()==1?true:false;
	}
	
	//鏄惁鍦ㄥ綍鍍�	
	public boolean isRecording(){
		return this.PlayerIsRecord()==1?true:false;
	}
	
	public void seekTo(int msec){
		PlayerSeekTo(msec);
	}
	
	
	public void exit(){
		PlayerExit();

	}
	
	public void resize(boolean fullscreen){
	}
    
    // Setup
//    protected void onCreate(Bundle savedInstanceState) {
//        //Log.v("SDL", "onCreate()");
//        super.onCreate(savedInstanceState);
//        // So we can call stuff from static callbacks
//        mSingleton = this;
//
//        // Set up the surface
//        mSurface = new SDLSurface(getApplication());
//        setContentView(mSurface);
//        SurfaceHolder holder = mSurface.getHolder();
//    }

    // Events
    public void onPause() {
        //super.onPause();
        Log.v("SDLActivity", "onPause()");
        SDLActivity.nativePause();
    }

    public void onResume() {
    	//super.onResume();
        Log.v("SDLActivity", "onResume()");
       
        SDLActivity.nativeResume();
    }

    public void onDestroy() {
        //super.onDestroy();
        Log.v("SDLActivity", "onDestroy()");
        // Send a quit message to the application
        SDLActivity.nativeQuit();

        // Now wait for the SDL thread to quit
        if (mSDLThread != null) {
            try {
                mSDLThread.join();
            } catch(Exception e) {
                Log.v("SDLActivity", "Problem stopping thread: " + e);
            }
            mSDLThread = null;

            //Log.v("SDL", "Finished waiting for SDL thread");
        }
    }
    
    //鏈湴褰曞儚锛屽弬鏁帮細褰曞儚璺緞锛岃繑鍥炲�锛�-鎴愬姛銆�-澶辫触
	public int StartRecord(String strRecordPath)
	{
		return PlayerStartRecord(strRecordPath);
	}
	
    //鍋滄褰曞儚
	public void StopRecord()
	{
		PlayerStopRecord();
	}
    
	//鏈湴鎶撴媿锛屽弬鏁帮細鍥惧儚璺緞锛岃繑鍥炲�锛�-鎴愬姛銆�-澶辫触
	public int CaptureImage(String strImagePath)
	{
		return PlayerCaptureImage(strImagePath);
	}
	
	//鎵撳紑澹伴煶
	public void OpenSound()
	{
		PlayerOpenSound();
	}

	//鍏抽棴澹伴煶
	public void CloseSound()
	{
		PlayerCloseSound();
	}	
	
    // Messages from the SDLMain thread
    static int COMMAND_CHANGE_TITLE = 1;

    // Handler for the messages
    static Handler commandHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.arg1 == COMMAND_CHANGE_TITLE) {
                //setTitle((String)msg.obj);
            	Log.v("SDLActivity", "Set title");
            }
            if(msg.arg1 == 1000){
            	SDLActivity.notify(1000);
            }
        }
    };



	public static void notify(int msg){
		Log.v("SDLActivity", "notify :" + msg);
		mHandler.sendEmptyMessage(msg);
		
	}
    
    // C functions we call
    //public static native void NotifyInit();
    public static native void nativeInit();
    public static native void nativeQuit();
    public static native void nativePause();
    public static native void nativeResume();
    public static native void onNativeResize(int x, int y, int format);
    public static native void onNativeKeyDown(int keycode);
    public static native void onNativeKeyUp(int keycode);
    public static native void onNativeTouch(int touchDevId, int pointerFingerId,
                                            int action, float x, 
                                            float y, float p);
    public static native void onNativeAccel(float x, float y, float z);
    public static native void nativeRunAudioThread();
    
    public static native int PlayerInit();
    public static native int PlayerPrepare(String url);
    public static native int PlayerMain();
    public static native int PlayerExit();
    public static native int PlayerSeekTo(int msec);   
    public static native int PlayerPause();
    public static native int PlayerIsPlay();
    public static native int PlayerGetDuration();
    public static native int PlayergetCurrentPosition();
    
    public static native int  PlayerStartRecord(String strRecordPath);//鏈湴褰曞儚JNI鎺ュ彛
    public static native void PlayerStopRecord();//鍋滄褰曞儚JNI鎺ュ彛
    public static native int  PlayerIsRecord();//鏄惁褰曞儚
    public static native int  PlayerCaptureImage(String strImagePath);//鏈湴鎶撴媿JNI鎺ュ彛
    public static native void PlayerOpenSound();//鎵撳紑澹伴煶JNI鎺ュ彛
    public static native void PlayerCloseSound();//鍏抽棴澹伴煶JNI鎺ュ彛
    public static native int  PlayerIsSound();//鏄惁鐩戝惉   
    
    // Java functions called from C

    public static boolean createGLContext(int majorVersion, int minorVersion) {
        return initEGL(majorVersion, minorVersion);
    }

    public static void flipBuffers() {
        flipEGL();
    }

    public static void setActivityTitle(String title) {
        // Called from SDLMain() thread and can't directly affect the view
        //mSingleton.sendCommand(COMMAND_CHANGE_TITLE, title);
    }

//    public static Context getContext() {
//        return mSingleton;
//    }

    public static void startApp() {
        // Start up the C app thread
        if (mSDLThread == null) {
        	PlayerInit();

        	mSDLThread = new Thread(new SDLMain(mFilename), "SDLThread");
        	mSDLThread.start();
        	
        	
        }
        else {
            SDLActivity.nativeResume();
        }

    }

    // EGL functions
    public static boolean initEGL(int majorVersion, int minorVersion) {
        if (SDLActivity.mEGLDisplay == null) {
            //Log.v("SDL", "Starting up OpenGL ES " + majorVersion + "." + minorVersion);

            try {
                EGL10 egl = (EGL10)EGLContext.getEGL();

                EGLDisplay dpy = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);

                int[] version = new int[2];
                egl.eglInitialize(dpy, version);

                int EGL_OPENGL_ES_BIT = 1;
                int EGL_OPENGL_ES2_BIT = 4;
                int renderableType = 0;
                if (majorVersion == 2) {
                    renderableType = EGL_OPENGL_ES2_BIT;
                } else if (majorVersion == 1) {
                    renderableType = EGL_OPENGL_ES_BIT;
                }
                int[] configSpec = {
                    //EGL10.EGL_DEPTH_SIZE,   16,
                    EGL10.EGL_RENDERABLE_TYPE, renderableType,
                    EGL10.EGL_NONE
                };
                EGLConfig[] configs = new EGLConfig[1];
                int[] num_config = new int[1];
                if (!egl.eglChooseConfig(dpy, configSpec, configs, 1, num_config) || num_config[0] == 0) {
                    Log.e("SDLActivity", "No EGL config available");
                    return false;
                }
                EGLConfig config = configs[0];

                /*int EGL_CONTEXT_CLIENT_VERSION=0x3098;
                int contextAttrs[] = new int[] { EGL_CONTEXT_CLIENT_VERSION, majorVersion, EGL10.EGL_NONE };
                EGLContext ctx = egl.eglCreateContext(dpy, config, EGL10.EGL_NO_CONTEXT, contextAttrs);

                if (ctx == EGL10.EGL_NO_CONTEXT) {
                    Log.e("SDL", "Couldn't create context");
                    return false;
                }
                SDLActivity.mEGLContext = ctx;*/
                SDLActivity.mEGLDisplay = dpy;
                SDLActivity.mEGLConfig = config;
                SDLActivity.mGLMajor = majorVersion;
                SDLActivity.mGLMinor = minorVersion;

                SDLActivity.createEGLSurface();
            } catch(Exception e) {
                Log.v("SDLActivity", e + "");
                for (StackTraceElement s : e.getStackTrace()) {
                    Log.v("SDLActivity", s.toString());
                }
            }
        }
        else SDLActivity.createEGLSurface();

        return true;
    }

    public static boolean createEGLContext() {
        EGL10 egl = (EGL10)EGLContext.getEGL();
        int EGL_CONTEXT_CLIENT_VERSION=0x3098;
        int contextAttrs[] = new int[] { EGL_CONTEXT_CLIENT_VERSION, SDLActivity.mGLMajor, EGL10.EGL_NONE };
        SDLActivity.mEGLContext = egl.eglCreateContext(SDLActivity.mEGLDisplay, SDLActivity.mEGLConfig, EGL10.EGL_NO_CONTEXT, contextAttrs);
        if (SDLActivity.mEGLContext == EGL10.EGL_NO_CONTEXT) {
            Log.e("SDLActivity", "Couldn't create context");
            return false;
        }
        return true;
    }

    public static boolean createEGLSurface() {
        if (SDLActivity.mEGLDisplay != null && SDLActivity.mEGLConfig != null) {
            EGL10 egl = (EGL10)EGLContext.getEGL();
            if (SDLActivity.mEGLContext == null) createEGLContext();

            Log.v("SDLActivity", "Creating new EGL Surface");
            EGLSurface surface = egl.eglCreateWindowSurface(SDLActivity.mEGLDisplay, SDLActivity.mEGLConfig, SDLActivity.mSurface, null);
            if (surface == EGL10.EGL_NO_SURFACE) {
                Log.e("SDLActivity", "Couldn't create surface");
                return false;
            }

            if (!egl.eglMakeCurrent(SDLActivity.mEGLDisplay, surface, surface, SDLActivity.mEGLContext)) {
                Log.e("SDLActivity", "Old EGL Context doesnt work, trying with a new one");
                createEGLContext();
                if (!egl.eglMakeCurrent(SDLActivity.mEGLDisplay, surface, surface, SDLActivity.mEGLContext)) {
                    Log.e("SDLActivity", "Failed making EGL Context current");
                    return false;
                }
            }
            SDLActivity.mEGLSurface = surface;
            return true;
        }
        return false;
    }

    // EGL buffer flip
    public static void flipEGL() {
        try {
            EGL10 egl = (EGL10)EGLContext.getEGL();

            egl.eglWaitNative(EGL10.EGL_CORE_NATIVE_ENGINE, null);

            // drawing here

            egl.eglWaitGL();

            egl.eglSwapBuffers(SDLActivity.mEGLDisplay, SDLActivity.mEGLSurface);


        } catch(Exception e) {
            Log.v("SDLActivity", "flipEGL(): " + e);
            for (StackTraceElement s : e.getStackTrace()) {
                Log.v("SDLActivity", s.toString());
            }
        }
    }

    // Audio
    private static Object buf;
    
    public static Object audioInit(int sampleRate, boolean is16Bit, boolean isStereo, int desiredFrames) {
        int channelConfig = isStereo ? AudioFormat.CHANNEL_CONFIGURATION_STEREO : AudioFormat.CHANNEL_CONFIGURATION_MONO;
        int audioFormat = is16Bit ? AudioFormat.ENCODING_PCM_16BIT : AudioFormat.ENCODING_PCM_8BIT;
        int frameSize = (isStereo ? 2 : 1) * (is16Bit ? 2 : 1);
        
        Log.v("SDLActivity", "SDL audio: wanted " + (isStereo ? "stereo" : "mono") + " " + (is16Bit ? "16-bit" : "8-bit") + " " + ((float)sampleRate / 1000f) + "kHz, " + desiredFrames + " frames buffer");
        
        // Let the user pick a larger buffer if they really want -- but ye
        // gods they probably shouldn't, the minimums are horrifyingly high
        // latency already
        desiredFrames = Math.max(desiredFrames, (AudioTrack.getMinBufferSize(sampleRate, channelConfig, audioFormat) + frameSize - 1) / frameSize);
        
        mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
                channelConfig, audioFormat, desiredFrames * frameSize, AudioTrack.MODE_STREAM);
        
        audioStartThread();
        
        Log.v("SDLActivity", "SDL audio: got " + ((mAudioTrack.getChannelCount() >= 2) ? "stereo" : "mono") + " " + ((mAudioTrack.getAudioFormat() == AudioFormat.ENCODING_PCM_16BIT) ? "16-bit" : "8-bit") + " " + ((float)mAudioTrack.getSampleRate() / 1000f) + "kHz, " + desiredFrames + " frames buffer");
        
        if (is16Bit) {
            buf = new short[desiredFrames * (isStereo ? 2 : 1)];
        } else {
            buf = new byte[desiredFrames * (isStereo ? 2 : 1)]; 
        }
        return buf;
    }
    
    public static void audioStartThread() {
        mAudioThread = new Thread(new Runnable() {
            public void run() {
                mAudioTrack.play();
                nativeRunAudioThread();
            }
        });
        
        // I'd take REALTIME if I could get it!
        mAudioThread.setPriority(Thread.MAX_PRIORITY);
        mAudioThread.start();
    }
    
    public static void audioWriteShortBuffer(short[] buffer) {
        for (int i = 0; i < buffer.length; ) {
            int result = mAudioTrack.write(buffer, i, buffer.length - i);
            if (result > 0) {
                i += result;
            } else if (result == 0) {
                try {
                    Thread.sleep(1);
                } catch(InterruptedException e) {
                    // Nom nom
                }
            } else {
                Log.w("SDLActivity", "SDL audio: error return from write(short)");
                return;
            }
        }
    }
    
    public static void audioWriteByteBuffer(byte[] buffer) {
        for (int i = 0; i < buffer.length; ) {
            int result = mAudioTrack.write(buffer, i, buffer.length - i);
            if (result > 0) {
                i += result;
            } else if (result == 0) {
                try {
                    Thread.sleep(1);
                } catch(InterruptedException e) {
                    // Nom nom
                }
            } else {
                Log.w("SDLActivity", "SDL audio: error return from write(short)");
                return;
            }
        }
    }
    
    public static void gestureMove(int move) {
		if(mHandler != null){
			mHandler.sendEmptyMessage(move);
		}
	}

    public static void audioQuit() {
        if (mAudioThread != null) {
            try {
                mAudioThread.join();
            } catch(Exception e) {
                Log.v("SDLActivity", "Problem stopping audio thread: " + e);
            }
            mAudioThread = null;

            //Log.v("SDL", "Finished waiting for audio thread");
        }

        if (mAudioTrack != null) {
            mAudioTrack.stop();
            mAudioTrack = null;
        }
    }
}

/**
    Simple nativeInit() runnable
*/
class SDLMain implements Runnable {
	private String mFile;
	
	SDLMain(String f){
		mFile = f;
	}
    public void run() {

    	SDLActivity.nativeInit();
    	SDLActivity.PlayerPrepare(mFile);
        SDLActivity.PlayerMain();

        //Log.v("SDL", "SDL thread terminated");
    }
}



