package com.co.example.entity.product.aide;

import com.co.example.entity.product.TBrIngredientAim;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrIngredientAimQuery extends TBrIngredientAim {
	
	
	 /** 成分名 */
    private Boolean joinIngredientFlg = false;
	
}