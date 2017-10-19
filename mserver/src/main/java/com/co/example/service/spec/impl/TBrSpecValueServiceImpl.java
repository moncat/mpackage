package com.co.example.service.spec.impl;

import com.co.example.dao.spec.TBrSpecValueDao;
import com.co.example.entity.spec.TBrSpecValue;
import com.co.example.service.spec.TBrSpecValueService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrSpecValueServiceImpl extends BaseServiceImpl<TBrSpecValue, Long> implements TBrSpecValueService {
    @Resource
    private TBrSpecValueDao tBrSpecValueDao;

    @Override
    protected BaseDao<TBrSpecValue, Long> getBaseDao() {
        return tBrSpecValueDao;
    }
}