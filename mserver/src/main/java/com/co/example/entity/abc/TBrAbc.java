package com.co.example.entity.abc;

import com.github.moncat.common.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrAbc extends BaseEntity {
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

    /** 运营企业id */
    private Long enterpriseId;

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

    /** 品类id */
    private Long categoryId;

    /** 品类名称 */
    private String categoryName;

    /** 是否国产 */
    private Byte isChina;

    /** 是否特殊 */
    private Byte isSpecial;

    /** 淘宝价格 */
    private BigDecimal taobaoPrice;

    /** 淘宝销量 */
    private Integer taobaoSale;

    /** 淘宝营业额 */
    private BigDecimal taobaoTurnover;

    /** 成分得分 */
    private BigDecimal iScore;

    /** 品牌id，新增用于数据冗余的字段 */
    private Long productBrandId;

    /** 品牌名称，新增用于数据冗余的字段 */
    private String productBrandName;

    /** 用于冗余图片链接，来自京东第二张图片 */
    private String moreData1;

    /** 1为已经备案注销    null为未注销 */
    private String moreData2;
}