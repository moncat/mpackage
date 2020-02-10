package com.co.example.service.enterprise;

import java.util.Map;

import com.co.example.entity.enterprise.TBrEnterprisePermission;
import com.github.moncat.common.service.BaseService;

public interface TBrEnterprisePermissionService extends BaseService<TBrEnterprisePermission, Long> {
	
	/**
	 * 抓取生产企业许可信息
	 * @param i 抓取开始页
	 * @param dateStr 开始时间限制，为增量抓取
	 * @param idLimit 该网站json中唯一id限制，作为去重方案
	 * @return
	 */
	int getEnterprisePermission(int i,String dateStr ,String idLimit);
	
	
	/**
	 * 解析王永美抓取的网页xls
	 * @param line 每一行数据
	 * @return
	 */
	int getEnterprisePermission(String line);
	int getEnterprisePermissionJson(Map<String, String> map);
	
	TBrEnterprisePermission queryVoByEId(Long id);
}