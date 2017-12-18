package com.co.example.entity.user;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrUserProductContrast extends BaseEntity {
    /** id */
    private Long id;

    /** 待对比产品id */
    private Long pid;

    /** 应用id */
    private Byte appId;
}