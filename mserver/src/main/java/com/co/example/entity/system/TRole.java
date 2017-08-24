package com.co.example.entity.system;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TRole extends BaseEntity {
    /** 主键 */
    private Long id;

    /** 角色名称 */
    private String roleName;

    /** 角色描述 */
    private String remark;
}