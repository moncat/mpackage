package com.co.example.entity.manifest;

import com.github.moncat.common.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrManifestIngredient extends BaseEntity {
    /** id */
    private Long id;

    /** 成分id */
    private Long ingredientId;

    /** 详单id */
    private Long manifestId;

    /** 成分名 */
    private String name;

    /** 使用目的 */
    private String aims;

    /** 产品数 */
    private Integer pnum;

    /** 淘宝销量 */
    private Integer taobaoSale;

    /** 淘宝营业额 */
    private BigDecimal taobaoTurnover;
}