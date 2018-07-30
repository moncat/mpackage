package com.co.example.entity.activity;

import com.github.moncat.common.entity.BaseEntity;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrActivity extends BaseEntity {
    /** id */
    private Long id;

    /** 活动标题 */
    private String title;

    /** 活动类型 1试用活动 2…… */
    private Integer type;

    /** 公告图链接 */
    private String noticeImage;

    /** 商品图片链接 */
    private String productImage;

    /** 活动详情 */
    private String detail;

    /** 商品限量 */
    private Integer productNum;

    /** 活动参与人数 */
    private Integer userNum;

    /** 开始时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 截止时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 应用id */
    private Byte appId;
}