package com.co.example.entity.statistics;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrStatistics extends BaseEntity {
    /** id */
    private Long id;

    /** 产品数量 */
    private String moreData1;

    /** 品牌数量 */
    private String moreData2;

    /** 产品品牌匹配数量 */
    private String moreData3;

    /** 京东产品匹配数量 */
    private String moreData4;

    /** 天猫产品匹配数量 */
    private String moreData5;

    /** 实际生产企业数量 */
    private String moreData6;

    /** 已获取企业信息数量 */
    private String moreData7;

    /** 当前成分数量 */
    private String moreData8;

    /** 产品成分匹配数量 */
    private String moreData9;

    /** 成分使用目的数量 */
    private String moreData10;

    /** 规格种类数量 */
    private String moreData11;

    /** 产品图片数量 */
    private String moreData12;

    /** 运营企业数据 */
    private String moreData13;

    /** 数据 */
    private String moreData14;

    /** 数据 */
    private String moreData15;

    /** 数据 */
    private String moreData16;

    /** 数据 */
    private String moreData17;

    /** 数据 */
    private String moreData18;

    /** 数据 */
    private String moreData19;

    /** 数据 */
    private String moreData20;

    /** 应用id */
    private Byte appId;
}