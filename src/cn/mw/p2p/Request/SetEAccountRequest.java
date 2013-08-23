package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 设置云帐户
 * @author zhouwb
 *
 */
public class SetEAccountRequest implements KvmSerializable {

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

	private String EstoreAccount;
	/**
	 * @return the estoreAccount
	 */
	public String getEstoreAccount() {
		return EstoreAccount;
	}

	/**
	 * @param estoreAccount the estoreAccount to set
	 */
	public void setEstoreAccount(String estoreAccount) {
		EstoreAccount = estoreAccount;
	}

	private String EstorePassword;
	
	/**
	 * @return the estorePassword
	 */
	public String getEstorePassword() {
		return EstorePassword;
	}

	/**
	 * @param estorePassword the estorePassword to set
	 */
	public void setEstorePassword(String estorePassword) {
		EstorePassword = estorePassword;
	}

	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
		case 0:
			return Account;
		case 1:
			return LoginSession;
		case 2:
			return EstoreAccount;
		case 3:
			return EstorePassword;
		default:
			break;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 4;
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
			arg2.name = "EstoreAccount";
			break;
		case 3:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "EstorePassword";
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
			EstoreAccount = arg1.toString();
			break;
		case 3:
			EstorePassword = arg1.toString();
			break;
		default:
			break;
		}
		
	}

}
