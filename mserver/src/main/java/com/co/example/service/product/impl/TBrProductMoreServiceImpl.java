package com.co.example.service.product.impl;

import com.co.example.dao.product.TBrProductMoreDao;
import com.co.example.entity.product.TBrProductMore;
import com.co.example.service.product.TBrProductMoreService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrProductMoreServiceImpl extends BaseServiceImpl<TBrProductMore, Long> implements TBrProductMoreService {
    @Resource
    private TBrProductMoreDao tBrProductMoreDao;

    @Override
    protected BaseDao<TBrProductMore, Long> getBaseDao() {
        return tBrProductMoreDao;
    }
}