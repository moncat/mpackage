package com.co.example.service.user;

import com.github.moncat.common.service.BaseService;
import com.co.example.entity.user.TUsers;

public interface TUsersService extends BaseService<TUsers, Integer> {

	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
	TUsers queryByLoginName(String username);
	
	/**
	 * 事务测试
	 * @param user
	 * @param tUserLogin
	 */
	void addUser();

	
}