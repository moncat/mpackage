package com.co.example.service.user.impl;

import com.co.example.dao.user.TBrUserAddressDao;
import com.co.example.entity.user.TBrUserAddress;
import com.co.example.service.user.TBrUserAddressService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrUserAddressServiceImpl extends BaseServiceImpl<TBrUserAddress, Long> implements TBrUserAddressService {
    @Resource
    private TBrUserAddressDao tBrUserAddressDao;

    @Override
    protected BaseDao<TBrUserAddress, Long> getBaseDao() {
        return tBrUserAddressDao;
    }
}