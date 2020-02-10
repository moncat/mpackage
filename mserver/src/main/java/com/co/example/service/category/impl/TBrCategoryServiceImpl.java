package com.co.example.service.category.impl;

import com.co.example.dao.category.TBrCategoryDao;
import com.co.example.entity.category.TBrCategory;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductVo;
import com.co.example.service.category.TBrCategoryService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TBrCategoryServiceImpl extends BaseServiceImpl<TBrCategory, Long> implements TBrCategoryService {
    @Resource
    private TBrCategoryDao tBrCategoryDao;

    @Override
    protected BaseDao<TBrCategory, Long> getBaseDao() {
        return tBrCategoryDao;
    }

	@Override
	public HashSet<Long> getCategorykeysByProductList(List<TBrProductVo> productList) {
		HashSet<Long> categorykeys = Sets.newHashSet();
		for (TBrProduct tBrProduct : productList) {
			categorykeys.add(tBrProduct.getCategoryId());
		}
		categorykeys.remove(null);
		return categorykeys;
	}
}