package com.co.example.service.system.impl;

import com.co.example.dao.system.TRoleRightDao;
import com.co.example.entity.system.TRoleRight;
import com.co.example.service.system.TRoleRightService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TRoleRightServiceImpl extends BaseServiceImpl<TRoleRight, Long> implements TRoleRightService {
    @Resource
    private TRoleRightDao tRoleRightDao;

    @Override
    protected BaseDao<TRoleRight, Long> getBaseDao() {
        return tRoleRightDao;
    }
}