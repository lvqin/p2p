package cn.mw.p2p.unitily;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;

public class p2punitily {
			
	/**
	 * 登录方法
	 */
//	public static final String LOGIN_FUNCTION = "UserLogin";//MonUserLogin
	public static final String LOGIN_FUNCTION = "MonUserLogin";//UserLogin
	
	/**
	 * 登录方法结构体参数
	 */
//	public static final String LOGIN_REQUEST = "UserLoginRequest";//MonUserLoginRequest
	public static final String LOGIN_REQUEST = "MonUserLoginRequest";//UserLoginRequest
	
	/**
	 * 获取播放地址方法
	 */
	public static final String PLAYURL_FUNCTION = "GetPlayUrl";
	
	/**
	 * 获取播放地址方法结构体参数
	 */
	public static final String PLAYURL_REQUEST = "GetPlayUrlRequest";
	
	/**
	 * 获取播放设备录像文件地址方法
	 */
	public static final String PLAYRECORDURL_FUNCTION = "MonGetRecordPlayUrl";
	
	/**
	 * 获取播放设备录像文件地址结构体参数
	 */
	public static final String PLAYRECORDURL_REQUEST = "MonGetRecordPlayUrlRequest";
	
	/**
	 * 获取设备配置参数
	 */
	public static final String GETDEVPARAM_FUNCTION = "GetDevParam";
	
	/**
	 * 获取设备配置结构体参数
	 */
	public static final String GETDEVPARAM_REQUEST = "GetDevParamRequest";
	
	/**
	 * 获取设备通道名称
	 */
	public static final String GETDEVCHANNELNAME_FUNCTION = "MonGetDevName";
	
	/**
	 * 获取设备通道名称结构体参数
	 */
	public static final String GETDEVCHANNELNAME_REQUEST = "MonGetDevNameRequest";
	
	/**
	 * 设置设备配置参数
	 */
	public static final String SETDEVPARAM_FUNCTION = "SetDevParam";
	
	/**
	 * 设置设备配置结构体参数
	 */
	public static final String SETDEVPARAM_REQUEST = "SetDevParamRequest";
	
	/**
	 * 添加设备方法
	 */
	public static final String ADDDEV_FUNCTION = "MonAddDev";
	
	/**
	 * 添加设备结构体参数
	 */
	public static final String ADDDEV_REQUEST = "MonAddDevRequest";
	
	/**
	 * 删除设备方法
	 */
	public static final String DELDDEV_FUNCTION = "DelDev";
	
	/**
	 * 删除设备结构体参数
	 */
	public static final String DELDEV_REQUEST = "DelDevRequest";
	
	/**
	 * 获取密码
	 */
	public static final String GETPWD_FUNCTION = "MonGetPassword";
	
	/**
	 * 获取密码结构体
	 */
	public static final String GETPWD_REQUEST = "MonGetPwdRequest";
	
	/**
	 * 查询E云帐户
	 */
	public static final String GETEACCOUNT_FUNCTION = "GetEAccount";
	
	/**
	 * 查询E云帐户结构体
	 */
	public static final String GETEACCOUNT_REQUEST = "GetEAccountRequest";
	
	/**
	 * 设置E云帐户
	 */
	public static final String SETEACCOUNT_FUNCTION = "SetEAccount";
	
	/**
	 * 设置E云帐户结构体
	 */
	public static final String SETEACCOUNT_REQUEST = "SetEAccountRequest";
	
	/**
	 * 重启摄像机
	 */
	public static final String REBOOTDEV_FUNCTION = "RebootDev";
	
	/**
	 * 重启摄像机结构体
	 */
	public static final String REBOOTDEV_REQUEST = "RebootDevRequest";
	
	/**
	 * 设置同时观看人数
	 */
	public static final String SETMAXLINENUM_FUNCTION = "SetMaxLineNum";
	
	/**
	 * 设置同时观看人数结构体
	 */
	public static final String SETMAXLINENUM_REQUEST = "SetMaxLineNumRequest";
	
	/**
	 * 设置设备密码
	 */
	public static final String SETDEVKEY_FUNCTION = "SetDevKey";
	
	/**
	 * 设置设备密码结构体
	 */
	public static final String SETDEVKEY_REQUEST = "SetDevKeyRequest";
	
	/**
	 * 用户注册
	 */
	public static final String REG_FUNCTION = "MobileRegister";
	
	/**
	 * 用户注册结构体
	 */
	public static final String REG_REQUEST = "MobileRegisterRequest";
	
	/**
	 * 获取录像策略
	 */
	public static final String GETRECPOLICY_FUNCTION = "GetRecordPolicy";
	
