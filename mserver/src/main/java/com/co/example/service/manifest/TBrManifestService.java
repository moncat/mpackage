package com.co.example.service.manifest;

import com.co.example.entity.manifest.TBrManifest;
import com.github.moncat.common.service.BaseService;

public interface TBrManifestService extends BaseService<TBrManifest, Long> {
	
	Boolean addAndAuth(TBrManifest t , Long adminId) ;
	
	/**
	 * 清单数据总计算，将data中的数据计算到result中
	 * @param tBrManifest 待处理清单
	 */
	void queryManifest(TBrManifest tBrManifest);
}