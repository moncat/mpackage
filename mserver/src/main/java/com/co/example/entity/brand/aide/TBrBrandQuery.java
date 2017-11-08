package com.co.example.entity.brand.aide;

import com.co.example.entity.brand.TBrBrand;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrBrandQuery extends TBrBrand {
	
	/** 中文名Like */
    private String nameLike;

    /** 英文名Like */
    private String nameEnLike;
    
    private Long  idNotEqual;
    
	
}