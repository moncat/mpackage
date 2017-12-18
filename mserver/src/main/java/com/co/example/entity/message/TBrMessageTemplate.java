package com.co.example.entity.message;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrMessageTemplate extends BaseEntity {
    /** id */
    private Long id;

    /** 消息标题 */
    private String title;

    /** 消息详情 */
    private String detail;

    /** 类型Id */
    private Long typeId;

    /** 应用id */
    private Byte appId;
}