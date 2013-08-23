package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class PlayURLRequest implements KvmSerializable{

	private String Account;
	private String LoginSession;
	private String DevID;
	private int ChannelNo;
	private int StreamType;
	private String FileName;

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
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return FileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		FileName = fileName;
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
			return DevID;
		case 3:
			return ChannelNo;
		case 4:
			return StreamType;
		case 5:
			return FileName;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		
		return 6;
	}


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
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "FileName";
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
			ChannelNo = Integer.parseInt(arg1.toString());
			break;
		case 4:
			StreamType = Integer.parseInt(arg1.toString());
			break;
		case 5:
			FileName = arg1.toString();
			break;
		default:
			break;
		}

	}
}

