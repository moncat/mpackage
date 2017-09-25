package com.co.example.common.utils.proxy;

import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.assertj.core.util.Lists;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.co.example.common.utils.HttpUtils;

/**
 * Created by paranoid on 17-4-21.
 * 测试此IP是否有效
 */

public class IPUtils {
	
	public static CloseableHttpResponse IPIsable(String ip,String port) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
			HttpHost proxy = new HttpHost(ip, Integer.parseInt(port));
			RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(5000).
					setSocketTimeout(5000).build();
			HttpGet httpGet = new HttpGet("https://www.baidu.com");
			httpGet.setConfig(config);
			
			httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;" +
					"q=0.9,image/webp,*/*;q=0.8");
			httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
			httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
			httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit" +
					"/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			
			try {
				response = httpClient.execute(httpGet);
			} catch (IOException e) {
				return null;
			}
		
		try {
			if (httpClient != null) {
				httpClient.close();
			}
			if (response != null) {
				response.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
	
    public static void IPIsable(List<IPMessage> ipMessages1) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        for(int i = 0; i < ipMessages1.size(); i++) {
            String ip = ipMessages1.get(i).getIPAddress();
            String port = ipMessages1.get(i).getIPPort();

            HttpHost proxy = new HttpHost(ip, Integer.parseInt(port));
            RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(5000).
                    setSocketTimeout(5000).build();
            HttpGet httpGet = new HttpGet("https://www.baidu.com");
            httpGet.setConfig(config);

            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;" +
                    "q=0.9,image/webp,*/*;q=0.8");
            httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
            httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit" +
                    "/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");

            try {
                response = httpClient.execute(httpGet);
            } catch (IOException e) {
                out.println("不可用代理已删除" + ipMessages1.get(i).getIPAddress()
                        + ": " + ipMessages1.get(i).getIPPort());
                ipMessages1.remove(ipMessages1.get(i));
                i--;
            }
        }

        try {
            if (httpClient != null) {
                httpClient.close();
            }
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static List<IPMessage>  getIps(String url){
		List<IPMessage>  IPMessages= Lists.newArrayList();
		String data = HttpUtils.getData(url);
		String[] strs = data.split("\n");
		IPMessage ipMessage = null;
		for (String str : strs) {
			ipMessage = new IPMessage();
			String[] infos = str.split(":");
			ipMessage.setIPAddress(infos[0]);
			ipMessage.setIPPort(infos[1]);
			IPMessages.add(ipMessage);
		}
		return IPMessages;
	}
    
    public static List<IPMessage>  getIpsFromAliyunMarket(){
    	List<IPMessage>  IPMessages= Lists.newArrayList();
    	String text = null;
    	String host = "http://jisuproxy.market.alicloudapi.com";
		String path = "/proxy/get";
		String method = "GET";
		String appcode = "2f435e055f6e4fbe98b4690bbe264a60";
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
		// 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
//		querys.put("area", "area");
//		querys.put("areaex", "areaex");
		querys.put("num", "50");
//		querys.put("port", "port");
//		querys.put("portex", "portex");
//		querys.put("protocol", "protocol");
//		querys.put("type", "type");

		try {
			HttpResponse response = com.co.example.common.utils.proxy.HttpUtils.doGet(host, path, method, headers, querys);
			System.out.println("1***"+response.toString());
			text = EntityUtils.toString(response.getEntity());
			System.out.println("2***"+text);

		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	JSONObject obj = JSON.parseObject(text);
		 String msg = obj.getString("msg");
		 if(StringUtils.equals("ok", msg)){
			 JSONObject result = obj.getJSONObject("result");
			 JSONArray list = result.getJSONArray("list");
			 IPMessage ipMessage = null;
			 for (int i = 0; i < list.size(); i++) {
				 JSONObject info = (JSONObject) list.get(i);
				 String str = info.getString("ip");
				 ipMessage = new IPMessage();
				 String[] infos = str.split(":");
				 ipMessage.setIPAddress(infos[0]);
				 ipMessage.setIPPort(infos[1]);
				 IPMessages.add(ipMessage);
			}
		 }
    	return IPMessages;
    }
    
    
    
    
    public static List<String>  getIpsFromFile(String filepath){
		File file = new File(filepath);
		try {
			@SuppressWarnings("deprecation")
			List<String> lines = FileUtils.readLines(file);
			return lines;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
    
    
}