	/**
	 * 获取录像策略结构体
	 */
	public static final String GETRECPOLICY_REQUEST = "GetRecordPolicyRequest";
	
	/**
	 * 删除录像策略
	 */
	public static final String DELRECPOLICY_FUNCTION = "DelRecordPolicy";
	
	/**
	 * 删除录像策略结构体
	 */
	public static final String DELRECPOLICY_REQUEST = "DelRecordPolicyRequest";
	
	/**
	 * 添加录像策略
	 */
	public static final String ADDRECPOLICY_FUNCTION = "AddRecordPolicy";
	
	/**
	 * 添加录像策略结构体
	 */
	public static final String ADDRECPOLICY_REQUEST = "AddRecordPolicyRequest";
	
	/**
	 * 云台控制
	 */
	public static final String ControlPtz_FUNCTION = "ControlPtz";
	
	/**
	 * 云台控制结构体
	 */
	public static final String ControlPtz_REQUEST = "ControlPtzRequest";
	
	/**
	 * 开始录像
	 */
	public static final String STARTREC_FUNCTION = "StartRecord";
	
	/**
	 * 开始录像结构体
	 */
	public static final String STARTREC_REQUEST = "StartRecordRequest";
	
	/**
	 * 停止录像
	 */
	public static final String STOPREC_FUNCTION = "StopRecord";
	
	/**
	 * 停止录像结构体
	 */
	public static final String STOPREC_REQUEST = "StopRecordRequest";
	
	/**
	 * 获取系统日志
	 */
	public static final String GETSYSLOG_FUNCTION = "GetSysLog";
	
	/**
	 * 获取系统日志结构体
	 */
	public static final String GETSYSLOG_REQUEST = "GetSysLogRequest";
	
	/**
	 * 设置设备名称
	 */
	public static final String SETDEVNAME_FUNCTION = "SetDevName";
	
	/**
	 * 设置设备名称结构体
	 */
	public static final String SETDEVNAME_REQUEST = "SetDevNameRequest";
	
	/**
	 * 远程升级
	 */
	public static final String UPGRADE_FUNCTION = "NotifyUpgrade";
	
	/**
	 * 远程升级结构体
	 */
	public static final String UPGRADE_REQUEST = "NotifyUpgradeRequest";
	
	/**
	 * 设备固件版本查询
	 */
	public static final String GETDEVESION_FUNCTION = "GetDevVersion";
	
	/**
	 * 设备固件版本查询构体
	 */
	public static final String GETDEVESION_REQUEST = "GetDevVersionRequest";
	
	/**
	 * 布防状态查询方法
	 */
	public static final String GETALARM_FUNCTION = "MonGetAlarmSwitch";
	
	/**
	 * 布防状态查询结构体
	 */
	public static final String GETALARM_REQUEST = "MonGetAlarmSwitchRequest";
	
	/**
	 * 布防状态设置方法
	 */
	public static final String SETALARM_FUNCTION = "MonSetAlarmSwitch";
	
	/**
	 * 布防状态设置结构体
	 */
	public static final String SETALARM_REQUEST = "MonSetAlarmSwitchRequest";
	
	
	/**
	 * 设备录像查询方法
	 */
	public static final String SELECTDEVRECOR_FUNCTION = "MonGetDevRecord";
	
	/**
	 * 设备录像查询方法结构体
	 */
	public static final String SELECTDEVRECOR_REQUEST = "MonGetDevRecordRequest";
	
	/**
	 * 刷新设备列表方法
	 */
	public static final String REFRESHDEVLIST_FUNCTION = "MonRefreshDevList2";
	
	/**
	 * 刷新设备列表方法结构体
	 */
	public static final String REFRESHDEVLIST_REQUEST = "MonRefreshDevList2Request";
	
	//TODO 新添加的方法===============================================================
	
	/**
	 * 设置手机号码
	 */
	public static final String SETMOBILE_FUNCTION = "MonSetMobile";
	
	/**
	 * 设置手机号码方法结构体
	 */
	public static final String SETMOBILE_REQUEST = "MonSetMobileRequest";
	
	/**
	 * 查询手机号码
	 */
	public static final String GETMOBILE_FUNCTION = "MonGetMobile";
	
	/**
	 * 查询手机号码方法结构体
	 */
	public static final String GETMOBILE_REQUEST = "MonGetMobileRequest";
	
	/**
	 * 设置用户密码
	 */
	public static final String SETUSERKEY_FUNCTION = "SetUserKey";
	
	/**
	 * 设置用户密码方法结构体
	 */
	public static final String SETUSERKEY_REQUEST = "SetUserKeyRequest";
	
