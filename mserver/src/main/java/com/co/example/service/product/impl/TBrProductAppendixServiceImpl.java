package com.co.example.service.product.impl;

import com.co.example.dao.product.TBrProductAppendixDao;
import com.co.example.entity.product.TBrProductAppendix;
import com.co.example.service.product.TBrProductAppendixService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrProductAppendixServiceImpl extends BaseServiceImpl<TBrProductAppendix, Long> implements TBrProductAppendixService {
    @Resource
    private TBrProductAppendixDao tBrProductAppendixDao;

    @Override
    protected BaseDao<TBrProductAppendix, Long> getBaseDao() {
        return tBrProductAppendixDao;
    }
}