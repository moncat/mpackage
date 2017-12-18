package com.co.example.im.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.druid.util.StringUtils;


@SuppressWarnings("deprecation")
public class ImHttpUtil {
	
	static DefaultHttpClient httpClient = new DefaultHttpClient();
	
	static String post(String url,Object obj) throws UnsupportedEncodingException, IOException, ClientProtocolException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		HttpPost httpPost = new HttpPost(url);
        String appKey = "f9299d32cee2c239d7dc76c8079f3b85";
        String appSecret = "559e83d3c376";
        String nonce = getNonce();
        
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);
        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Map<String,Object> map = PropertyUtils.describe(obj); 
        String value ="";
        for (String key : map.keySet()) {
        	if(!StringUtils.equals(key, "class")){
        		value = map.get(key).toString();
        		nvps.add(new BasicNameValuePair(key, value));
        	}
		}
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        HttpResponse response = httpClient.execute(httpPost);
        return EntityUtils.toString(response.getEntity(), "utf-8");
	}
	
	 private static String getNonce(){
    	double random = Math.random()*100000d;
    	String d = random+"";
    	String str = d.substring(0, 5);
    	return str;
    }
	 
	 
	 
}
