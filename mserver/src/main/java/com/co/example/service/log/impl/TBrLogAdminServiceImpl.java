package com.co.example.service.log.impl;

import com.co.example.dao.log.TBrLogAdminDao;
import com.co.example.entity.log.TBrLogAdmin;
import com.co.example.service.log.TBrLogAdminService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrLogAdminServiceImpl extends BaseServiceImpl<TBrLogAdmin, Long> implements TBrLogAdminService {
    @Resource
    private TBrLogAdminDao tBrLogAdminDao;

    @Override
    protected BaseDao<TBrLogAdmin, Long> getBaseDao() {
        return tBrLogAdminDao;
    }
}