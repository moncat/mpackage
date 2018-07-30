package com.co.example.entity.question.aide;

import java.util.List;

import com.co.example.entity.question.TBrQuestionTypeLabel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class TBrQuestionTypeLabelQuery extends TBrQuestionTypeLabel {
	
	/**
	 * 根据类型组获得标签组
	 */
	private List<Long> typeIds;
	
	/**
	 * 是否关联label表
	 */
	private Boolean joinLabelFlg = false;
	
}