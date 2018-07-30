package com.co.example.entity.user;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrUserPlanLabel extends BaseEntity {
    /** id */
    private Long id;

    /** 用户标签id */
    private Long labelId;

    /** 标签名称 */
    private String name;

    /** 应用id */
    private Byte appId;
}