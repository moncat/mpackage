package com.co.example.entity.label;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrLabelClass extends BaseEntity {
    /** id */
    private Long id;

    /** 标签分类名称 */
    private String name;

    /** 更多 */
    private String moreData;

    /** 应用id */
    private Byte appId;
}