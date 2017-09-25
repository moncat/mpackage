package com.co.example.entity.enterprise;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrEnterpriseShareholder extends BaseEntity {
    /** id */
    private Long id;

    /** 企业表Id */
    private Long eid;

    /** 股东名称 */
    private String name;

    /** 股东类型 1,自然人  2企业法人 */
    private Byte type;

    /** 出资比例 */
    private String moneyPercent;

    /** 认缴出资 */
    private String moneyPlan;

    /** 实际出资 */
    private String moneyActual;

    /** 时间 */
    private String operDate;

    /** 更多数据1 */
    private String moreData1;

    /** 更多数据2 */
    private String moreData2;

    /** 应用id */
    private Byte appId;
}