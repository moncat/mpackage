package com.co.example.entity.user;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrUserAddress extends BaseEntity {
    /** id */
    private Long id;

    /** 用户id */
    private Long uid;

    /** 姓名 */
    private String name;

    /** 联系方式 */
    private String contact;

    /** 省Id */
    private Long provinceId;

    /** 省名称 */
    private String provinceName;

    /** 地市Id */
    private Long cityId;

    /** 地市名称 */
    private String cityName;

    /** 区县Id */
    private Long contryId;

    /** 区县名称 */
    private String contryName;

    /** 乡镇Id */
    private Long townId;

    /** 乡镇名称 */
    private String townName;

    /** 详细地址 */
    private String detail;

    /** 是否默认 1是 0否 */
    private Byte isDefault;

    /** 应用id */
    private Byte appId;
}