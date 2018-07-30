package com.co.example.service.admin.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import com.co.example.dao.admin.TAdminRoleDao;
import com.co.example.entity.admin.TAdminRole;
import com.co.example.entity.admin.aide.TAdminRoleQuery;
import com.co.example.service.admin.TAdminRoleService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TAdminRoleServiceImpl extends BaseServiceImpl<TAdminRole, Long> implements TAdminRoleService {
    @Resource
    private TAdminRoleDao tAdminRoleDao;

    @Override
    protected BaseDao<TAdminRole, Long> getBaseDao() {
        return tAdminRoleDao;
    }

	@Override
	public void addAdminConn(Long roleId, Long[] adminIds) {
		//删除旧关联
		TAdminRoleQuery tAdminRoleQuery = new TAdminRoleQuery();
		tAdminRoleQuery.setRoleId(roleId);
		delete(tAdminRoleQuery);
		//新增新关联
		TAdminRole tAdminRole = null;
		ArrayList<TAdminRole> tAdminRoleList = Lists.newArrayList();
		for (Long adminId : adminIds) {
			tAdminRole = new TAdminRole();
			tAdminRole.setRoleId(roleId);
			tAdminRole.setAdminId(adminId);
			BaseDataUtil.setDefaultData(tAdminRole);
			tAdminRoleList.add(tAdminRole);
		}
		addInBatch(tAdminRoleList);
	}
}



