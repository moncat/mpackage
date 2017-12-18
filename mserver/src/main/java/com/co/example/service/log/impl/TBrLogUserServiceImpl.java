package com.co.example.service.log.impl;

import com.co.example.dao.log.TBrLogUserDao;
import com.co.example.entity.log.TBrLogUser;
import com.co.example.service.log.TBrLogUserService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrLogUserServiceImpl extends BaseServiceImpl<TBrLogUser, Long> implements TBrLogUserService {
    @Resource
    private TBrLogUserDao tBrLogUserDao;

    @Override
    protected BaseDao<TBrLogUser, Long> getBaseDao() {
        return tBrLogUserDao;
    }
}