package com.co.example.service.article;

import com.co.example.common.service.BaseService;
import com.co.example.entity.article.TArticle;
import com.co.example.entity.article.aide.TArticleQuery;

public interface TArticleService extends BaseService<TArticle, Integer> {
	
	void setTop(TArticleQuery tArticleQuery);
}