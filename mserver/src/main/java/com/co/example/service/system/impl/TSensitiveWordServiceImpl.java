package com.co.example.service.system.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.co.example.dao.system.TSensitiveWordDao;
import com.co.example.entity.system.TSensitiveWord;
import com.co.example.entity.system.aide.TSensitiveWordQuery;
import com.co.example.service.system.TSensitiveWordService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TSensitiveWordServiceImpl extends BaseServiceImpl<TSensitiveWord, Long> implements TSensitiveWordService {
    @Resource
    private TSensitiveWordDao tSensitiveWordDao;

    @Override
    protected BaseDao<TSensitiveWord, Long> getBaseDao() {
        return tSensitiveWordDao;
    }

	@Override
	public Boolean passByWord(String info) {
		if(StringUtils.isBlank(info)){
			return true;
		}
		TSensitiveWordQuery tSensitiveWordQuery = new TSensitiveWordQuery();
		tSensitiveWordQuery.setDelFlg(Constant.NO);
		List<TSensitiveWord> list = queryList(tSensitiveWordQuery);
		for (TSensitiveWord tSensitiveWord : list) {
			if(StringUtils.contains(info, tSensitiveWord.getName())){
				return false;
			}
		}
		return true;
	}
}