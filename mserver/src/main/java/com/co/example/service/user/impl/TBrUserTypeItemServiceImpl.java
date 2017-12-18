package com.co.example.service.user.impl;

import com.co.example.dao.user.TBrUserTypeItemDao;
import com.co.example.entity.user.TBrUserTypeItem;
import com.co.example.service.user.TBrUserTypeItemService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrUserTypeItemServiceImpl extends BaseServiceImpl<TBrUserTypeItem, Long> implements TBrUserTypeItemService {
    @Resource
    private TBrUserTypeItemDao tBrUserTypeItemDao;

    @Override
    protected BaseDao<TBrUserTypeItem, Long> getBaseDao() {
        return tBrUserTypeItemDao;
    }
}