package com.co.example.service.mall.impl;

import com.co.example.common.utils.DateUtil;
import com.co.example.dao.mall.TBrMallMonthDao;
import com.co.example.entity.mall.TBrMallMonth;
import com.co.example.entity.mall.aide.TBrMallMonthQuery;
import com.co.example.service.mall.TBrMallMonthService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class TBrMallMonthServiceImpl extends BaseServiceImpl<TBrMallMonth, Long> implements TBrMallMonthService {
    @Resource
    private TBrMallMonthDao tBrMallMonthDao;

    @Override
    protected BaseDao<TBrMallMonth, Long> getBaseDao() {
        return tBrMallMonthDao;
    }
    
    @Override
	public List<Integer> getMonthSalesByProductId(Long id) {
		TBrMallMonthQuery tBrMallMonthQuery = new TBrMallMonthQuery();
		tBrMallMonthQuery.setProductId(id);
		//设置日期大于的时间
		List<TBrMallMonth> mallList = queryList(tBrMallMonthQuery, new Sort(Direction.ASC,"t.month"));
		List<String> monthsOnYear = DateUtil.getMonthsOnYear();
		List<Integer> sales = Lists.newArrayList();
		Integer saleTmp = 0;
		for (String str : monthsOnYear) {
			for (TBrMallMonth tBrMallMonth : mallList) {
				String month = tBrMallMonth.getMonth();
				//销量求和
				if(str.equals(month)){
					if(saleTmp == 0){
						saleTmp = tBrMallMonth.getSale();
					}else{
						saleTmp = saleTmp + tBrMallMonth.getSale();      
					}
				}
			}
			sales.add(saleTmp);
			saleTmp= 0;
		}
		return sales;
	}

	@Override
	public List<BigDecimal> getMonthPricesByProductId(Long id) {
		TBrMallMonthQuery tBrMallMonthQuery = new TBrMallMonthQuery();
		tBrMallMonthQuery.setProductId(id);
		//设置日期大于的时间
		List<TBrMallMonth> mallList = queryList(tBrMallMonthQuery, new Sort(Direction.ASC,"t.month"));
		List<String> monthsOnYear = DateUtil.getMonthsOnYear();
		List<BigDecimal> prices = Lists.newArrayList();
		BigDecimal priceTmp = BigDecimal.valueOf(0);
		for (String str : monthsOnYear) {
			for (TBrMallMonth tBrMallMonth : mallList) {
				String month = tBrMallMonth.getMonth();
				//销量求和
				if(str.equals(month)){
					BigDecimal multiply = tBrMallMonth.getPrice().multiply(BigDecimal.valueOf(tBrMallMonth.getSale()));
					if(priceTmp == BigDecimal.valueOf(0)){
						priceTmp = multiply;
					}else{
						priceTmp = priceTmp.add(multiply); 
					}
				}
			}
			prices.add(priceTmp);
			priceTmp = BigDecimal.valueOf(0);
		}
		return prices;
	}
}