package com.co.example.service.log.impl;

import com.co.example.dao.log.TBrLogUserDao;
import com.co.example.entity.log.TBrLogUser;
import com.co.example.entity.log.aide.TBrLogConstant;
import com.co.example.service.log.TBrLogUserService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

import java.util.Date;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrLogUserServiceImpl extends BaseServiceImpl<TBrLogUser, Long> implements TBrLogUserService {
    @Resource
    private TBrLogUserDao tBrLogUserDao;

    @Override
    protected BaseDao<TBrLogUser, Long> getBaseDao() {
        return tBrLogUserDao;
    }

	@Override
	public void updatePwd(String phoneNum,String ip) {
		TBrLogUser tBrLogUser = new TBrLogUser();
		tBrLogUser.setUserName(phoneNum);
		tBrLogUser.setCreateTime(new Date());
		tBrLogUser.setOperType(TBrLogConstant.OPER_TYPE_UPDATE);
		tBrLogUser.setModule(TBrLogConstant.MODULE_USER_LOGIN);
		tBrLogUser.setIp(ip);
		tBrLogUser.setDetail("修改了账号密码");
		add(tBrLogUser);
		
	}

	@Override
	public void addAccount(String phoneNum, String ip) {
		TBrLogUser tBrLogUser = new TBrLogUser();
		tBrLogUser.setUserName(phoneNum);
		tBrLogUser.setCreateTime(new Date());
		tBrLogUser.setOperType(TBrLogConstant.OPER_TYPE_ADD);
		tBrLogUser.setModule(TBrLogConstant.MODULE_USER_REGISTER);
		tBrLogUser.setIp(ip);
		tBrLogUser.setDetail("注册了账号");
		add(tBrLogUser);
	}

	@Override
	public void addSkinTest(String phoneNum, String ip) {
		TBrLogUser tBrLogUser = new TBrLogUser();
		tBrLogUser.setUserName(phoneNum);
		tBrLogUser.setCreateTime(new Date());
		tBrLogUser.setOperType(TBrLogConstant.OPER_TYPE_ADD);
		tBrLogUser.setModule(TBrLogConstant.MODULE_SKIN_TEST);
		tBrLogUser.setIp(ip);
		tBrLogUser.setDetail("完成了肤质测试");
		add(tBrLogUser);
		
	}

	@Override
	public void addConsult(String phoneNum, String ip) {
		TBrLogUser tBrLogUser = new TBrLogUser();
		tBrLogUser.setUserName(phoneNum);
		tBrLogUser.setCreateTime(new Date());
		tBrLogUser.setOperType(TBrLogConstant.OPER_TYPE_ADD);
		tBrLogUser.setModule(TBrLogConstant.MODULE_SKIN_CONSULT);
		tBrLogUser.setIp(ip);
		tBrLogUser.setDetail("点开了护肤咨询");
		add(tBrLogUser);
	}

	@Override
	public void addApply(String phoneNum, String ip) {
		TBrLogUser tBrLogUser = new TBrLogUser();
		tBrLogUser.setUserName(phoneNum);
		tBrLogUser.setCreateTime(new Date());
		tBrLogUser.setOperType(TBrLogConstant.OPER_TYPE_ADD);
		tBrLogUser.setModule(TBrLogConstant.MODULE_PRODUCT_ACTIVITY);
		tBrLogUser.setIp(ip);
		tBrLogUser.setDetail("提交了试用申请");
		add(tBrLogUser);
	}
}