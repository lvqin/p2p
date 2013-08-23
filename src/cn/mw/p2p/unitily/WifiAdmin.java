﻿package cn.mw.p2p.unitily;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

/**
 * WIFI操作对象
 * @author zhouwb
 *
 */
public class WifiAdmin {
	
    //定义一个WifiManager对象
	private WifiManager mWifiManager;
	//定义一个WifiInfo对象
	private WifiInfo mWifiInfo;
	//扫描出的网络连接列表
	private List<ScanResult> mWifiList;
	//网络连接列表
	private List<WifiConfiguration> mWifiConfigurations;
	WifiLock mWifiLock;
	public WifiAdmin(Context context){
		//取得WifiManager对象
		mWifiManager=(WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		// 取得WifiInfo对象
		mWifiInfo = mWifiManager.getConnectionInfo();
	}
	//打开wifi
	public void openWifi(){
		if(!mWifiManager.isWifiEnabled()){
			mWifiManager.setWifiEnabled(true);
		}

		while (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
			try {
				// 为了避免程序一直while循环，让它睡个100毫秒在检测……
				Thread.currentThread();
				Thread.sleep(100);
			} catch (InterruptedException ie) {

			}
		}
	}
	//关闭wifi
	public void closeWifi(){
		if(!mWifiManager.isWifiEnabled()){
			mWifiManager.setWifiEnabled(false);
		}
	}
	 // 检查当前wifi状态  
    public int checkState() {  
        return mWifiManager.getWifiState();  
    }  
	//锁定wifiLock
	public void acquireWifiLock(){
		mWifiLock.acquire();
	}
	//解锁wifiLock
	public void releaseWifiLock(){
		//判断是否锁定
		if(mWifiLock.isHeld()){
			mWifiLock.acquire();
		}
	}
	//创建一个wifiLock
	public void createWifiLock(){
		mWifiLock = mWifiManager.createWifiLock("test");
	}
	//得到配置好的网络
	public List<WifiConfiguration> getConfiguration(){
		return mWifiConfigurations;
	}
	//指定配置好的网络进行连接
	public void connetionConfiguration(int index){
		if (index > mWifiConfigurations.size()) {
			return;
		}
		//连接配置好指定ID的网络
		mWifiManager.enableNetwork(mWifiConfigurations.get(index).networkId, true);
	}
	public void startScan(){
		mWifiManager.startScan();
		// 得到扫描结果
		mWifiList = mWifiManager.getScanResults();
		// 得到配置好的网络连接
		mWifiConfigurations = mWifiManager.getConfiguredNetworks();
	}
	//得到网络列表
	public List<ScanResult> getWifiList(){
		return mWifiList;
	}
	//查看扫描结果
	public StringBuffer lookUpScan(){
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<mWifiList.size();i++){
			sb.append("Index_" + new Integer(i + 1).toString() + ":");
			 // 将ScanResult信息转换成一个字符串包  
            // 其中把包括：BSSID、SSID、capabilities、frequency、level  
			sb.append((mWifiList.get(i)).toString()).append("\n");
		}
		return sb;	
	}
	public String getMacAddress(){
		return (mWifiInfo==null)?"NULL":mWifiInfo.getMacAddress();
	}
	public String getBSSID(){
		return (mWifiInfo==null)?"NULL":mWifiInfo.getBSSID();
	}
	public int getIpAddress(){
		return (mWifiInfo==null)?0:mWifiInfo.getIpAddress();
	}
	//得到连接的ID
	public int getNetWordId(){
		return (mWifiInfo==null)?0:mWifiInfo.getNetworkId();
	}
	//得到连接的SSID
	public String getNetWordSSID(){
		return (mWifiInfo==null)?"NULL":mWifiInfo.getSSID();
	}
	//得到wifiInfo的所有信息
	public String getWifiInfo(){
		return (mWifiInfo==null)?"NULL":mWifiInfo.toString();
	}
	//更新一个网络并连接
	public boolean addNetWork(WifiConfiguration configuration,String connSSID,String ssid){
		int networkId = mWifiManager.addNetwork(configuration);
		boolean blRes = mWifiManager.enableNetwork(networkId, false);
		WifiConfiguration tempConfig = this.IsExsits(connSSID);
		if(tempConfig!=null)
		{
			if(blRes)
			{
				disConnectionWifi(networkId);
				mWifiManager.removeNetwork(networkId);
			}
			else {
				disConnectionWifi(tempConfig.networkId);
			}
			mWifiManager.enableNetwork(tempConfig.networkId, false);
			mWifiManager.reconnect();
		}


		return blRes;
	}
	//断开指定ID的网络
	public void disConnectionWifi(int netId){
		mWifiManager.disableNetwork(netId);
		mWifiManager.disconnect();
	}
	
    /**
     * 查看以前是否也配置过这个网络    
     * @param SSID 当前正在连接的SSID
     * @return
     */
	private WifiConfiguration IsExsits(String SSID)     
	{     
		List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
		for (WifiConfiguration existingConfig : existingConfigs) {
			if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
				return existingConfig;
			}
		}
		return null;
	}
}
