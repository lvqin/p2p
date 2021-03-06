﻿package cn.mw.p2p.bean;

public class VedioPointBean
{
  private String ChannelNo;
  private String DevID;
  private int PtzFlag;
  private String URL;
  public CameraParametersQuary mCameraParametersSelect;
  private String name;
  private boolean online = false;
  private int ShareFlag;	//是否分享
  private int Groupid;		//分组ID

  public String getChannelNo()
  {
    return this.ChannelNo;
  }

  public String getDevID()
  {
    return this.DevID;
  }

  public String getName()
  {
    return this.name;
  }

  public int getPtzFlag()
  {
    return this.PtzFlag;
  }

  public String getURL()
  {
    return this.URL;
  }

  public boolean isOnline()
  {
    return this.online;
  }

  public void setChannelNo(String paramString)
  {
    this.ChannelNo = paramString;
  }

  public void setDevID(String paramString)
  {
    this.DevID = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setOnline(boolean paramBoolean)
  {
    this.online = paramBoolean;
  }

  public void setPtzFlag(int paramInt)
  {
    this.PtzFlag = paramInt;
  }

  public void setURL(String paramString)
  {
    this.URL = paramString;
  }

  /**
 * @return the shareFlag
 */
public int getShareFlag() {
	return ShareFlag;
}

/**
 * @param shareFlag the shareFlag to set
 */
public void setShareFlag(int shareFlag) {
	ShareFlag = shareFlag;
}

/**
 * @return the groupid
 */
public int getGroupid() {
	return Groupid;
}

/**
 * @param groupid the groupid to set
 */
public void setGroupid(int groupid) {
	Groupid = groupid;
}

public String toString()
  {
    return "VedioPointBean [DevID=" + this.DevID + ", name=" + this.name + ", ChannelNo=" + this.ChannelNo + ", online=" + this.online + ", PtzFlag=" + this.PtzFlag + ", URL=" + this.URL + ", ShareFlag=" + this.ShareFlag + ", Groupid=" + this.Groupid + "]";
  }
}
