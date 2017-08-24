package com.co.example.entity.product.aide;

import java.util.List;

import com.co.example.entity.product.TBrAim;
import com.co.example.entity.product.TBrIngredient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrIngredientVo extends TBrIngredient {
	
	/** 成分目的组 */
	private List<TBrAim> tBrAims;
	
	private String  safeColour;
}