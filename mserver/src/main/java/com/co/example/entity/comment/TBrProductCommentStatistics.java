package com.co.example.entity.comment;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrProductCommentStatistics extends BaseEntity {
    /** id */
    private Long id;

    /** 产品id */
    private Long pid;

    /** 天猫全部评价个数 */
    private String tmallNumAll;

    /** 天猫带图评价个数 */
    private String tmallNumImg;

    /** 天猫追评个数 */
    private String tmallNumMore;

    /** 京东全部评价个数 */
    private String jdNumAll;

    /** 京东带图评价个数 */
    private String jdNumImg;

    /** 京东追评个数 */
    private String jdNumMore;

    /** 京东好评个数 */
    private String jdNumGood;

    /** 京东中评个数 */
    private String jdNumMiddle;

    /** 京东查评个数 */
    private String jdNumBad;

    /** 备注 */
    private String remark;

    /**  */
    private String moreData1;

    /**  */
    private String moreData2;
}