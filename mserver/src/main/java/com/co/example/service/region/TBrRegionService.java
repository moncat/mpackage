package com.co.example.service.region;

import java.util.List;

import com.co.example.entity.product.TBrEnterprise;
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
	
	
	/**
	 * 全量，根据产品名称对比
	 */
	void setEnterpriseRegion();
	/**
	 * 全量，根据产品备案缩写对比
	 */
	void setEnterpriseRegionByShort();
	
	/**
	 * 抓取时，单个对比，根据产品名称、产品备案缩写对比,对比每个企业
	 */
	void setEnterpriseRegionByAll(TBrEnterprise tBrEnterprise);
	
}