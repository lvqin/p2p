package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 添加设备
 * @author zhouwb
 *
 */
public class MonAddDevRequest implements KvmSerializable {
	
	private String Account;
	private String LoginSession;
	private String DevID;
	private String DevKey;
	private String ChannelName;
	private String Someone;
	private int Privilege;
	
    
	public String getChannelName() {
		return ChannelName;
	}

	public void setChannelName(String channelName) {
		ChannelName = channelName;
	}

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

	public String getDevID() {
		return DevID;
	}

	public void setDevID(String devID) {
		DevID = devID;
	}

	public String getDevKey() {
		return DevKey;
	}

	public void setDevKey(String devKey) {
		DevKey = devKey;
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
			return DevKey;
		case 4:
			return ChannelName;	
		case 5:
			return Someone;
		case 6:
			return Privilege;
		default:
			break;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 7;
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
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "DevKey";
			break;
		case 4:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "ChannelName";
			break;
		case 5:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "Someone";
			break;
		case 6:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "Privilege";
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
			DevID = arg1.toString();
			break;
		case 3:
			DevKey = arg1.toString();
			break;
		case 4:
			ChannelName = arg1.toString();
			break;	
		case 5:
			Someone = arg1.toString();
			break;
		case 6:
			Privilege = Integer.parseInt(arg1.toString());
			break;	
		default:
			break;
		}
		
	}

}
