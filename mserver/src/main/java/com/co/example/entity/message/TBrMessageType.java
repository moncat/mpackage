package com.co.example.entity.message;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrMessageType extends BaseEntity {
    /** id */
    private Long id;

    /** 类型名称 */
    private String name;

    /** 类型备注 */
    private String remark;

    /** 应用id */
    private Byte appId;
}