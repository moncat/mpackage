package com.co.example.service.advertisement.impl;

import com.co.example.dao.advertisement.TBrAdvertisementDao;
import com.co.example.entity.advertisement.TBrAdvertisement;
import com.co.example.service.advertisement.TBrAdvertisementService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrAdvertisementServiceImpl extends BaseServiceImpl<TBrAdvertisement, Long> implements TBrAdvertisementService {
    @Resource
    private TBrAdvertisementDao tBrAdvertisementDao;

    @Override
    protected BaseDao<TBrAdvertisement, Long> getBaseDao() {
        return tBrAdvertisementDao;
    }
}