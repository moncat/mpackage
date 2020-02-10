package com.co.example.entity.mall;

import com.github.moncat.common.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrMallMonth extends BaseEntity {
    /** id */
    private Long id;

    /** 产品id */
    private Long productId;

    /** 产品商城url表id */
    private Long mallId;

    /** 月份 格式YYYYMM */
    private String month;

    /** 淘宝价格 */
    private BigDecimal price;

    /** 淘宝销量 */
    private Integer sale;
}