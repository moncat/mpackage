package com.co.example.service.label.impl;

import com.co.example.dao.label.TBrProductLabelDao;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.service.label.TBrProductLabelService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrProductLabelServiceImpl extends BaseServiceImpl<TBrProductLabel, Long> implements TBrProductLabelService {
    @Resource
    private TBrProductLabelDao tBrProductLabelDao;

    @Override
    protected BaseDao<TBrProductLabel, Long> getBaseDao() {
        return tBrProductLabelDao;
    }
}