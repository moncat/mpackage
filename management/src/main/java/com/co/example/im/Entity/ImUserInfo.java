package com.co.example.im.Entity;

import lombok.Data;

@Data
public class ImUserInfo {
	private String accid ;	//是 用户帐号，最大长度32字符，必须保证一个APP内唯一
	private String name	 ;	//否 用户昵称，最大长度64字符
	private String icon	 ;	//否 用户icon，最大长度1024字符
	private String sign	 ;	//否 用户签名，最大长度256字符
	private String email ;	//否 用户email，最大长度64字符
	private String birth ;	//否 用户生日，最大长度16字符
	private String mobile ;	//否 用户mobile，最大长度32字符，只支持国内号码
	private int	   gender ;	//否 用户性别，0表示未知，1表示男，2女表示女，其它会报参数错误
	private String ex	 ;  //否 用户名片扩展字段，最大长度1024字符，用户可自行扩展，建议封装成JSON字符串   
}
