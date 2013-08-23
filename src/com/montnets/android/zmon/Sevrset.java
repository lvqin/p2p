package com.montnets.android.zmon;

import cn.mw.p2p.unitily.ExitApplication;
import cn.mw.p2p.unitily.P2pBaseUrl;
import cn.mw.p2p.unitily.p2punitily;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Sevrset extends Activity {
	
	private SharedPreferences sp;//配置
	private EditText edtIP;
	private EditText edtprot;
	private Button btnSave;
	private ImageButton imgbtnBack;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set_server);
		ExitApplication.getInstance().addActivity(this);
		sp = getSharedPreferences("mwp2p", MODE_PRIVATE);
		edtIP = (EditText) findViewById(R.id.edtsvrIP);
		edtprot = (EditText)findViewById(R.id.edtprot);
		String ip = sp.getString("serverIP", P2pBaseUrl.SERVER_IP);
		edtIP.setText(ip);
		edtprot.setText(sp.getString("serverPort", P2pBaseUrl.SERVER_PORT));
		//保存
		btnSave = (Button)findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String ip = edtIP.getText().toString().trim();
				String iport = edtprot.getText().toString().trim();
				if(!p2punitily.isIp(ip)){
					edtIP.setError("请输入正确的IP！");
					return;
				}
				if(!p2punitily.isIpport(iport)){
					edtprot.setError("请输入正确的端口号！");
					return;
				}
				sp.edit()
				.putString("serverIP", ip)
				.putString("serverPort",iport)
				.commit();
				Toast.makeText(Sevrset.this, "设置成功",	Toast.LENGTH_SHORT).show();
			}
		});
		//返回
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Sevrset.this.finish();
			}
		});
	}
}
