package com.co.example.im.Entity;

import lombok.Data;

//用户关系
@Data
public class Friend {
	private String	accid;	//是	加好友发起者accid
	private String	faccid;	//是	加好友接收者accid
	private int	    type;	//是	1直接加好友，2请求加好友，3同意加好友，4拒绝加好友
	private String	msg	 ;  //否	加好友对应的请求消息，第三方组装，最长256字符
	private String  alias;  //否	给好友增加备注名，限制长度128
	private String  ex	;  //否	修改ex字段，限制长度256
	private Long updatetime;	//	是	更新时间戳，接口返回该时间戳之后有更新的好友列表
	private Long createtime;	//	否	【Deprecated】定义同updatetime
	
}
