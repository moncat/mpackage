package com.co.example.entity.question;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrQuestionPlanSpec extends BaseEntity {
    /** id */
    private Long id;

    /** 方案id */
    private Long planId;

    /** 方案标签id */
    private Long specValueId;

    /** 应用id */
    private Byte appId;
}