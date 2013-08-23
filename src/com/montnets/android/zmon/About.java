package com.montnets.android.zmon;

import cn.mw.p2p.unitily.ExitApplication;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class About extends Activity {

	private ImageButton imgbtnBack;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.about_layout);
        ExitApplication.getInstance().addActivity(this);
		imgbtnBack = (ImageButton) findViewById(R.id.back_btn);
		imgbtnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				About.this.finish();
			}
		});
	}
}
