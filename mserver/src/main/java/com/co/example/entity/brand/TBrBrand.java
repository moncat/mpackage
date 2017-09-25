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

    /** 数据来源标识 
     *  1 从太平洋女性网抓取品牌数据
		2 从YOKA时尚网抓取数据
		3从Onlylady女人志时尚网抓取数据
		4从腾讯女性时尚网抓取数据
		5从39化妆品库抓取数据
		6从凤凰时尚化妆品库抓取数据
		7从瑞丽网抓取数据
		8从网易女人抓取数据
     */
    private Byte appId;
}