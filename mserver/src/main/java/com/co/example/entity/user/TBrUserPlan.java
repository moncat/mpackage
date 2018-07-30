package com.co.example.entity.user;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrUserPlan extends BaseEntity {
    /** id */
    private Long id;

    /** 方案id */
    private Long planId;

    private String remark;

    /** 应用id */
    private Byte appId;
}