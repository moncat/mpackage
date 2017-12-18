package com.co.example.entity.label;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrProductLabel extends BaseEntity {
    /** id */
    private Long id;

    /** 产品id */
    private Long productId;

    /** 标签id */
    private Long labelId;
}