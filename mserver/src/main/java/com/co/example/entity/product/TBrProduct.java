package com.co.example.entity.product;

import com.github.moncat.common.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrProduct extends BaseEntity {
    /** id */
    private Long id;

    /** 产品名称 */
    private String productName;

    /** 产品别名 */
    private String productAlias;

    /** 京东价格 */
    private BigDecimal jdPrice;

    /** 天猫价格 */
    private BigDecimal tmallPrice;

    /** 从天猫获得销量，京东获取不到销量 */
    private Integer sales;

    /** 天猫的url地址 */
    private String tmallUrl;

    /** 京东的url地址 */
    private String jdUrl;

    /** 备案类型 1国产备案 2进口备案 */
    private String applyType;

    /** 备案号 */
    private String applySn;

    /** 企业名称 */
    private String enterpriseName;

    /** 企业英文名 */
    private String enterpriseNameEn;

    /** 产地 */
    private String producingArea;

    /** 备案日期 */
    private String confirmDate;

    /** 是否注销 */
    private String isOff;

    /** 药监局id1 */
    private String cfdaProcessid;

    /** 药监局id2 */
    private String cfdaNewProcessid;

    /** 美丽修行 产品mid */
    private String bevolMid;

    /** 备注 */
    private String remark;

    /** 来源  1 药监局 ； 2美丽修行 */
    private Byte source;

    /**  */
    private String moreData1;

    /**  */
    private String moreData2;
}