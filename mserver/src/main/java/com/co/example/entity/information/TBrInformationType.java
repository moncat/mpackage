package com.co.example.entity.information;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrInformationType extends BaseEntity {
    /** id */
    private Long id;

    /** 接口Id */
    private String portId;

    /** 类型名称 */
    private String name;

    /** 备注 */
    private String remark;

    /** 应用id */
    private Byte appId;
}