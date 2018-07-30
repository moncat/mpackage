package com.co.example.service.label;

import java.util.List;

import org.springframework.data.domain.Page;

import com.co.example.common.utils.PageReq;
import com.co.example.entity.label.TBrProductLabel;
import com.github.moncat.common.service.BaseService;

public interface TBrProductLabelService extends BaseService<TBrProductLabel, Long> {
	
	/**
	 * 根据商品Id 获得商品标签列表
	 * @return
	 */
	List<TBrProductLabel> getProductLabels(Long productId);
	
	/**
	 * 使用 用户标签关联表   产品标签关联表 二者进行关联获得 产品id数组
	 * 前台接收产品id数组，产品其他信息则异步获取，
	 * @param labelId
	 * @param userId
	 * @param pageReq
	 * @return
	 * 
	 */
	Page<TBrProductLabel> queryByLabel(Long labelId,Long userId,PageReq pageReq);
	
	
	
}