package com.montnets.android.zmon;

import java.util.ArrayList;

import cn.mw.p2p.Request.DelRecordPolicyRequest;
import cn.mw.p2p.Request.GetDevParamRequest;
import cn.mw.p2p.bean.PolicyBean;
import cn.mw.p2p.handler.MethodListCount;
import cn.mw.p2p.handler.Threadhandler;
import cn.mw.p2p.unitily.ExitApplication;
import cn.mw.p2p.unitily.MsgEnum;
import cn.mw.p2p.unitily.P2pBaseUrl;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RecpolicyList extends Activity {
	
	private static String userIDString;//用户名
	private String loginSession = null;
	private ImageButton imgbtnBack;
	private ListView lvRecpolicy;
	private String DevID;
	private String ChannelNO = null;
	private ProgressDialog dialog;
	private Threadhandler thd;
	private String baseurlString;
	private ArrayList<PolicyBean> ArryPolicy;
	private MyAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.recpolicy_layout);
        ExitApplication.getInstance().addActivity(this);
		SharedPreferences sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
		userIDString = sp.getString("userInfo", "").split(",")[0];
		loginSession = sp.getString("userInfo", "").split(",")[2];
		System.out.println("RecpolicyList-用户ID：" + userIDString);
		DevID = getIntent().getStringExtra("DevID");
		ChannelNO = getIntent().getStringExtra("ChannelNO").toString();
		baseurlString = P2pBaseUrl.BaseUrl(sp);
		getRecPolicyList();
		initUI();
	}

	private void initUI() {
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new backOnClickListener());	
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
			RecpolicyList.this.finish();
		}
	}
	
	public static final int ADDPOLICY = android.view.Menu.FIRST;

	
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		menu.add(0, ADDPOLICY, 0, "添加录像策略");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case ADDPOLICY:
			Bundle bundle = new Bundle();
			bundle.putString("DevID", DevID);
			bundle.putString("ChannelNO", ChannelNO);
			bundle.putString("SKIP_TYPE", "ADD");
			Intent intent = new Intent(RecpolicyList.this, RecpolicySetActivity.class);
			intent.putExtra("PolicyInfo", bundle);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void getRecPolicyList()
	{
		GetDevParamRequest gpr = new GetDevParamRequest();
		gpr.setAccount(userIDString);
		gpr.setLoginSession(loginSession);
		gpr.setDevID(DevID);
		gpr.setChannelNo(Integer.parseInt(ChannelNO));
		dialog = ProgressDialog.show(RecpolicyList.this, "", "正在获取录像策略", true);
		thd = new Threadhandler(mHandler, gpr, baseurlString, "getRecPolicy");
		thd.start();
	}
	
	
	/**
	 * 消息处理
	 */
	private final Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			dialog.dismiss();
			int msgID = msg.what;
			switch (msgID) {
			case MsgEnum.SUCCESS:
				ArryPolicy = MethodListCount.arrayPolicyList;
				lvRecpolicy = (ListView)findViewById(R.id.lvRecpolicyList);
				adapter = new MyAdapter();
				lvRecpolicy.setAdapter(adapter);
				Toast.makeText(RecpolicyList.this, "查询成功！", 0).show();
				break;
			case MsgEnum.USERNAME_NULL:
				Toast.makeText(RecpolicyList.this, "用户不存在！", 0).show();
				break;
			case MsgEnum.SESSION_TIMEOUT:
				Toast.makeText(RecpolicyList.this, "SESSION超时！", 0).show();
				break;
			case MsgEnum.DEVICE_EXISTS_NULL:
				Toast.makeText(RecpolicyList.this, "设备不存在！", 0).show();
				break;
			case MsgEnum.SERVER_ERROR:
				Toast.makeText(RecpolicyList.this, "服务器异常！", 0).show();
				break;
			case MsgEnum.OK:
				Toast.makeText(RecpolicyList.this, "删除成功！", 0).show();
				getRecPolicyList();
				break;
			default:
				break;
			}
		}
	};

	public final class ViewHolder {
		public TextView text;
		public ImageButton cancel;
	}

	public class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;

		public MyAdapter() {
			this.mInflater = LayoutInflater.from(RecpolicyList.this);
		}
		
		@Override
		public int getCount() {
			return ArryPolicy.size();
		}

		@Override
		public Object getItem(int position) {
			return ArryPolicy.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0L;
		}

		@Override
		public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
			ViewHolder localviewHoder = null;
			if (paramView == null) {
				paramView = mInflater.inflate(R.layout.repolicyitem, null, false);
				localviewHoder = new ViewHolder();
				localviewHoder.text = ((TextView) paramView.findViewById(R.id.txtName));
				localviewHoder.cancel = (ImageButton)paramView.findViewById(R.id.ivDel);
				paramView.setTag(localviewHoder);
				final PolicyBean localPolicyBean = (PolicyBean) getItem(paramInt);
				String repolicyName = null;
				if(localPolicyBean.getWeek()==0)
				{
					repolicyName = localPolicyBean.getStartTime() + " - "
							+ localPolicyBean.getEndTime() + " " + "一次性";
				}else {
					repolicyName = localPolicyBean.getStartTime() + " - "
							+ localPolicyBean.getEndTime() + " " + "周期性";
				}
				localviewHoder.text.setText(repolicyName);
				localviewHoder.text.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Bundle bundle = new Bundle();
						bundle.putString("PolicyID", localPolicyBean.getPolicyID());
						bundle.putString("DevID", DevID);
						bundle.putString("ChannelNO", ChannelNO);
						bundle.putString("SKIP_TYPE", "EDIT");
						bundle.putString("StartTime", localPolicyBean.getStartTime());
						bundle.putString("EndTime", localPolicyBean.getEndTime());
						bundle.putInt("Week", localPolicyBean.getWeek());
						bundle.putInt("RecordFlag", localPolicyBean.getRecordFlag());
						bundle.putInt("StreamType", localPolicyBean.getStreamType());
						Intent intent = new Intent(RecpolicyList.this, RecpolicySetActivity.class);
						intent.putExtra("PolicyInfo", bundle);
						startActivity(intent);
					}
				});
				
				//删除策略
				localviewHoder.cancel.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						DelRecordPolicyRequest dpr = new DelRecordPolicyRequest();
						dpr.setAccount(userIDString);
						dpr.setLoginSession(loginSession);
						dpr.setDevID(DevID);
						dpr.setChannelNo(Integer.parseInt(ChannelNO));
						dpr.setPolicyId(Integer.parseInt(localPolicyBean.getPolicyID()));
						dialog = ProgressDialog.show(RecpolicyList.this, "", "正在删除录像策略", true);
						thd = new Threadhandler(mHandler, dpr, baseurlString);
						thd.start();
					}
				});
				
			} else {
				localviewHoder = (ViewHolder) paramView.getTag();
			}
			return paramView;
		}
		
	}
}
