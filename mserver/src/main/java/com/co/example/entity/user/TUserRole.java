package com.co.example.entity.user;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TUserRole extends BaseEntity {
    /** 主键 */
    private Long id;

    /** 用户id */
    private Long userId;

    /** 角色id */
    private Long roleId;
}