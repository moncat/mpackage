package com.co.example.service.system.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.dao.system.TRoleRightDao;
import com.co.example.entity.system.TRoleRight;
import com.co.example.entity.system.aide.TRoleRightQuery;
import com.co.example.service.system.TRoleRightService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;

@Service
public class TRoleRightServiceImpl extends BaseServiceImpl<TRoleRight, Long> implements TRoleRightService {
    @Resource
    private TRoleRightDao tRoleRightDao;

    @Override
    protected BaseDao<TRoleRight, Long> getBaseDao() {
        return tRoleRightDao;
    }

	@Override
	public void addConn(Long roleId, Long[] menuIds) {
		//删除旧关联
		TRoleRightQuery tRoleRightQuery = new TRoleRightQuery();
		tRoleRightQuery.setRoleId(roleId);
		delete(tRoleRightQuery);
		//新增新关联
		TRoleRight tRoleRight = null;
		ArrayList<TRoleRight> tRoleRightList = Lists.newArrayList();
		for (Long rightId : menuIds) {
			tRoleRight = new TRoleRight();
			tRoleRight.setRoleId(roleId);
			tRoleRight.setRightId(rightId);
			BaseDataUtil.setDefaultData(tRoleRight);
			tRoleRightList.add(tRoleRight);
		}
		addInBatch(tRoleRightList);
	}

}