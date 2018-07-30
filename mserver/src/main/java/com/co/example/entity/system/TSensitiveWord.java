package com.co.example.entity.system;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TSensitiveWord extends BaseEntity {
    /** id */
    private Long id;

    /** 敏感词名 */
    private String name;

    /** 应用id */
    private Byte appId;
}