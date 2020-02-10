package com.co.example.service.enterprise.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.dao.enterprise.TBrEnterpriseBaseDao;
import com.co.example.entity.enterprise.TBrEnterpriseBase;
import com.co.example.entity.enterprise.aide.TBrEnterpriseBaseQuery;
import com.co.example.service.enterprise.TBrEnterpriseBaseService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TBrEnterpriseBaseServiceImpl extends BaseServiceImpl<TBrEnterpriseBase, Long> implements TBrEnterpriseBaseService {
    @Resource
    private TBrEnterpriseBaseDao tBrEnterpriseBaseDao;

    @Override
    protected BaseDao<TBrEnterpriseBase, Long> getBaseDao() {
        return tBrEnterpriseBaseDao;
    }

	@Override
	public TBrEnterpriseBase queryEnterpriseBaseServiceByEId(Long id) {
		TBrEnterpriseBaseQuery tBrEnterpriseBaseQuery = new TBrEnterpriseBaseQuery();
		tBrEnterpriseBaseQuery.setEid(id);
		return queryOne(tBrEnterpriseBaseQuery);
	}
}