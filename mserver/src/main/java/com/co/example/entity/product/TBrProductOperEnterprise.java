package com.co.example.entity.product;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrProductOperEnterprise extends BaseEntity {
    /** id */
    private Long id;

    /** 产品id */
    private Long productId;

    /** 企业id */
    private Long enterpriseId;
}