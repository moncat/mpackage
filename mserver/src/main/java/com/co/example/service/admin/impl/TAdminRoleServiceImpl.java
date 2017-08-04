package com.co.example.service.admin.impl;

import com.co.example.common.dao.BaseDao;
import com.co.example.common.service.BaseServiceImpl;
import com.co.example.dao.admin.TAdminRoleDao;
import com.co.example.entity.admin.TAdminRole;
import com.co.example.service.admin.TAdminRoleService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TAdminRoleServiceImpl extends BaseServiceImpl<TAdminRole, Long> implements TAdminRoleService {
    @Resource
    private TAdminRoleDao tAdminRoleDao;

    @Override
    protected BaseDao<TAdminRole, Long> getBaseDao() {
        return tAdminRoleDao;
    }
}