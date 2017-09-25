package com.co.example.entity.product.aide;

import com.co.example.entity.product.TBrProduct;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrProductQuery extends TBrProduct {
	
	/** 成分id */
	private Long ingredientId;
	
	/** 连接开关 */
	private Boolean joinFlg = false;
	
	/** 连接开关 */
	private Boolean joinBrandFlg = false;
	
	private String productNameLike;
	
	
	
}