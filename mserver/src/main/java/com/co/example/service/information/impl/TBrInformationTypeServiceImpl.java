package com.co.example.service.information.impl;

import com.co.example.dao.information.TBrInformationTypeDao;
import com.co.example.entity.information.TBrInformationType;
import com.co.example.service.information.TBrInformationTypeService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrInformationTypeServiceImpl extends BaseServiceImpl<TBrInformationType, Long> implements TBrInformationTypeService {
    @Resource
    private TBrInformationTypeDao tBrInformationTypeDao;

    @Override
    protected BaseDao<TBrInformationType, Long> getBaseDao() {
        return tBrInformationTypeDao;
    }
}