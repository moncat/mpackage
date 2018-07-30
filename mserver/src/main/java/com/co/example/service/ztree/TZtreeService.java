package com.co.example.service.ztree;

import com.co.example.entity.ztree.TZtree;
import com.github.moncat.common.service.BaseService;

public interface TZtreeService extends BaseService<TZtree, Long> {
	
	
	void addNow(TZtree tree);
	
	
	void updateDrag(TZtree tree);
	
}