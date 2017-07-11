package com.co.example.service.user.impl;

import com.co.example.common.dao.BaseDao;
import com.co.example.common.service.BaseServiceImpl;
import com.co.example.dao.user.TRoleDao;
import com.co.example.entity.user.TRole;
import com.co.example.service.user.TRoleService;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TRoleServiceImpl extends BaseServiceImpl<TRole, Integer> implements TRoleService {
    @Resource
    private TRoleDao tRoleDao;

    @Override
    protected BaseDao<TRole, Integer> getBaseDao() {
        return tRoleDao;
    }

	@Override
	public List<TRole> queryRolesByUserId(Integer userId) {
		return null;
	}
	
}