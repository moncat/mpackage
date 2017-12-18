package com.co.example.service.user.impl;

import com.co.example.dao.user.TBrUserReportDao;
import com.co.example.entity.user.TBrUserReport;
import com.co.example.service.user.TBrUserReportService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrUserReportServiceImpl extends BaseServiceImpl<TBrUserReport, Long> implements TBrUserReportService {
    @Resource
    private TBrUserReportDao tBrUserReportDao;

    @Override
    protected BaseDao<TBrUserReport, Long> getBaseDao() {
        return tBrUserReportDao;
    }
}