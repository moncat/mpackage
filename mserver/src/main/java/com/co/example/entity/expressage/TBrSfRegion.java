package com.co.example.entity.expressage;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrSfRegion extends BaseEntity {
    /** id */
    private Long id;

    /** 顺丰Id */
    private String sfId;

    /** 顺丰地域Id */
    private String code;

    /** 比率Id */
    private Integer rateCode;

    /** 地区级别 */
    private Byte level;

    /** 上级地域id */
    private String parentCode;

    /** 地域名称 */
    private String name;

    /** 是否可到达  1全境到达  2部分到达   3不到达  4偏远地区/特别收送地区 */
    private Byte reachType;

    /** 不明字段，或许为语言 */
    private String lang;

    /** 国家地区码 */
    private String countryCode;

    /** 不明字段，或许为是否开放 */
    private Byte opening;

    /** 不明字段 是否自送  1true 0false */
    private Byte selfSend;

    /** 不明字段 是否自提 1true 0false */
    private Byte selfPickup;

    /** 不明字段   可作为终点 1true 0false */
    private Byte availableAsDestination;

    /** 不明字段  可作为起点 1true 0false */
    private Byte availableAsOrigin;

    /** 不明字段 */
    private String workAddDays;

    /** 备注 */
    private String remark;

    /** 应用id */
    private Byte appId;
}