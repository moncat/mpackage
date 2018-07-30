package com.co.example.service.user.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.dao.user.TBrUserProductContrastDao;
import com.co.example.entity.user.TBrUserProductContrast;
import com.co.example.entity.user.aide.TBrUserProductContrastPriceDto;
import com.co.example.service.comment.TBrProductCommentStatisticsService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.user.TBrUserProductContrastService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Maps;

@Service
public class TBrUserProductContrastServiceImpl extends BaseServiceImpl<TBrUserProductContrast, Long> implements TBrUserProductContrastService {
    @Resource
    private TBrUserProductContrastDao tBrUserProductContrastDao;
    
    @Resource
    TBrProductService tBrProductService;
    
    @Resource
    TBrProductCommentStatisticsService tBrProductCommentStatisticsService;

    @Override
    protected BaseDao<TBrUserProductContrast, Long> getBaseDao() {
        return tBrUserProductContrastDao;
    }

	@Override
	public Map<Long, TBrUserProductContrastPriceDto> contrastPrice(Long id1, Long id2) {
		HashMap<Long, TBrUserProductContrastPriceDto> map = Maps.newHashMap();
		BigDecimal price1 = tBrProductService.getCheapPrice(id1);
		BigDecimal price2 = tBrProductService.getCheapPrice(id2);
		if(price1!= null && price2!= null){
			TBrUserProductContrastPriceDto dto1 = new TBrUserProductContrastPriceDto();
			TBrUserProductContrastPriceDto dto2 = new TBrUserProductContrastPriceDto();
			BigDecimal count = price1.add(price2);
			BigDecimal divide1 = price1.divide(count, 2);
			BigDecimal divide2 = price2.divide(count, 2);
			dto1.setPrice(price1);
			dto1.setPercent(divide1);
			map.put(id1, dto1);
			dto2.setPrice(price2);
			dto2.setPercent(divide2);
			map.put(id2, dto2);
		}
		return map;
	}

}









