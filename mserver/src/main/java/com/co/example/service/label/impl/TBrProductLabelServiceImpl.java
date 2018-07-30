package com.co.example.service.label.impl;

import com.co.example.common.utils.PageReq;
import com.co.example.dao.label.TBrProductLabelDao;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.label.aide.TBrProductLabelQuery;
import com.co.example.service.label.TBrProductLabelService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TBrProductLabelServiceImpl extends BaseServiceImpl<TBrProductLabel, Long> implements TBrProductLabelService {
    @Resource
    private TBrProductLabelDao tBrProductLabelDao;

    @Override
    protected BaseDao<TBrProductLabel, Long> getBaseDao() {
        return tBrProductLabelDao;
    }

	@Override
	public List<TBrProductLabel> getProductLabels(Long productId) {
		TBrProductLabelQuery tBrProductLabelQuery = new TBrProductLabelQuery();
		tBrProductLabelQuery.setProductId(productId);
		tBrProductLabelQuery.setJoinLabelFlg(true);
		List<TBrProductLabel> list = queryList(tBrProductLabelQuery);
		return list;
	}

	@Override
	public Page<TBrProductLabel> queryByLabel(Long labelId, Long userId, PageReq pageReq) {
		return tBrProductLabelDao.queryByLabel(labelId, userId, pageReq);
	} 
}