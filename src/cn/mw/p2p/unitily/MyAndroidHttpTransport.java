﻿package cn.mw.p2p.unitily;

import java.io.IOException;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection; 

public class MyAndroidHttpTransport extends HttpTransportSE {
	
	private int timeout = 20000; // 默认超时时间为20s

	public MyAndroidHttpTransport(String url) {
		super(url);
	}

	public MyAndroidHttpTransport(String url, int timeout) {
		super(url,timeout);
		this.timeout = timeout;
	}

	protected ServiceConnection getServiceConnection(String url)throws IOException {
		ServiceConnectionSE serviceConnection = new ServiceConnectionSE(url);
		serviceConnection.setConnectionTimeOut(timeout);
		return new ServiceConnectionSE(url);
	} 

}
