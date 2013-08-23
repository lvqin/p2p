/**
 * 
 */
package com.montnets.android.zmon;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.libsdl.app.SDLActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import cn.mw.p2p.Request.ControlPtzRequest;
import cn.mw.p2p.Request.MonGetAlarmSwitchRequest;
import cn.mw.p2p.Request.MonSetAlarmSwitchRequest;
import cn.mw.p2p.unitily.ExitApplication;
import cn.mw.p2p.unitily.P2pBaseUrl;
import cn.mw.p2p.unitily.WSUtils;
import cn.mw.p2p.unitily.p2punitily;

import com.broov.player.AudioThread;
import com.broov.player.DemoRenderer;
import com.broov.player.GLSurfaceView_SDL;
import com.broov.player.Globals;
import com.montnets.android.zmon.RegActitivty1.TimeCount;

/**
 * @author YangBen
 * 
 */
public class FullScreenPlayer3 extends Activity {

	// variable member
	private String mURL;
	private AudioThread mAudioThread;
	private DemoRenderer mRenderer;

	// UI member
	private GLSurfaceView_SDL mSurfaceView;
	private Button btnPlayStop;
	private Button btnCapture;
	private ToggleButton btnVideotape;
	private ToggleButton btnVoiceKey;
	private ToggleButton btnDefenseKey;
	public ProgressBar progressBar;
	private ImageView imgLeft;
	private ImageView imgRight;
	private ImageView imgUp;
	private ImageView imgDown;
	private RelativeLayout relative;
	private TextView tvstateRemark;

	// Debug member
	private int dProgress;
	private String str22 =null;
	private TimeCount time;
	// from others
	private String userIDString;
	private String loginSession;
	private String DevID;
	private String ChannelNo;
	private String PtzFlag;
	private String baseurlString;

