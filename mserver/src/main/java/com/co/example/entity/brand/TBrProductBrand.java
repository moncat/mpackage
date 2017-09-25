package com.co.example.entity.brand;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrProductBrand extends BaseEntity {
    /** id */
    private Long id;

    /** 产品id */
    private Long productId;

    /** 品牌id */
    private Long brandId;
}