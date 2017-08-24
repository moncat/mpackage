package com.co.example.service.product.impl;

import com.co.example.dao.product.TBrProductImageDao;
import com.co.example.entity.product.TBrProductImage;
import com.co.example.service.product.TBrProductImageService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrProductImageServiceImpl extends BaseServiceImpl<TBrProductImage, Long> implements TBrProductImageService {
    @Resource
    private TBrProductImageDao tBrProductImageDao;

    @Override
    protected BaseDao<TBrProductImage, Long> getBaseDao() {
        return tBrProductImageDao;
    }
}