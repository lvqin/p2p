package cn.mw.p2p.adpter;

import cn.mw.p2p.bean.PolicyBean;
import cn.mw.p2p.unitily.MsgEnum;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 创建本地录像策略列表数据库
 * @author zhouwb
 *
 */
public class RecPolicyAdapter {
	
	private String tableName = "mw_RecPolicy";//数据表名称
	//设置数据表列名
	public static final String ID = "_id";
	public static final String POLICYID = "PolicyID";
	public static final String STARTTIME = "StartTime";
	public static final String ENDTIME = "EndTime";
	public static final String WEEK = "Week";
	public static final String RECORDFLAG = "RecordFlag";
	public static final String STREAMTYPE = "StreamType";
	
	private SQLiteDatabase sdb ;
	private DataBaseUtil dbaseUtil;
	
	public RecPolicyAdapter(Context context)
	{
		String[] cols = new String[] { 
				POLICYID, 
				STARTTIME, 
				ENDTIME, 
				WEEK,
				RECORDFLAG, 
				STREAMTYPE };
		String[] cons = new String[] { 
				MsgEnum.TEXT_NOT_NULL,
				MsgEnum.TEXT_NOT_NULL, 
				MsgEnum.TEXT_NOT_NULL,
				MsgEnum.TEXT_NOT_NULL, 
				MsgEnum.TEXT_NOT_NULL,
				MsgEnum.TEXT_NOT_NULL };
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
	public long Insert(PolicyBean pb)
	{
		ContentValues values = new ContentValues();
		values.put(POLICYID, pb.getPolicyID());
		values.put(STARTTIME, pb.getStartTime());
		values.put(ENDTIME, pb.getEndTime());
		values.put(WEEK, pb.getWeek());
		values.put(RECORDFLAG, pb.getRecordFlag());
		values.put(STREAMTYPE, pb.getStreamType());

		long tag = sdb.insert(tableName, null, values);
		return tag;
		
	}
	
	/**
	 * 删除设备
	 * @return
	 */
	public int Delete(String POLICYID) {
		openDB();
		String[] args = { String.valueOf(POLICYID) };
		return sdb.delete(tableName, "POLICYID=?", args);
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
				POLICYID, 
				STARTTIME, 
				ENDTIME, 
				WEEK,
				RECORDFLAG, 
				STREAMTYPE };
		Cursor cur = sdb.query(tableName, cols, null, null, null, null, "_id DESC");
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
