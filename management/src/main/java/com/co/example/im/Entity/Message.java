package com.co.example.im.Entity;

import lombok.Data;

/**
 * @see http://dev.netease.im/docs/product/IM即时通讯/服务端API文档/消息功能
 * @author zyl
 *
 */
@Data
public class Message {
	private String	from; //是		
	private int	ope;	//是
	private String	to;	//是
	private int	type;	//是
	private String	body;	//是		
	private String	antispam;	//否
	private String	antispamCustom;	//否
	private String	option;	//否
	private String	pushcontent;	//否
	private String	payload;	//否
	private String	ext;	//否          
	private String	forcepushlist	;//否
	private String	forcepushcontent;	//否
	private String	forcepushall	;//否
}

