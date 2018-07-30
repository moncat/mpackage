package com.co.example.entity.question.aide;

import java.util.List;

import com.co.example.entity.question.TBrQuestion;
import com.co.example.entity.question.TBrQuestionOption;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class TBrQuestionVo extends TBrQuestion {
	
	private List<TBrQuestionOption> optionList;
}