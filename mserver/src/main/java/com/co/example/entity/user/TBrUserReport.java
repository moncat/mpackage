package com.co.example.entity.user;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrUserReport extends BaseEntity {
    /** id */
    private Long id;

    /** 报告名称 */
    private String name;

    /** 活动id */
    private Long activityId;

    /** 试用说明 */
    private String detail;

    /** 应用id */
    private Byte appId;
}