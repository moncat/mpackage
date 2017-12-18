package com.co.example.service.region;

import java.util.List;

import com.co.example.entity.region.TBrRegion;
import com.github.moncat.common.service.BaseService;

public interface TBrRegionService extends BaseService<TBrRegion, Long> {
	
	void crawRegionData() throws InterruptedException;
	
	/**
	 * 修正部分GB2312编码导致的无法解码问题
	 * @param tBrRegion
	 * @throws InterruptedException
	 */
	void findData(TBrRegion tBrRegion) throws InterruptedException;
	
	/**
	 * 根据父地区Id获得地区列表
	 * @param id
	 * @return
	 */
	List<TBrRegion> getRegionListByParentRegionId(String id);
	
	
}