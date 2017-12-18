package com.co.example.service.label.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.dao.label.TBrLabelDao;
import com.co.example.entity.label.TBrLabel;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.label.TBrLabelService;
import com.co.example.service.label.TBrProductLabelService;
import com.co.example.service.product.TBrProductService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.entity.BaseEntity;
import com.github.moncat.common.service.BaseServiceImpl;
import com.google.common.collect.Lists;

@Service
public class TBrLabelServiceImpl extends BaseServiceImpl<TBrLabel, Long> implements TBrLabelService {
    @Resource
    private TBrLabelDao tBrLabelDao;

    @Override
    protected BaseDao<TBrLabel, Long> getBaseDao() {
        return tBrLabelDao;
    }
    
    @Resource
    TBrProductService tBrProductService;
    
    @Resource
    TBrProductLabelService tBrProductLabelService;

	@Override
	public int addConnect2Product(TBrLabel label) {
		List<TBrProductLabel> tBrProductLabelList = Lists.newArrayList();
		TBrProductLabel tBrProductLabel = null;
		String name = label.getName();
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setProductNameLike(name);
		List<TBrProduct> list = tBrProductService.queryList(tBrProductQuery);
		int size = list.size();
		if(size >0){
			for (TBrProduct tBrProduct : list) {
				tBrProductLabel = new TBrProductLabel();
				tBrProductLabel.setLabelId(label.getId());
				tBrProductLabel.setProductId(tBrProduct.getId());
				setDefaultData((BaseEntity)tBrProductLabel);
				tBrProductLabelList.add(tBrProductLabel);
			}
			tBrProductLabelService.addInBatch(tBrProductLabelList);
		}
		return size;
	}
	
	private void setDefaultData(BaseEntity be) {
		be.setCreateTime(new Date());
		be.setDelFlg(Constant.NO);
		be.setIsActive(Constant.STATUS_ACTIVE);
	}
}