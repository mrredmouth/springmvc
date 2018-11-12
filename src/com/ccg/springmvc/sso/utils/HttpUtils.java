package com.ccg.springmvc.sso.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
	/**
	 * 模拟发送http请求
	 * @param httpURL
	 * @throws IOException
	 */
	public static void sendHttpRequest(String httpURL,String jsessionId) throws IOException{
		URL url = new URL(httpURL);
		//创建连接
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		//设置请求方式，需要大写
		conn.setRequestMethod("POST");
		//设置需要输出
		conn.setDoOutput(false);
		conn.addRequestProperty("Cookie", "JSESSIONID="+jsessionId);
		//发送请求到服务器
		conn.connect();
		conn.getInputStream();
		conn.disconnect();
	}
}
