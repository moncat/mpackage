package com.co.example.service.taobao.impl;

import com.co.example.dao.taobao.TBrTaobaoSortDao;
import com.co.example.entity.taobao.TBrTaobaoSort;
import com.co.example.service.taobao.TBrTaobaoSortService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrTaobaoSortServiceImpl extends BaseServiceImpl<TBrTaobaoSort, Long> implements TBrTaobaoSortService {
    @Resource
    private TBrTaobaoSortDao tBrTaobaoSortDao;

    @Override
    protected BaseDao<TBrTaobaoSort, Long> getBaseDao() {
        return tBrTaobaoSortDao;
    }
}