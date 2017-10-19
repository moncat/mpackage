package com.co.example.entity.spec;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrSpecKey extends BaseEntity {
    /** id */
    private Long id;

    /** 规格键名称 */
    private String keyName;

    /** 备注 */
    private String remark;

    /** 来源  1 天猫 ； 2京东 */
    private Byte source;

    /**  */
    private String moreData1;

    /**  */
    private String moreData2;
}