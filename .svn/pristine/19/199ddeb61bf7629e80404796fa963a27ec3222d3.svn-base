package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class MonSetMobileRequest implements KvmSerializable {
	
	private String Account;
	private String LoginSession;
	private String MobileTel;
	private String NewMobileTel;
	
	
	

	public String getAccount() {
		return Account;
	}

	public void setAccount(String account) {
		Account = account;
	}

	public String getLoginSession() {
		return LoginSession;
	}

	public void setLoginSession(String loginSession) {
		LoginSession = loginSession;
	}

	public String getMobileTel() {
		return MobileTel;
	}

	public void setMobileTel(String mobileTel) {
		MobileTel = mobileTel;
	}

	public String getNewMobileTel() {
		return NewMobileTel;
	}

	public void setNewMobileTel(String newMobileTel) {
		NewMobileTel = newMobileTel;
	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			return Account;
		case 1:
			return LoginSession;
		case 2:
			return MobileTel;
		case 3:
			return NewMobileTel;
		
		default:
			break;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
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
		
		default:
			break;
		}

	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
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
		default:
			break;

	   }

	}

}
