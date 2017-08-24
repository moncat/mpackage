package com.co.example.entity.product.aide;

import com.co.example.entity.product.TBrAim;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrAimQuery extends TBrAim {
	/** 成分id */
	private Long ingredientId;
	
	/** 连接开关 */
	private Boolean joinFlg;
}