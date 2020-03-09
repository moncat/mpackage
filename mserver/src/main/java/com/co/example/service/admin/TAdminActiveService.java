package com.co.example.service.admin;

import com.co.example.entity.admin.TAdminActive;
import com.github.moncat.common.service.BaseService;

public interface TAdminActiveService extends BaseService<TAdminActive, Long> {
	
	/**
	 * 查询最新的信息
	 * @param loginName
	 * @return
	 */
	TAdminActive queryLastByEmail(String loginName);
	/**
	 * 发送验证码
	 * @param email
	 * @param name
	 * @param ip
	 */
	void addBindCodeByEmail(String email, String name ,String ip);
	
	//发送手机验证码
	int sendVerifyCodeByPhone(String phoneNum,String ip);

	//验证表单提交的验证码是否正确
	Boolean verifyVCode(String phoneNum, String vcode);
	
	//将验证码修改为已使用
	Boolean updateVCodeUsed(String phoneNum, String vcode);
}