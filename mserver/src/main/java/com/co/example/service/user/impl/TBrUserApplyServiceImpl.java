package com.co.example.service.user.impl;

import com.co.example.dao.user.TBrUserApplyDao;
import com.co.example.entity.user.TBrUserApply;
import com.co.example.service.user.TBrUserApplyService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrUserApplyServiceImpl extends BaseServiceImpl<TBrUserApply, Long> implements TBrUserApplyService {
    @Resource
    private TBrUserApplyDao tBrUserApplyDao;

    @Override
    protected BaseDao<TBrUserApply, Long> getBaseDao() {
        return tBrUserApplyDao;
    }
}