	public final static int MOVE_LEFT = 3;
	public final static int MOVE_RIGHT = 4;
	public final static int MOVE_UP = 1;
	public final static int MOVE_DOWN = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// screen setting
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		ExitApplication.getInstance().addActivity(this);
		setContentView(R.layout.fullscreenplayer3);
		time = new TimeCount(1500, 1000);
		// logic setting
		initUI();
		initMember();
		initSDL();
		progressGetDefenseStatus();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "播放视频链接已断开！",
				Toast.LENGTH_SHORT).show();
		super.onDestroy();

	}

	class NativeClassListener implements OnTouchListener, OnGestureListener,
			OnDoubleTapListener {

		private GestureDetector mGestureDetector;
		private Handler mHandler;

		public NativeClassListener(Handler argHandler) {
			mGestureDetector = new GestureDetector(this);
			mHandler = argHandler;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			Log.v("YB", "onDown");
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			Log.v("YB", "onFling");
			if (e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 0) {
				// Fling left
				mHandler.sendEmptyMessage(FullScreenPlayer3.MOVE_LEFT);
			} else if (e2.getX() - e1.getX() > 100 && Math.abs(velocityX) > 0) {
				// Fling right
				mHandler.sendEmptyMessage(FullScreenPlayer3.MOVE_RIGHT);
			} else if (e1.getY() - e2.getY() > 100 && Math.abs(velocityY) > 0) {
				// Fling Up
				mHandler.sendEmptyMessage(FullScreenPlayer3.MOVE_UP);
			} else if (e2.getY() - e1.getY() > 100 && Math.abs(velocityY) > 0) {
				// Fling down
				mHandler.sendEmptyMessage(FullScreenPlayer3.MOVE_DOWN);
			}
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			Log.v("YB", "onLongPress");
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			Log.v("YB", "onScroll");
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			Log.v("YB", "onShowPress");
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			Log.v("YB", "onSingleTapUp");
			return false;
		}

		@Override
		public boolean onTouch(View arg0, MotionEvent event) {
			Log.v("YB", "onTouch");
			return mGestureDetector.onTouchEvent(event);
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// TODO Auto-generated method stub
			if (relative.getVisibility() == View.GONE) {
				relative.setVisibility(View.VISIBLE);
			} else {
				relative.setVisibility(View.GONE);
			}
			return false;
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

	}

	private void initUI() {
		tvstateRemark = (TextView) findViewById(R.id.tv_stateRemark);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		imgLeft = (ImageView) findViewById(R.id.imageView1);
		imgRight = (ImageView) findViewById(R.id.imageView3);
		imgUp = (ImageView) findViewById(R.id.imageView2);
		imgDown = (ImageView) findViewById(R.id.imageView4);
		relative = (RelativeLayout) findViewById(R.id.Relative);
		// Typeface type= Typeface.createFromAsset(getAssets(),
		// "fonts/broov.ttf");
		mSurfaceView = (GLSurfaceView_SDL) findViewById(R.id.glsurfaceview);
		mSurfaceView.setLongClickable(true);
		mSurfaceView.setOnTouchListener(new NativeClassListener(handler1));
		// play pause
		final String PLAY_START = "播放";
		final String PLAY_PAUSE = "暂停";
		btnPlayStop = (Button) findViewById(R.id.button1);
		// btnPlayStop.setTypeface(type);
		btnPlayStop.setText(PLAY_PAUSE);
		btnPlayStop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				time.start();
				if (mRenderer.nativePlayerIsRecord() == 1) {
					mRenderer.nativePlayerStopRecord();
					Toast.makeText(FullScreenPlayer3.this, "录像已停止！",
							Toast.LENGTH_SHORT).show();
					btnVideotape.setChecked(false);
					if (btnPlayStop.getText().equals(PLAY_PAUSE)) {
						if (mRenderer != null) {
							Log.v("YB", btnPlayStop.getText().toString());
							mRenderer.nativePlayerPlay(); // actually is to
															// pause
						}
						btnPlayStop.setText(PLAY_START);
						tvstateRemark.setText("播放暂停！");
					} else if (btnPlayStop.getText().equals(PLAY_START)) {
						if (mRenderer != null) {
							Log.v("YB", btnPlayStop.getText().toString());
							mRenderer.nativePlayerPause(); // actually is to
															// play
						}
						btnPlayStop.setText(PLAY_PAUSE);
						tvstateRemark.setText("正在播放视频！");
					}
				} else {
					if (btnPlayStop.getText().equals(PLAY_PAUSE)) {
						if (mRenderer != null) {
							Log.v("YB", btnPlayStop.getText().toString());
							mRenderer.nativePlayerPlay(); // actually is to
															// pause
						}
						btnPlayStop.setText(PLAY_START);
						tvstateRemark.setText("播放暂停！");
					} else if (btnPlayStop.getText().equals(PLAY_START)) {
						if (mRenderer != null) {
							Log.v("YB", btnPlayStop.getText().toString());
							mRenderer.nativePlayerPause(); // actually is to
															// play
						}
						btnPlayStop.setText(PLAY_PAUSE);
						tvstateRemark.setText("正在播放视频！");
					}
				}
			}
		});

		// capture
		btnCapture = (Button) findViewById(R.id.button3);
		// btnCapture.setTypeface(type);
		btnCapture.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				time.start();
				Log.v("btnCapture", "on click");
				if (mRenderer.nativePlayerIsPlaying() == 1) {
					Log.v("mSoftPlayer", "is playing");
					if (p2punitily.getSDPath() == null) {
						Log.v("SDcard check", "no");
						Toast.makeText(FullScreenPlayer3.this, "没有SD卡或SD卡被拔出",
								Toast.LENGTH_SHORT).show();
						return;
					} else {
						Log.v("SDcard check", "yes");
						String mRoot = p2punitily.getSDPath() + "/"
								+ P2pBaseUrl.DIR_ROOT;
						String mRoot_image = mRoot + "/"
								+ P2pBaseUrl.DIR_ROOT_IMAGE;
						int result = mRenderer
								.nativePlayerCaptureImage(mRoot_image);
						Log.v("capture result", String.valueOf(result));
                        
                        //tvstateRemark.setText();
						// Toast.makeText(FullScreenPlayer3.this, "抓拍成功",
						// Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(FullScreenPlayer3.this, "播放暂停中，不能抓拍！",
							Toast.LENGTH_SHORT).show();
				}
			}

		});

		// videotape
		btnVideotape = (ToggleButton) findViewById(R.id.toggleButton3);
		// btnVideotape.setTypeface(type);
		btnVideotape
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						time.start();
						if (mRenderer.nativePlayerIsPlaying() == 0) {
							btnVideotape.setChecked(false);
							Toast.makeText(FullScreenPlayer3.this,
									"播放暂停中，不能录像！", Toast.LENGTH_SHORT).show();
						} else {
							if (isChecked) {
								Log.v("btnVideotape", "isChecked true");
								if (p2punitily.getSDPath() == null) {
									Log.v("SDcard check", "no");
									Toast.makeText(FullScreenPlayer3.this,
											"没有SD卡或SD卡被拔出", Toast.LENGTH_SHORT)
											.show();

									btnVideotape.setChecked(false);
								} else {
									Log.v("SDcard check", "yes");
									String mRoot = p2punitily.getSDPath() + "/"
											+ P2pBaseUrl.DIR_ROOT;
									String mRoot_record = mRoot + "/"
											+ P2pBaseUrl.DIR_ROOT_VIDEO;
									int result = mRenderer
											.nativePlayerStartRecord(mRoot_record);
									Log.v("record result",
											String.valueOf(result));
								}
							} else {
								Log.v("btnVideotape", "isChecked false");
								if (mRenderer.nativePlayerIsRecord() == 1) {
									mRenderer.nativePlayerStopRecord();
								}
							}
						}
					}
				});
		// voice key
		btnVoiceKey = (ToggleButton) findViewById(R.id.toggleButton4);
		// btnVoiceKey.setTypeface(type);
		btnVoiceKey
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						time.start();
						if (isChecked) {
							Log.v("btnVoiceKey", "isChecked true");
							if (mRenderer.nativePlayerIsSound() == 0) {
								mRenderer.nativePlayerOpenSound();
							}
						} else {
							Log.v("btnVoiceKey", "isChecked false");
							if (mRenderer.nativePlayerIsSound() == 1) {
								mRenderer.nativePlayerCloseSound();
							}
						}
					}
				});

		// defense key
		btnDefenseKey = (ToggleButton) findViewById(R.id.toggleButton5);
		// btnDefenseKey.setTypeface(type);
		btnDefenseKey.setText("查询中...");
		btnDefenseKey.setClickable(false);
	}

	@Override
	public void onBackPressed() {
		mRenderer.exitApp();
	}

	private void initSDL() {
		Globals.setNativeVideoPlayer(false);

		// Native libraries loading code
		Globals.LoadNativeLibraries();
		Globals.setFileName(mURL);
		System.out.printf("%d. native libraries loaded", dProgress++);

		// Audio thread initializer
		mAudioThread = new AudioThread(this);
		System.out.printf("%d. Audio thread initialized", dProgress++);
		// Custom Renderer
		mRenderer = new DemoRenderer(this, handler5);
		mSurfaceView.setRenderer(mRenderer);
		System.out.printf("%d. Set the surface view renderer\n", dProgress++);

		// Get SurfaceHolder
		SurfaceHolder sh = mSurfaceView.getHolder();
		sh.addCallback(mSurfaceView);
		System.out.printf("%d. Added the holder callback", dProgress++);
	}

	private void initMember() {
		SharedPreferences sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
		String[] strUserList = sp.getString("userInfo", "").split(",");
		userIDString = strUserList[0];
		loginSession = strUserList[2];
		mURL = getIntent().getStringExtra("PlayURL");
		DevID = getIntent().getStringExtra("DevID");
		ChannelNo = getIntent().getStringExtra("ChannelNo");
		PtzFlag = getIntent().getStringExtra("PtzFlag");

		baseurlString = P2pBaseUrl.BaseUrl(sp);
	}

	protected void progressYunTaiControl(final int what) {
		if (PtzFlag.equals("1")) {
			// support YunTai control
			new Thread(new Runnable() {

				@Override
				public void run() {
					ControlPtzRequest request = new ControlPtzRequest();
					request.setAccount(userIDString);
					request.setChannelNo(Integer.parseInt(ChannelNo));
					request.setDevID(DevID);
					request.setLoginSession(loginSession);
					request.setPtzDirection(what);

					PropertyInfo pi = new PropertyInfo();
					pi.setName("req");
					pi.setType(request.getClass());
					pi.setValue(request);

					Message msg = handler4.obtainMessage();
					SoapObject soapObject = WSUtils.wsRequestStruct(
							baseurlString, "ControlPtz", pi, request,
							"ControlPtzRequest");
					if (soapObject != null) {
						Object objRes = (Object) soapObject
								.getProperty("Result");
						String strReString = objRes.toString();
						// 3. send handle msg
						msg.what = Integer.parseInt(strReString);
					} else {
						msg.what = -1;
					}
					handler4.sendMessage(msg);
				}

			}).start();

		} else {
			// unsupport YunTaiControl
			Log.v("ProgressYunTaiControl", "unSupport");
			Toast.makeText(FullScreenPlayer3.this, "此设备不支持移动控制",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void progressGetDefenseStatus() {
		btnDefenseKey.setClickable(false);
		new Thread(new Runnable() {

			@Override
			public void run() {
				MonGetAlarmSwitchRequest request = new MonGetAlarmSwitchRequest();
				request.setAccount(userIDString);
				request.setChannelNo(Integer.parseInt(ChannelNo));
				request.setDevID(DevID);
				request.setLoginSession(loginSession);

				PropertyInfo pi = new PropertyInfo();
				pi.setName("req");
				pi.setType(request.getClass());
				pi.setValue(request);

				Message msg = handler2.obtainMessage();
				SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString,
						"MonGetAlarmSwitch", pi, request,
						"MonGetAlarmSwitchRequest");
				if (soapObject != null) {
					Object objRes = (Object) soapObject.getProperty("Result");
					int AlarmSwitch = Integer.parseInt(soapObject.getProperty(
							"AlarmSwitch").toString());
					Log.v("AlarmSwitch", soapObject.getProperty("AlarmSwitch")
							.toString());
					String strReString = objRes.toString();
					// 3. send handle msg
					msg.what = Integer.parseInt(strReString);
					msg.arg1 = AlarmSwitch;
				} else {
					msg.what = -1;
				}
				handler2.sendMessage(msg);
			}

		}).start();
	}

	private void progressOpenDefense(final boolean b) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				MonSetAlarmSwitchRequest request = new MonSetAlarmSwitchRequest();
				request.setAccount(userIDString);
				request.setAlarmSwitch((b ? 1 : 0));
				request.setChannelNo(Integer.parseInt(ChannelNo));
				request.setDevID(DevID);
				request.setLoginSession(loginSession);

				PropertyInfo pi = new PropertyInfo();
				pi.setName("req");
				pi.setType(request.getClass());
				pi.setValue(request);

				Message msg = handler3.obtainMessage();
				SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString,
						"MonSetAlarmSwitch", pi, request,
						"MonSetAlarmSwitchRequest");
				if (soapObject != null) {
					Object objRes = (Object) soapObject.getProperty("Result");
					String strReString = objRes.toString();
					// 3. send handle msg
					msg.what = Integer.parseInt(strReString);
				} else {
					msg.what = -1;
				}
				handler3.sendMessage(msg);
			}

		}).start();
	}

	private void ShowYunTaiAnimation(int what) {
		// 声明一个AlphaAnimation对象，从完全透明到不透明
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		// 设置动画持续时间为1秒钟
		alphaAnimation.setDuration(1000);
		// 设置重复次数
		alphaAnimation.setRepeatCount(1);
		alphaAnimation.setRepeatMode(Animation.REVERSE);

		switch (what) {
		case MOVE_LEFT:
			imgLeft.setVisibility(View.VISIBLE);
			alphaAnimation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation animation) {
					imgLeft.setVisibility(ImageView.INVISIBLE);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}

				@Override
				public void onAnimationStart(Animation animation) {
					imgLeft.setVisibility(ImageView.VISIBLE);
				}

			});
			imgLeft.startAnimation(alphaAnimation);
			break;
		case MOVE_RIGHT:
			imgRight.setVisibility(View.VISIBLE);
			alphaAnimation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation animation) {
					imgRight.setVisibility(ImageView.INVISIBLE);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}

				@Override
				public void onAnimationStart(Animation animation) {
					imgRight.setVisibility(ImageView.VISIBLE);
				}

			});
			imgRight.startAnimation(alphaAnimation);
			break;
		case MOVE_UP:
			imgUp.setVisibility(View.VISIBLE);
			alphaAnimation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation animation) {
					imgUp.setVisibility(ImageView.INVISIBLE);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}

				@Override
				public void onAnimationStart(Animation animation) {
					imgUp.setVisibility(ImageView.VISIBLE);
				}

			});
			imgUp.startAnimation(alphaAnimation);
			break;
		case MOVE_DOWN:
			imgDown.setVisibility(View.VISIBLE);
			alphaAnimation.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationEnd(Animation animation) {
					imgDown.setVisibility(ImageView.INVISIBLE);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}

				@Override
				public void onAnimationStart(Animation animation) {
					imgDown.setVisibility(ImageView.VISIBLE);
				}

			});
			imgDown.startAnimation(alphaAnimation);
			break;
		default:
			break;
		}
	};

	// receive move event
	private Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			ShowYunTaiAnimation(msg.what);
			progressYunTaiControl(msg.what);
		}
	};

	// deal GetAlarmSwitch result
	private Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			System.out.printf("AlarmGet[%d,%d]", msg.what, msg.arg1);
			if (msg.what == 0) {
				// show AlarmSwitch Button, can change status
				if (msg.arg1 == 1) {
					btnDefenseKey.setChecked(true);
				} else {
					btnDefenseKey.setChecked(false);
				}
				btnDefenseKey.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						time.start();
						btnDefenseKey.setChecked(!btnDefenseKey.isChecked());
						if (btnDefenseKey.isChecked()) {
							// 发送撤防命令
							Toast.makeText(FullScreenPlayer3.this, "已发送撤防命令",
									Toast.LENGTH_SHORT).show();
							progressOpenDefense(false);
						} else {
							// 发关布防命令
							Toast.makeText(FullScreenPlayer3.this, "已发送布防命令",
									Toast.LENGTH_SHORT).show();
							progressOpenDefense(true);
						}
					}
				});
			} else {
				btnDefenseKey.setChecked(false);
				// show AlarmSwitch Button, cannot change status
				btnDefenseKey
						.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (isChecked) {
									Log.v("btnDefenseKey", "isChecked true");
									time.start();
									btnDefenseKey.setChecked(false);
									Toast.makeText(FullScreenPlayer3.this,
											"此设备不支持布防功能", Toast.LENGTH_SHORT)
											.show();
								} else {
									Log.v("btnDefenseKey", "isChecked false");
								}
							}
						});
			}

			btnDefenseKey.setClickable(true);
		}
	};

	// deal SetAlarmSwitch result
	private Handler handler3 = new Handler() {
		public void handleMessage(Message msg) {
			System.out.printf("AlarmSet[%d]", msg.what);
			switch (msg.what) {
			case -1:
				Toast.makeText(FullScreenPlayer3.this, "网络不给力，布/撤防请求发送失败！",
						Toast.LENGTH_SHORT).show();
				break;
			case 0:
				if (btnDefenseKey.isChecked()) {
					Toast.makeText(FullScreenPlayer3.this, "撤防成功",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(FullScreenPlayer3.this, "布防成功",
							Toast.LENGTH_SHORT).show();
				}

				btnDefenseKey.setChecked(!btnDefenseKey.isChecked());
				break;
			case 1:
				
				Toast.makeText(FullScreenPlayer3.this, "修改失败，用户名不存在",
						Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(FullScreenPlayer3.this, "修改失败，会话超时，请重新登录",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(FullScreenPlayer3.this, "修改失败，设备不存在",
						Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(FullScreenPlayer3.this, "修改失败，设备属于其他用户",
						Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(FullScreenPlayer3.this, "修改失败，设备不在线",
						Toast.LENGTH_SHORT).show();
				break;
			case 6:
				Toast.makeText(FullScreenPlayer3.this, "修改失败，请求发送失败",
						Toast.LENGTH_SHORT).show();
				break;
			case 7:
				Toast.makeText(FullScreenPlayer3.this, "修改失败，操作超时",
						Toast.LENGTH_SHORT).show();
				break;
			case 8:
				Toast.makeText(FullScreenPlayer3.this, "修改失败，参数无效",
						Toast.LENGTH_SHORT).show();
				break;
			case 9:
				Toast.makeText(FullScreenPlayer3.this, "修改失败，服务器处理出错",
						Toast.LENGTH_SHORT).show();
				break;
			case 11:
				Toast.makeText(FullScreenPlayer3.this, "修改失败，用户已注销",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				Toast.makeText(FullScreenPlayer3.this, "修改失败，未定义错误",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	// deal YunTai result
	private Handler handler4 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case -1:
				Toast.makeText(FullScreenPlayer3.this, "云台命令发送失败，网络不给力请稍后再试",
						Toast.LENGTH_SHORT).show();
				break;
			case 0:
				Toast.makeText(FullScreenPlayer3.this, "云台命令响应成功",
						Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(FullScreenPlayer3.this, "操作失败，用户名不存在",
						Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(FullScreenPlayer3.this, "操作失败，会话超时，请重新登录",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(FullScreenPlayer3.this, "操作失败，设备不存在",
						Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(FullScreenPlayer3.this, "操作失败，设备属于其他用户",
						Toast.LENGTH_SHORT).show();
				break;
			case 5:
				Toast.makeText(FullScreenPlayer3.this, "操作失败，设备不在线",
						Toast.LENGTH_SHORT).show();
				break;
			case 6:
				Toast.makeText(FullScreenPlayer3.this, "操作失败，请求发送失败",
						Toast.LENGTH_SHORT).show();
				break;
			case 7:
				Toast.makeText(FullScreenPlayer3.this, "操作超时",
						Toast.LENGTH_SHORT).show();
				break;
			case 8:
				Toast.makeText(FullScreenPlayer3.this, "操作失败，设备不支持移动命令",
						Toast.LENGTH_SHORT).show();
				break;
			case 9:
				Toast.makeText(FullScreenPlayer3.this, "操作失败，服务器处理出错",
						Toast.LENGTH_SHORT).show();
				break;
			case 11:
				Toast.makeText(FullScreenPlayer3.this, "操作失败，用户已注销",
						Toast.LENGTH_SHORT).show();
				break;
			default:
				Toast.makeText(FullScreenPlayer3.this, "操作失败，未定义错误",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	private Handler handler5 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				progressBar.setVisibility(ProgressBar.GONE);
				break;
			case 2:
				
					Bundle b = msg.getData();
					str22 = b.getString("stateRemark");
					tvstateRemark.setText(str22);
					System.out.println("==========================>" + str22);
				
				break;
			default:
				break;
			}
		}
	};

	/* 定义一个倒计时的内部类 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {
			// 计时完毕时触发

			btnPlayStop.setEnabled(true);
			btnCapture.setEnabled(true);
			btnVideotape.setEnabled(true);
			btnVoiceKey.setEnabled(true);
			btnDefenseKey.setEnabled(true);

		}

		@Override
		public void onTick(long millisUntilFinished) {

			btnPlayStop.setEnabled(false);
			btnCapture.setEnabled(false);
			btnVideotape.setEnabled(false);
			btnVoiceKey.setEnabled(false);
			btnDefenseKey.setEnabled(false);
		}
	}
}
