package com.co.example.utils.tbk;


import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.time.DateFormatUtils;
import org.oxerr.taobao.DefaultTaobaoClientConfiguration;

import com.co.example.common.utils.DateFormatUtil;
import com.co.example.common.utils.HttpUtils;
import com.google.common.collect.Maps;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;

public class TbkUtil {
	
	static String url = "http://gw.api.taobao.com/router/rest";
	
	static String appkey = "24802323";
	
	static String secret = "7e80c345a2e0c3a134cf55815686f42d";
	
	
	static String adzoneId = "258074410";
	
	
	
	public void test1() {
//		￥免费 不需要授权
//		taobao.tbk.item.convert (淘宝客商品链接转换)
//		请求地址：
//		环境	HTTP请求地址	HTTPS请求地址
//		正式环境	http://gw.api.taobao.com/router/rest	https://eco.taobao.com/router/rest
//		沙箱环境	http://gw.api.tbsandbox.com/router/rest	https://gw.api.tbsandbox.com/router/rest
//		公共请求参数：
//		method	String	是	API接口名称。
//		app_key	String	是	TOP分配给应用的AppKey。
//		target_app_key	String	否	被调用的目标AppKey，仅当被调用的API为第三方ISV提供时有效。
//		sign_method	String	是	签名的摘要算法，可选值为：hmac，md5。
//		sign	String	是	API输入参数签名结果，签名算法介绍请点击这里。
//		session	String	否	用户登录授权成功后，TOP颁发给应用的授权信息，详细介绍请点击这里。当此API的标签上注明：“需要授权”，则此参数必传；“不需要授权”，则此参数不需要传；“可选授权”，则此参数为可选。
//		timestamp	String	是	时间戳，格式为yyyy-MM-dd HH:mm:ss，时区为GMT+8，例如：2015-01-01 12:00:00。淘宝API服务端允许客户端请求最大时间误差为10分钟。
//		format	String	否	响应格式。默认为xml格式，可选值：xml，json。
//		v	String	是	API协议版本，可选值：2.0。
//		partner_id	String	否	合作伙伴身份标识。
//		simplify	Boolean	否	是否采用精简JSON返回格式，仅当format=json时有效，默认值为：false。
		//请求参数
//		fields	String	必须	num_iid,click_url		需返回的字段列表
//		num_iids	String	必须	123,456		商品ID串，用','分割，从taobao.tbk.item.get接口获取num_iid字段，最大40个
//		adzone_id	Number	必须	123		广告位ID，区分效果位置
//		platform	Number	可选	123	     默认值：1        链接形式：1：PC，2：无线，默认：１
//		unid	String	可选	demo		自定义输入串，英文和数字组成，长度不能大于12个字符，区分不同的推广渠道
//		dx	String	可选	1    1表示商品转通用计划链接，其他值或不传表示转营销计划链接
		
		HashMap<String, String> params = Maps.newHashMap();
		//公共参数
		String dateTime = DateFormatUtil.formatDateTime(new Date());
		params.put("method", "taobao.tbk.item.convert");
		params.put("app_key", appkey);
		params.put("sign_method", "md5");
		params.put("sign", "");//API输入参数签名结果
		params.put("timestamp", dateTime);
		params.put("format", "json");
		params.put("v", "2.0");
		params.put("fields", "num_iid,click_url");
		params.put("num_iids", "561862295223"); //改参数内容
		params.put("adzone_id", adzoneId);
		params.put("platform", "1");  //改为2
		
		String res = HttpUtils.postData(url, params);
		System.out.println("res"+res);
		
	}
	
	
//	public static void test2() throws ApiException{  
//		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
//		TbkItemConvertRequest req = new TbkItemConvertRequest();
//		req.setFields("num_iid,click_url");
//		req.setNumIids("123,456");
//		req.setAdzoneId(123L);
//		req.setPlatform(123L);
//		req.setUnid("demo");
//		req.setDx("1");
//		TbkItemConvertResponse rsp = client.execute(req);
//		System.out.println(rsp.getBody()); 
//    }
	
	
	public static void main(String[] args) {
//		test2();
	}
	
}






