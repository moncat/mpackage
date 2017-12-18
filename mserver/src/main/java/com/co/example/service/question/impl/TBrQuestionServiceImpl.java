package com.co.example.service.question.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.co.example.dao.question.TBrQuestionDao;
import com.co.example.entity.question.TBrQuestion;
import com.co.example.entity.question.aide.TBrQuestionOptionQuery;
import com.co.example.entity.question.aide.TBrQuestionQuery;
import com.co.example.service.question.TBrQuestionOptionService;
import com.co.example.service.question.TBrQuestionService;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;

@Service
public class TBrQuestionServiceImpl extends BaseServiceImpl<TBrQuestion, Long> implements TBrQuestionService {
    @Resource
    private TBrQuestionDao tBrQuestionDao;

    @Override
    protected BaseDao<TBrQuestion, Long> getBaseDao() {
        return tBrQuestionDao;
    }
    
    @Resource
    TBrQuestionOptionService tBrQuestionOptionService;
    
    @Override
	public void editData(TBrQuestionQuery question) {
		updateByIdSelective(question);
		TBrQuestionOptionQuery tBrQuestionOptionQuery = new TBrQuestionOptionQuery();
		tBrQuestionOptionQuery.setQuestionId(question.getId());
		tBrQuestionOptionService.delete(tBrQuestionOptionQuery);
		tBrQuestionOptionService.addQuestionOption(question);
		
	}
    
   @Override
   public int addData(TBrQuestionQuery question){
	    add(question);
	    tBrQuestionOptionService.addQuestionOption(question);
		return 1;
   }
    
    



	
}