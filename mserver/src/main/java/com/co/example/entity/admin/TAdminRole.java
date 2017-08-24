package com.co.example.entity.admin;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TAdminRole extends BaseEntity {
    /** 主键 */
    private Long id;

    /** 管理员id */
    private Long adminId;

    /** 角色id */
    private Long roleId;
}