package cn.mw.p2p.unitily;

import android.content.SharedPreferences;

public class P2pBaseUrl {
	
	/**
	 * 服务器IP
	 */
	public static String SERVER_IP = "192.168.1.58";

	/**
	 * 服务器端口
	 */
	public static String SERVER_PORT = "8587";
	
	/**
	 * 基础URL
	 */
	public static String BASE_URL = "http://" + SERVER_IP + ":" + SERVER_PORT +"/";
	
	public static String DIR_ROOT = "智眸监控";
	
	public static String DIR_ROOT_IMAGE = "我的图片";
	
	public static String DIR_ROOT_VIDEO = "我的视频";
	
	/**
	 * 获取基本URL
	 * @param sp
	 * @return
	 */
	public static String BaseUrl(SharedPreferences sp)
	{
		SERVER_IP = sp.getString("serverIP", SERVER_IP);
		SERVER_PORT = sp.getString("serverPort", SERVER_PORT);
		BASE_URL = "http://" + SERVER_IP + ":" + SERVER_PORT +"/";
		return BASE_URL;
	}

}
