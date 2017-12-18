package com.co.example.service.user.impl;

import com.co.example.dao.user.TBrUserProductContrastDao;
import com.co.example.entity.user.TBrUserProductContrast;
import com.co.example.service.user.TBrUserProductContrastService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrUserProductContrastServiceImpl extends BaseServiceImpl<TBrUserProductContrast, Long> implements TBrUserProductContrastService {
    @Resource
    private TBrUserProductContrastDao tBrUserProductContrastDao;

    @Override
    protected BaseDao<TBrUserProductContrast, Long> getBaseDao() {
        return tBrUserProductContrastDao;
    }
}