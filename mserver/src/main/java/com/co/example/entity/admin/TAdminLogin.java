package com.co.example.entity.admin;

import com.github.moncat.common.entity.BaseEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TAdminLogin extends BaseEntity {
    /** 主键 */
    private Long id;

    /** 管理员Id */
    private Long adminId;

    /** 管理员自动登录的key */
    private String rememberKey;

    /** 登录Ip */
    private String loginIp;

    /** 用户登录时间 */
    private Date loginTime;
}