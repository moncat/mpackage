package com.co.example.entity.label;

import com.github.moncat.common.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrIngredientLabelJoin extends BaseEntity {
    /** id */
    private Long id;

    /** 成分id */
    private Long ingredientId;

    /** 标签id */
    private Long labelId;

	public TBrIngredientLabelJoin() {
		super();
	}

	public TBrIngredientLabelJoin(Long ingredientId) {
		super();
		this.ingredientId = ingredientId;
	}
    
    
}