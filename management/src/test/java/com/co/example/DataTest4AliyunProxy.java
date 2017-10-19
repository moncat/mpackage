package com.co.example;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DataTest4AliyunProxy {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// File file = new
		// File("D:\\Workspaces2\\package\\management\\src\\test\\resources\\ip.json");
		// String str = "";
		// try {
		// str = FileUtils.readFileToString(file);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

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
		querys.put("num", "15");
//		querys.put("port", "port");
//		querys.put("portex", "portex");
//		querys.put("protocol", "protocol");
//		querys.put("type", "type");

		try {
			/**
			 * 重要提示如下: HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			// 获取response的body
			text = EntityUtils.toString(response.getEntity());
//			System.out.println(text);

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		
		JSONObject obj = JSON.parseObject(text);
		String msg = obj.getString("msg");
		if (StringUtils.equals("ok", msg)) {
			JSONObject result = obj.getJSONObject("result");
			JSONArray list = result.getJSONArray("list");
			for (int i = 0; i < list.size(); i++) {
				JSONObject info = (JSONObject) list.get(i);
				String string = info.getString("ip");
				System.out.println(string);

			}
		}

	}

}
