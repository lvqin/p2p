package cn.mw.p2p.handler;

import java.util.ArrayList;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import cn.mw.p2p.Request.AccountRequest;
import cn.mw.p2p.Request.MonAddDevRequest;
import cn.mw.p2p.Request.AddRecordPolicyRequest;
import cn.mw.p2p.Request.ControlPtzRequest;
import cn.mw.p2p.Request.DelRecordPolicyRequest;
import cn.mw.p2p.Request.DevRecordFileSelectRequest;
import cn.mw.p2p.Request.GetDevParamRequest;
import cn.mw.p2p.Request.GetEAccountRequest;
import cn.mw.p2p.Request.GetPwdRequest;
import cn.mw.p2p.Request.GetSysLogRequest;
import cn.mw.p2p.Request.LoginRequest;
import cn.mw.p2p.Request.MobileRegisterRequest;
import cn.mw.p2p.Request.PlayURLRequest;
import cn.mw.p2p.Request.SafeRequest;
import cn.mw.p2p.Request.SetDevParamRequest;
import cn.mw.p2p.Request.SetEAccountRequest;
import cn.mw.p2p.Request.StartRecordRequest;
import cn.mw.p2p.adpter.DeviceAdapter;
import cn.mw.p2p.bean.DevRecordFileBean;
import cn.mw.p2p.bean.LogDescBean;
import cn.mw.p2p.bean.PolicyBean;
import cn.mw.p2p.bean.VedioPointBean;
import cn.mw.p2p.unitily.MsgEnum;
import cn.mw.p2p.unitily.P2pBaseUrl;
import cn.mw.p2p.unitily.WSUtils;
import cn.mw.p2p.unitily.p2punitily;

/**
 * 操作方法类
 * @author zhouwb
 *
 */
public class MethodListCount {
	


	public MethodListCount()
	{

	}
	

	/**
	 * TODO 登录方法线程
	 * @param mHandler
	 * @param lr
	 * @param baseUrl
	 * @param dp
	 */
	public static void LoginThread(Handler mHandler, LoginRequest lr, String baseUrl, DeviceAdapter dp)
	{
		String strRes = "-1";
		String strUrl = baseUrl; 
		System.out.println("登录URL" + strUrl);
  		PropertyInfo pinfo=new PropertyInfo();
  		pinfo.setName("req");
  		pinfo.setValue(lr);
  		pinfo.setType(lr.getClass());
  		
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.LOGIN_FUNCTION, pinfo, lr, p2punitily.LOGIN_REQUEST);
		if (soapObject != null) {
			Object obj = (Object) soapObject.getProperty("Result");
			strRes = obj.toString();
		} else {
			strRes = "-1";
		}

		int intResult = Integer.parseInt(strRes);// 0：认证成功 1：用户名不存在 2：密码错误 3：MCU版本过低
		Message msg = mHandler.obtainMessage();
		switch(intResult)
		{
			case 0:
				Bundle bundle = new Bundle();
				String loginSession = ((Object) soapObject.getProperty("LoginSession")).toString();
				bundle.putString("LoginSession", loginSession);
				getDataForSqlite(soapObject, dp);
				msg.setData(bundle);
				msg.what = MsgEnum.LOGIN_SUCCESS;
				break;
			case 1:
				msg.what = MsgEnum.USERNAME_NULL;
				break;
			case 2:
				msg.what = MsgEnum.PASSWORD_ERROR;
				break;
			case 3:
				msg.what = MsgEnum.MCU_VERSION_DOWN;
				break;
			case 4:
				msg.what = MsgEnum.LOGIN_ERROR;
				break;
			case -1:
				msg.what = MsgEnum.SERVER_ERROR;
				break;
			default:
				break;
		}
		
