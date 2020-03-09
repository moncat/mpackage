package com.co.example.entity.admin;

import com.github.moncat.common.entity.BaseEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TAdmin extends BaseEntity {
    /** id */
    private Long id;

    /** 用户名 */
    private String loginName;

    /** 展示名称 */
    private String displayName;

    /** 密码 */
    private String password;

    /** 个人邮箱 */
    private String email;

    /** 手机号 */
    private String mobilePhone;

    /** 登录次数 */
    private Short visitCount;

    /** 最后一次登录时间 */
    private Date lastTime;

    /** 最后一次登录ip */
    private String lastIp;

    /** 页面版本 */
    private String pageVersion;

    /** 头像图像链接 */
    private String headImg;

    /** 会员类型   0管理员   1普通注册用户   2付费会员 */
    private Byte memberType;

    /** 会员到期时间 */
    private Date endTime;
}