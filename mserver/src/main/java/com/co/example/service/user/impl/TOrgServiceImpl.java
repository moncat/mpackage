package com.co.example.service.user.impl;

import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.co.example.dao.user.TOrgDao;
import com.co.example.entity.user.TOrg;
import com.co.example.service.user.TOrgService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TOrgServiceImpl extends BaseServiceImpl<TOrg, Integer> implements TOrgService {
    @Resource
    private TOrgDao tOrgDao;

    @Override
    protected BaseDao<TOrg, Integer> getBaseDao() {
        return tOrgDao;
    }
}