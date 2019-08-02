package com.co.example.entity.label.aide;

import com.co.example.entity.label.TBrProductLabel;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class TBrProductLabelQuery extends TBrProductLabel {
	
	Boolean joinLabelFlg = false;
	
	/** 用户id */
    private Long userId;   
    
}