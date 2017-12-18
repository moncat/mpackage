package com.co.example.entity.user;

import com.github.moncat.common.entity.BaseEntity;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrUserApply extends BaseEntity {
    /** id */
    private Long id;

    /** 试用活动id */
    private Long activityId;

    /** 审核时间（管理员） */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date operTime;

    /** 审核人（管理员） */
    private Long operBy;

    /** 应用id */
    private Byte appId;
}