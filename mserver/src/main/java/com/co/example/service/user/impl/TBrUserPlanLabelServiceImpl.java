package com.co.example.service.user.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.co.example.dao.user.TBrUserPlanLabelDao;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.label.aide.TBrProductLabelQuery;
import com.co.example.entity.user.TBrUserPlanLabel;
import com.co.example.entity.user.aide.TBrUserPlanLabelQuery;
import com.co.example.service.label.TBrProductLabelService;
import com.co.example.service.user.TBrUserPlanLabelService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class TBrUserPlanLabelServiceImpl extends BaseServiceImpl<TBrUserPlanLabel, Long>
		implements TBrUserPlanLabelService {
	@Resource
	private TBrUserPlanLabelDao tBrUserPlanLabelDao;

	@Override
	protected BaseDao<TBrUserPlanLabel, Long> getBaseDao() {
		return tBrUserPlanLabelDao;
	}

	@Resource
	TBrUserPlanLabelService tBrUserPlanLabelService;

	@Resource
	TBrProductLabelService tBrProductLabelService;

	@Override
	public Map<String, Object> getMatching(Long userId, Long productId) {

		Map<String, Object> map = Maps.newHashMap();

		TBrUserPlanLabelQuery tBrUserPlanLabelQuery = new TBrUserPlanLabelQuery();
		tBrUserPlanLabelQuery.setCreateBy(userId);
		List<TBrUserPlanLabel> userlabelList = tBrUserPlanLabelService.queryList(tBrUserPlanLabelQuery);
		if(userlabelList.size()==0){
			map.put("type", 1);
			map.put("info", "您尚未进行肤质测试。");
			return map;
		}
		
		
		TBrProductLabelQuery tBrProductLabelQuery = new TBrProductLabelQuery();
		tBrProductLabelQuery.setProductId(productId);
		List<TBrProductLabel> productlabelList = tBrProductLabelService.queryList(tBrProductLabelQuery);
		if(productlabelList.size()==0){
			map.put("type", 2);
			map.put("info", "该产品暂无匹配信息。");
			return map;
		}
		
		Long p = 0l;
		Long u = 0l;
		List<Long> pl = Lists.newArrayList();		
		List<Long> ul = Lists.newArrayList();		
		
		for (TBrProductLabel tBrProductLabel : productlabelList) {
			p = tBrProductLabel.getLabelId();
			pl.add(p);
		}
		for (TBrUserPlanLabel tBrUserPlanLabel : userlabelList) {
			u = tBrUserPlanLabel.getLabelId();
			ul.add(u);
		}
		//取交集
		Collection<Long> intersection = CollectionUtils.intersection(pl, ul);
		int size = intersection.size();
		if(size >5){
			size =5;
		}
		map.put("type", 3);
		map.put("info", "匹配成功");
		map.put("data", size);
		
		return map;
	}
}












