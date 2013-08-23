package cn.mw.p2p.unitily;

import java.util.Calendar;

import com.montnets.android.zmon.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;

public class P2pSetServer extends Activity {
	
	//定义5个记录当前时间的变量
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private String strCheckDateTime;

	//系统设置
	public void SetServerInfo(final Context context,final SharedPreferences sp)
	{
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.set_server,	null);
		final EditText edtIP = (EditText) textEntryView.findViewById(R.id.edtsvrIP);
		final EditText edtprot = (EditText)textEntryView.findViewById(R.id.edtprot);
		String ip = sp.getString("serverIP", P2pBaseUrl.SERVER_IP);
		edtIP.setText(ip);
		edtprot.setText(sp.getString("serverPort", P2pBaseUrl.SERVER_PORT));
		AlertDialog msgdialog = new AlertDialog.Builder(context)
				.setTitle("设置服务器")
				.setView(textEntryView)
				.setPositiveButton("确定",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int whichButton) {
								String ip = edtIP.getText().toString().trim();
								String iport = edtprot.getText().toString().trim();
								sp.edit()
								.putString("serverIP", ip)
								.putString("serverPort",iport)
								.commit();
								Toast.makeText(context, "设置成功",	Toast.LENGTH_SHORT).show();
							}
						})
				.setNegativeButton("取消",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int whichButton) {
							}
						}).create();
		msgdialog.show();
	}
	
	/**
	 * 设置日期
	 * @param context
	 */
	public void SetDate(final Context context)
	{
		LayoutInflater factory = LayoutInflater.from(context);
		final View textEntryView = factory.inflate(R.layout.choosedate_layout,	null);
		DatePicker datePicker = (DatePicker) textEntryView.findViewById(R.id.datePicker);
		TimePicker timePicker = (TimePicker) textEntryView.findViewById(R.id.timePicker);
		// 获取当前的年、月、日、小时、分钟
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR);
		minute = c.get(Calendar.MINUTE);
		//初始化DatePicker组件，初始化时指定监听器
		datePicker.init(year, month, day, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker arg0, int year, int month, int day) {
				P2pSetServer.this.year = year;
				P2pSetServer.this.month = month;
				P2pSetServer.this.day = day;
				// 显示当前日期、时间
				showDate(year, month, day, hour, minute);
			}
		});
		//为TimePicker指定监听器
		timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker arg0, int hour, int minute) {
				P2pSetServer.this.hour = hour;
				P2pSetServer.this.minute = minute;
				// 显示当前日期、时间
				showDate(year, month, day, hour, minute);
			}
		});
		AlertDialog msgdialog = new AlertDialog.Builder(context)
				.setTitle("设置服务器")
				.setView(textEntryView)
				.setPositiveButton("确定",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,	int whichButton) {
								Intent intent = new Intent();
								intent.putExtra("result", strCheckDateTime);
								P2pSetServer.this.setResult(1, intent);
							}
						})
				.setNegativeButton("取消",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int whichButton) {
							}
						}).create();
		msgdialog.show();
	}
	
	//定义在EditText中显示当前日期、时间的方法
	private void showDate(int year, int month, int day, int hour, int minute) {
		strCheckDateTime = String.valueOf(year + (month + 1) + day + hour + minute);
	}
}
