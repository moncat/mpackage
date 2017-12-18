package com.co.example.im.Entity;

import lombok.Data;

@Data
public class FriendSetting {
	private String	accid	 ;  //是	用户帐号，最大长度32字符，必须保证一个APP内唯一
	private String	targetAcc;  //是	被加黑或加静音的帐号
	private int	relationType ;  //是	本次操作的关系类型,1:黑名单操作，2:静音列表操作
	private int	value	     ;  //是	操作值，0:取消黑名单或静音，1:加入黑名单或静音
}
