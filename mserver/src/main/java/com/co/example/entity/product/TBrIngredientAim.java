package com.co.example.entity.product;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrIngredientAim extends BaseEntity {
    /** id */
    private Long id;

    /** 成分id */
    private Long ingredientId;

    /** 目的id */
    private Long aimId;
}