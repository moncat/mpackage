package com.co.example.entity.question;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrQuestion extends BaseEntity {
    /** id */
    private Long id;

    /** 问题名称 */
    private String name;

    /** 问题描述 */
    private String detail;

    /** 问题类型 1干油性 2敏感度 3色素 4紧致 */
    private Byte type;

    /** 问题备注 */
    private String remark;

    /** 应用id */
    private Byte appId;
}