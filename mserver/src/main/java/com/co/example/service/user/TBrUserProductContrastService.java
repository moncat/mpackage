package com.co.example.service.user;

import java.util.Map;

import com.co.example.entity.user.TBrUserProductContrast;
import com.co.example.entity.user.aide.TBrUserProductContrastPriceDto;
import com.github.moncat.common.service.BaseService;

public interface TBrUserProductContrastService extends BaseService<TBrUserProductContrast, Long> {


	/**
	 * 价格及比率
	 * @param id1
	 * @param id2
	 * @return
	 */
	Map<Long,TBrUserProductContrastPriceDto> contrastPrice(Long id1  ,Long id2);
	
}