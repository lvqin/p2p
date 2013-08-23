package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class MonGetAuthCodeRequest implements KvmSerializable {
	
	private String MobileTel;
	
	

	public String getMobileTel() {
		return MobileTel;
	}

	public void setMobileTel(String mobileTel) {
		MobileTel = mobileTel;
	}

	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
		case 0:
			return MobileTel;
		
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
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "MobileTel";
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
			MobileTel = arg1.toString();
			break;
		default:
			break;
		}

	}

}
