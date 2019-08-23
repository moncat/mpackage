package com.co.example.service.label.impl;

import com.co.example.dao.label.TBrLabelClassDao;
import com.co.example.entity.label.TBrLabelClass;
import com.co.example.service.label.TBrLabelClassService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrLabelClassServiceImpl extends BaseServiceImpl<TBrLabelClass, Long> implements TBrLabelClassService {
    @Resource
    private TBrLabelClassDao tBrLabelClassDao;

    @Override
    protected BaseDao<TBrLabelClass, Long> getBaseDao() {
        return tBrLabelClassDao;
    }
}