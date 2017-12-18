package com.co.example.entity.user;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrUserReportItem extends BaseEntity {
    /** id */
    private Long id;

    /** 报告Id */
    private Long reportId;

    /** 图片 */
    private String image;

    /** 图片说明 */
    private String imageInfo;

    /** 应用id */
    private Byte appId;
}