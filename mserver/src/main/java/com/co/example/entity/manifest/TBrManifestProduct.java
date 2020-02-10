package com.co.example.entity.manifest;

import com.github.moncat.common.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrManifestProduct extends BaseEntity {
    /** id */
    private Long id;

    /** 产品id */
    private Long productId;

    /** 详单id */
    private Long manifestId;

    /** 产品名称 */
    private String productName;

    /** 成分得分 */
    private BigDecimal iScore;

    /** 品牌名称 */
    private String productBrandName;

    /** 品类名称 */
    private String categoryName;

    /** 企业名称 */
    private String enterpriseName;

    /** 实际生产企业名称 */
    private String realEnterpriseName;

    /** 淘宝价格 */
    private BigDecimal taobaoPrice;

    /** 淘宝销量 */
    private Integer taobaoSale;

    /** 淘宝营业额 */
    private BigDecimal taobaoTurnover;

    /** 是否国产 */
    private Byte isChina;

    /** 是否特殊 */
    private Byte isSpecial;
}