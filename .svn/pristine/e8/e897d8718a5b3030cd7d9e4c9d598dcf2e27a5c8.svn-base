package com.montnets.android.zmon;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.google.zxing.pdf417.encoder.Compaction;
import com.montnets.android.zmon.GroupDeviceListActitviy1.MyDeviceManageAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cn.mw.p2p.Request.MonAddShareDevRequest;
import cn.mw.p2p.Request.MonCancelShareDevRequest;
import cn.mw.p2p.Request.MonGetDevShareListRequest;
import cn.mw.p2p.Request.MonRefreshDevList2Request;
import cn.mw.p2p.Request.PlayURLRequest;
import cn.mw.p2p.adpter.DeviceAdapter;
import cn.mw.p2p.bean.MonShareInfo;
import cn.mw.p2p.bean.VedioPointBean;
import cn.mw.p2p.handler.MethodListCount;
import cn.mw.p2p.handler.Threadhandler;
import cn.mw.p2p.unitily.ExitApplication;
import cn.mw.p2p.unitily.MsgEnum;
import cn.mw.p2p.unitily.P2pBaseUrl;
import cn.mw.p2p.unitily.WSUtils;

public class GroupDeviceListActitviy1 extends Activity {
	
	private ProgressDialog dialog;
	private ProgressDialog dialog2;
	private ProgressDialog dialog3; // search share user list
	private MyDeviceManageAdapter mydeviceAdapter;
	private DeviceAdapter deviceAdapter;
	private Cursor cursor;
	private ImageButton imgbtnBack, imgbtnRef;
	private ListView lvMyDevList;
	private ArrayList<VedioPointBean> vedioPointsList;
	private VedioPointBean points = null;
	private String userIDString;// 用户名
	private Threadhandler thd;
	private String baseurlString;//基本URL
	private String loginSession;
	private SharedPreferences sp;//配置
	private String JumpType;//跳转类型
	private TextView myDeviceTitle;
	private DeviceAdapter devDao;
	private String id;
	//权限设置
	private String[] PrivilegeItem  = new String[]{"播放视频","云台控制","录像查询"};
	// Share List
	private ArrayList<MonShareInfo> mShareArray;
	//private String delname;
	private EditText edtUserID;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.groupdevlist_layout); 
        ExitApplication.getInstance().addActivity(this);
        sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
        baseurlString = P2pBaseUrl.BaseUrl(sp);
		String[] strUserList = sp.getString("userInfo", "").split(",");
		userIDString = strUserList[0];
		loginSession = strUserList[2];
		JumpType = getIntent().getStringExtra("JumpType");//获取跳转类型
		devDao = new DeviceAdapter(GroupDeviceListActitviy1.this);
        initUI();
    }
	
	/**
	 * 初始化UI
	 */
	private void initUI() {
		myDeviceTitle = (TextView)findViewById(R.id.txtmyDeviceTitle);
		if(JumpType.equals("DEVRECORD"))//设备录像
		{
			myDeviceTitle.setText("设备录像查询");
		}else {
			myDeviceTitle.setText("视频播放");
		}
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new backOnClickListener());

		imgbtnRef = (ImageButton) findViewById(R.id.btnRef);
		imgbtnRef.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				AddDevRequest adr = new AddDevRequest();