		mHandler.sendMessage(msg);
	}
	
	/**
	 * TODO 获取设备播放视频地址
	 * @param mHandler 处理对象
	 * @param prt 设备对象
	 * @param baseUrl 请求URL
	 * @return RTSP PLAY URL
	 */
	public static void getPlayURL(Handler mHandler, PlayURLRequest prt, String baseUrl, String funName)
	{
		String strPlayURL = null;
		String strRes = null;
		String functionName = null;
		String functionRequest = null;
		if(funName.equals("GetPlayUrl"))
		{
			functionName = p2punitily.PLAYURL_FUNCTION;
			functionRequest = p2punitily.PLAYURL_REQUEST;
		}
		else {
			functionName = p2punitily.PLAYRECORDURL_FUNCTION;
			functionRequest = p2punitily.PLAYRECORDURL_REQUEST;
		}
		
		// 将pRequest对象放在PropertyInfo集合中
		System.out.println("request stream type: " + prt.getStreamType());
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(prt);
		pInfo.setType(prt.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, functionName, pInfo, prt, functionRequest);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				try {
					Object obj = (Object) soapObject.getProperty("VideoPlayUrl");
					strPlayURL = obj.toString();
					Bundle bundle = new Bundle();
					bundle.putString("PLAYURL", strPlayURL);
					msgMessage.setData(bundle);
					msgMessage.what = MsgEnum.PLAYURL_SUCCESS;	//成功
					System.out.println("获取播放地址成功:" + strPlayURL);
				} catch (Exception e) {
					System.out.println("获取播放地址失败：" + e.getMessage());
					msgMessage.what = MsgEnum.PLAYURL_FAILE;	//失败
				}
				break;
			case 1:
				msgMessage.what = MsgEnum.USERNAME_NULL;	//用户名不存在
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;	//SESSION过期
				break;
			case 6:
				msgMessage.what = MsgEnum.AGENTREQUEST_FAINLD;//设备反向代理请求失败
				break;
			case 7:
				msgMessage.what = MsgEnum.AGENTRESPONSE_FAINLD;//设备反向代理请求响应超时
				break;
			case 8:
				msgMessage.what = MsgEnum.NOT_PROXY;//设备不支持反向代理
				break;
			case 20:
				msgMessage.what = MsgEnum.ADRESS_NOTAVAILABLE;//设备直连地址不可用
				break;
			case 21:
				msgMessage.what = MsgEnum.NOT_STREAMMEDIA;//设备无可用流媒体
				break;

			case 22:
				msgMessage.what = MsgEnum.MAX_CONNECTED;//已达设备最大直连数
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}

		} else {
			msgMessage.what = MsgEnum.PLAYURL_FAILE;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 获取设备列表
	 * @param userIDString 用户ID
	 * @param userPwdString 用户密码
	 * @param videoPoints 视频设备对象集合
	 * @param point 设备对象
	 * @param sp 配置参数
	 * @return ArrayList<VedioPointBean>
	 */
	public static ArrayList<VedioPointBean> getData(String userIDString, String userPwdString, ArrayList<VedioPointBean> videoPoints, VedioPointBean point, SharedPreferences sp)
	{
		LoginRequest lr = new LoginRequest();
		lr.setAccount(userIDString);
		lr.setPwdType(1);
		lr.setTelPassword(userPwdString);
		lr.setVersion("1.0");
  		
  		PropertyInfo pinfo=new PropertyInfo();
  		pinfo.setName("req");
  		pinfo.setValue(lr);
  		pinfo.setType(lr.getClass());
  		String strUrl = P2pBaseUrl.BaseUrl(sp);
		System.out.println("获取设备列表远程请求URL:" + strUrl);
		SoapObject soapObject = WSUtils.wsRequestStruct(strUrl, p2punitily.LOGIN_FUNCTION, pinfo, lr, p2punitily.LOGIN_REQUEST);
		videoPoints = new ArrayList<VedioPointBean>();
		for (int i = 0; i < soapObject.getPropertyCount(); i++) {
			Object soapChilds = (Object)soapObject.getProperty(i);
			if (soapChilds.toString().indexOf("DevID") == -1)
				continue;
			else {
				point = new VedioPointBean();
				point.setName(((SoapObject) soapChilds).getProperty("Name").toString());
				point.setDevID(((SoapObject) soapChilds).getProperty("DevID").toString());
				point.setChannelNo(((SoapObject) soapChilds).getProperty("ChannelNo").toString());
				boolean blOnline = ((SoapObject) soapChilds).getProperty("OnLine").toString().equals("1")?true:false;
				point.setOnline(blOnline);
				point.setPtzFlag(Integer.parseInt(((SoapObject) soapChilds).getProperty("PtzFlag").toString()));
				videoPoints.add(point);
			}
		}
		return videoPoints;
	}
	
	/**
	 * 将数据写入到本地SQLITE数据库
	 * @param soapObject
	 * @param dp 上下文对象
	 */
	public static void getDataForSqlite(SoapObject soapObject, DeviceAdapter dp)
	{
		VedioPointBean point = null;
		
		try {
			dp.Delete();//(插入数据之前先将所有数据删除，以免出现重复数据)
		} catch (Exception e) {
			System.out.println("删除数据异常:" + e.getMessage());
		}
		
		Log.v("SoapObjectCount", String.valueOf(soapObject.getPropertyCount()));
		for (int i = 0; i < soapObject.getPropertyCount(); i++) {
			Object soapChilds = (Object)soapObject.getProperty(i);
			if (soapChilds.toString().indexOf("DevID") == -1)
				continue;
			else {
				Log.v("InsertName", ((SoapObject) soapChilds).getProperty("Name").toString());
				Log.v("InsertDevID", ((SoapObject) soapChilds).getProperty("DevID").toString());
				Log.v("InsertChannelNo", ((SoapObject) soapChilds).getProperty("ChannelNo").toString());
				Log.v("InsertOnline", ((SoapObject) soapChilds).getProperty("OnLine").toString());
				Log.v("InsertPtzFlag", ((SoapObject) soapChilds).getProperty("PtzFlag").toString());
				Log.v("InsertShareFlag", ((SoapObject) soapChilds).getProperty("ShareFlag").toString());
				Log.v("InsertShareFlag", ((SoapObject) soapChilds).getProperty("ShareFlag").toString());
				Log.v("InsertGroupid", ((SoapObject) soapChilds).getProperty("Groupid").toString());
				
				point = new VedioPointBean();
				point.setName(((SoapObject) soapChilds).getProperty("Name").toString());
				point.setDevID(((SoapObject) soapChilds).getProperty("DevID").toString());
				point.setChannelNo(((SoapObject) soapChilds).getProperty("ChannelNo").toString());
				boolean blOnline = ((SoapObject) soapChilds).getProperty("OnLine").toString().equals("1")?true:false;
				point.setOnline(blOnline);
				point.setPtzFlag(Integer.parseInt(((SoapObject) soapChilds).getProperty("PtzFlag").toString()));
				point.setShareFlag(Integer.parseInt(((SoapObject) soapChilds).getProperty("ShareFlag").toString()));
				point.setGroupid(Integer.parseInt(((SoapObject) soapChilds).getProperty("Groupid").toString()));
				point.setURL("");
				
				//插入数据
				try {
					dp.Insert(point);
				} catch (Exception e) {
					System.out.println("插入数据异常:" + e.getMessage());
				}
			}
		}
		dp.closeDB();
	}
	
	/**
	 * 获取设备列表
	 * @return
	 */
	public static ArrayList<VedioPointBean> getDataArrayListForSqlite(Cursor cursor, DeviceAdapter deviceAdapter, Context context)
	{
		ArrayList<VedioPointBean> vPoints = new ArrayList<VedioPointBean>();
		VedioPointBean vpoint = null;
		cursor = deviceAdapter.Query();
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
		}
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Log.v("QueryName", cursor.getString(3));
			Log.v("QueryDevID", cursor.getString(1));
			Log.v("QueryChannelNo", cursor.getString(2));
			Log.v("QueryPtzflag", cursor.getString(4));
			Log.v("QueryUrl", cursor.getString(5));
			Log.v("QueryOnline", cursor.getString(6));
			Log.v("QueryShareflag", cursor.getString(7));
			Log.v("QueryGroupID", cursor.getString(8));
			
			vpoint = new VedioPointBean();
			vpoint.setDevID(cursor.getString(1));
			vpoint.setChannelNo(cursor.getString(2));
			vpoint.setName(cursor.getString(3));
			boolean blOnline = cursor.getString(6).equals("1") ? true : false;
			vpoint.setOnline(blOnline);
			vpoint.setPtzFlag(cursor.getInt(4));
			vpoint.setURL("");
			vpoint.setShareFlag(cursor.getInt(7));
			vpoint.setGroupid(cursor.getInt(8));
			vPoints.add(vpoint);
		}
		
		return vPoints;
	}
	
	// donot output share device
	public static ArrayList<VedioPointBean> getDataArrayListForSqlite2(Cursor cursor, DeviceAdapter deviceAdapter, Context context)
	{
		ArrayList<VedioPointBean> vPoints = new ArrayList<VedioPointBean>();
		VedioPointBean vpoint = null;
		cursor = deviceAdapter.Query();
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
		}
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			Log.v("QueryName", cursor.getString(3));
			Log.v("QueryDevID", cursor.getString(1));
			Log.v("QueryChannelNo", cursor.getString(2));
			Log.v("QueryPtzflag", cursor.getString(4));
			Log.v("QueryUrl", cursor.getString(5));
			Log.v("QueryOnline", cursor.getString(6));
			Log.v("QueryShareflag", cursor.getString(7));
			Log.v("QueryGroupID", cursor.getString(8));
			
			if (cursor.getString(8).equals("1")) {
				continue;
			}
			
			vpoint = new VedioPointBean();
			vpoint.setDevID(cursor.getString(1));
			vpoint.setChannelNo(cursor.getString(2));
			vpoint.setName(cursor.getString(3));
			boolean blOnline = cursor.getString(6).equals("1") ? true : false;
			vpoint.setOnline(blOnline);
			vpoint.setPtzFlag(cursor.getInt(4));
			vpoint.setURL("");
			vpoint.setShareFlag(cursor.getInt(7));
			vpoint.setGroupid(cursor.getInt(8));
			vPoints.add(vpoint);
		}
		
		return vPoints;
	}
	
	
	/**
	 * TODO 获取设备配置参数
	 * @param mHandler
	 * @param gpr
	 * @param baseUrl
	 */
	public static void getDeviceParam(Handler mHandler, GetDevParamRequest gpr, String baseUrl)
	{
		String strParamResult = null;
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(gpr);
		pInfo.setType(gpr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.GETDEVPARAM_FUNCTION, pInfo, gpr, p2punitily.GETDEVPARAM_REQUEST);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			if (strRes.equals("0")) {
				strParamResult = ((Object) soapObject.getProperty("DevID")).toString();
				strParamResult += "," + ((Object) soapObject.getProperty("FrameSize")).toString();
				strParamResult += "," + ((Object) soapObject.getProperty("FrameRate")).toString();
				strParamResult += "," + ((Object) soapObject.getProperty("RateType")).toString();
				strParamResult += "," + ((Object) soapObject.getProperty("BitRate")).toString();
				strParamResult += "," + ((Object) soapObject.getProperty("ImageQuality")).toString();
				strParamResult += "," + ((Object) soapObject.getProperty("AudioInput")).toString();
				Bundle bundle = new Bundle();
				bundle.putString("ParamResult", strParamResult);
				msgMessage.setData(bundle);
				msgMessage.what = MsgEnum.GETPARAM_SUCCESS;
			} else {
				msgMessage.what = MsgEnum.GETPARAM_ERROR;
			}
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 设置摄像头参数和名称
	 * @param mHandler
	 * @param spr
	 * @param baseUrl
	 */
	public static void setDeviceParam(Handler mHandler, SetDevParamRequest spr, String baseUrl, String funName)
	{
		String functionName = null;
		String functionRequest = null;
		if(funName.equals("SetDevParam"))
		{
			functionName = p2punitily.SETDEVPARAM_FUNCTION;
			functionRequest = p2punitily.SETDEVPARAM_REQUEST;
		}
		else {
			functionName = p2punitily.SETDEVNAME_FUNCTION;
			functionRequest = p2punitily.SETDEVNAME_REQUEST;
		}
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(spr);
		pInfo.setType(spr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, functionName, pInfo, spr, functionRequest);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				msgMessage.what = MsgEnum.SUCCESS;
				break;
			case 1:
				msgMessage.what = MsgEnum.ACCOUNT_NOT_EXIST;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			case 3:
				msgMessage.what = MsgEnum.DEVICE_EXISTS_NULL;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 获取设备通道名称
	 * @param mHandler
	 * @param gpr
	 * @param baseUrl
	 */
	public static void getDeviceChannelName(Handler mHandler, GetDevParamRequest gpr, String baseUrl)
	{
		String strParamResult = null;
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(gpr);
		pInfo.setType(gpr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.GETDEVCHANNELNAME_FUNCTION, pInfo, gpr, p2punitily.GETDEVCHANNELNAME_REQUEST);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			if (strRes.equals("0")) {
				strParamResult = ((Object) soapObject.getProperty("ChannelName")).toString();
				Bundle bundle = new Bundle();
				bundle.putString("ParamResult", strParamResult);
				msgMessage.setData(bundle);
				msgMessage.what = MsgEnum.GETPARAM_SUCCESS;
			} else {
				msgMessage.what = MsgEnum.GETPARAM_ERROR;
			}
		}
		mHandler.sendMessage(msgMessage);
	}
	
	
	/**
	 * TODO 添加设备
	 * @param mHandler
	 * @param adr
	 * @param baseUrl
	 */
	public static void addDevice(Handler mHandler, MonAddDevRequest adr, String baseUrl)
	{
		String strParamResult = null;
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(adr);
		pInfo.setType(adr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.ADDDEV_FUNCTION, pInfo, adr, p2punitily.ADDDEV_REQUEST);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			System.out.println("================添加设备返回值：" + strRes);
			switch (Integer.parseInt(strRes)) {
			case 0:
				Object soapChilds = (Object)soapObject.getProperty(1);
				strParamResult = ((SoapObject) soapChilds).getProperty("DevID").toString();
				strParamResult += "," + ((SoapObject) soapChilds).getProperty("ChannelNo").toString();
				strParamResult += "," + ((SoapObject) soapChilds).getProperty("Name").toString();
				strParamResult += "," + ((SoapObject) soapChilds).getProperty("OnLine").toString();
				strParamResult += "," + ((SoapObject) soapChilds).getProperty("PtzFlag").toString();
				Bundle bundle = new Bundle();
				bundle.putString("ParamResult", strParamResult);
				msgMessage.setData(bundle);
				msgMessage.what = MsgEnum.SUCCESS;
				break;
			case 1:
				msgMessage.what = MsgEnum.ACCOUNT_NOT_EXIST;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			case 3:
				msgMessage.what = MsgEnum.DEVICE_EXISTS_NULL;
				break;
			case 4:
				msgMessage.what = MsgEnum.DEV_IS_OTHERUSER;
				break;
			case 7:
				msgMessage.what = MsgEnum.PASSWORD_ERROR;
				break;
			case 6:
				msgMessage.what = MsgEnum.DEVICE_EXIST;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 删除设备
	 * @param mHandler
	 * @param adr
	 * @param baseUrl
	 */
	public static void delDevice(Handler mHandler, MonAddDevRequest adr, String baseUrl)
	{
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(adr);
		pInfo.setType(adr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.DELDDEV_FUNCTION, pInfo, adr, p2punitily.DELDEV_REQUEST);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				msgMessage.what = MsgEnum.OK;
				break;
			case 1:
				msgMessage.what = MsgEnum.ACCOUNT_NOT_EXIST;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			case 3:
				msgMessage.what = MsgEnum.DEVICE_EXISTS_NULL;
				break;
			case 4:
				msgMessage.what = MsgEnum.PASSWORD_ERROR;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 动态获取密码
	 * @param mHandler
	 * @param gpr
	 * @param baseUrl
	 */
	public static void getpwd(Handler mHandler, GetPwdRequest gpr, String baseUrl)
	{
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(gpr);
		pInfo.setType(gpr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.GETPWD_FUNCTION, pInfo, gpr, p2punitily.GETPWD_REQUEST);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				msgMessage.what = MsgEnum.SUCCESS;
				break;
			case 2:
				msgMessage.what = MsgEnum.SENDMESSAGE_ERROR;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 查询E云帐户
	 * @param mHandler
	 * @param ger
	 * @param baseUrl
	 */
	public static void getEAccount(Handler mHandler, GetEAccountRequest ger, String baseUrl)
	{
		String strParamResult = null;
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(ger);
		pInfo.setType(ger.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.GETEACCOUNT_FUNCTION, pInfo, ger, p2punitily.GETEACCOUNT_REQUEST);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				strParamResult = ((Object) soapObject.getProperty("EstoreAccount")).toString();
				strParamResult += "," + ((Object) soapObject.getProperty("EstorePassword")).toString();
				Bundle bundle = new Bundle();
				bundle.putString("ParamResult", strParamResult);
				msgMessage.setData(bundle);
				msgMessage.what = MsgEnum.SUCCESS;
				break;
			case 1:
				msgMessage.what = MsgEnum.ACCOUNT_NOT_EXIST;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 设置E云帐户
	 * @param mHandler
	 * @param ger
	 * @param baseUrl
	 */
	public static void setEAccount(Handler mHandler, SetEAccountRequest ser, String baseUrl)
	{
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(ser);
		pInfo.setType(ser.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.SETEACCOUNT_FUNCTION, pInfo, ser, p2punitily.SETEACCOUNT_REQUEST);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				msgMessage.what = MsgEnum.OK;
				break;
			case 1:
				msgMessage.what = MsgEnum.ACCOUNT_NOT_EXIST;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			case 3:
				msgMessage.what = MsgEnum.PASSWORD_OLD_REEOR;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 设置安全策略
	 * @param mHandler
	 * @param sr
	 * @param baseUrl
	 * @param funName SetDevKey=设置设备密码，SetMaxLineNum=设置同时观看人数，RebootDev=重启摄像机
	 */
	public static void setSafePolicy(Handler mHandler, SafeRequest sr, String baseUrl, String funName)
	{
		String SoapFunction = null;
		String SoapRequest = null;
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(sr);
		pInfo.setType(sr.getClass());
		if (funName.equals("SetDevKey")) {
			SoapFunction = p2punitily.SETDEVKEY_FUNCTION;
			SoapRequest = p2punitily.SETDEVKEY_REQUEST;
		} else if (funName.equals("SetMaxLineNum")) {
			SoapFunction = p2punitily.SETMAXLINENUM_FUNCTION;
			SoapRequest = p2punitily.SETMAXLINENUM_REQUEST;
		} else {
			SoapFunction = p2punitily.REBOOTDEV_FUNCTION;
			SoapRequest = p2punitily.REBOOTDEV_REQUEST;
		}
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, SoapFunction, pInfo, sr, SoapRequest);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				if (funName.equals("SetDevKey")) {
					msgMessage.what = MsgEnum.SETDEVKEY_SUCCESS;
				} else if (funName.equals("SetMaxLineNum")) {
					msgMessage.what = MsgEnum.SETMAXLINE_SUCCESS;
				} else {
					msgMessage.what = MsgEnum.SETDEVREBOOT_SUCCESS;
				}
				break;
			case 1:
				msgMessage.what = MsgEnum.ACCOUNT_NOT_EXIST;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			case 3:
				msgMessage.what = MsgEnum.DEVICE_EXISTS_NULL;
				break;
			case 8:
				msgMessage.what = MsgEnum.PASSWORD_OLD_REEOR;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 用户注册
	 * @param mHandler
	 * @param mrr
	 * @param baseUrl
	 */
	public static void MobileRegister(Handler mHandler, MobileRegisterRequest mrr, String baseUrl)
	{
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(mrr);
		pInfo.setType(mrr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.REG_FUNCTION, pInfo, mrr, p2punitily.REG_REQUEST);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				msgMessage.what = MsgEnum.SUCCESS;
				break;
			case 1:
				msgMessage.what = MsgEnum.DEVICE_EXISTS_NULL;
				break;
			case 2:
				msgMessage.what = MsgEnum.HASHTOKEN_NULL;
				break;
			case 3:
				msgMessage.what = MsgEnum.ERROR;
				break;
			case 4:
				msgMessage.what = MsgEnum.ACCOUNT_NOT_ACTIVE;
				break;
			case 5:
				msgMessage.what = MsgEnum.ACCOUNT_ISEXISTS;
				break;
			case 6:
				msgMessage.what = MsgEnum.MSGPWD_ERROR;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 获取录像策略
	 * @param mHandler
	 * @param mrr
	 * @param baseUrl
	 */
	public static void GetRecordPolicy(Handler mHandler, GetDevParamRequest gpc, String baseUrl)
	{
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(gpc);
		pInfo.setType(gpc.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.GETRECPOLICY_FUNCTION, pInfo, gpc, p2punitily.GETRECPOLICY_REQUEST);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				arrayPolicyList = getPolicyList(soapObject);
				msgMessage.what = MsgEnum.SUCCESS;
				break;
			case 1:
				msgMessage.what = MsgEnum.USERNAME_NULL;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			case 3:
				msgMessage.what = MsgEnum.DEVICE_EXISTS_NULL;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	//录像策略列表
	public static ArrayList<PolicyBean> arrayPolicyList = null;
	
	/**
	 * 获取策略列表
	 * @param soapObject
	 * @return
	 */
	public static ArrayList<PolicyBean> getPolicyList(SoapObject soapObject)
	{
		PolicyBean pBean = null;
		ArrayList<PolicyBean> pbList = new ArrayList<PolicyBean>();
		for (int i = 0; i < soapObject.getPropertyCount(); i++) {
			Object soapChilds = (Object)soapObject.getProperty(i);
			if (soapChilds.toString().indexOf("PolicyID") == -1)
				continue;
			else if(soapChilds.toString().indexOf("DayPolicy") > -1){
				pBean = new PolicyBean();
				pBean.setPolicyID(((SoapObject) soapChilds).getProperty("PolicyID").toString());
				//获取子节点下的子节点值
				Object soapChildss = ((SoapObject) soapChilds).getProperty(1);
				pBean.setStartTime(((SoapObject) soapChildss).getProperty("StartTime").toString());
				pBean.setEndTime(((SoapObject) soapChildss).getProperty("EndTime").toString());
				pBean.setWeek(0);
				pBean.setRecordFlag(Integer.parseInt(((SoapObject) soapChildss).getProperty("RecordFlag").toString()));
				pBean.setStreamType(Integer.parseInt(((SoapObject) soapChildss).getProperty("StreamType").toString()));
				pbList.add(pBean);
			}
			else if(soapChilds.toString().indexOf("WeekPolicy") > -1){
				pBean = new PolicyBean();
				pBean.setPolicyID(((SoapObject) soapChilds).getProperty("PolicyID").toString());
				//获取子节点下的子节点值
				Object soapChildss = ((SoapObject) soapChilds).getProperty(1);
				pBean.setStartTime(((SoapObject) soapChildss).getProperty("StartTime").toString());
				pBean.setEndTime(((SoapObject) soapChildss).getProperty("EndTime").toString());
				pBean.setWeek(Integer.parseInt(((SoapObject) soapChildss).getProperty("Week").toString()));
				pBean.setRecordFlag(Integer.parseInt(((SoapObject) soapChildss).getProperty("RecordFlag").toString()));
				pBean.setStreamType(Integer.parseInt(((SoapObject) soapChildss).getProperty("StreamType").toString()));
				pbList.add(pBean);
			}
		}
		return pbList;
	}
	
	/**
	 * TODO 删除录像策略
	 * @param mHandler
	 * @param dpr
	 * @param baseUrl
	 */
	public static void DetRecordPolicy(Handler mHandler, DelRecordPolicyRequest dpr, String baseUrl)
	{
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(dpr);
		pInfo.setType(dpr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.DELRECPOLICY_FUNCTION, pInfo, dpr, p2punitily.DELRECPOLICY_REQUEST);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				msgMessage.what = MsgEnum.OK;
				break;
			case 1:
				msgMessage.what = MsgEnum.USERNAME_NULL;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			case 3:
				msgMessage.what = MsgEnum.DEVICE_EXISTS_NULL;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	
	/**
	 * TODO 添加录像策略
	 * @param mHandler
	 * @param apr
	 * @param baseUrl
	 */
	public static void AddRecordPolicy(Handler mHandler, AddRecordPolicyRequest apr, String baseUrl)
	{
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(apr);
		pInfo.setType(apr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.ADDRECPOLICY_FUNCTION, pInfo, apr, p2punitily.ADDRECPOLICY_REQUEST);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				msgMessage.what = MsgEnum.OK;
				break;
			case 1:
				msgMessage.what = MsgEnum.USERNAME_NULL;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			case 3:
				msgMessage.what = MsgEnum.DEVICE_EXISTS_NULL;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 云台控制
	 * @param mHandler
	 * @param cpr
	 * @param baseUrl
	 */
	public static void controlPTZ(Handler mHandler, ControlPtzRequest cpr, String baseUrl)
	{
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(cpr);
		pInfo.setType(cpr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.ControlPtz_FUNCTION, pInfo, cpr, p2punitily.ControlPtz_REQUEST);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				msgMessage.what = MsgEnum.MOVE_SUCCESS;
				break;
			case 1:
				msgMessage.what = MsgEnum.USERNAME_NULL;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			case 3:
				msgMessage.what = MsgEnum.DEVICE_EXISTS_NULL;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 开始/停止视频录像
	 * @param mHandler
	 * @param srr
	 * @param baseUrl
	 * @param funcName
	 */
	public static void RecoredVideo(Handler mHandler, StartRecordRequest srr, String baseUrl, String funcName)
	{
		String functionName = null;
		String functionRequest = null;
		int returnSuccess = 0;
		if(funcName.equals("StartRecord"))
		{
			returnSuccess = MsgEnum.STARTREC_SUCCESS;
			functionName = p2punitily.STARTREC_FUNCTION;
			functionRequest = p2punitily.STARTREC_REQUEST;
		}
		else {
			returnSuccess = MsgEnum.STOPREC_SUCCESS;
			functionName = p2punitily.STOPREC_FUNCTION;
			functionRequest = p2punitily.STOPREC_REQUEST;
		}
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(srr);
		pInfo.setType(srr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, functionName, pInfo, srr, functionRequest);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				msgMessage.what = returnSuccess;
				break;
			case 1:
				msgMessage.what = MsgEnum.USERNAME_NULL;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			case 3:
				msgMessage.what = MsgEnum.DEVICE_EXISTS_NULL;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 查询系统日志
	 * @param mHandler
	 * @param glr
	 * @param baseUrl
	 */
	public static void getSysLog(Handler mHandler, GetSysLogRequest glr, String baseUrl)
	{
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(glr);
		pInfo.setType(glr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.GETSYSLOG_FUNCTION, pInfo, glr, p2punitily.GETSYSLOG_REQUEST);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				arraySysLogList = getSysLogList(soapObject);
				msgMessage.what = MsgEnum.SUCCESS;
				break;
			case 1:
				msgMessage.what = MsgEnum.USERNAME_NULL;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	//系统日志列表
	public static ArrayList<LogDescBean> arraySysLogList = null;
	
	/**
	 * 获取系统日志列表
	 * @param soapObject
	 * @return
	 */
	public static ArrayList<LogDescBean> getSysLogList(SoapObject soapObject)
	{
		LogDescBean lBean = null;
		ArrayList<LogDescBean> lbList = new ArrayList<LogDescBean>();
		for (int i = 0; i < soapObject.getPropertyCount(); i++) {
			Object soapChilds = (Object)soapObject.getProperty(i);
			if (soapChilds.toString().indexOf("DevID") == -1)
				continue;
			else{
				lBean = new LogDescBean();
				lBean.setDevID(((SoapObject) soapChilds).getProperty("DevID").toString());
				lBean.setChannelNo(Integer.parseInt(((SoapObject) soapChilds).getProperty("ChannelNo").toString()));
				lBean.setName(((SoapObject) soapChilds).getProperty("Name").toString());
				lBean.setLogType(Integer.parseInt(((SoapObject) soapChilds).getProperty("LogType").toString()));
				lBean.setLogDesc(((SoapObject) soapChilds).getProperty("LogDesc").toString());
				lBean.setAddTime(((SoapObject) soapChilds).getProperty("AddTime").toString());
				lbList.add(lBean);
			}
		}
		return lbList;
	}
	
	/**
	 * TODO 远程升级和版本查询
	 * @param mHandler
	 * @param glr
	 * @param baseUrl
	 * @param funcName 
	 */
	public static void NotifyUpgrade(Handler mHandler, GetSysLogRequest glr, String baseUrl, String funcName)
	{
		String functionName = null;
		String functionRequest = null;
		if(funcName.equals("NotifyUpgrade"))
		{
			functionName = p2punitily.UPGRADE_FUNCTION;
			functionRequest = p2punitily.UPGRADE_REQUEST;
		}
		else {
			functionName = p2punitily.GETDEVESION_FUNCTION;
			functionRequest = p2punitily.GETDEVESION_REQUEST;
		}
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(glr);
		pInfo.setType(glr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, functionName, pInfo, glr, functionRequest);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				msgMessage.what = MsgEnum.SUCCESS;
				break;
			case 1:
				msgMessage.what = MsgEnum.USERNAME_NULL;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 布防状态查询/设置
	 * @param mHandler
	 * @param cprt
	 * @param baseUrl
	 * @param funcName
	 */
	public static void AlarmStatus(Handler mHandler, ControlPtzRequest cprt, String baseUrl, String funcName)
	{
		String functionName = null;
		String functionRequest = null;
		if(funcName.equals("get"))
		{
			functionName = p2punitily.GETALARM_FUNCTION;
			functionRequest = p2punitily.GETALARM_REQUEST;
		}
		else {
			functionName = p2punitily.SETALARM_FUNCTION;
			functionRequest = p2punitily.SETALARM_REQUEST;
		}
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(cprt);
		pInfo.setType(cprt.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, functionName, pInfo, cprt, functionRequest);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				if(funcName.equals("get"))
				{
					String ALarmSwitch = ((Object) soapObject.getProperty("AlarmSwitch")).toString();
					Bundle bundle = new Bundle();
					bundle.putString("ALarmSwitch", ALarmSwitch);
					msgMessage.setData(bundle);
					msgMessage.what = MsgEnum.OK;
				}
				else {
					msgMessage.what = MsgEnum.SUCCESS;
				}
				break;
			case 1:
				msgMessage.what = MsgEnum.USERNAME_NULL;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			case 3:
				msgMessage.what = MsgEnum.DEVICE_EXISTS_NULL;
				break;
			default:
				if (funcName.equals("get")) {
					msgMessage.what = MsgEnum.GETALARMSTATUS_FAILE;
				} else {
					msgMessage.what = MsgEnum.SETALARMSTATUS_FAILE;
				}
				break;
			}
		}
		else {
			if (funcName.equals("get")) {
				msgMessage.what = MsgEnum.GETALARMSTATUS_FAILE;
			} else {
				msgMessage.what = MsgEnum.SETALARMSTATUS_FAILE;
			}
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 用户手机号码修改和查询/用户密码修改
	 * @param mHandler
	 * @param art
	 * @param baseUrl
	 * @param funcName
	 */
	public static void AccountControl(Handler mHandler, AccountRequest art, String baseUrl, String funcName)
	{
		String strParamResult = null;
		String functionName = null;
		String functionRequest = null;
		if(funcName.equals("get"))
		{
			functionName = p2punitily.GETMOBILE_FUNCTION;
			functionRequest = p2punitily.GETMOBILE_REQUEST;
		}
		else if(funcName.equals("set")) {
			functionName = p2punitily.SETMOBILE_FUNCTION;
			functionRequest = p2punitily.SETMOBILE_REQUEST;
		}
		else {
			functionName = p2punitily.SETUSERKEY_FUNCTION;
			functionRequest = p2punitily.SETUSERKEY_REQUEST;
		}
		
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(art);
		pInfo.setType(art.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, functionName, pInfo, art, functionRequest);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			Log.v("SetUserPhoneNum", strRes);
			switch (Integer.parseInt(strRes)) {
			case 0:
				if(funcName.equals("get"))
				{
					strParamResult = ((Object) soapObject.getProperty("MobileTel")).toString();
					Bundle bundle = new Bundle();
					bundle.putString("ParamResult", strParamResult);
					msgMessage.setData(bundle);
					msgMessage.what = MsgEnum.SUCCESS;
				}
				else if(funcName.equals("set")){
					msgMessage.what = MsgEnum.OK;
				}
				else {
					msgMessage.what = MsgEnum.PASSWORD_MODIFY_OK;
				}
				break;
			case 1:
				msgMessage.what = MsgEnum.ACCOUNT_NOT_EXIST;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			case 10:
				msgMessage.what = MsgEnum.PASSWORD_OLD_REEOR;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	/**
	 * TODO 获取设备录像文件列表
	 * @param mHandler
	 * @param dfr
	 * @param baseUrl
	 */
	public static void getDevRecList(Handler mHandler, DevRecordFileSelectRequest dfr, String baseUrl)
	{
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(dfr);
		pInfo.setType(dfr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.SELECTDEVRECOR_FUNCTION, pInfo, dfr, p2punitily.SELECTDEVRECOR_REQUEST);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				arrayDevRecList = getDevRecList(soapObject);
				msgMessage.what = MsgEnum.SUCCESS;
				break;
			case 1:
				msgMessage.what = MsgEnum.USERNAME_NULL;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			case 3:
				msgMessage.what = MsgEnum.DEVICE_EXISTS_NULL;
				break;
			case 4:
				msgMessage.what = MsgEnum.DEV_IS_OTHERUSER;
				break;
			case 5:
				msgMessage.what = MsgEnum.DEV_NOTONLINE;
				break;
			case 6:
				msgMessage.what = MsgEnum.REQUEST_FAINLD;
				break;
			case 7:
				msgMessage.what = MsgEnum.OPERRATE_TIMEOUT;
				break;
			case 8:
				msgMessage.what = MsgEnum.PARAMETER_INVALID;
				break;
			case 9:
				msgMessage.what = MsgEnum.OPERRATE_FAINLD;
				break;
			case 11:
				msgMessage.what = MsgEnum.ACCOUNT_UNUSABLE;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	//设备录像文件列表
	public static ArrayList<DevRecordFileBean> arrayDevRecList = null;
	
	/**
	 * 获取设备录像文件列表
	 * @param soapObject
	 * @return
	 */
	public static ArrayList<DevRecordFileBean> getDevRecList(SoapObject soapObject)
	{
		DevRecordFileBean drBean = null;
		ArrayList<DevRecordFileBean> drList = new ArrayList<DevRecordFileBean>();
		for (int i = 0; i < soapObject.getPropertyCount(); i++) {
			Object soapChilds = (Object)soapObject.getProperty(i);
			if (soapChilds.toString().indexOf("Name") == -1)
				continue;
			else{
				drBean = new DevRecordFileBean();
				drBean.setName(((SoapObject) soapChilds).getProperty("Name").toString());
				drBean.setRecordType(Integer.parseInt(((SoapObject) soapChilds).getProperty("RecordType").toString()));
				drBean.setBeginTime(((SoapObject) soapChilds).getProperty("BeginTime").toString());
				drBean.setEndTime(((SoapObject) soapChilds).getProperty("EndTime").toString());
				drBean.setFileSize(Long.parseLong(((SoapObject) soapChilds).getProperty("FileSize").toString()));
				drList.add(drBean);
			}
		}
		return drList;
	}
	
	/**
	 * TODO 刷新设备列表
	 * @param mHandler
	 * @param adrt
	 * @param baseUrl
	 * @param dp
	 */
	public static void Ref_DevList(Handler mHandler, MonAddDevRequest adr, String baseUrl, DeviceAdapter dp)
	{
  		PropertyInfo pinfo=new PropertyInfo();
  		pinfo.setName("req");
  		pinfo.setValue(adr);
  		pinfo.setType(adr.getClass());
  		
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.REFRESHDEVLIST_FUNCTION, pinfo, adr, p2punitily.REFRESHDEVLIST_REQUEST);
		Message msg = mHandler.obtainMessage();
		if (soapObject != null) {
			Object obj = (Object) soapObject.getProperty("Result");
			String strRes = obj.toString();
			int intResult = Integer.parseInt(strRes);
			switch (intResult) {
			case 0:
				getDataForSqlite(soapObject, dp);
				msg.what = MsgEnum.SUCCESS;
				break;
			case 1:
				msg.what = MsgEnum.USERNAME_NULL;
				break;
			case 2:
				msg.what = MsgEnum.SESSION_TIMEOUT;
				break;
			case 3:
				msg.what = MsgEnum.DEVICE_EXISTS_NULL;
			default:
				msg.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msg.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msg);
	}
	
	
	/**
	 * TODO 获取手机验证码
	 * @param mHandler
	 * @param gdr
	 * @param baseUrl
	 */
	public static void getAuthCode(Handler mHandler, GetPwdRequest gdr, String baseUrl)
	{
  		PropertyInfo pinfo=new PropertyInfo();
  		pinfo.setName("req");
  		pinfo.setValue(gdr);
  		pinfo.setType(gdr.getClass());
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, p2punitily.GETAUTHCODE_FUNCTION, pinfo, gdr, p2punitily.GETAUTHCODE_REQUEST);
		Message msg = mHandler.obtainMessage();
		if (soapObject != null) {
			Object obj = (Object) soapObject.getProperty("Result");
			String strRes = obj.toString();
			int intResult = Integer.parseInt(strRes);
			switch (intResult) {
			case 0:
				msg.what = MsgEnum.OK;
				String authcode = ((Object) soapObject.getProperty("AuthCode")).toString();
				Bundle bundle = new Bundle();
				bundle.putString("AuthCode", authcode);
				msg.setData(bundle);
				break;
			case 2:
				msg.what = MsgEnum.MAKE_ORDER_ERROR;
				break;
			default:
				msg.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msg.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msg);
	}
	
	/**
	 * TODO 设备分享/取消分享
	 * @param mHandler
	 * @param adr
	 * @param baseUrl
	 * @param funcName
	 */
	public static void ShareDevControl(Handler mHandler, MonAddDevRequest adr, String baseUrl, String funcName)
	{
		String functionName = null;
		String functionRequest = null;
		if(funcName.equals("add"))
		{
			functionName = p2punitily.ADDSHARE_FUNCTION;
			functionRequest = p2punitily.ADDSHARE_REQUEST;
		}
		else {
			functionName = p2punitily.CANCELSHARE_FUNCTION;
			functionRequest = p2punitily.CANCELSHARE_REQUEST;
		}
		
		PropertyInfo pInfo = new PropertyInfo();
		pInfo.setName("req");
		pInfo.setValue(adr);
		pInfo.setType(adr.getClass());
		// 执行远程对象调用方法
		SoapObject soapObject = WSUtils.wsRequestStruct(baseUrl, functionName, pInfo, adr, functionRequest);
		Message msgMessage = mHandler.obtainMessage();
		if (soapObject != null) {
			Object objRes = (Object) soapObject.getProperty("Result");
			String strRes = objRes.toString();
			switch (Integer.parseInt(strRes)) {
			case 0:
				if(funcName.equals("add"))
				{
					msgMessage.what = MsgEnum.ADDSHARE_SUCCESS;
				}
				else {
					msgMessage.what = MsgEnum.CANCELSHARE_SUCCESS;
				}
				break;
			case 1:
				msgMessage.what = MsgEnum.ACCOUNT_NOT_EXIST;
				break;
			case 2:
				msgMessage.what = MsgEnum.SESSION_TIMEOUT;
				break;
			default:
				msgMessage.what = MsgEnum.SERVER_ERROR;
				break;
			}
		}
		else {
			msgMessage.what = MsgEnum.SERVER_ERROR;
		}
		mHandler.sendMessage(msgMessage);
	}
	
	
	
}
