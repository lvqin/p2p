package cn.mw.p2p.bean;

/**
 * 设备录像文件对象
 * @author zhouwb
 *
 */
public class DevRecordFileBean {

	private String Name;
	private int RecordType;
	private String BeginTime;
	private String EndTime;
	private long FileSize;
	
	
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
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
	 * @return the fileSize
	 */
	public long getFileSize() {
		return FileSize;
	}
	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		FileSize = fileSize;
	}
}
