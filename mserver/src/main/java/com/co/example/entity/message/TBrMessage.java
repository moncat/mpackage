package com.co.example.entity.message;

import com.github.moncat.common.entity.BaseEntity;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrMessage extends BaseEntity {
    /** id */
    private Long id;

    /** 消息标题 */
    private String title;

    /** 消息详情 */
    private String detail;

    /** 消息类型 */
    private Long typeId;

    /** 接收人id */
    private Long receiveBy;

    /** 接收时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    /** 是否已读 1 是 0否 */
    private Byte isRead;

    /** 用户阅读时间 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date readTime;

    /** 应用id */
    private Byte appId;
}