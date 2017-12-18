package com.co.example.service.information.impl;

import com.co.example.dao.information.TBrInformationDao;
import com.co.example.entity.information.TBrInformation;
import com.co.example.service.information.TBrInformationService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrInformationServiceImpl extends BaseServiceImpl<TBrInformation, Long> implements TBrInformationService {
    @Resource
    private TBrInformationDao tBrInformationDao;

    @Override
    protected BaseDao<TBrInformation, Long> getBaseDao() {
        return tBrInformationDao;
    }
}