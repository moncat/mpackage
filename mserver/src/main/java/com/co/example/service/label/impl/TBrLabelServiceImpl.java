package com.co.example.service.label.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.dao.label.TBrLabelDao;
import com.co.example.entity.label.TBrLabel;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.label.aide.TBrLabelQuery;
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

	@Override
	public String queryLabelsByProductId(Long id) {
		List<TBrLabel> selectList = queryLabelListByProductId(id);
		String collect = selectList.stream().map(TBrLabel::getName).collect(Collectors.joining(","));
		return collect;
	}
	

	@Override
	public List<TBrLabel> queryLabelListByProductId(Long id) {
		TBrLabelQuery tBrLabelQuery = new TBrLabelQuery();
		tBrLabelQuery.setProductJoinFlg(true);
		tBrLabelQuery.setProductId(id);
		List<TBrLabel> selectList = tBrLabelDao.selectList(tBrLabelQuery);	
		return selectList;
	}
	
	public static void main(String[] args) {
		List<TBrLabel> selectList = new ArrayList<TBrLabel>();
		
		TBrLabel tBrLabel = new TBrLabel();
		tBrLabel.setName("aaa");
		selectList.add(tBrLabel);
		
		TBrLabel tBrLabel2 = new TBrLabel();
		tBrLabel2.setName("bbb");
		selectList.add(tBrLabel2);
		
		TBrLabel tBrLabel3 = new TBrLabel();
		tBrLabel3.setName("ccc");
		selectList.add(tBrLabel3);
		
		String collect = selectList.stream().map(TBrLabel::getName).collect(Collectors.joining(","));
		System.out.println(collect);
	}

}








