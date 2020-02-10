package com.co.example.entity.product;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrProductMore extends BaseEntity {
    /** id */
    private Long id;

    /** 产品id */
    private Long productId;

    /** 进口国家 */
    private String fromCountry;

    /** 进口城市 */
    private String importCity;

    /** 进口企业id */
    private Long importEnterpriseId;
}