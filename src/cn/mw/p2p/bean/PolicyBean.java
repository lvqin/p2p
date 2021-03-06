﻿package cn.mw.p2p.bean;

/**
 * 录像策略
 * @author zhouwb
 *
 */
public class PolicyBean {
	
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
	private int Week;
	/**
	 * @return the week
	 */
	public int getWeek() {
		return Week;
	}
	/**
	 * @param week the week to set
	 */
	public void setWeek(int week) {
		Week = week;
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
	
	private String PolicyID;
	/**
	 * @return the policyID
	 */
	public String getPolicyID() {
		return PolicyID;
	}
	/**
	 * @param policyID the policyID to set
	 */
	public void setPolicyID(String policyID) {
		PolicyID = policyID;
	}
	

}
