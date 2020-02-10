package com.co.example.service.manifest.impl;

import com.co.example.dao.manifest.TBrManifestEnterpriseDao;
import com.co.example.entity.manifest.TBrManifestEnterprise;
import com.co.example.service.manifest.TBrManifestEnterpriseService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrManifestEnterpriseServiceImpl extends BaseServiceImpl<TBrManifestEnterprise, Long> implements TBrManifestEnterpriseService {
    @Resource
    private TBrManifestEnterpriseDao tBrManifestEnterpriseDao;

    @Override
    protected BaseDao<TBrManifestEnterprise, Long> getBaseDao() {
        return tBrManifestEnterpriseDao;
    }
}