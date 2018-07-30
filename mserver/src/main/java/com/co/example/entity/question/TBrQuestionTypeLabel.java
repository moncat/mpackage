package com.co.example.entity.question;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrQuestionTypeLabel extends BaseEntity {
    /** id */
    private Long id;

    /** 问题类型类目id */
    private Long typeId;

    /** 标签id */
    private Long labelId;

    /** 应用id */
    private Byte appId;
}