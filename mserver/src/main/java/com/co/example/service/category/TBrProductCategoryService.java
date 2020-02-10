package com.co.example.service.category;

import com.co.example.entity.category.TBrProductCategory;
import com.github.moncat.common.service.BaseService;

public interface TBrProductCategoryService extends BaseService<TBrProductCategory, Long> {
	
	int deleteProductCategory(Long productId,  Long categoryId);
}