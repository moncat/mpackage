package com.co.example.entity.abc;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrAaa extends BaseEntity {
    /**  */
    private Long id;

    /**  */
    private String name;
}