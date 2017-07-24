package com.co.example.service.article.impl;

import com.co.example.common.dao.BaseDao;
import com.co.example.common.service.BaseServiceImpl;
import com.co.example.dao.article.TArticleDao;
import com.co.example.entity.article.TArticle;
import com.co.example.service.article.TArticleService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TArticleServiceImpl extends BaseServiceImpl<TArticle, Integer> implements TArticleService {
    @Resource
    private TArticleDao tArticleDao;

    @Override
    protected BaseDao<TArticle, Integer> getBaseDao() {
        return tArticleDao;
    }
}