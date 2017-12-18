package com.co.example.service.user.impl;

import com.co.example.dao.user.TUserRoleDao;
import com.co.example.entity.user.TUserRole;
import com.co.example.service.user.TUserRoleService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TUserRoleServiceImpl extends BaseServiceImpl<TUserRole, Long> implements TUserRoleService {
    @Resource
    private TUserRoleDao tUserRoleDao;

    @Override
    protected BaseDao<TUserRole, Long> getBaseDao() {
        return tUserRoleDao;
    }
}