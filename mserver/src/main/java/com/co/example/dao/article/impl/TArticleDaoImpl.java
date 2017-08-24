package com.co.example.dao.article.impl;

import com.github.moncat.common.dao.BaseDaoImpl;
import com.co.example.dao.article.TArticleDao;
import com.co.example.entity.article.TArticle;
import org.springframework.stereotype.Repository;

@Repository
public class TArticleDaoImpl extends BaseDaoImpl<TArticle, Integer> implements TArticleDao {
}