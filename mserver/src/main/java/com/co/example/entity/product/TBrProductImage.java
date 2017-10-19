package com.co.example.entity.product;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrProductImage extends BaseEntity {
    /** id */
    private Long id;

    /** 图片名称 */
    private String name;

    /** 产品id */
    private Long productId;

    /** 药监局图片id */
    private String cfdaImageId;

    /** 药监局ssid 用于下载链接 */
    private String cfdaSsid;

    /** 美丽修行 图片url */
    private String bevolUrl;

    /** 天猫的图片url */
    private String tmallUrl;

    /** 京东的图片url */
    private String jdUrl;

    /** 文件类型 jpg、png */
    private String fileType;

    /** 图片类型 1平面图 2立体图 3商标证，授权书 4其他未确定类型 */
    private Byte imageType;

    /** 数据来源 1药监局 2美丽修行 3京东 4天猫 */
    private Byte source;
}