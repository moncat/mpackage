package com.co.example.entity.label;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrLabel extends BaseEntity {
    /** id */
    private Long id;

    /** 标签名称 */
    private String name;

    /** 父标签id */
    private Long parentId;

    /** 应用id */
    private Byte appId;
}