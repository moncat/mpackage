package com.co.example.service.manifest.impl;

import com.co.example.dao.manifest.TBrManifestResultDao;
import com.co.example.entity.manifest.TBrManifestResult;
import com.co.example.service.manifest.TBrManifestResultService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrManifestResultServiceImpl extends BaseServiceImpl<TBrManifestResult, Long> implements TBrManifestResultService {
    @Resource
    private TBrManifestResultDao tBrManifestResultDao;

    @Override
    protected BaseDao<TBrManifestResult, Long> getBaseDao() {
        return tBrManifestResultDao;
    }
}