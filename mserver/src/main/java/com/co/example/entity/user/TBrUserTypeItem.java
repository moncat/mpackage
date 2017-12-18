package com.co.example.entity.user;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrUserTypeItem extends BaseEntity {
    /** id */
    private Long id;

    /** 问题类型类目id */
    private Long typeItemId;

    /** 用户方案id */
    private Long planId;

    /** 应用id */
    private Byte appId;
}