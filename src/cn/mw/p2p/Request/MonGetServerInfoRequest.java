package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class MonGetServerInfoRequest implements KvmSerializable {
	
	private int ReqType;
	

	public int getReqType() {
		return ReqType;
	}

	public void setReqType(int reqType) {
		ReqType = reqType;
	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			return ReqType;
		
		default:
			break;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		switch (arg0) {
		case 0:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "ReqType";
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
			ReqType = (Integer) arg1;
			break;
		default:
			break;

	   }

	}

}
