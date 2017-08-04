package com.co.example.service.admin;

import com.co.example.common.service.BaseService;
import com.co.example.entity.admin.TAdminActive;

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
}