﻿package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 添加录像策略
 * @author zhouwb
 *
 */
public class AddRecordPolicyRequest implements KvmSerializable {
	
	private String Account;
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

	private String LoginSession;
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

	private String DevID;
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

	private int ChannelNo;
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

	private DayPolicyAdd DayPolicyList;


	/**
	 * @return the dayPolicyList
	 */
	public DayPolicyAdd getDayPolicyList() {
		return DayPolicyList;
	}

	/**
	 * @param dayPolicyList the dayPolicyList to set
	 */
	public void setDayPolicyList(DayPolicyAdd dayPolicyList) {
		DayPolicyList = dayPolicyList;
	}

	private WeekPolicyAdd WeekPolicyList;



	/**
	 * @return the weekPolicyList
	 */
	public WeekPolicyAdd getWeekPolicyList() {
		return WeekPolicyList;
	}

	/**
	 * @param weekPolicyList the weekPolicyList to set
	 */
	public void setWeekPolicyList(WeekPolicyAdd weekPolicyList) {
		WeekPolicyList = weekPolicyList;
	}

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
			return DayPolicyList;
		case 5:
			return WeekPolicyList;
		default:
			break;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 6;
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
			arg2.type = PropertyInfo.OBJECT_CLASS;
			arg2.name = "DayPolicyAdd";
			break;
		case 5:
			arg2.type = PropertyInfo.OBJECT_CLASS;
			arg2.name = "WeekPolicyAdd";
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
			DayPolicyList = (DayPolicyAdd) arg1;
			break;
		case 5:
			WeekPolicyList = (WeekPolicyAdd) arg1;
			break;
		default:
			break;
		}
		
	}

}
