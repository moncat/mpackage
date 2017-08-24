package com.co.example.service.system.impl;

import com.co.example.dao.system.TRoleDao;
import com.co.example.entity.system.TRole;
import com.co.example.service.system.TRoleService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TRoleServiceImpl extends BaseServiceImpl<TRole, Long> implements TRoleService {
    @Resource
    private TRoleDao tRoleDao;

    @Override
    protected BaseDao<TRole, Long> getBaseDao() {
        return tRoleDao;
    }
}