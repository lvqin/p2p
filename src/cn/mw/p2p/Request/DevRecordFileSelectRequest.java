package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 设备录像文件查询
 * @author zhouwb
 *
 */
public class DevRecordFileSelectRequest implements KvmSerializable {

	private String Account;
	private String LoginSession;
	private String DevID;
	private int ChannelNo;
	private int RecordType;
	private String BeginTime;
	private String EndTime;
	private int BeginInex;
	private int RecordNum;
	
	

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
			return RecordType;
		case 5:
			return BeginTime;
		case 6:
			return EndTime;
		case 7:
			return BeginInex;
		case 8:
			return RecordNum;
		default:
			break;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 9;
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
			arg2.name = "RecordType";
			break;
		case 5:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "BeginTime";
			break;
		case 6:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "EndTime";
			break;
		case 7:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "BeginInex";
			break;
		case 8:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "RecordNum";
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
			RecordType = (Integer) arg1;
			break;
		case 5:
			BeginTime = arg1.toString();
			break;
		case 6:
			EndTime = arg1.toString();
			break;
		case 7:
			BeginInex = (Integer) arg1;
			break;
		case 8:
			RecordNum = (Integer) arg1;
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
	 * @return the recordType
	 */
	public int getRecordType() {
		return RecordType;
	}

	/**
	 * @param recordType the recordType to set
	 */
	public void setRecordType(int recordType) {
		RecordType = recordType;
	}

	/**
	 * @return the beginTime
	 */
	public String getBeginTime() {
		return BeginTime;
	}

	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(String beginTime) {
		BeginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return EndTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	/**
	 * @return the beginInex
	 */
	public int getBeginInex() {
		return BeginInex;
	}

	/**
	 * @param beginInex the beginInex to set
	 */
	public void setBeginInex(int beginInex) {
		BeginInex = beginInex;
	}

	/**
	 * @return the recordNum
	 */
	public int getRecordNum() {
		return RecordNum;
	}

	/**
	 * @param recordNum the recordNum to set
	 */
	public void setRecordNum(int recordNum) {
		RecordNum = recordNum;
	}

	
}
