package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class MonAddShareDevRequest implements KvmSerializable {
	
	private String Account;
	private String LoginSession;
	private String DevID;
	private String Someone;
	private String Begintime;
	private String Endtime;
	private int Privilege;

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
	 * @return the someone
	 */
	public String getSomeone() {
		return Someone;
	}

	/**
	 * @param someone the someone to set
	 */
	public void setSomeone(String someone) {
		Someone = someone;
	}

	/**
	 * @return the begintime
	 */
	public String getBegintime() {
		return Begintime;
	}

	/**
	 * @param begintime the begintime to set
	 */
	public void setBegintime(String begintime) {
		Begintime = begintime;
	}

	/**
	 * @return the endtime
	 */
	public String getEndtime() {
		return Endtime;
	}

	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(String endtime) {
		Endtime = endtime;
	}

	/**
	 * @return the privilege
	 */
	public int getPrivilege() {
		return Privilege;
	}

	/**
	 * @param privilege the privilege to set
	 */
	public void setPrivilege(int privilege) {
		Privilege = privilege;
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
			return Someone;
		case 4:
			return Begintime;
		case 5:
			return Endtime;
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
			arg2.name = "Someone";
			break;
		case 4:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "Begintime";
			break;
		case 5:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "Endtime";
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
			Someone = arg1.toString();
			break;
		case 4:
			Begintime = arg1.toString();
			break;
		case 5:
			Endtime = arg1.toString();
			break;
		case 6:
			Privilege = Integer.parseInt(arg1.toString());
		default:
			break;
		}
	}

}
