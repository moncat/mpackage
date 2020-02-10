package com.co.example.entity.manifest;

import com.github.moncat.common.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrManifestCategory extends BaseEntity {
    /** id */
    private Long id;

    /** 品类id */
    private Long categoryId;

    /** 详单id */
    private Long manifestId;

    /** 品类名称 */
    private String name;

    /** 产品数 */
    private Integer pnum;

    /** 品牌数 */
    private Integer bnum;

    /** 供应商数 */
    private Integer eenum;

    /** 淘宝销量 */
    private Integer taobaoSale;

    /** 淘宝营业额 */
    private BigDecimal taobaoTurnover;
}