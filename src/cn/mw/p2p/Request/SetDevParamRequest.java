﻿package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 设置设备参数
 * @author zhouwb
 *
 */
public class SetDevParamRequest implements KvmSerializable {

	private String Account;
	private String LoginSession;
	private String DevID;
	private int ChannelNo;
	private int StreamType;
	private int FrameSize;
	private int FrameRate;
	private int RateType;
	private int BitRate;
	private int ImageQuality;
	private int AudioInput;
	private String ChannelName;
	
	

	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
		case 0:
			return Account;
		case 1:
			return LoginSession;
		case 2:
			return DevID;
		case 3:
			return ChannelNo;
		case 4:
			return StreamType;
		case 5:
			return FrameSize;
		case 6:
			return FrameRate;
		case 7:
			return RateType;
		case 8:
			return BitRate;
		case 9:
			return ImageQuality;
		case 10:
			return AudioInput;
		case 11:
			return ChannelName;
		default:
			break;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 12;
	}

	@Override
	public void getPropertyInfo(int arg0, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo arg2) {
		switch (arg0) {
		case 0:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "Account";
			break;
		case 1:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "LoginSession";
			break;
		case 2:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "DevID";
			break;
		case 3:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "ChannelNo";
			break;
		case 4:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "StreamType";
			break;
		case 5:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "FrameSize";
			break;
		case 6:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "FrameRate";
			break;
		case 7:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "RateType";
			break;
		case 8:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "BitRate";
			break;
		case 9:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "ImageQuality";
			break;
		case 10:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "AudioInput";
			break;
		case 11:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "ChannelName";
			break;
		default:
			break;
		}
		
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch (arg0) {
		case 0:
			Account = arg1.toString();
			break;
		case 1:
			LoginSession = arg1.toString();
			break;
		case 2:
			DevID =arg1.toString();
			break;
		case 3:
			ChannelNo = (Integer) arg1;
			break;
		case 4:
			StreamType = (Integer) arg1;
			break;
		case 5:
			FrameSize = (Integer) arg1;
			break;
		case 6:
			FrameRate = (Integer) arg1;
			break;
		case 7:
			RateType = (Integer) arg1;
			break;
		case 8:
			BitRate = (Integer) arg1;
			break;
		case 9:
			ImageQuality = (Integer) arg1;
			break;
		case 10:
			AudioInput = (Integer) arg1;
			break;
		case 11:
			ChannelName =arg1.toString();
			break;
		default:
			break;
		}
		
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return Account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		Account = account;
	}

	/**
	 * @return the loginSession
	 */
	public String getLoginSession() {
		return LoginSession;
	}

	/**
	 * @param loginSession the loginSession to set
	 */
	public void setLoginSession(String loginSession) {
		LoginSession = loginSession;
	}

	/**
	 * @return the devID
	 */
	public String getDevID() {
		return DevID;
	}

	/**
	 * @param devID the devID to set
	 */
	public void setDevID(String devID) {
		DevID = devID;
	}

	/**
	 * @return the channelNo
	 */
	public int getChannelNo() {
		return ChannelNo;
	}

	/**
	 * @param channelNo the channelNo to set
	 */
	public void setChannelNo(int channelNo) {
		ChannelNo = channelNo;
	}

	/**
	 * @return the streamType
	 */
	public int getStreamType() {
		return StreamType;
	}

	/**
	 * @param streamType the streamType to set
	 */
	public void setStreamType(int streamType) {
		StreamType = streamType;
	}
	
	public int getFrameSize() {
		return FrameSize;
	}

	public void setFrameSize(int frameSize) {
		FrameSize = frameSize;
	}

	public int getFrameRate() {
		return FrameRate;
	}

	public void setFrameRate(int frameRate) {
		FrameRate = frameRate;
	}

	
	public int getRateType() {
		return RateType;
	}

	public void setRateType(int rateType) {
		RateType = rateType;
	}

	
	public int getBitRate() {
		return BitRate;
	}

	public void setBitRate(int bitRate) {
		BitRate = bitRate;
	}

	
	public int getImageQuality() {
		return ImageQuality;
	}

	public void setImageQuality(int imageQuality) {
		ImageQuality = imageQuality;
	}

	public int getAudioInput() {
		return AudioInput;
	}

	public void setAudioInput(int audioInput) {
		AudioInput = audioInput;
	}

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return ChannelName;
	}

	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		ChannelName = channelName;
	}
}
