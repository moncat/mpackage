package com.co.example.service.admin.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.co.example.dao.admin.TAdminLoginDao;
import com.co.example.entity.admin.TAdminLogin;
import com.co.example.entity.admin.aide.TAdminLoginQuery;
import com.co.example.service.admin.TAdminLoginService;

@Service
public class TAdminLoginServiceImpl extends BaseServiceImpl<TAdminLogin, Long> implements TAdminLoginService {
    @Resource
    private TAdminLoginDao tAdminLoginDao;

    @Override
    protected BaseDao<TAdminLogin, Long> getBaseDao() {
        return tAdminLoginDao;
    }

	@Override
	public TAdminLogin queryByRememberKey(String rememberKey) {
		TAdminLoginQuery query = new TAdminLoginQuery();
		query.setRememberKey(rememberKey);
		return queryOne(query);
	}
	
	
}