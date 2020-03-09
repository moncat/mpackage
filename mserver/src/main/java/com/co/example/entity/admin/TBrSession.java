package com.co.example.entity.admin;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrSession extends BaseEntity {
    /** id */
    private Long id;

    /** 管理员id */
    private Long adminId;

    /** sessionId */
    private String loginSessionId;

    /** 登录ip */
    private String loginIp;
}