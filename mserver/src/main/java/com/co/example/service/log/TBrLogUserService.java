package com.co.example.service.log;

import com.co.example.entity.log.TBrLogUser;
import com.github.moncat.common.service.BaseService;

public interface TBrLogUserService extends BaseService<TBrLogUser, Long> {
	
	/**
	 * 修改密码
	 * @param phoneNum
	 * @param ip
	 */
	void updatePwd(String phoneNum,String ip);
	
	/**
	 * 添加账户
	 * @param phoneNum
	 * @param ip
	 */
	void addAccount(String phoneNum,String ip);
	
	/**
	 * 肤质测试
	 * @param phoneNum
	 * @param ip
	 */
	void addSkinTest(String phoneNum,String ip);
	
	/**
	 * 肤质咨询
	 * @param phoneNum
	 * @param ip
	 */
	void addConsult(String phoneNum,String ip);
	
	/**
	 * 试用申请
	 * @param phoneNum
	 * @param ip
	 */
	void addApply(String phoneNum,String ip);
	
}