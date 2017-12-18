package com.co.example.entity.product;

import com.github.moncat.common.entity.BaseEntity;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrCarousel extends BaseEntity {
    /** id */
    private Long id;

    /** 轮播图名称 */
    private String name;

    /** 轮播图 */
    private String image;

    /** 轮播图链接 */
    private String url;

    /** 开始时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 备注 */
    private String remark;

    /** 应用id */
    private Byte appId;
}