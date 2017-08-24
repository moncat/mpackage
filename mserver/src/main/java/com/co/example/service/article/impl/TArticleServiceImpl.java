package com.co.example.service.article.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.common.constant.Constant;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import com.co.example.dao.article.TArticleDao;
import com.co.example.entity.article.TArticle;
import com.co.example.entity.article.aide.TArticleQuery;
import com.co.example.service.article.TArticleService;

@Service
public class TArticleServiceImpl extends BaseServiceImpl<TArticle, Integer> implements TArticleService {
    @Resource
    private TArticleDao tArticleDao;

    @Override
    protected BaseDao<TArticle, Integer> getBaseDao() {
        return tArticleDao;
    }

	@Override
	public void setTop(TArticleQuery tArticleQuery) {
		List<TArticle> list = queryList();
		list.forEach(one -> one.setIsTop(Constant.NO));
		updateInBatch(list);
		tArticleQuery.setIsTop(Constant.YES);
		updateByIdSelective(tArticleQuery);
	}
}