package cn.mw.p2p.Request;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

/**
 * 天策略
 * @author zhouwb
 *
 */
public class DayPolicyAdd implements KvmSerializable {
	private int StreamType;
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
	private int RecordFlag;
	/**
	 * @return the recordFlag
	 */
	public int getRecordFlag() {
		return RecordFlag;
	}
	/**
	 * @param recordFlag the recordFlag to set
	 */
	public void setRecordFlag(int recordFlag) {
		RecordFlag = recordFlag;
	}

	private String StartTime;
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return StartTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	private String EndTime;
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
	
	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
		case 0:
			return StreamType;
		case 1:
			return RecordFlag;
		case 2:
			return StartTime;
		case 3:
			return EndTime;
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
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "StreamType";
			break;
		case 1:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "RecordFlag";
			break;
		case 2:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "StartTime";
			break;
		case 3:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "EndTime";
			break;
		default:
			break;
		}
	}
	@Override
	public void setProperty(int arg0, Object arg1) {
		switch (arg0) {
		case 0:
			StreamType = (Integer) arg1;
			break;
		case 1:
			RecordFlag = (Integer) arg1;
			break;
		case 2:
			StartTime = arg1.toString();
		case 3:
			EndTime = arg1.toString();
			break;
		}
	}
}
