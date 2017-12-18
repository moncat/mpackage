package com.co.example.service.user.impl;

import com.co.example.dao.user.TBrUserReportItemDao;
import com.co.example.entity.user.TBrUserReportItem;
import com.co.example.service.user.TBrUserReportItemService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrUserReportItemServiceImpl extends BaseServiceImpl<TBrUserReportItem, Long> implements TBrUserReportItemService {
    @Resource
    private TBrUserReportItemDao tBrUserReportItemDao;

    @Override
    protected BaseDao<TBrUserReportItem, Long> getBaseDao() {
        return tBrUserReportItemDao;
    }
}