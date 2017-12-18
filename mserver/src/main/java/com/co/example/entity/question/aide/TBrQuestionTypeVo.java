package com.co.example.entity.question.aide;

import java.util.List;

import com.co.example.entity.question.TBrQuestionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrQuestionTypeVo extends TBrQuestionType {
	private List<TBrQuestionType> questionTypes;
}