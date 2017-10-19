package com.co.example.entity.product.aide;

import com.co.example.entity.product.TBrIngredient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrIngredientQuery extends TBrIngredient {
	
	/** 产品id */
	private Long productId;
	
	/** 连接开关 */
	private Boolean joinFlg = false;
	
	
	/** 模糊查询 */
	private String nameLike;
	
}