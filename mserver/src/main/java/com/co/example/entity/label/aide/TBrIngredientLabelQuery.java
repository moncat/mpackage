package com.co.example.entity.label.aide;

import com.co.example.entity.label.TBrIngredientLabel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrIngredientLabelQuery extends TBrIngredientLabel {
	
    private String nameLike;

    private Boolean joinClassFlg;
    
}