//				adr.setAccount(userIDString);
//				adr.setLoginSession(loginSession);
//				thd = new Threadhandler(handler, adr, baseurlString, devDao);
//				thd.start();
				
				DoRefresh();
			}
		});
		
		lvMyDevList = (ListView)findViewById(R.id.lvmyDevList);
		vedioPointsList = getDataForSqlite();
		mydeviceAdapter = new MyDeviceManageAdapter();
		this.lvMyDevList.setAdapter(mydeviceAdapter);
		lvMyDevList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				points = (VedioPointBean) vedioPointsList.get(arg2);
                System.out.println("=========");
				if(!points.isOnline())
				{
					Toast.makeText(GroupDeviceListActitviy1.this, "设备不在线，无法执行此操作！", Toast.LENGTH_SHORT).show();
					return;
				}
				if(JumpType.equals("DEVRECORD"))//设备录像
				{
					Intent intent = new Intent(GroupDeviceListActitviy1.this,DeviceRecordSearch.class);
					intent.putExtra("DevID", points.getDevID());
					intent.putExtra("ChannelNo", points.getChannelNo());
					startActivity(intent);
					return;
				}
				
				// start video player
				new AlertDialog.Builder(GroupDeviceListActitviy1.this)
				.setTitle("请选择视频类型：")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setSingleChoiceItems(new String[]{" 标清", " 高清"}, -1, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog2, int which) {
						dialog = ProgressDialog.show(GroupDeviceListActitviy1.this, "", "正在打开视频，请等待...", true);
						dialog.setCancelable(true);
						//开启线程
						PlayURLRequest prt = new PlayURLRequest();
						prt.setAccount(userIDString);
						prt.setLoginSession(loginSession);
						prt.setDevID(points.getDevID());
						prt.setChannelNo(Integer.parseInt(points.getChannelNo()));
						
						
						switch(which){
							case 0:
								prt.setStreamType(2);
								break;
							case 1:
								prt.setStreamType(1);
								break;
							default:
								break;
						}
						
						thd = new Threadhandler(handler, prt, baseurlString, "GetPlayUrl");
						thd.start();
						dialog2.dismiss();
					}
				})
				.show();
			}
		});
		deviceAdapter.closeDB(cursor);
		deviceAdapter.closeDB();
	}
	
	
	protected void DoRefresh() {
		dialog = ProgressDialog.show(GroupDeviceListActitviy1.this, "", "正在刷新设备列表，请等待...", true);
		dialog.setCancelable(true);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//Refresh Task with share info
				// 1. get request info
					MonRefreshDevList2Request request = new MonRefreshDevList2Request();
					request.setAccount(userIDString);
					request.setLoginSession(loginSession);
					
					PropertyInfo pi = new PropertyInfo();
					pi.setName("req");
					pi.setValue(request);
					pi.setType(request.getClass());
				// 2. send request info
					Message msg = handler4.obtainMessage();
					SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "MonRefreshDevList2", pi, request, "MonRefreshDevList2Request");
					if (soapObject != null) {
						Object objRes = (Object) soapObject.getProperty("Result");
						String strRes = objRes.toString();
						
						// 3. send handle msg
						msg.what = Integer.parseInt(strRes);
						
						// 4. save info
						if (msg.what == 0) {
							MethodListCount.getDataForSqlite(soapObject, devDao);
						}
					}else{
						msg.what = -1; // can not connect to server
					}
					handler4.sendMessage(msg);
			}
		}).start();
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
			GroupDeviceListActitviy1.this.finish();
		}
	}
	
	/**
	 * 获取设备列表
	 * @return
	 */
	private ArrayList<VedioPointBean> getDataForSqlite()
	{
		deviceAdapter = new DeviceAdapter(GroupDeviceListActitviy1.this);
		dialog = ProgressDialog.show(GroupDeviceListActitviy1.this, "", "获取数据中，请等待...", true);
		dialog.setCancelable(true);
		vedioPointsList = MethodListCount.getDataArrayListForSqlite(cursor, deviceAdapter, GroupDeviceListActitviy1.this);
		dialog.dismiss();
		if(vedioPointsList != null){
		Collections.sort(vedioPointsList,  new Comparator<VedioPointBean>() {
                          
			private int compare(Boolean lhs, Boolean rhs) {
				// TODO Auto-generated method stub
				return (lhs.equals(rhs)? 0 : (lhs.booleanValue()==true?1:-1));   
			}
			
			@Override
			public int compare(VedioPointBean lhs, VedioPointBean rhs) {
				// TODO Auto-generated method stub
				Boolean e1 = lhs.isOnline();
				Boolean e2 = rhs.isOnline();
				
				return ( (compare(e2, e1) == 0 ?  0 : compare(e2, e1)) );   
			}
		});
		}
		return vedioPointsList;
	}

	// refresh devList handler
	private final Handler handler4 = new Handler(){
		public void handleMessage(Message msg){
			dialog.dismiss();
			int msgId = msg.what;
			switch (msgId) {
				case -1:
				{
					Toast.makeText(GroupDeviceListActitviy1.this, "网络不给力，刷新请求发送失败！", Toast.LENGTH_SHORT).show();
				}
					break;
				case 0:
				{
					vedioPointsList = getDataForSqlite();
					mydeviceAdapter = new MyDeviceManageAdapter();
					lvMyDevList.setAdapter(mydeviceAdapter);
					Toast.makeText(GroupDeviceListActitviy1.this, "刷新列表成功", Toast.LENGTH_SHORT).show();
				}
				break;
				case 1:
				{
					Toast.makeText(GroupDeviceListActitviy1.this, "刷新失败\n帐户不存在", Toast.LENGTH_SHORT).show();
				}
				break;
				case 2:
				{
					Toast.makeText(GroupDeviceListActitviy1.this, "刷新失败\n会话超时，请重新登录", Toast.LENGTH_SHORT).show();
				}
				break;
				case 3:
				{
					Toast.makeText(GroupDeviceListActitviy1.this, "刷新失败\n设备不存在", Toast.LENGTH_SHORT).show();
				}
				break;
				case 11:
				{
					Toast.makeText(GroupDeviceListActitviy1.this, "刷新失败\n用户已注销", Toast.LENGTH_SHORT).show();
				}
				break;
				default:
				{
					Toast.makeText(GroupDeviceListActitviy1.this, "刷新失败\n未定义错误", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	};
	
	// del share Message handler
	private final Handler handler3 = new Handler(){
		public void handleMessage(Message msg){
			dialog2.dismiss();
			int msgId = msg.what;
			switch (msgId) {
			case -1:
			{
				Toast.makeText(GroupDeviceListActitviy1.this, "网络不给力，删除分享请求发送失败！", Toast.LENGTH_SHORT).show();
			}
				break;
			case 0:
			{
				DoRefresh();
				Toast.makeText(GroupDeviceListActitviy1.this, "删除分享成功", Toast.LENGTH_SHORT).show();
			}
			break;
			case 1:
			{
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n帐户不存在", Toast.LENGTH_SHORT).show();
			}
			break;
			case 2:
			{
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n会话超时，请重新登录", Toast.LENGTH_SHORT).show();
			}
			break;
			case 3:
			{
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n设备不存在", Toast.LENGTH_SHORT).show();
			}
			break;
			case 4:
			{
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n此设备不属于你", Toast.LENGTH_SHORT).show();
			}
			break;
			case 9:
			{
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败", Toast.LENGTH_SHORT).show();
			}
			break;
			case 11:
			{
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n用户已注销", Toast.LENGTH_SHORT).show();
			}
			break;
			case 14:
			{
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n分享用户不存在", Toast.LENGTH_SHORT).show();
			}
			break;
			case 18:
			{
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n未分享设备给该用户", Toast.LENGTH_SHORT).show();
			}
			break;
			default:
			{
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n未定义错误", Toast.LENGTH_SHORT).show();
			}
				break;
			}
		}
	};
	
	// get share user list handler
	private final Handler handler5 = new Handler(){
		public void handleMessage(Message msg){
			dialog3.cancel();
			int msgId = msg.what;
			switch(msgId){
			case -1:
				{
					Toast.makeText(GroupDeviceListActitviy1.this, "网络不给力，删除分享请求发送失败！", Toast.LENGTH_SHORT).show();
				}
				break;
			case 0:
				{
					// TODO: success
					
					String[] shareUsers = new String[mShareArray.size()];
					for(int i=0; i<mShareArray.size(); i++){
						shareUsers[i] = mShareArray.get(i).getSomeone();
						Log.v("ShareUser", shareUsers[i]);
					}
					if(mShareArray.size() > 0){
					new AlertDialog.Builder(GroupDeviceListActitviy1.this)
					.setTitle("请选择要删除的分享用户")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setSingleChoiceItems(shareUsers, -1, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog,  final int which) {
							String[] shareUsers = new String[mShareArray.size()];
							
						final String Someone = mShareArray.get(which).getSomeone().toString();
							//edtUserID.setText(mShareArray.get(which).getSomeone().toString());
							Log.i("=============>WHICH", Someone+"");
							dialog.dismiss();
							new AlertDialog.Builder(GroupDeviceListActitviy1.this)//Context
							.setTitle("确定删除")
							.setIcon(android.R.drawable.ic_dialog_alert)//图标
							.setMessage("您确定要删除分享用户:" + "\n" + Someone)
							.setNegativeButton("取消",null)//按钮1
							
							.setPositiveButton("确定", new DialogInterface.OnClickListener() {//按钮2
								public void onClick(DialogInterface arg0, int arg1) {
									
									dialog2 = ProgressDialog.show(GroupDeviceListActitviy1.this, "", "正在处理中...", true);
									DelSharedDev(id, Someone);
								}
							}).show();//显示
						}
					}).show();
					}else{
						Toast.makeText(GroupDeviceListActitviy1.this, "警告，没有要删除的分享用户", Toast.LENGTH_SHORT).show();
					}
					
				}
				break;
			case 1:
				{
					// TODO:　帐户不存在
					Toast.makeText(GroupDeviceListActitviy1.this, "查询失败，用户账号不存在", Toast.LENGTH_SHORT).show();
				}
				break;
			case 2:
				{
					// TODO: session超时
					Toast.makeText(GroupDeviceListActitviy1.this, "查询失败，会话超时，请重新登录", Toast.LENGTH_SHORT).show();
				}
				break;
			case 3:
				{
					// TODO: 设备不存在
					Toast.makeText(GroupDeviceListActitviy1.this, "查询失败，设备不存在", Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				{
					// TODO: 未定义错误
					Toast.makeText(GroupDeviceListActitviy1.this, "查询失败，未定义的错误", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	};
	
	// add share Message handler
	private final Handler handler2 = new Handler(){
		public void handleMessage(Message msg){
			dialog2.dismiss();
			int msgId = msg.what;
			switch (msgId) {
			case -1:
			{
				Toast.makeText(GroupDeviceListActitviy1.this, "网络不给力，分享请求发送失败！", Toast.LENGTH_SHORT).show();
			}
			break;
			case 0:
			{
				// success
				DoRefresh();
				Toast.makeText(GroupDeviceListActitviy1.this, "添加分享成功", Toast.LENGTH_SHORT).show();
			}
			break;
			case 1:
			{
				// user unexist
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n用户名不存在", Toast.LENGTH_SHORT).show();
			}
			break;
			case 2:
			{
				// session out of date
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n会话超时，请重新登录", Toast.LENGTH_SHORT).show();
			}
			break;
			case 3:
			{
				// device not exist
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n设备不存在", Toast.LENGTH_SHORT).show();
			}
			break;
			case 4:
			{
				// device not belong to this user
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n此设备属于其他账户", Toast.LENGTH_SHORT).show();
			}
			break;
			case 5:
			{
				// device not on line
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n设备不在线", Toast.LENGTH_SHORT).show();
			}
			break;
			case 14:
			{
				// the user to Share with is not exist
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n指定分享人不存在", Toast.LENGTH_SHORT).show();
			}
			break;
			case 15:
			{
				// up the limit of max share
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n超出最大分享数", Toast.LENGTH_SHORT).show();
			}
			break;
			case 16:
			{
				// cannot share to yourself
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n设备不能分享给自己", Toast.LENGTH_SHORT).show();
			}
			break;
			case 17:
			{
				// already share to the one
				Toast.makeText(GroupDeviceListActitviy1.this, "分享重复\n设备已分享给该用户", Toast.LENGTH_SHORT).show();
			}
			break;
			default:
			{
				// undefined error code
				Toast.makeText(GroupDeviceListActitviy1.this, "操作失败\n未定义的错误", Toast.LENGTH_SHORT).show();
			}
				break;
			}
		}
	};
	
	private final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			dialog.dismiss();
    		int msgID = msg.what;
    		switch(msgID)
    		{
				case MsgEnum.PLAYURL_SUCCESS:	//获取播放地址成功	
					String playURL = msg.getData().getString("PLAYURL");
					System.out.println("视频播放地址：" + playURL);
					if (playURL != null && points != null) {
						Toast.makeText(GroupDeviceListActitviy1.this, "获取播放地址成功。转播放中...！", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(GroupDeviceListActitviy1.this,/*FFmpeg_Player.class*/FullScreenPlayer3.class);
						intent.putExtra("PlayURL", playURL);
						intent.putExtra("DevID", points.getDevID());
						intent.putExtra("ChannelNo", points.getChannelNo());
						intent.putExtra("PtzFlag", String.valueOf(points.getPtzFlag()));
						startActivity(intent);
					}
					else {
						Toast.makeText(GroupDeviceListActitviy1.this, "获取播放地址失败！", Toast.LENGTH_SHORT).show();
					}
					break;
				case MsgEnum.USERNAME_NULL:
					Toast.makeText(GroupDeviceListActitviy1.this, "用户不存在！", Toast.LENGTH_SHORT).show();
					break;
				case MsgEnum.SESSION_TIMEOUT:
					Toast.makeText(GroupDeviceListActitviy1.this, "SESSION超时！", Toast.LENGTH_SHORT).show();
					break;
				case MsgEnum.DEVICE_EXISTS_NULL:
					Toast.makeText(GroupDeviceListActitviy1.this, "设备不存在！", Toast.LENGTH_SHORT).show();
					break;
				case MsgEnum.PLAYURL_FAILE:		//获取播放地址失败
					Toast.makeText(GroupDeviceListActitviy1.this, "获取播放地址失败,请重试！", Toast.LENGTH_SHORT).show();
					break;
				case MsgEnum.SERVER_ERROR:
					Toast.makeText(GroupDeviceListActitviy1.this, "网络不给力，播放地址请求发送失败！", Toast.LENGTH_SHORT).show();
					break;
				case MsgEnum.SUCCESS://刷新设备列表成功
					vedioPointsList = getDataForSqlite();
					mydeviceAdapter = new MyDeviceManageAdapter();
					lvMyDevList.setAdapter(mydeviceAdapter);
					Toast.makeText(GroupDeviceListActitviy1.this, "设备列表刷新成功！", Toast.LENGTH_SHORT).show();
					break;
				case MsgEnum.AGENTREQUEST_FAINLD:
					Toast.makeText(GroupDeviceListActitviy1.this, "设备反向代理请求失败！", Toast.LENGTH_SHORT).show();
					break;
				case MsgEnum.AGENTRESPONSE_FAINLD:
					Toast.makeText(GroupDeviceListActitviy1.this, "设备反向代理请求响应超时！", Toast.LENGTH_SHORT).show();
					break;
				case MsgEnum.NOT_PROXY:
					Toast.makeText(GroupDeviceListActitviy1.this, "设备不支持反向代理！", Toast.LENGTH_SHORT).show();
					break;
				case MsgEnum.ADRESS_NOTAVAILABLE:
					Toast.makeText(GroupDeviceListActitviy1.this, "设备直连地址不可用！", Toast.LENGTH_SHORT).show();
					break;
				case MsgEnum.NOT_STREAMMEDIA:
					Toast.makeText(GroupDeviceListActitviy1.this, "无可用流媒体！", Toast.LENGTH_SHORT).show();
					break;
				case MsgEnum.MAX_CONNECTED:
					Toast.makeText(GroupDeviceListActitviy1.this, "已达设备最大直连数！", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
    		}
		}
	};
	
	public class MyDeviceManageAdapter extends BaseAdapter {


		public MyDeviceManageAdapter() {
			
		}

		public int getCount() {
			Log.v("AdapterSize", String.valueOf(vedioPointsList.size()));
			return vedioPointsList.size();
		}

		public Object getItem(int paramInt) {
			return vedioPointsList.get(paramInt);
		}

		public long getItemId(int paramInt) {
			return 0L;
		}

		public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
			Log.v("getViewId", String.valueOf(paramInt));
			Log.v("getViewDevID", vedioPointsList.get(paramInt).getDevID());
			viewHoder localviewHoder;
			
			if (paramView != null) {
				localviewHoder = (viewHoder) paramView.getTag();
			}else{
				localviewHoder = new viewHoder();
			}
			paramView = LayoutInflater.from(GroupDeviceListActitviy1.this).inflate(R.layout.grouprecorditem, null, false);
			localviewHoder.ivonlie = (ImageView) paramView.findViewById(R.id.ivOnline);
			localviewHoder.textview = ((TextView) paramView.findViewById(R.id.txtName));
			localviewHoder.ivShare = (ImageView) paramView.findViewById(R.id.ivShare);
			paramView.setTag(localviewHoder);
			VedioPointBean points = (VedioPointBean) getItem(paramInt);
			final VedioPointBean tempPointBean = points;
			// Group id: 0-my device; 1-shared device
			// Share Flag: 0-unshared; 1-shared
			if (points.getGroupid() == 0) {
				// my device
				if(points.isOnline())
				{
					localviewHoder.ivonlie.setBackgroundResource(R.drawable.sp);
				}
				else {
					localviewHoder.ivonlie.setBackgroundResource(R.drawable.sp2);
				}
				
				// set share button
				localviewHoder.ivShare.setImageResource(R.drawable.ishare);
				String loginType = sp.getString("loginType", "0");
				if(loginType.equals("1")){
					localviewHoder.ivShare.setEnabled(false);
					localviewHoder.ivShare.setVisibility(ImageView.INVISIBLE);
				}
			}else{
				// share device
				if (points.isOnline()) {
					localviewHoder.ivonlie.setBackgroundResource(R.drawable.share01);
				}else{
					localviewHoder.ivonlie.setBackgroundResource(R.drawable.share02);
				}
				
				// set share button
				localviewHoder.ivShare.setImageResource(R.drawable.isharedel);
			}
			localviewHoder.textview.setText(points.getName());
			Log.v("getViewDevName", points.getName());
			//点击事件
			localviewHoder.ivShare.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Log.v("SetListener", tempPointBean.isOnline() ? "online" : "offline");
//					if(!tempPointBean.isOnline())
//					{
//						Toast.makeText(GroupDeviceListActitviy.this, "设备不在线，无法分享！", 0).show();
//						return;
//					}
					if (tempPointBean.getGroupid() == 0) {
						// add or del my device's share
						//SetShareInfo(tempPointBean);
						new AlertDialog.Builder(GroupDeviceListActitviy1.this)
						.setTitle("请选择分享类型：")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setSingleChoiceItems(new String[]{" 添加分享", " 删除分享"}, -1, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog4, int which) {
								
								switch(which){
									case 0:
										SetShareInfo(tempPointBean);
										break;
									case 1:
										dialog3 = ProgressDialog.show(GroupDeviceListActitviy1.this,"", "获取分享用户列表中...");
												GetShareList(tempPointBean);
												id = tempPointBean.getDevID();
										break;
									default:
										break;
								}
								dialog4.dismiss();
							}
						})
						.show();
						
					}else{
						// del share device from others
						AlertDialog ad = new AlertDialog.Builder(GroupDeviceListActitviy1.this)
							.setTitle("真的要删除?")
							.setMessage("您真的要删除设备:" + "\n" + tempPointBean.getName() + "  !")
							.setNegativeButton("取消", null)
							.setPositiveButton("确定", new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog2 = ProgressDialog.show(GroupDeviceListActitviy1.this, "", "正在处理中...", true);
									new Thread(new Runnable() {
										@Override
										public void run() {
											id = tempPointBean.getDevID();
											Log.i("当前删除的设备的ID是=====>", id);
											DelSharedDev(id, userIDString);
										}
									}).start();
								}
							
						}).create();
						ad.show();
					}
					
				}
			});
			return paramView;
		}

		public final class viewHoder {
			ImageView ivonlie;
			TextView textview;
			ImageView ivShare;
		}
	}
	
	// Del shared device
	private void DelSharedDev(final String id, final String someone)
	{
				MonCancelShareDevRequest request = new MonCancelShareDevRequest();
				request.setAccount(userIDString);
				request.setDevID(id);
				request.setSomeone(someone);
				request.setLoginSession(loginSession);
				
				PropertyInfo pi = new PropertyInfo();
				pi.setName("req");
				pi.setValue(request);
				pi.setType(request.getClass());
			// 2. send request
				Message msg = handler3.obtainMessage();
				SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "MonCancelShareDev", pi, request, "MonCancelShareDevRequest");
				if (soapObject != null) {
					Object objRes = (Object) soapObject.getProperty("Result");
					String strReString = objRes.toString();
					// 3. send handle msg
					msg.what = Integer.parseInt(strReString);
				}else{
					msg.what = -1;
				}
				handler3.sendMessage(msg);
	};
	
	/**
	 * 设备分享
	 * @param tempPointBean 
	 */
	public void SetShareInfo(final VedioPointBean tempPointBean)
	{
		LayoutInflater factory = LayoutInflater.from(this);
		final View textEntryView = factory.inflate(R.layout.share_layout,	null);
		
		Spinner spinnerPer = (Spinner)textEntryView.findViewById(R.id.spinnerPer);
		ArrayAdapter<String> adapterSleep = new ArrayAdapter<String>(
				GroupDeviceListActitviy1.this, android.R.layout.simple_spinner_item,PrivilegeItem);
		adapterSleep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPer.setAdapter(adapterSleep);

		edtUserID = (EditText) textEntryView.findViewById(R.id.edtUserID);
		
		//final RadioButton radioDel = (RadioButton) textEntryView.findViewById(R.id.radio1);
		/*radioDel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if(radioDel.isChecked()){
					dialog3 = ProgressDialog.show(GroupDeviceListActitviy1.this,"", "获取分享用户列表中..."); 
					new Thread(new Runnable(){

						@Override
						public void run() {
							GetShareList(tempPointBean);
						}
						
					}).start();
				}  
			}
			
		});*/
		
		AlertDialog msgdialog = new AlertDialog.Builder(this)
				.setTitle("添加分享设置")
				.setView(textEntryView)
				.setPositiveButton("确定",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								final String someone = ((EditText) textEntryView.findViewById(R.id.edtUserID)).getText().toString().trim();
								if (someone == null || someone.equals("")) {
									((EditText) textEntryView.findViewById(R.id.edtUserID)).setHint("分享用户账号不能为空");
									
									// set positive button cannot dismiss auto
									try { 
										Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing"); 
										field.setAccessible(true); 
										field.set(dialog, false);
									} catch (Exception e) { 
										e.printStackTrace(); 
									}
									
									return;
								}
								
								// set positive button can dismiss auto
								try {
									Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
									field.setAccessible(true);
									field.set(dialog, true);
								} catch (Exception e) {
									e.printStackTrace();
								}

								final int privilege = ((Spinner)textEntryView.findViewById(R.id.spinnerPer)).getSelectedItemPosition();
								//final boolean isAdd = ((RadioButton) textEntryView.findViewById(R.id.radio0)).isChecked();
								Log.i("textField", someone);
								Log.i("spinner", PrivilegeItem[privilege]);
								//Log.i("radio", isAdd ? "ADD" : "DEL");
								
								dialog2 = ProgressDialog.show(GroupDeviceListActitviy1.this, "", "正在处理中...", true);
								// deal share request
								new Thread(new Runnable() {
									@Override
									public void run() {
										// which function should be invoke
										
											// add share
											// 1. get request info
												Log.v("ShareAccount", userIDString);
												Log.v("ShareSession", loginSession);
												Log.v("ShareDevID", tempPointBean.getDevID());
												Log.v("ShareSomeone", someone);
												Log.v("SharePrivilege", String.valueOf(privilege));
											
												MonAddShareDevRequest request = new MonAddShareDevRequest();
												request.setAccount(userIDString);
												request.setLoginSession(loginSession);
												request.setDevID(tempPointBean.getDevID());
												request.setSomeone(someone);
												request.setBegintime("19700101000000");
												request.setEndtime("30000101000000");
												request.setPrivilege(privilege);
												
												PropertyInfo pi = new PropertyInfo();
												pi.setName("req");
												pi.setValue(request);
												pi.setType(request.getClass());
											// 2. send request
												Message msg = handler2.obtainMessage();
												SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "MonAddShareDev", pi, request, "MonAddShareDevRequest");
												if (soapObject != null) {
													Object objRes = (Object) soapObject.getProperty("Result");
													String strRes = objRes.toString();
													// 3. send handle msg
													msg.what = Integer.parseInt(strRes);
												}else{
													msg.what = -1; // can not connect to server
												}
												handler2.sendMessage(msg);
										/*}
										else{
											// Del share msg
											// 1. get request info
												Log.v("DelShareAccount", userIDString);
												Log.v("DelShareSession", loginSession);
												Log.v("DelShareDevID", tempPointBean.getDevID());
												Log.v("DelShareSomeone", someone);
												DelSharedDev(id, someone);
										}*/
									}
								}).start();
							}
						})
				.setNegativeButton("取消",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int whichButton) {
								// set positive button can dismiss auto
								try {
									Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
									field.setAccessible(true);
									field.set(dialog, true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}).create();
		msgdialog.show();
	}
	
	private void GetShareList(final VedioPointBean tempPointBean){
		// 1. get request info
		Log.v("GetShareList_user", userIDString);
		Log.v("GetShareList_sess", loginSession);
		Log.v("GetShareList_devi", tempPointBean.getDevID());
		
		MonGetDevShareListRequest request = new MonGetDevShareListRequest();
		request.setAccount(userIDString);
		request.setLoginSession(loginSession);
		request.setDevID(tempPointBean.getDevID());
		
		PropertyInfo pi = new PropertyInfo();
		pi.setName("req");
		pi.setValue(request);
		pi.setType(request.getClass());
		
		// 2. send request
		Message msg = handler5.obtainMessage();
		SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "MonGetDevShareList", pi, request, "MonGetDevShareListRequest");
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			Log.v("ShareListResult", strRes);
			msg.what = Integer.parseInt(strRes);
			
			if(msg.what == 0){
				mShareArray = new ArrayList<MonShareInfo>();
				for (int i = 0; i < soapObject.getPropertyCount(); i++) {
					Object soapChilds = (Object)soapObject.getProperty(i);
					if (soapChilds.toString().indexOf("Someone") == -1)
						continue;
					else{
						MonShareInfo item = new MonShareInfo();
						item.setSomeone(((SoapObject) soapChilds).getProperty("Someone").toString());
						item.setBeginTime(((SoapObject) soapChilds).getProperty("BeginTime").toString());
						item.setEndTime(((SoapObject) soapChilds).getProperty("EndTime").toString());
						item.setPrivilege(((SoapObject) soapChilds).getProperty("Privilege").toString());
						mShareArray.add(item);
					}
				}
			}
		}else{
			msg.what = -1; // can not connect to server
		}
		handler5.sendMessage(msg);
	}
	
}
