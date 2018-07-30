package com.co.example.service.product.impl;

import com.co.example.dao.product.TBrRecommendDao;
import com.co.example.entity.product.TBrRecommend;
import com.co.example.service.product.TBrRecommendService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrRecommendServiceImpl extends BaseServiceImpl<TBrRecommend, Long> implements TBrRecommendService {
    @Resource
    private TBrRecommendDao tBrRecommendDao;

    @Override
    protected BaseDao<TBrRecommend, Long> getBaseDao() {
        return tBrRecommendDao;
    }
}