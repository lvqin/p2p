package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 安全策略
 * @author zhouwb
 *
 */
public class SafeRequest implements KvmSerializable {

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

	private int MaxLineNum;

	/**
	 * @return the maxLineNum
	 */
	public int getMaxLineNum() {
		return MaxLineNum;
	}

	/**
	 * @param maxLineNum the maxLineNum to set
	 */
	public void setMaxLineNum(int maxLineNum) {
		MaxLineNum = maxLineNum;
	}
	
	private String NewDevKey;

	/**
	 * @return the newDevKey
	 */
	public String getNewDevKey() {
		return NewDevKey;
	}

	/**
	 * @param newDevKey the newDevKey to set
	 */
	public void setNewDevKey(String newDevKey) {
		NewDevKey = newDevKey;
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
			return ChannelNo;
		case 5:
			return MaxLineNum;
		case 6:
			return NewDevKey;
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
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "ChannelNo";
			break;
		case 5:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "MaxLineNum";
			break;
		case 6:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "NewDevKey";
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
			DevKey = arg1.toString();
			break;
		case 4:
			ChannelNo =(Integer) arg1;
			break;
		case 5:
			MaxLineNum = (Integer) arg1;
			break;
		case 6:
			NewDevKey = arg1.toString();
			break;
		default:
			break;
		}
		
	}

}
