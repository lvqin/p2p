package cn.mw.p2p.adpter;

import cn.mw.p2p.bean.VedioPointBean;
import cn.mw.p2p.unitily.MsgEnum;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 创建本地设备列表数据库
 * @author zhouwb
 *
 */
public class DeviceAdapter {
	
	private String tableName = "mw_Device";//数据表名称
	//设置数据表列名
	public static final String ID = "_id";
	public static final String DEVID = "DevID";
	public static final String CHANNELNO = "ChannelNo";
	public static final String DEVICENAME = "name";
	public static final String PTZFLAG = "PtzFlag";
	public static final String URL = "URL";
	public static final String ONLINE = "online";
	public static final String SHAREFLAG = "ShareFlag";
	public static final String GROUPID = "Groupid";
	
	private SQLiteDatabase sdb ;
	private DataBaseUtil dbaseUtil;
	
	public DeviceAdapter(Context context)
	{
		String[] cols = new String[] { 
				DEVID, 
				CHANNELNO, 
				DEVICENAME, 
				PTZFLAG,
				URL, 
				ONLINE,
				SHAREFLAG,
				GROUPID };
		String[] cons = new String[] { 
				MsgEnum.TEXT_NOT_NULL,
				MsgEnum.TEXT_NOT_NULL, 
				MsgEnum.TEXT_NOT_NULL,
				MsgEnum.TEXT_NOT_NULL, 
				MsgEnum.TEXT_NOT_NULL,
				MsgEnum.TEXT_NOT_NULL,
				MsgEnum.TEXT_NOT_NULL,
				MsgEnum.TEXT_NOT_NULL};
		dbaseUtil = new DataBaseUtil(context, tableName, cols, cons);
		sdb = dbaseUtil.openWriteDB();
	}
	
	/**打开数据库**/
	public void openDB(){
		sdb = dbaseUtil.openWriteDB();
	}
	
	/**
	 * 插入数据
	 * @param vpb 数据对象
	 * @return
	 */
	public long Insert(VedioPointBean vpb)
	{	
		ContentValues values = new ContentValues();
		values.put(DEVID, vpb.getDevID());
		values.put(CHANNELNO, vpb.getChannelNo());
		values.put(DEVICENAME, vpb.getName());
		values.put(PTZFLAG, vpb.getPtzFlag());
		values.put(URL, vpb.getURL());
		values.put(ONLINE, vpb.isOnline());
		values.put(SHAREFLAG, vpb.getShareFlag());
		values.put(GROUPID, vpb.getGroupid());

		long tag = sdb.insert(tableName, null, values);
		return tag;
		
	}
	
	/**
	 * 删除设备
	 * @return
	 */
	public int Delete(String devid) {
		openDB();
		String[] args = { String.valueOf(devid) };
		return sdb.delete(tableName, "DEVID=?", args);
	}
	
	/**
	 * 删除设备
	 * @return
	 */
	public int Delete() {
		openDB();
		return sdb.delete(tableName, null, null);
	}
	
	/**
	 * 查询数据
	 */
	public Cursor Query(){
		String[] cols = new String[] { 
				ID, 
				DEVID, 
				CHANNELNO, 
				DEVICENAME,
				PTZFLAG, 
				URL, 
				ONLINE,
				SHAREFLAG,
				GROUPID };
		Cursor cur = sdb.query(tableName, cols,null, null, null, null, "_id DESC");
		return cur;
	}
	
	/**
	 * 关闭数据库
	 */
	public void closeDB(Cursor cursor) {
		if(null != cursor && !cursor.isClosed())
			cursor.close();
		closeDB();
	}
	
	/**
	 * 关闭数据库
	 */
	public void closeDB() {
		if(null != sdb && sdb.isOpen())
			sdb.close();
	}
}
