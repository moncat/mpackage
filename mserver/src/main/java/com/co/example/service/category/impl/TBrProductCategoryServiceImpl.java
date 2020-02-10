package com.co.example.service.category.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.dao.category.TBrProductCategoryDao;
import com.co.example.entity.category.TBrProductCategory;
import com.co.example.entity.category.aide.TBrProductCategoryQuery;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.category.TBrProductCategoryService;
import com.co.example.service.product.TBrProductService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TBrProductCategoryServiceImpl extends BaseServiceImpl<TBrProductCategory, Long> implements TBrProductCategoryService {
    @Resource
    private TBrProductCategoryDao tBrProductCategoryDao;
    @Resource
    private TBrProductService tBrProductService;

    @Override
    protected BaseDao<TBrProductCategory, Long> getBaseDao() {
        return tBrProductCategoryDao;
    }

	@Override
	public int deleteProductCategory(Long productId, Long categoryId) {
		TBrProductCategoryQuery tBrProductCategoryQuery = new TBrProductCategoryQuery();
		tBrProductCategoryQuery.setCategoryId(categoryId);
		tBrProductCategoryQuery.setProductId(productId);
		delete(tBrProductCategoryQuery);
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setId(productId);
		tBrProductQuery.setCategoryId(0l);
		tBrProductQuery.setCategoryName("");
		tBrProductService.updateByIdSelective(tBrProductQuery);
		return 1;
	}
}