package com.montnets.android.zmon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

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
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.mw.p2p.Request.DelDevRequest;
import cn.mw.p2p.Request.MonAddDevRequest;
import cn.mw.p2p.Request.MonRefreshDevList2Request;
import cn.mw.p2p.adpter.DeviceAdapter;
import cn.mw.p2p.bean.VedioPointBean;
import cn.mw.p2p.handler.MethodListCount;
import cn.mw.p2p.handler.Threadhandler;
import cn.mw.p2p.unitily.ExitApplication;
import cn.mw.p2p.unitily.P2pBaseUrl;
import cn.mw.p2p.unitily.WSUtils;

public class DevListActivity extends Activity {
	
	private String userIDString;//用户名
	private String loginSession = null;
	private ImageButton imgbtnBack;
	private ImageButton imgbtnAdd;
	private ImageButton imgbtnRef;
	private ListView lvDevList;
	private ArrayList<VedioPointBean> videoPoints = null;
	private DeviceAdapter deviceAdapter;
	private Cursor cursor;
	private ProgressDialog dialog;
	private Threadhandler thd;
	private String baseurlString;
	private String DevID;//设备ID
	private DeviceManageAdapter deviceManageAdapter;
	private String loginType;
	private ProgressDialog pd;
	private SharedPreferences sp;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.devlist_layout);
        ExitApplication.getInstance().addActivity(this);
		SharedPreferences sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
		userIDString = sp.getString("userInfo", "").split(",")[0];
		loginSession = sp.getString("userInfo", "").split(",")[2];
		loginType = sp.getString("loginType", "0");
		System.out.println("DevListActivity-用户ID：" + userIDString);
		baseurlString = P2pBaseUrl.BaseUrl(sp);
		initUI();
    }
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		DoRefresh();
	}


	/**
	 * 初始化UI
	 */
	private void initUI() {
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new backOnClickListener());
		imgbtnAdd = (ImageButton) findViewById(R.id.btn_add);
		if(loginType.equals("1"))
		{
			//设备登录隐藏设备添加功能
			imgbtnAdd.setVisibility(View.GONE);
		}
		imgbtnAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				LayoutInflater factory = LayoutInflater.from(DevListActivity.this);
				View textEntryView = factory.inflate(R.layout.bindevice,null);
				final EditText hostid = (EditText) textEntryView.findViewById(R.id.edtHostID);
				final EditText hostpwd = (EditText)textEntryView.findViewById(R.id.edtHostPwd);

				AlertDialog msgdialog = new AlertDialog.Builder(DevListActivity.this)
						.setIcon(android.R.drawable.ic_input_get)
						.setTitle("添加设备")
						.setView(textEntryView)
						.setPositiveButton("确定",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog1,int whichButton) {
										DevID = hostid.getText().toString().trim();
										String strPwd = hostpwd.getText().toString().trim();
										if(DevID.equals("") || strPwd.equals(""))
										{
											Toast.makeText(DevListActivity.this, "设备ID或验证码不能为空！", 0).show();
											return;
										}
										
										MonAddDevRequest request = new MonAddDevRequest();
										request.setAccount(userIDString);
										request.setLoginSession(loginSession);
										request.setDevID(DevID);
										request.setDevKey(strPwd);
										request.setChannelName(DevID);
										dialog = ProgressDialog.show(DevListActivity.this, "", "正在添加设备，请等待...", true);
										dialog.setCancelable(true);
										PropertyInfo pi = new PropertyInfo();
										pi.setName("req");
										pi.setValue(request);
										pi.setType(request.getClass());
										Message msg = mHandler.obtainMessage();
										SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "MonAddDev", pi, request, "MonAddDevRequest");
										if (soapObject != null) {
											Object objRes = (Object) soapObject.getProperty("Result");
											String strReString = objRes.toString();
											// 3. send handle msg
											msg.what = Integer.parseInt(strReString);
											if(msg.what == 0){
												String strParamResult = null;
												Object soapChilds = (Object)soapObject.getProperty(1);
												strParamResult = ((SoapObject) soapChilds).getProperty("DevID").toString();
												strParamResult += "," + ((SoapObject) soapChilds).getProperty("ChannelNo").toString();
												strParamResult += "," + ((SoapObject) soapChilds).getProperty("Name").toString();
												strParamResult += "," + ((SoapObject) soapChilds).getProperty("OnLine").toString();
												strParamResult += "," + ((SoapObject) soapChilds).getProperty("PtzFlag").toString();
												Bundle bundle = new Bundle();
												bundle.putString("ParamResult", strParamResult);
												System.out
														.println("ParamResult是------------->" + strParamResult);
												msg.setData(bundle);
											}
										}else{
											msg.what = -1;
										}
										mHandler.sendMessage(msg);
										
									}
								})
						.setNegativeButton("取消",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,int whichButton) {
									}
								}).create();
				msgdialog.show();
			}
		});
		videoPoints = getDataSqlite();
		lvDevList = (ListView)findViewById(R.id.lvDevList);
		deviceManageAdapter = new DeviceManageAdapter();
		this.lvDevList.setAdapter(deviceManageAdapter);
		deviceAdapter.closeDB(cursor);
		deviceAdapter.closeDB();
		
		imgbtnRef = (ImageButton) findViewById(R.id.btnRef);
		imgbtnRef.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DoRefresh();
			}
		});
	}
	
	/**
	 * 获取设备列表
	 * @return
	 */
	private ArrayList<VedioPointBean> getDataSqlite()
	{
		deviceAdapter = new DeviceAdapter(DevListActivity.this);
//		dialog = ProgressDialog.show(DevListActivity.this, "", "获取数据中，请等待...", true);
		videoPoints = MethodListCount.getDataArrayListForSqlite2(cursor, deviceAdapter, DevListActivity.this);
//		dialog.dismiss();
		if(videoPoints != null){
		Collections.sort(videoPoints,  new Comparator<VedioPointBean>() {
            
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
		return videoPoints;

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
			DevListActivity.this.finish();
		}
	}
	
	
		
	
    //=======================TODO 线程操作==============================
    private final Handler mHandler = new Handler()
    {
    	public void handleMessage(Message msg)
    	{
			dialog.dismiss();
			int msgID = msg.what;
			switch (msgID) {
			case 0:
				System.out.println("添加设备成功");
				String resString = msg.getData().getString("ParamResult");
				String[] res = null;
				if (!resString.equals("") && resString != null) {
					res = resString.split(",");
				}
				// 插入本地数据库
				VedioPointBean vpb = new VedioPointBean();
				vpb.setDevID(res[0]);
				vpb.setChannelNo(res[1]);
				vpb.setName(res[2]);
				if (res[3].equals("1")) {
					vpb.setOnline(true);
				}
				else {
					vpb.setOnline(false);
				}
				vpb.setPtzFlag(Integer.parseInt(res[4]));
				vpb.setURL("");
				deviceAdapter.openDB();
				deviceAdapter.Insert(vpb);
				deviceAdapter.closeDB();
				//刷新列表
				initUI();
				Toast.makeText(DevListActivity.this, "设备添加成功！", 0).show();
				break;
			case 1:
				
				Toast.makeText(DevListActivity.this, "用户不存在！", 0).show();
				break;
			case 2:
				Toast.makeText(DevListActivity.this, "SESSION超时！", 0).show();
				break;
			case 3:
				Toast.makeText(DevListActivity.this, "设备不存在！", 0).show();
				break;
			case 4:
				Toast.makeText(DevListActivity.this, "设备属于其他用户！", 0).show();
				break;
			case 5:
				Toast.makeText(DevListActivity.this, "超过用户添加设备上限！", 0).show();
				break;
			case 6:
				Toast.makeText(DevListActivity.this, "设备已添加！无需重复添加", 0).show();
				break;
			case 7:
				Toast.makeText(DevListActivity.this, "设备验证码错误！", 0).show();
				break;
			case 11:
				Toast.makeText(DevListActivity.this, "用户不可用！", 0).show();
				break;
			default:
				break;
			}
    	}
	
    };
    
    //删除设备消息处理
    private final Handler mHandler2 = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case -1:
				Toast.makeText(DevListActivity.this, "连接服务器失败，请检查网络环境", Toast.LENGTH_SHORT).show();
				break;
			case 0:
				//删除本地数据库中设备
				deviceAdapter.Delete(DevID);
				deviceAdapter.closeDB();
				//刷新列表
				initUI();
				Toast.makeText(DevListActivity.this, "删除设备成功", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				Toast.makeText(DevListActivity.this, "删除失败，用户名不存在", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(DevListActivity.this, "删除失败，会话超时，请重新登录", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(DevListActivity.this, "设备不存在", Toast.LENGTH_SHORT).show();
				break;
			case 4:
				Toast.makeText(DevListActivity.this, "设备属于其他账户", Toast.LENGTH_SHORT).show();
				break;
			case 7:
				Toast.makeText(DevListActivity.this, "设备验证码错误", Toast.LENGTH_SHORT).show();
				break;
				default:
					Toast.makeText(DevListActivity.this, "用户不可用", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	};
    
    //删除设备
    /*protected void progressDelDev() {
		pd = ProgressDialog.show(DevListActivity.this, "", "发送命令中...", true);
		pd.setCancelable(true);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				//RebootDevRequest request = new RebootDevRequest();
				DelDevRequest request = new DelDevRequest();
				request.setAccount(userIDString);
				request.setLoginSession(loginSession);
				request.setDevID(DevID);
				request.setDevKey(pwdString);
				
				PropertyInfo pi = new PropertyInfo();
				pi.setName("req");
				pi.setValue(request);
				
				Message msg = mHandler2.obtainMessage();
				SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "DelDev", pi, request, "DelDevRequest");
				if (soapObject != null) {
					Object objRes = (Object) soapObject.getProperty("Result");
					String strReString = objRes.toString();
					// 3. send handle msg
					msg.what = Integer.parseInt(strReString);
				}else{
					msg.what = -1;
				}
				mHandler2.sendMessage(msg);
			}
			
		}).start();
	}*/

    //===================================================
    public class DeviceManageAdapter extends BaseAdapter {

    	public DeviceManageAdapter(){
    		
    	}

    	public int getCount() {
    		return videoPoints.size();
    	}

    	public Object getItem(int paramInt) {
    		return videoPoints.get(paramInt);
    	}

    	public long getItemId(int paramInt) {
    		return 0L;
    	}

    	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
    		viewHoder localviewHoder;
    		if (paramView != null) {
				localviewHoder = (viewHoder) paramView.getTag();
    		}else {localviewHoder = new viewHoder();}
    			paramView = LayoutInflater.from(DevListActivity.this).inflate(R.layout.deviceitem, null, false);
    			localviewHoder = new viewHoder();
    			localviewHoder.ivonlie = (ImageView) paramView.findViewById(R.id.ivOnline);
    			localviewHoder.textview = ((TextView) paramView.findViewById(R.id.txtName));
    			localviewHoder.ivSet = ((ImageButton) paramView.findViewById(R.id.ivSet));
    			localviewHoder.ivdel = (ImageButton)paramView.findViewById(R.id.ivDel);
    			paramView.setTag(localviewHoder);
    			final VedioPointBean localVedioPointBean = (VedioPointBean) getItem(paramInt);
    			if(localVedioPointBean.isOnline())
    			{
    				localviewHoder.ivonlie.setBackgroundResource(R.drawable.sp);
    			}
    			else {
    				localviewHoder.ivonlie.setBackgroundResource(R.drawable.sp2);
    			}
    			localviewHoder.textview.setText(localVedioPointBean.getName());
    			//设备配置
    			localviewHoder.ivSet.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					
    					if(!localVedioPointBean.isOnline())
    					{
    						Toast.makeText(DevListActivity.this, "设备不在线，无法进行参数配置", 0).show();
    						return;
    					}
//    					Toast.makeText(DevListActivity.this, "设备ID："+localVedioPointBean.getDevID(), 0).show();
    					Intent intent = new Intent(DevListActivity.this, DeviceParamSet2.class);
    					intent.putExtra("DevID", localVedioPointBean.getDevID());
    					intent.putExtra("ChannelNO", localVedioPointBean.getChannelNo());
    					intent.putExtra("ChannelName", localVedioPointBean.getName());
    					startActivity(intent);
    				}
    			});
    			//设备删除
    			
    			localviewHoder.ivdel.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					if(loginType.equals("1"))
    					{
    						Toast.makeText(DevListActivity.this, "设备登录不能进行删除操作！", 0).show();
    						return;
    					}
    					LayoutInflater factory = LayoutInflater.from(DevListActivity.this);
    					View textEntryView = factory.inflate(R.layout.bindevice,null);
    					final EditText hostid = (EditText) textEntryView.findViewById(R.id.edtHostID);
    					hostid.setVisibility(View.GONE);
    					final EditText hostpwd = (EditText)textEntryView.findViewById(R.id.edtHostPwd);

    					AlertDialog msgdialog = new AlertDialog.Builder(DevListActivity.this)
    							.setIcon(android.R.drawable.ic_input_get)
    							.setTitle("删除设备")
    							.setView(textEntryView)
    							.setPositiveButton("确定",new DialogInterface.OnClickListener() {
    									public void onClick(DialogInterface dialog1,int whichButton) {
    										String pwdString = hostpwd.getText().toString().trim();
    										if(pwdString.equals(""))
    										{
    											Toast.makeText(DevListActivity.this, "验证码不能为空!", 0).show();
    											return;
    										}
    										DevID = localVedioPointBean.getDevID();
    										DelDevRequest request = new DelDevRequest();
    										request.setAccount(userIDString);
    										request.setLoginSession(loginSession);
    										request.setDevID(DevID);
    										request.setDevKey(pwdString);
    										
    										PropertyInfo pi = new PropertyInfo();
    										pi.setName("req");
    										pi.setValue(request);
    										
    										Message msg = mHandler2.obtainMessage();
    										SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "DelDev", pi, request, "DelDevRequest");
    										if (soapObject != null) {
    											Object objRes = (Object) soapObject.getProperty("Result");
    											String strReString = objRes.toString();
    											// 3. send handle msg
    											msg.what = Integer.parseInt(strReString);
    										}else{
    											msg.what = -1;
    										}
    										mHandler2.sendMessage(msg);
    										
    									}
    								})
    							.setNegativeButton("取消",new DialogInterface.OnClickListener() {
    									public void onClick(DialogInterface dialog,int whichButton) {
    									}
    								}).create();
    					msgdialog.show();
    					
    				}
    			});
    			
    		
    		return paramView;
    	}

    	public final class viewHoder {
    		ImageButton ivSet;
    		ImageButton ivdel;
    		ImageView ivonlie;
    		TextView textview;
    	}    	
    }
    
    protected void DoRefresh() {
		dialog = ProgressDialog.show(DevListActivity.this, "", "正在刷新设备列表，请等待...", true);
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
							MethodListCount.getDataForSqlite(soapObject, deviceAdapter);
						}
					}else{
						msg.what = -1; // can not connect to server
					}
					handler4.sendMessage(msg);
			}
		}).start();
	}
    
    private final Handler handler4 = new Handler(){
		public void handleMessage(Message msg){
			dialog.dismiss();
			int msgId = msg.what;
			switch (msgId) {
				case -1:
				{
					Toast.makeText(DevListActivity.this, "连接服务器失败，请检查网络环境", Toast.LENGTH_SHORT).show();
				}
					break;
				case 0:
				{
					//vedioPointsList = getDataForSqlite();
					//mydeviceAdapter = new MyDeviceManageAdapter();
					//lvMyDevList.setAdapter(mydeviceAdapter);
					videoPoints = getDataSqlite();
					deviceManageAdapter = new DeviceManageAdapter();
					lvDevList.setAdapter(deviceManageAdapter);
					Toast.makeText(DevListActivity.this, "刷新列表成功", Toast.LENGTH_SHORT).show();
				}
				break;
				case 1:
				{
					Toast.makeText(DevListActivity.this, "刷新失败\n帐户不存在", Toast.LENGTH_SHORT).show();
				}
				break;
				case 2:
				{
					Toast.makeText(DevListActivity.this, "刷新失败\n会话超时，请重新登录", Toast.LENGTH_SHORT).show();
				}
				break;
				case 3:
				{
					Toast.makeText(DevListActivity.this, "刷新失败\n设备不存在", Toast.LENGTH_SHORT).show();
				}
				break;
				case 11:
				{
					Toast.makeText(DevListActivity.this, "刷新失败\n用户已注销", Toast.LENGTH_SHORT).show();
				}
				break;
				default:
				{
					Toast.makeText(DevListActivity.this, "刷新失败\n未定义错误", Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	};
}
