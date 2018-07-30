package com.co.example.service.question.impl;

import com.co.example.dao.question.TBrQuestionOptionDao;
import com.co.example.entity.question.TBrQuestionOption;
import com.co.example.entity.question.aide.QuestionConstant;
import com.co.example.entity.question.aide.TBrQuestionQuery;
import com.co.example.service.question.TBrQuestionOptionService;
import com.co.example.utils.BaseDataUtil;
import com.github.moncat.common.dao.BaseDao;
import com.github.moncat.common.service.BaseServiceImpl;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class TBrQuestionOptionServiceImpl extends BaseServiceImpl<TBrQuestionOption, Long> implements TBrQuestionOptionService {
    @Resource
    private TBrQuestionOptionDao tBrQuestionOptionDao;

    @Override
    protected BaseDao<TBrQuestionOption, Long> getBaseDao() {
        return tBrQuestionOptionDao;
    }
	
	public int addQuestionOption(TBrQuestionQuery question) {
		Long id = question.getId();
		Byte video =1;
		TBrQuestionOption tBrQuestionOption = new TBrQuestionOption();
		tBrQuestionOption.setQuestionId(id);
		tBrQuestionOption.setType(video);
		BaseDataUtil.setDefaultData(tBrQuestionOption);
		
		if(StringUtils.isNotBlank(question.getOptionA())){
			tBrQuestionOption.setName(question.getOptionA());
			tBrQuestionOption.setGrade(QuestionConstant.OPTION_1_GRADE);
			add(tBrQuestionOption);
		}
		
		if(StringUtils.isNotBlank(question.getOptionB())){
			tBrQuestionOption.setName(question.getOptionB());
			tBrQuestionOption.setGrade(QuestionConstant.OPTION_2_GRADE);
			add(tBrQuestionOption);
		}
		
		if(StringUtils.isNotBlank(question.getOptionC())){
			tBrQuestionOption.setName(question.getOptionC());
			tBrQuestionOption.setGrade(QuestionConstant.OPTION_3_GRADE);
			add(tBrQuestionOption);
		}
		
		if(StringUtils.isNotBlank(question.getOptionD())){
			tBrQuestionOption.setName(question.getOptionD());
			tBrQuestionOption.setGrade(QuestionConstant.OPTION_4_GRADE);
			add(tBrQuestionOption);
		}
		if(StringUtils.isNotBlank(question.getOptionE())){
			tBrQuestionOption.setName(question.getOptionE());
			tBrQuestionOption.setGrade(QuestionConstant.OPTION_2P5_GRADE);
			add(tBrQuestionOption);
		}
		
		if(StringUtils.isNotBlank(question.getOptionF())){
			tBrQuestionOption.setName(question.getOptionF());
			tBrQuestionOption.setGrade(QuestionConstant.OPTION_5_GRADE);
			add(tBrQuestionOption);
		}
		
		if(StringUtils.isNotBlank(question.getOptionG())){
			tBrQuestionOption.setName(question.getOptionG());
			tBrQuestionOption.setGrade(QuestionConstant.OPTION_0_GRADE);
			add(tBrQuestionOption);
		}
		
		return 0;
	}
		
}