package com.co.example.service.manifest.impl;

import com.co.example.dao.manifest.TBrManifestAuthDao;
import com.co.example.entity.manifest.TBrManifestAuth;
import com.co.example.service.manifest.TBrManifestAuthService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrManifestAuthServiceImpl extends BaseServiceImpl<TBrManifestAuth, Long> implements TBrManifestAuthService {
    @Resource
    private TBrManifestAuthDao tBrManifestAuthDao;

    @Override
    protected BaseDao<TBrManifestAuth, Long> getBaseDao() {
        return tBrManifestAuthDao;
    }
}