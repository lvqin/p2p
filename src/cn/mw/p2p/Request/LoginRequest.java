package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class LoginRequest implements KvmSerializable {

	private String Account;
	private int PwdType;
	private String TelPassword;
	private String Version;

	public String getAccount() {
		return Account;
	}

	public void setAccount(String Account) {
		this.Account = Account;
	}

	public int getPwdType() {
		return PwdType;
	}

	public void setPwdType(int PwdType) {
		this.PwdType = PwdType;
	}
	
	public String getTelPassword() {
		return TelPassword;
	}

	public void setTelPassword(String TelPassword) {
		this.TelPassword = TelPassword;
	}
	
	public String getVersion() {
		return Version;
	}

	public void setVersion(String Version) {
		this.Version = Version;
	}
	
	

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			return Account;
		case 1:
			return PwdType;
		case 2:
			return TelPassword;
		case 3:
			return Version;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 4;
	}


	public void getPropertyInfo(int arg0, @SuppressWarnings("rawtypes") Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
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
			arg2.name = "Version";
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
			Version = arg1.toString();
			break;
		default:
			break;
		}

	}

}
