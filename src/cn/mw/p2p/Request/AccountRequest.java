﻿package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 用户信息
 * @author zhouwb
 *
 */
public class AccountRequest implements KvmSerializable {

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

	private String NewMobileTel;
	/**
	 * @return the newMobileTel
	 */
	public String getNewMobileTel() {
		return NewMobileTel;
	}

	/**
	 * @param newMobileTel the newMobileTel to set
	 */
	public void setNewMobileTel(String newMobileTel) {
		NewMobileTel = newMobileTel;
	}

	private String Password;
	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		Password = password;
	}

	private String NewPassword;

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return NewPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		NewPassword = newPassword;
	}

	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
		case 0:
			return Account;
		case 1:
			return LoginSession;
		case 2:
			return MobileTel;
		case 3:
			return NewMobileTel;
		case 4:
			return Password;
		case 5:
			return NewPassword;
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
			arg2.name = "MobileTel";
			break;
		case 3:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "NewMobileTel";
			break;
		case 4:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "Password";
			break;
		case 5:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "NewPassword";
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
			MobileTel = arg1.toString();
			break;
		case 3:
			NewMobileTel = arg1.toString();
			break;
		case 4:
			Password = arg1.toString();
			break;
		case 5:
			NewPassword = arg1.toString();
			break;
		default:
			break;
		}
		
	}

}
