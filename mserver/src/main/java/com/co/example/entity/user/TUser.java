package com.co.example.entity.user;

import com.github.moncat.common.entity.BaseEntity;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TUser extends BaseEntity {
    /** id */
    private Long id;

    /** 用户名 */
    private String loginName;

    /** 展示名称 */
    private String displayName;

    /** 真实姓名 */
    private String personName;

    /** 头像链接 */
    private String headImage;

    /** 生日日期 */
    private String birthday;

    /** 年龄 */
    private Integer age;

    /** 性别 1男 0女 */
    private Byte sex;

    /** 密码 */
    private String password;

    /** 个人邮箱 */
    private String email;

    /** 手机号 */
    private String mobilePhone;

    /** 登录次数 */
    private Short visitCount;

    /** 最后一次登录时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastTime;

    /** 最后一次登录ip */
    private String lastIp;

    /** qq号 */
    private String qq;

    /** 微信号 */
    private String wx;

    /** qq openId */
    private String qqOpenId;

    /** 微信 openId */
    private String wxOpenId;
}