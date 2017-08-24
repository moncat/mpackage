package com.co.example.service.admin.impl;

import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.co.example.common.constant.Constant;
import com.co.example.common.utils.EmailUtil;
import com.co.example.dao.admin.TAdminActiveDao;
import com.co.example.entity.admin.TAdminActive;
import com.co.example.entity.admin.aide.TAdminActiveConstant;
import com.co.example.entity.admin.aide.TAdminActiveQuery;
import com.co.example.service.admin.TAdminActiveService;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class TAdminActiveServiceImpl extends BaseServiceImpl<TAdminActive, Long> implements TAdminActiveService {
    @Resource
    private TAdminActiveDao tAdminActiveDao;

    @Override
    protected BaseDao<TAdminActive, Long> getBaseDao() {
        return tAdminActiveDao;
    }

	@Override
	public TAdminActive queryLastByEmail(String email) {
		TAdminActiveQuery query = new TAdminActiveQuery();
		query.setEmail(email);
		query.setIsUse(Constant.NO);
		Sort sort = new Sort(Direction.DESC,"t.id");
		return queryOne(query,sort);
	}
	

	public void addBindCodeByEmail(String email, String name ,String ip) {
		String emailCode = RandomStringUtils.randomNumeric(6);
		TAdminActive adminActive = new TAdminActive();
		adminActive.setEmail(email); //
		adminActive.setVcode(emailCode);
		adminActive.setUserIp(ip);
		adminActive.setCreateTime(new Date());
		adminActive.setIsUse(Constant.NO);
		adminActive.setType(TAdminActiveConstant.FLAG_EMAIL.intValue());
		add(adminActive);
		EmailUtil.sendEmailCode(email,name,emailCode);
	}
}