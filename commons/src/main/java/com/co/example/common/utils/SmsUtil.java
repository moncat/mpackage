package com.co.example.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SmsUtil {

	/** 日志记录 */
	protected static final Log logger = LogFactory.getLog(SmsUtil.class);

	public static String SMS(String postData, String postUrl) {
		try {
			// 发送POST请求
			URL url = new URL(postUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setUseCaches(false);
			conn.setDoOutput(true);

			conn.setRequestProperty("Content-Length", "" + postData.length());
			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8");
			out.write(postData);
			out.flush();
			out.close();

			// 获取响应状态
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				//System.out.println("connect failed!");
				return "";
			}
			// 获取响应内容体
			String line, result = "";
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8"));
			while ((line = in.readLine()) != null) {
				result += line + "\n";
			}
			in.close();
			return result;
		} catch (IOException e) {
			//e.printStackTrace(System.out);
		}
		return "";
	}

	
	private static String name;
	private static String password;
	private static String productId;
	private static String url;
	

	public static void sendMobileMessage(String mobilePhone, String message) {
		try {
			String PostData = "sname="
					+ name
					+ "&spwd="
					+ password
					+ "&scorpid=&sprdid="
					+ productId
					+ "&sdst=" + mobilePhone + "&smsg="
					+ java.net.URLEncoder.encode(message, "utf-8");
			String result = SMS(PostData,url);
			//记录日志
			logger.info(result);
		} catch (Exception e) {
			//记录日志
			logger.error("短信发送失败!mobileNum=" + mobilePhone + "message="+ message);
		}

	}

}
