package com.co.example.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;


@Slf4j
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
	static int errCount = 0;
    public static String postData(String strUrlPath,String paramsStr, String encode) {

        byte[] data = paramsStr.getBytes();
        HttpURLConnection httpURLConnection = null;
        int response = 400;
        try {
            URL url = new URL(strUrlPath);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            
            //设置请求超时时间
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.setConnectTimeout(30000);
            
            //如下一行也是必须
            httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;");
            httpURLConnection.setRequestProperty("Accept-Charset", encode);
            httpURLConnection.setRequestProperty("contentType", encode);
            httpURLConnection.setRequestProperty("charset", encode);
            
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            
            //获得输出流，向服务器写入数据
            int ms =300;
            String result = null;
            for (int i = 1; i < 4; i++) {
            	if(response == HttpURLConnection.HTTP_OK) {
            		 InputStream inptStream = httpURLConnection.getInputStream();
                     result = dealResponseResult(inptStream,encode); 
                     if(StringUtils.isBlank(result)){
                    	 log.info("***httpPost请求结果为空***");
                     }
                     return result ;
            	}else{
            		if(i >1){
            			//log.info("正在发起第***"+i+"***次请求");
            			ms = ms+600;
            		}
            		try {
            			Thread.sleep(ms);
            		} catch (InterruptedException e) {
            			e.printStackTrace();
            		}
            		response = startRequest(data, httpURLConnection);
            	}
            	
			}
            
        } catch (IOException e) {
            //e.printStackTrace();
//        	log.info("***httppost请求超时***");
        	errCount++;
        	if(errCount>3){
        		errCount = 0;
        		 return "-1";
        	}else{
        		log.info("***httppost请求超时,再次请求***"+errCount);
        		postData(strUrlPath,paramsStr,  encode);
        	}
        	
//        	StackTraceElement[] stackTrace = e.getStackTrace();
//        	for (int i = 0; i < stackTrace.length; i++) {
//        		log.info("***"+stackTrace[i].toString());
//			}
//            return "err: " + e.getMessage().toString();
        }
        return "-1";
    }
	private static int startRequest(byte[] data, HttpURLConnection httpURLConnection) throws IOException {
		OutputStream outputStream = httpURLConnection.getOutputStream();
		outputStream.write(data);
		int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
		return response;
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
    public static String dealResponseResult(InputStream inputStream, String encode) {
    	 StringBuffer sb = new StringBuffer();
    	 List<String> lines = null;
		 try {
			lines = IOUtils.readLines(inputStream, encode);
		 } catch (IOException e) {
			e.printStackTrace();
		 }
    	 for (int i = 0; i < lines.size(); i++) {
    		 sb.append(lines.get(i));
		 }
    	 return sb.toString();
    	
//        String resultData = null;      //存储处理结果
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        byte[] data = new byte[1024];
//        int len = 0;
//        try {
//            while((len = inputStream.read(data)) != -1) {
//                byteArrayOutputStream.write(data, 0, len);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        resultData = new String(byteArrayOutputStream.toByteArray());
//        return resultData;
    }

    
    public static String getData(String path){
    	return getData(path, "utf-8");
    }
    public static String getData(String path,String encode){
    	ByteArrayOutputStream baos  =null;
    	InputStream is = null;
        try {
            URL url = new URL(path.trim());
            //打开连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //Get请求不需要DoOutPut
            conn.setDoOutput(false);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
            conn.setRequestProperty("Accept-Charset", encode);
            conn.setRequestProperty("contentType", encode);
            conn.setRequestProperty("charset", encode);
            
            //连接服务器  
            conn.connect();
            
            
            if(200 == conn.getResponseCode()){
                //得到输入流
                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while(-1 != (len = is.read(buffer))){
                    baos.write(buffer,0,len);
                    baos.flush();
                }
                return baos.toString(encode);
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
      //关闭输入流
        finally{
            try{
            	if(is!=null){
            		is.close();
            	}
                if(baos!=null){
                	baos.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
    
    static int a =1;
    public static void main(String[] args) {   	
//		String url="http://club.jd.com/comment/skuProductPageComments.action";
		
//		String paramsStr = "productId=3126016&page=1&pageSize=10&fold=1";
		
//         score: n.type,
//         sortType: n.sortType,
//         isShadowSku: n.isShadowSku,
//         rid: s,
		
//    	String data = postData(url,paramsStr,"utf-8");
    	
    	
    	
//    	String data = getData(url);
//    	for (int i = 0; i < 3; i++) {
//			a++;
//			System.out.println(a);
//			
//		}
    	
    	String data = getData("https://club.jd.com/comment/productCommentSummaries.action?referenceIds=3126016","gbk");
    	System.out.println(data);
    	
    	
    	
    	
    	
    	
	}
    

}