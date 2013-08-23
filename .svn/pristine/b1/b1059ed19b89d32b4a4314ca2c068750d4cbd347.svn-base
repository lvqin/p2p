package cn.mw.p2p.unitily;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class P2pHttpRequest {
	
	// 获得Get请求对象request
		public static HttpGet getHttpGet(String url){
			HttpGet request = new HttpGet(url);
			 return request;
		}
		// 获得Post请求对象request
		public static HttpPost getHttpPost(String url){
			 HttpPost request = new HttpPost(url);
			 return request;
		}
		// 根据请求获得响应对象response
		public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException, IOException{
			HttpResponse response = new DefaultHttpClient().execute(request);
			return response;
		}
		// 根据请求获得响应对象response
		public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException{
			HttpResponse response = new DefaultHttpClient().execute(request);
			return response;
		}
		
		/**
		 * 将URL打包成HttpPost请求，发送，得到查询结果 网络异常 返回 "exception"
		 */
		public static String getHttpPostResultForUrl(String url){
			System.out.println("url==="+url);
			HttpPost httpPost = getHttpPost(url);
			String resultString = null;
			
			try {
				HttpResponse response = getHttpResponse(httpPost);
				if(response.getStatusLine().getStatusCode() == 200)
					resultString = EntityUtils.toString(response.getEntity());				
			} catch (ClientProtocolException e) {
				resultString = "-1";
				e.printStackTrace();
			} catch (IOException e) {
				resultString = "-1";
				e.printStackTrace();
			}
			
			return resultString;
		}
		
		/**
		 * 发送Post请求，得到查询结果 网络异常 返回 "exception"
		 */
		public static String getHttpPostResultForRequest(HttpPost httpPost){
			String resultString = null;
			
			try {
				HttpResponse response = getHttpResponse(httpPost);
				
				if(response.getStatusLine().getStatusCode() == 200)
					resultString = EntityUtils.toString(response.getEntity());				
				
			} catch (ClientProtocolException e) {
				resultString = "exception";
				e.printStackTrace();
			} catch (IOException e) {
				resultString = "exception";
				e.printStackTrace();
			}
			
			return resultString;
		}
		
		/**
		 * 将URL打包成HttpGet请求，发送，得到查询结果 网络异常 返回 "exception"
		 */
		public static String getHttpGetResultForUrl(String url){
			
			HttpGet httpGet = getHttpGet(url);
			String resultString = null;
			
			try {
				HttpResponse response = getHttpResponse(httpGet);
				if(response.getStatusLine().getStatusCode() == 200)
					resultString = EntityUtils.toString(response.getEntity());
			} catch (ClientProtocolException e) {
				resultString = "exception";
				e.printStackTrace();
			} catch (IOException e) {
				resultString = "exception";
				e.printStackTrace();
			}
			
			return resultString;
		}
		
		/**
		 * 发送Get请求，得到查询结果 网络异常 返回 "exception"
		 */
		public static String getHttpGetResultForRequest(HttpGet httpGet){
			String resultString = null;
			try {
				HttpResponse response = getHttpResponse(httpGet);
				if(response.getStatusLine().getStatusCode() == 200)
					resultString = EntityUtils.toString(response.getEntity());
			} catch (ClientProtocolException e) {
				resultString = "exception";
				e.printStackTrace();
			} catch (IOException e) {
				resultString = "exception";
				e.printStackTrace();
			}
			
			return resultString;
		}

	/**
	 * 将URL请求，发送，得到查询结果 网络异常 返回 "exception"
	 * @param strUrl
	 * @return String
	 */
	public static String getHttpRequestForUrl(String strUrl)
	{
		System.out.println("远程请求URL："+strUrl);
		String resultString = null;
		try {
			URL url = new URL(strUrl);
			InputStream in = url.openStream();
			SAXReader reader = new SAXReader();
			Document document = reader.read(in);
			Node node = document.selectSingleNode("string");
			resultString = node.getText().trim();
			in.close();
		} catch (Exception e) {
			resultString = "-1";
			e.printStackTrace();
		}
		
		return resultString;
	}
}
