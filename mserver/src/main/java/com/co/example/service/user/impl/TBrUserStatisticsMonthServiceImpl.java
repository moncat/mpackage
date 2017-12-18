package com.co.example.service.user.impl;

import com.co.example.dao.user.TBrUserStatisticsMonthDao;
import com.co.example.entity.user.TBrUserStatisticsMonth;
import com.co.example.service.user.TBrUserStatisticsMonthService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrUserStatisticsMonthServiceImpl extends BaseServiceImpl<TBrUserStatisticsMonth, Long> implements TBrUserStatisticsMonthService {
    @Resource
    private TBrUserStatisticsMonthDao tBrUserStatisticsMonthDao;

    @Override
    protected BaseDao<TBrUserStatisticsMonth, Long> getBaseDao() {
        return tBrUserStatisticsMonthDao;
    }
}