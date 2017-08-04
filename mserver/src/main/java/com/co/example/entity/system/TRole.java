package com.co.example.entity.system;

import com.co.example.common.entity.BaseEntity;
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
    private String name;

    /** 角色描述 */
    private String describe;

    /** 是否是管理员 1是 0不是 */
    private Byte isAdmin;
}