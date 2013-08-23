package com.montnets.android.zmon;


import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import cn.mw.p2p.Request.MonGetServerInfoRequest;
import cn.mw.p2p.unitily.ExitApplication;
import cn.mw.p2p.unitily.MsgEnum;
import cn.mw.p2p.unitily.P2pBaseUrl;
import cn.mw.p2p.unitily.WSUtils;
import cn.mw.p2p.unitily.WifiAdmin;
import cn.mw.p2p.unitily.p2punitily;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 生成二维码
 * @author zhouwb
 *
 */
public class ZxingActivity extends Activity {
	
	private String userIDString;
	private String loginSession;
	private String DevID;
	private String ChannelNo;
	private String PtzFlag;
	private String mURL;
	
	
	private ImageButton imgbtnBack;
	private Button btn_GetZxing;
	private EditText wifiPWD;
	private Spinner spinnerSSID;
	private Spinner spinnercap;
	private Spinner spinnerNetType;
    private WifiAdmin mWifiAdmin;   
    private String strParamResult;
    
    private  String baseurlString;
    // 扫描结果列表    
    private List<ScanResult> list;    
    private ScanResult mScanResult;
    private String[] SSID;//SSID
    private String[] SSIDCap;//WIFI加密方式
    private String[] SSIDCap1;//WIFI加密方式
    private String[] Channel;//信道
    private String[] arr;
    private static SharedPreferences sp;
	private String SevrIP;
	private String SevrPort;
	
	
	private TextView txtTextView1;
	private TextView txtTextView2;
	private TextView txtTextView3;
    
    
	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(1);
		setContentView(R.layout.zxing_layout);
		ExitApplication.getInstance().addActivity(this);
		sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
		SevrIP = sp.getString("serverIP", P2pBaseUrl.SERVER_IP);
		getServerPort();
		SevrPort = strParamResult;
		mWifiAdmin = new WifiAdmin(ZxingActivity.this);
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new backOnClickListener());
		txtTextView1 = (TextView)findViewById(R.id.txtWifiSSID);
		txtTextView2 = (TextView)findViewById(R.id.txtWifipwd);
		txtTextView3 = (TextView) findViewById(R.id.txtWificap);
		baseurlString = P2pBaseUrl.BaseUrl(sp);
		System.out.println("此时的URL是--------->"+baseurlString);
		//网络类型[有线，无线]
		spinnerNetType = (Spinner)findViewById(R.id.spinnernetType);
		ArrayAdapter<String> adapterNetType = new ArrayAdapter<String>(
				ZxingActivity.this, android.R.layout.simple_spinner_item,MsgEnum.NET_TYPE);
		adapterNetType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerNetType.setAdapter(adapterNetType);
		spinnerNetType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if(MsgEnum.NET_TYPE[arg2].equals("有线"))
				{
					txtTextView1.setVisibility(View.GONE);
					txtTextView2.setVisibility(View.GONE);
					txtTextView3.setVisibility(View.GONE);
					spinnerSSID.setVisibility(View.GONE);
					wifiPWD.setVisibility(View.GONE);
					spinnercap.setVisibility(View.GONE);
				}
				else {
					txtTextView1.setVisibility(View.VISIBLE);
					txtTextView2.setVisibility(View.VISIBLE);
					txtTextView3.setVisibility(View.VISIBLE);
					spinnerSSID.setVisibility(View.VISIBLE);
					wifiPWD.setVisibility(View.VISIBLE);
					spinnercap.setVisibility(View.VISIBLE);
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		//绑定WIFI列表
		
				String[] wifiTemp = getAllNetWorkList();
		
		if(wifiTemp == null)
		{
			SSID = new String[] { "没有扫描到可用的WIFI网络!" };
			SSIDCap = new String[] { "0" };
		}
		else {
			SSID = wifiTemp[0].split(",");
			SSIDCap = wifiTemp[1].split(",");
			SSIDCap1 = SSIDCap[0].split(",");
			String str0 = "";
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < SSIDCap1.length; i++){
				 sb. append(SSIDCap1[i]);
				}
				String s = sb.toString();
				System.out.println("*******" + s);
				Pattern pattern = Pattern.compile("\\[(.*?)\\]");
		        Matcher matcher = pattern.matcher(s);
		        while(matcher.find()){
		            System.out.println("$$$$$$$$" + matcher.group(1));
		            str0 +=   matcher.group(1) + ",";
		            System.out.println("^^^^^^^^^" + str0);
		        }
		        arr = str0.split(",");
		        
			Channel = wifiTemp[2].split(",");
			System.out.println("=====" + Channel + "=======");
		}
		
		spinnerSSID = (Spinner)findViewById(R.id.spinnerSSID);
		ArrayAdapter<String> adapterSSID = new ArrayAdapter<String>(
				ZxingActivity.this, android.R.layout.simple_spinner_item,SSID);
		adapterSSID.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerSSID.setAdapter(adapterSSID);
		
		wifiPWD = (EditText) findViewById(R.id.edtWIFIPWD);
		btn_GetZxing = (Button) findViewById(R.id.btn_getZxing);
		btn_GetZxing.setOnClickListener(new getZxingOnClickListener());
		//SSIDCap1 = new String[] {"WPA-PAK-CCPM","WPA2-PSK-CCMP","WPS"};
		spinnercap = (Spinner) findViewById(R.id.spinnercap);
		ArrayAdapter<String> adapterSSIDCap = new ArrayAdapter<String>(ZxingActivity.this, android.R.layout.simple_spinner_item,arr);
		adapterSSIDCap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnercap.setAdapter(adapterSSIDCap);
	}
	
	public  void getServerPort(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String strParamResult = null;
				sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
				baseurlString = P2pBaseUrl.BaseUrl(sp);
				MonGetServerInfoRequest request = new MonGetServerInfoRequest();
				request.setReqType(0);
				PropertyInfo pinfo = new PropertyInfo();
		  		pinfo.setName("req");
		  		pinfo.setValue(request);
		  		pinfo.setType(request.getClass());
		  		System.out.println("========>"+baseurlString);
		  		Message msg = handler.obtainMessage();
				SoapObject soapObject = WSUtils.wsRequestStruct(baseurlString, "MonGetServerInfo",pinfo, request, "MonGetServerInfoRequest");
				if(soapObject != null){
				strParamResult = ((Object) soapObject.getProperty("InfoString")).toString();
				}
                msg.what = 2;
                Bundle b = new Bundle();
                b.putString("strParamResult", strParamResult);
                msg.setData(b);
                handler.sendMessage(msg);
			}
		}).start();
			
	}
	
	private Handler handler = new Handler(){
    	public void handleMessage(Message msg){
    		switch(msg.what){
    		
    		case 2:
    			Bundle b = msg.getData();
    			strParamResult = b.getString("strParamResult");
    			System.out.println("--------------------->" + strParamResult);
    			SevrPort = strParamResult;
    			break;
    			default:
    				break;
    		}
    	}
    };
	
	private final class backOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			ZxingActivity.this.finish();
		}
	}
	
	/**
	 * 生成二维码    联网方式#SSID#SSID密码#加密方法#信道#服务器IP#服务器端口
	 *
	 */
	private final class getZxingOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			String strSSID;
			String strCap;
			String str00 = "";
			String strfrequency;
			int channel = 0 ;
			String qRCodeString = null;
			Long netTypeIndex = spinnerNetType.getSelectedItemId();
			String netSelectValue = MsgEnum.NET_TYPE[netTypeIndex.intValue()];
			if(netSelectValue.equals("有线"))
			{
				//qRCodeString = "###" + SevrIP + "#" + SevrPort;
				  qRCodeString = "LAN" + "#" +"NULL" + "#" + "NULL" +"#" + "NULL" 
				                     + "#" + "NULL" + "#"  + SevrIP + "#" + SevrPort;
			}
			else {
				Long SSIDIndex = spinnerSSID.getSelectedItemId();
				String SSIDValue = SSIDCap[SSIDIndex.intValue()];
				String value = Channel[SSIDIndex.intValue()];
				if(SSIDValue.equals("0"))
				{
					Toast.makeText(ZxingActivity.this, "没有可用的WIFI网络！", 0).show();
					return;
				}
				else {
					String[] SSIDTemp = SSIDValue.split("@");
					strSSID = SSIDTemp[0];
					strCap = SSIDTemp[1];
					Pattern pattern = Pattern.compile("\\[(.*?)\\]");
			        Matcher matcher = pattern.matcher(strCap);
			        while(matcher.find()){
			            System.out.println("\\\\\\\\" + matcher.group(1));
			            str00 +=   matcher.group(1) + ",";
			            System.out.println("%%%%%%%%%" + str00);
			        }
			        String[] arr1 = str00.split(",");
			        System.out.println("~~~~~~~`" + Arrays.asList(arr1));
			        spinnercap = (Spinner) findViewById(R.id.spinnercap);
					ArrayAdapter<String> adapterSSIDCap = new ArrayAdapter<String>(ZxingActivity.this, android.R.layout.simple_spinner_item,arr1);
					adapterSSIDCap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinnercap.setAdapter(adapterSSIDCap);
					strfrequency = value;
					System.out.println("此时信道值是====>"+strfrequency);
					if(strfrequency.equals("2412")){
						channel = 1;
					}
					if(strfrequency.equals("2417")){
						channel = 2;
					}
					if(strfrequency.equals("2422")){
						channel = 3;
					}
					if(strfrequency.equals("2427")){
						channel = 4;
					}
					if(strfrequency.equals("2432")){
						channel = 5;
					}
					if(strfrequency.equals("2437")){
						channel = 6;
					}
					if(strfrequency.equals("2442")){
						channel = 7;
					}
					if(strfrequency.equals("2447")){
						channel = 8;
					}
					if(strfrequency.equals("2452")){
						channel = 9;
					}
					if(strfrequency.equals("2457")){
						channel = 10;
					}
					if(strfrequency.equals("2462")){
						channel = 11;
					}
					if(strfrequency.equals("2467")){
						channel = 12;
					}
					if(strfrequency.equals("2472")){
						channel = 13;
					}
					if(strfrequency.equals("2477")){
						channel = 14;
					}
					System.out.println("======>" + strCap + "信道" +String.valueOf(channel));
				}
				String strwifiPWD = wifiPWD.getText().toString().trim();
				if(strwifiPWD.equals("")){
					strwifiPWD = null;
				}else {
				    strwifiPWD = wifiPWD.getText().toString().trim();
				}
				String cap = spinnercap.getSelectedItem().toString();
				if(cap.equals("")){
					cap = null;
				}else {
					cap = spinnercap.getSelectedItem().toString();
				}
				String strchannel = String.valueOf(channel);
				
				//qRCodeString = strSSID + "#" + strwifiPWD + "#" + strCap + "#" + SevrIP + "#" + SevrPort;
				qRCodeString = "WIFI" + "#" +strSSID + "#" + strwifiPWD +"#" + cap 
                + "#" + strchannel + "#"  + SevrIP + "#" + SevrPort;
			}
			System.out.print("二维码信息：" + qRCodeString);
			Intent intent = new Intent(ZxingActivity.this,QRCodeBitmap.class);
			intent.putExtra("CodeString", qRCodeString);
			startActivity(intent);
		}
	}
	
	
	
	
	/**
	 * 扫描WIFI列表
	 * @return
	 */
	public String[] getAllNetWorkList() {
		String[] str = new String[3];
		String wifiSSID = null;
		String wifiValue = null;
		String wifistr = null ;
				// 开始扫描网络
				mWifiAdmin.openWifi();
				mWifiAdmin.startScan();
				list = mWifiAdmin.getWifiList();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				// 得到扫描结果
				mScanResult = list.get(i);
				if(i == 0)
				{
					wifiSSID = mScanResult.SSID;
					wifiValue = mScanResult.SSID + "@" + mScanResult.capabilities;
					wifistr = String.valueOf(mScanResult.frequency);
				}
				else {
					wifiSSID += "," + mScanResult.SSID;
					wifiValue += "," + mScanResult.SSID + "@" + mScanResult.capabilities ;
					System.out.println("------->" + wifiSSID);
					System.out.print(mScanResult.frequency);
					System.out.println("扫描结果====>" + wifiValue);
					wifistr += "," + mScanResult.frequency;
					System.out.println("+++++++++>" + wifistr);
				}
			}
			str[0] = wifiSSID;
			str[1] = wifiValue;
			str[2] = wifistr;
		}
		else {
			str = null;
		}
		return str;
	}
	
	/*private void initMember() {
		SharedPreferences sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
		baseurlString = P2pBaseUrl.BaseUrl(sp);
	}*/
	
	

}
