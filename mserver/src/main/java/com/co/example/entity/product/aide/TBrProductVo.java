package com.co.example.entity.product.aide;

import java.util.List;

import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrProductVo extends TBrProduct {
	
	private List<TBrIngredient> tBrIngredients;
	
}