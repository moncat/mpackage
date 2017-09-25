package com.co.example.entity.enterprise;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrEnterpriseBrand extends BaseEntity {
    /** id */
    private Long id;

    /** 企业id */
    private Long productId;

    /** 品牌id */
    private Long brandId;
}