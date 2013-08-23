package com.montnets.android.zmon;

import java.util.Calendar;

import cn.mw.p2p.unitily.ExitApplication;
import cn.mw.p2p.unitily.MsgEnum;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

public class DeviceRecordSearch extends Activity implements View.OnTouchListener{
	
	private Spinner spinnerRecordType;
	private EditText beginTime;
	private EditText endTime;
	private String DevID;
	private String ChannelNo;
	private Button btnSearch;
	private ImageButton imgbtnBack;

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.search_devicerecord_layout); 
        ExitApplication.getInstance().addActivity(this);
		DevID = getIntent().getStringExtra("DevID");
		ChannelNo = getIntent().getStringExtra("ChannelNo");
        initUI();
    }
	
	/**
	 * 初始化UI
	 */
	private void initUI()
	{
		spinnerRecordType = (Spinner)findViewById(R.id.spinnerRecordType);
		ArrayAdapter<String> adapterRecordType = new ArrayAdapter<String>(
				DeviceRecordSearch.this, android.R.layout.simple_spinner_item,
				MsgEnum.RECORD_TYPE_NAME);
		adapterRecordType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerRecordType.setAdapter(adapterRecordType);
		
		beginTime = (EditText)findViewById(R.id.BeginTime);
		endTime = (EditText)findViewById(R.id.EndinTime);
		beginTime.setOnTouchListener(this);  
		endTime.setOnTouchListener(this); 
		
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new backOnClickListener());
		
		btnSearch = (Button)findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				 //close this function
				//Toast.makeText(DeviceRecordSearch.this, "维护中,暂不提供此功能", Toast.LENGTH_SHORT).show();
				//return;

				Long RecTypeIndex = spinnerRecordType.getSelectedItemId();
				String RecTypeValue = MsgEnum.RECORD_TYPE_VALUE[RecTypeIndex.intValue()];
				String strBeginTime = beginTime.getText().toString().trim();
				String strBeginTime1 = strBeginTime + "0000";
				System.out.println("开始时间是=======>" + strBeginTime);
				String strendTime = endTime.getText().toString().trim();
				String strendTime1 = strendTime + "2359";
				System.out.println("结束时间是=======>" + strendTime);
				Log.v("BeginTime", strBeginTime);
				Log.v("EndTime", strendTime);
				if(strBeginTime.equals(""))
				{
					Toast.makeText(DeviceRecordSearch.this, "开始时间不能为空", 0).show();
					return;
				}
				if(strendTime.equals(""))
				{
					Toast.makeText(DeviceRecordSearch.this, "结束时间不能为空", 0).show();
					return;
				}
				if (Long.parseLong(strBeginTime) >= Long.parseLong(strendTime)) {
					Toast.makeText(DeviceRecordSearch.this, "结束时间不能小于或等于开始时间", 0).show();
					return;
				}
				String SearchCondtion = DevID + "," + ChannelNo + ","
						+ RecTypeValue + "," + strBeginTime1 + "," + strendTime1;
				System.out.println("查询条件：" + SearchCondtion);
				Intent intent = new Intent(DeviceRecordSearch.this, DeviceRecordSearchResultList.class);
				intent.putExtra("SearchCondtion", SearchCondtion);
				startActivity(intent);
			}
		});
	}
	
	private final class backOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			DeviceRecordSearch.this.finish();
		}
	}
	

    @Override  
    public boolean onTouch(View v, MotionEvent event) {  
        if (event.getAction() == MotionEvent.ACTION_DOWN) {  
  
            AlertDialog.Builder builder = new AlertDialog.Builder(this);  
            View view = View.inflate(this, R.layout.date_time_dialog, null);  
            final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker); 
//            final TimePicker timePicker = (android.widget.TimePicker) view.findViewById(R.id.time_picker);  
            builder.setView(view);  
  
            Calendar cal = Calendar.getInstance();  
            cal.setTimeInMillis(System.currentTimeMillis());  
            datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);  
  
//            timePicker.setIs24HourView(true);  
//            timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));  
//            timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));
  
			if (v.getId() == R.id.BeginTime) {
				final int inType = beginTime.getInputType();
				beginTime.setInputType(InputType.TYPE_NULL);
				beginTime.onTouchEvent(event);
				beginTime.setInputType(inType);
				beginTime.setSelection(beginTime.getText().length());

				builder.setTitle("选取开始时间");
				builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,int which) {
								datePicker.clearFocus();
								StringBuffer sb = new StringBuffer();
								sb.append(String.format("%d%02d%02d",
										datePicker.getYear(),
										datePicker.getMonth() + 1,
										datePicker.getDayOfMonth()));
//								sb.append(timePicker.getCurrentHour())
//								.append(timePicker.getCurrentMinute());
								beginTime.setText(sb);
								endTime.requestFocus();
								dialog.cancel();
							}
						});
			} else if (v.getId() == R.id.EndinTime) {
				int inType = endTime.getInputType();
				endTime.setInputType(InputType.TYPE_NULL);
				endTime.onTouchEvent(event);
				endTime.setInputType(inType);
				endTime.setSelection(endTime.getText().length());

				builder.setTitle("选取结束时间");
				builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,int which) {
								datePicker.clearFocus();
								StringBuffer sb = new StringBuffer();
								sb.append(String.format("%d%02d%02d",
										datePicker.getYear(),
										datePicker.getMonth() + 1,
										datePicker.getDayOfMonth()));
//								sb.append(timePicker.getCurrentHour())
//								.append(timePicker.getCurrentMinute());
								endTime.setText(sb);
								dialog.cancel();
							}
						});
			}

			Dialog dialog = builder.create();
			dialog.show();
		}  
  
        return true;  
    }
    
 
}
