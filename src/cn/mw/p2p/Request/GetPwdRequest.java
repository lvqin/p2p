﻿package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 动态获取密码
 * @author zhouwb
 *
 */
public class GetPwdRequest implements KvmSerializable {

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

	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
		case 0:
			return Account;
		case 1:
			return PwdType;
		case 2:
			return MobileTel;
		default:
			break;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 3;
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
			MobileTel = arg1.toString();
			break;
		default:
			break;
		}
		
	}

}
