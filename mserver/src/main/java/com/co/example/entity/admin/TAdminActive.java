package com.co.example.entity.admin;

import com.co.example.common.entity.BaseEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TAdminActive extends BaseEntity {
    /** id */
    private Long id;

    /** 认证方式 1，邮箱，2手机 */
    private Integer type;

    /** 邮箱 */
    private String email;

    /** 手机号 */
    private String phone;

    /** 用户ip地址 */
    private String userIp;

    /** 验证码 */
    private String vcode;

    /** 是否使用：0未使用，1已使用 */
    private Byte isUse;

    /** 是否发送：0未发送，1已发送 */
    private Byte isSend;

    /** 使用时间 */
    private Date usetime;

    /** 有效时间 */
    private Date expireTime;

    /** 认证url */
    private String url;

    /** 是否已经激活  0，未激活 1 已激活  */
    private Byte status;
}