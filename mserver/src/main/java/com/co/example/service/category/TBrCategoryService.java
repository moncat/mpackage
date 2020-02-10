package com.co.example.service.category;

import java.util.HashSet;
import java.util.List;

import com.co.example.entity.category.TBrCategory;
import com.co.example.entity.product.aide.TBrProductVo;
import com.github.moncat.common.service.BaseService;

public interface TBrCategoryService extends BaseService<TBrCategory, Long> {
	
	HashSet<Long> getCategorykeysByProductList(List<TBrProductVo> productList) ;
}