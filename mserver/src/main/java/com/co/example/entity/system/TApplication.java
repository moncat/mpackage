package com.co.example.entity.system;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TApplication extends BaseEntity {
    /** id */
    private Long id;

    /** 应用程序id */
    private Integer appId;

    /** 网站名称 */
    private String name;

    /** 网站网址 */
    private String url;

    /** 网站描述 */
    private String description;

    /** 版权保护 */
    private String copyRight;

    /** 浏览次数 */
    private Integer pv;

    /** 是否有效 1有效 0无效 */
    private Byte isActive;
}