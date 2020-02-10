package com.co.example.entity.manifest;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrManifestBrand extends BaseEntity {
    /** id */
    private Long id;

    /** 品牌id */
    private Long brandId;

    /** 详单id */
    private Long manifestId;

    /** 中文名 */
    private String name;

    /** 品牌等级 顶级10分 一线8 二线5 三线2 四线1 其他0.5 */
    private Byte level;

    /** 供应商数 */
    private Integer eenum;

    /** 品类数 */
    private Integer cnum;

    /** 产品备案数 */
    private Integer pnum;
}