package com.co.example.im.Entity;

import lombok.Data;

@Data
public class ImUserSetting {
	private String accid;		    //是	用户帐号
	private String donnopOpen;		//是	桌面端在线时，移动端是否不推送：	true:移动端不需要推送，false:移动端需要推送
}