	/**
	 * 获取验证码
	 */
	public static final String GETAUTHCODE_FUNCTION = "MonGetAuthCode";
	
	/**
	 * 获取验证码结构体
	 */
	public static final String GETAUTHCODE_REQUEST = "MonGetAuthCodeRequest";
	
	//================================新增设备分享功能=========================================
	
	/**
	 * 添加设备分享方法
	 */
	public static final String ADDSHARE_FUNCTION = "MonAddShareDev";
	
	/**
	 * 添加设备分享结构体
	 */
	public static final String ADDSHARE_REQUEST = "MonAddShareDevRequest";
	
	
	/**
	 * 取消设备分享方法
	 */
	public static final String CANCELSHARE_FUNCTION = "MonCancelShareDev";
	
	/**
	 * 取消设备分享结构体
	 */
	public static final String CANCELSHARE_REQUEST = "MonCancelShareDevRequest";
	
	
    /*********************************************************************
     * 设置Spinner选择项
     * @param str
     * @param strValue
     * @return
     */
	public static int getSelectionIndex(String[] str, String strValue) {
		int selsectIndex = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i].equals(strValue))
				selsectIndex = i;
		}
		return selsectIndex;
	}
	
	/**
	 * 验证输入数据类型
	 * @param str
	 * @return
	 */
	public static String getInputDataType(String str)
	{
		String strTemp = null;
		try {
			char[] temC = str.toCharArray();
			int mid = temC[0];
			if (mid >= 48 && mid <= 57) {// 数字
				strTemp = "0";
			}

			if (mid >= 65 && mid <= 90) {// 大写字母
				strTemp = "A";
			}
			if (mid >= 97 && mid <= 122) {// 小写字母
				strTemp = "a";
			}
		} catch (Exception e) {
		}
		return strTemp;
	}
	
	/**
	 * 判断sd卡是否存在 ，存在则返回sd卡根目录
	 * @return
	 */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取根目录
		}
		else {
			return null;
		}
		return sdDir.toString();

	}
	

	/**
	 * 判断文件MimeType的方法
	 * @param f
	 * @return
	 */
	public static String getMimeType(File f) {
		String type = "";
		String fName = f.getName();
		// 取得扩展名
		String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
		// 根据扩展名决定Mime类型
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) 
		{
			type = "audio/*";
		} 
		else if (end.equals("3gp") || end.equals("mp4")) {
			type = "video/*";
		}
		else if (end.equals("jpg") || end.equals("gif") 
				|| end.equals("png") || end.equals("jpeg") || end.equals("bmp")) 
		{
			type = "image/*";
		}
		else if (end.equals("apk")) {
			// 打开安装apk程序 ， 需要在AndroidManifest中注册
			// android.permission.INSTALL_PACKAGES
			type = "application/vnd.android.package-archive";
		}
		return type;
	}

	/**
	 * 获取本机号码
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context)
	{
		try {
			// 与手机建立连接
			TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
			// 获取手机号码
			return tm.getLine1Number();
		} catch (Exception e) {
			return "";
		}		
	}
	
	/**
	 * 验证手机号码
	 * @param mobiles
	 * @return
	 */
    public static boolean isMobileNO(String mobiles) { 
		Pattern pattern = Pattern.compile("^(13[0-9]|15[0-9]|18[0-9])[0-9]{8}$");
		Matcher matcher = pattern.matcher(mobiles);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}   
    }
    /**
     * 验证ip地址
     */
    public static boolean isIp(String ip){
    	Pattern pattern = Pattern.compile
    	("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
    	Matcher matcher = pattern.matcher(ip);
    	if(matcher.matches()){
    		return true;
    	}else{
    		return false;
    	}
    }
    /**
     * 验证ipport
     */
    public static boolean isIpport(String ipport){
    	Pattern pattern = Pattern.compile("^[1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]{1}|6553[0-5]$");
    	//^[1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]{1}|6553[0-5]$
    	Matcher matcher = pattern.matcher(ipport);
    	if(matcher.matches()){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /**
     * 判断wifi网络是否链接
     */
    public static boolean isWiFiActive(Context inContext) {
    	          WifiManager mWifiManager = (WifiManager) inContext
    	          .getSystemService(Context.WIFI_SERVICE);
    	          WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
    	          int ipAddress = wifiInfo == null ? 0 : wifiInfo.getIpAddress();
    	          if (mWifiManager.isWifiEnabled() && ipAddress != 0) {
    	          System.out.println("**** WIFI is on");
    	              return true;
    	          } else {
    	             System.out.println("**** WIFI is off");
    	             return false;   
    	         }
    	 }
}
