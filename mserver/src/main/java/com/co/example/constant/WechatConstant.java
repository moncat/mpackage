package com.co.example.constant;

public class WechatConstant {
		
	
	public static  String CODE_URL ="https://open.weixin.qq.com/connect/oauth2/authorize?"
			+ "appid=APPID"
			+ "&redirect_uri=REDIRECT_URI"
			+ "&response_type=code"
			+ "&scope=SCOPE"
			+ "&state=STATE#wechat_redirect"; 
	
	public static  String OPENID_URL ="https://api.weixin.qq.com/sns/oauth2/access_token?"
			+ "appid=APPID"
			+ "&secret=SECRET"
			+ "&code=CODE"
			+ "&grant_type=authorization_code"; 

	
	public static String OPEN_ID ="openId";
	
	public static String IS_WECHAT ="is_wechat";
	
	public static String APPID="wx607adf094642e51e";

	public static String SECRET ="31fcf4832b460f982957ea9b554e0520";
	
	public static String REDIRECT_URI="http://beauty.zylweb.com/wechat/callback";
	
	//默认拥有scope参数中的snsapi_base和snsapi_userinfo
	public static String SCOPE="snsapi_base";
	
}
