package com.co.example.service.mall;

import java.math.BigDecimal;
import java.util.List;

import com.co.example.entity.mall.TBrMallMonth;
import com.github.moncat.common.service.BaseService;

public interface TBrMallMonthService extends BaseService<TBrMallMonth, Long> {
	
	List<Integer> getMonthSalesByProductId(Long id);
	List<BigDecimal> getMonthPricesByProductId(Long id);
	
}