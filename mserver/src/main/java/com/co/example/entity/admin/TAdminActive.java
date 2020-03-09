package com.co.example.entity.admin;

import com.github.moncat.common.entity.BaseEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TAdminActive extends BaseEntity {
    /**  */
    private Long id;

    /** 认证方式 1，邮箱，2手机 */
    private Integer type;

    /** 邮箱 */
    private String email;

    /** 手机号 */
    private String phone;

    /** 用户ip地址 */
    private String adminIp;

    /** 验证码 */
    private String vcode;

    /** 是否使用：0未使用，1已使用 */
    private Byte isUse;

    /** 使用时间 */
    private Date useTime;

    /** 有效时间 */
    private Date expireTime;

    /**  */
    private String url;
}