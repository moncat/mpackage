package com.co.example.entity.label.aide;

import com.co.example.entity.label.TBrLabel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrLabelQuery extends TBrLabel {
	
    private Long productId;
    
    private boolean productJoinFlg;
    
    private String nameLike;

    
    private boolean labelClassJoinFlg;
}