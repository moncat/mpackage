package com.co.example.service.user;

import com.co.example.entity.user.TUser;
import com.co.example.entity.user.aide.UserSession;
import com.github.moncat.common.service.BaseService;

public interface TUserService extends BaseService<TUser, Long> {
	
	
	TUser queryByLoginName(String name);

	void updateLogin(UserSession oldUsersSession, TUser user, String ip,String openId);

	Boolean updatePwd(String ip ,String phoneNum, String password1, String password2,String vcode);

	Boolean addUser(String vcode ,String ip ,String phoneNum, String password1,String openId);
}