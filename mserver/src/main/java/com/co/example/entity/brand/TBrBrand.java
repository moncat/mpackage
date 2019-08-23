package com.co.example.entity.brand;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrBrand extends BaseEntity {
    /** id */
    private Long id;

    /** 中文名 */
    private String name;

    /** 英文名 */
    private String nameEn;

    /** 图片地址 */
    private String imgUrl;

    /** 创始人 */
    private String founder;

    /** 发源地 */
    private String originate;

    /** 创始人创建时间 */
    private String foundDate;

    /** 官网 */
    private String website;

    /** 故事历史 */
    private String story;

    /** 是否被选择为常用 */
    private Byte isChoice;

    /** 应用id */
    private Byte appId;
}