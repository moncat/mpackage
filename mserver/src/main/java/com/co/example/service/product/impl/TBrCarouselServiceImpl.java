package com.co.example.service.product.impl;

import com.co.example.dao.product.TBrCarouselDao;
import com.co.example.entity.product.TBrCarousel;
import com.co.example.service.product.TBrCarouselService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrCarouselServiceImpl extends BaseServiceImpl<TBrCarousel, Long> implements TBrCarouselService {
    @Resource
    private TBrCarouselDao tBrCarouselDao;

    @Override
    protected BaseDao<TBrCarousel, Long> getBaseDao() {
        return tBrCarouselDao;
    }
}