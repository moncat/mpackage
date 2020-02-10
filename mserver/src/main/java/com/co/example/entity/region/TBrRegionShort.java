package com.co.example.entity.region;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrRegionShort extends BaseEntity {
    /** id */
    private Long id;

    /** 地域id */
    private String regionId;

    /** 地域名称 */
    private String name;

    /** 地区级别 */
    private Byte level;

    /** 父级地域id */
    private String parentRegionId;

    /** 简称 */
    private String shortName;

    /** 应用id */
    private Byte appId;
}