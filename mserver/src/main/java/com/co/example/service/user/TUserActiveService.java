package com.co.example.service.user;

import com.co.example.entity.user.TUserActive;
import com.github.moncat.common.service.BaseService;

public interface TUserActiveService extends BaseService<TUserActive, Long> {
	
	int sendVerifyCodeByPhone(String phoneNum,String ip);

	/**
	 * 验证 验证码
	 * @param phoneNum
	 * @param vcode
	 * @return
	 */
	Boolean verifyVCode(String phoneNum, String vcode);
	
}