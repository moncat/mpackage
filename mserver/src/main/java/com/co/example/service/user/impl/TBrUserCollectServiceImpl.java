package com.co.example.service.user.impl;

import com.co.example.dao.user.TBrUserCollectDao;
import com.co.example.entity.user.TBrUserCollect;
import com.co.example.service.user.TBrUserCollectService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrUserCollectServiceImpl extends BaseServiceImpl<TBrUserCollect, Long> implements TBrUserCollectService {
    @Resource
    private TBrUserCollectDao tBrUserCollectDao;

    @Override
    protected BaseDao<TBrUserCollect, Long> getBaseDao() {
        return tBrUserCollectDao;
    }
}