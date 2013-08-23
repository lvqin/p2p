﻿package cn.mw.p2p.unitily;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.util.Log;

public class WSUtils {
	
   
	/**
	 * 远程调用方法
	 * @param serviceUrl WSDL文档的URL，192.168.4.103:8081为PC的ID地址
	 * @param methodName 方法名称
	 * @param paramName 参数名称集合
	 * @param paramValue 对应的参数值
	 * @param xmlNodeName XML节点名称
	 * @return strRes
	 */
	public static String wSReturnString(String serviceUrl, String methodName,
			String[] paramName, Object[] paramValue, String[] xmlNodeName)
	{
		
		String strRes = null;
		int timeout1 = 5000;
		System.out.println("WSDL文档地址" + serviceUrl);
		// 第1步：创建SoapObject对象，并指定WebService的命名空间和调用的方法名
		SoapObject request = new SoapObject("urn:cu", methodName);
		// 第2步：设置WebService方法的参数
		if (paramName != null || paramValue != null) {
			for (int i = 0; i < paramName.length; i++) {
				request.addProperty(paramName[i].toString(), paramValue[i]);
			}
		}
		// 第3步：创建SoapSerializationEnvelope对象，并指定WebService的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		// 设置bodyOut属性
		envelope.bodyOut = request;
		// 第4步：创建HttpTransportSE对象，并指定WSDL文档的URL
		MyAndroidHttpTransport ht1 = new MyAndroidHttpTransport(serviceUrl,timeout1);  
		ht1.debug = true;
		try {
			// 第5步：调用WebService
			ht1.call(null, envelope);
			if (envelope.getResponse() != null) {
				// 第6步：使用getResponse方法获得WebService方法的返回结果
				SoapObject soapObject = (SoapObject) envelope.bodyIn;
				// 通过getProperty方法获得节点对象的属性值
				for (int k = 0; k < xmlNodeName.length; k++) {
					if(k==0)
						strRes = soapObject.getProperty(xmlNodeName[k].toString()).toString();
					else {
						strRes += "," + soapObject.getProperty(xmlNodeName[k].toString()).toString();
					}
				}

			} else {
				strRes = "-1";
			}
		} catch (Exception e) {
			Log.d("远程调用错误提示：",e.getMessage());
			strRes = "-1";
		}
		return strRes;
	}
	
	/**
	 * 远程调用方法，传递结构体对象
	 * @param serviceUrl URL地址
	 * @param methodName 方法名称
	 * @param PropertyInfo 对象参数
	 * @param objclass 类对象
	 * @param structName 结构名称
	 * @return
	 */
	public static SoapObject wsRequestStruct(String serviceUrl, String methodName,
			PropertyInfo pinfo, Object objclass,String structName)
	{
		String NAMESPACE = MsgEnum.NAME_SPACE;// 空间
		String METHOD_NAME = methodName;// 方法名
		String SOAP_ACTION = NAMESPACE + methodName;// 空间外+方法名
		int timeout = 6000;
  		try{
  			
	  		SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
	  		rpc.addProperty(pinfo);
			//HttpTransportSE ht = new HttpTransportSE(serviceUrl);// WEB服务地址
			MyAndroidHttpTransport ht = new MyAndroidHttpTransport(serviceUrl,timeout);
	  		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	  		envelope.bodyOut = ht;
	  		//envelope.wait();
	  		envelope.dotNet = false;
	  		envelope.setOutputSoapObject(rpc);
			envelope.addMapping(NAMESPACE, structName, objclass.getClass());
			ht.debug = true;
	  		ht.call(SOAP_ACTION, envelope);	
	  		//获得WebService方法的返回结果
			SoapObject soapObject = (SoapObject) envelope.bodyIn;
			return soapObject;
  		
		} catch (Exception e) {
			System.out.println("远程请求异常：" + e.getMessage());
			return null;
		}
	}
	
}
