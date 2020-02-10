package com.co.example.service.region.impl;

import com.co.example.dao.region.TBrRegionShortDao;
import com.co.example.entity.region.TBrRegionShort;
import com.co.example.service.region.TBrRegionShortService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrRegionShortServiceImpl extends BaseServiceImpl<TBrRegionShort, Long> implements TBrRegionShortService {
    @Resource
    private TBrRegionShortDao tBrRegionShortDao;

    @Override
    protected BaseDao<TBrRegionShort, Long> getBaseDao() {
        return tBrRegionShortDao;
    }
}