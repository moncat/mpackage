package com.co.example.service.export.impl;

import com.co.example.dao.export.TBrExportDao;
import com.co.example.entity.export.TBrExport;
import com.co.example.service.export.TBrExportService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrExportServiceImpl extends BaseServiceImpl<TBrExport, Long> implements TBrExportService {
    @Resource
    private TBrExportDao tBrExportDao;

    @Override
    protected BaseDao<TBrExport, Long> getBaseDao() {
        return tBrExportDao;
    }
}