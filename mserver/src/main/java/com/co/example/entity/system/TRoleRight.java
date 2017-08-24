package com.co.example.entity.system;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TRoleRight extends BaseEntity {
    /** id */
    private Long id;

    /** 角色id */
    private Long roleId;

    /** 权限id */
    private Long rightId;

    /** 权限类型 1应用菜单 */
    private Byte type;

    /** 应用id */
    private Byte appId;
}