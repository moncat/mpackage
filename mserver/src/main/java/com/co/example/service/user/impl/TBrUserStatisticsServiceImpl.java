package com.co.example.service.user.impl;

import com.co.example.dao.user.TBrUserStatisticsDao;
import com.co.example.entity.user.TBrUserStatistics;
import com.co.example.service.user.TBrUserStatisticsService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrUserStatisticsServiceImpl extends BaseServiceImpl<TBrUserStatistics, Long> implements TBrUserStatisticsService {
    @Resource
    private TBrUserStatisticsDao tBrUserStatisticsDao;

    @Override
    protected BaseDao<TBrUserStatistics, Long> getBaseDao() {
        return tBrUserStatisticsDao;
    }
}