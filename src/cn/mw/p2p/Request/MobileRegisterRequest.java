﻿package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 用户注册
 * @author zhouwb
 *
 */
public class MobileRegisterRequest implements KvmSerializable {

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
	
	private String MobileTel;
	
	/**
	 * @return the mobileTel
	 */
	public String getMobileTel() {
		return MobileTel;
	}
	/**
	 * @param mobileTel the mobileTel to set
	 */
	public void setMobileTel(String mobileTel) {
		MobileTel = mobileTel;
	}

	private int PwdType;
	/**
	 * @return the pwdType
	 */
	public int getPwdType() {
		return PwdType;
	}
	/**
	 * @param pwdType the pwdType to set
	 */
	public void setPwdType(int pwdType) {
		PwdType = pwdType;
	}
	private String TelPassword;
	/**
	 * @return the telPassword
	 */
	public String getTelPassword() {
		return TelPassword;
	}
	/**
	 * @param telPassword the telPassword to set
	 */
	public void setTelPassword(String telPassword) {
		TelPassword = telPassword;
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
	private String DevKey;
	
	/**
	 * @return the devKey
	 */
	public String getDevKey() {
		return DevKey;
	}
	/**
	 * @param devKey the devKey to set
	 */
	public void setDevKey(String devKey) {
		DevKey = devKey;
	}
	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
		case 0:
			return Account;
		case 1:
			return PwdType;
		case 2:
			return TelPassword;
		case 3:
			return DevID;
		case 4:
			return DevKey;
		case 5:
			return MobileTel;
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
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "PwdType";
			break;
		case 2:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "TelPassword";
			break;
		case 3:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "DevID";
			break;
		case 4:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "DevKey";
			break;
		case 5:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "MobileTel";
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
			PwdType = (Integer) arg1;
			break;
		case 2:
			TelPassword =arg1.toString();
			break;
		case 3:
			DevID = arg1.toString();
			break;
		case 4:
			DevKey = arg1.toString();
			break;
		case 5:
			MobileTel = arg1.toString();
			break;
		default:
			break;
		}
		
	}
	
}
