package com.co.example.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.google.common.collect.Maps;


public class HttpUtils {
	
	public static String postData(String strUrlPath) {
		return postData(strUrlPath,"","UTF-8");
	}
	public static String postData(String strUrlPath,String params) {
		return postData(strUrlPath,params,"UTF-8");
	}
	public static String postData(String strUrlPath,Map<String, String> params) {
		 String paramsStr = getRequestData(params,"UTF-8").toString();
		 String result = postData(strUrlPath,paramsStr,"UTF-8");
		 return result;
	}
	public static String postData(String strUrlPath,Map<String, String> params, String encode) {
		String paramsStr = getRequestData(params,encode).toString();
		 String result = postData(strUrlPath,paramsStr,encode);
		 return result;
	} 
    public static String postData(String strUrlPath,String paramsStr, String encode) {

        byte[] data = paramsStr.getBytes();
        try {

            URL url = new URL(strUrlPath);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            
            //设置请求超时时间
            httpURLConnection.setReadTimeout(20000);
            httpURLConnection.setConnectTimeout(20000);
            
            //如下一行也是必须
            httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);

            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }
        } catch (IOException e) {
            //e.printStackTrace();
            return "err: " + e.getMessage().toString();
        }
        return "-1";
    }

    //处理发送
    public static StringBuffer getRequestData(Map<String, String> params) {
    	return getRequestData(params,"UTF-8");
    }
    
    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
        try {
        	if(MapUtils.isNotEmpty(params)){
        		for(Map.Entry<String, String> entry : params.entrySet()) {
        			stringBuffer.append(entry.getKey())
        			.append("=")
        			.append(URLEncoder.encode(entry.getValue(), encode))
        			.append("&");
        		}
        		stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }
    

    //处理返回
    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }

    
    public static String getData(String path){
        try {
            URL url = new URL(path.trim());
            //打开连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if(200 == urlConnection.getResponseCode()){
                //得到输入流
                InputStream is =urlConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while(-1 != (len = is.read(buffer))){
                    baos.write(buffer,0,len);
                    baos.flush();
                }
                return baos.toString("utf-8");
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public static void main(String[] args) {   	
		String url="http://125.35.6.80:8080/ftba/itownet/fwAction.do?method=getBaNewInfoPage";
		Map<String, String> map = Maps.newHashMap();
		map.put("on", "true");
		map.put("page", "3021");
		map.put("pageSize", "15");
    	String data = postData(url,map);
    	System.out.println(data);
	}
    

}