package com.co.example.service.user;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.co.example.entity.user.TUser;
import com.co.example.entity.user.aide.UserSession;
import com.github.moncat.common.service.BaseService;

public interface TUserService extends BaseService<TUser, Long> {
	
	
	TUser queryByLoginName(String name);

	void updateLogin(UserSession oldUsersSession, TUser user, String ip);

	HashMap<String, Object> updatePwd(HttpSession session, String password1, String password2);
}