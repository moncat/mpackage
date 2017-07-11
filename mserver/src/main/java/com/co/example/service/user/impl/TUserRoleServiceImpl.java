package com.co.example.service.user.impl;

import com.co.example.common.dao.BaseDao;
import com.co.example.common.service.BaseServiceImpl;
import com.co.example.dao.user.TUserRoleDao;
import com.co.example.entity.user.TUserRole;
import com.co.example.service.user.TUserRoleService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TUserRoleServiceImpl extends BaseServiceImpl<TUserRole, Integer> implements TUserRoleService {
    @Resource
    private TUserRoleDao tUserRoleDao;

    @Override
    protected BaseDao<TUserRole, Integer> getBaseDao() {
        return tUserRoleDao;
    }
}