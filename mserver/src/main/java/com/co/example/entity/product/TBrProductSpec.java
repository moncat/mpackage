package com.co.example.entity.product;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrProductSpec extends BaseEntity {
    /** id */
    private Long id;

    /** 产品id */
    private Long pid;

    /** 规格键id */
    private Long specKeyId;

    /** 规格键名称 */
    private String specKeyName;

    /** 规格值id */
    private Long specValueId;

    /** 规格值名称 */
    private String specValueName;

    /** 备注 */
    private String remark;

    /** 来源  1 天猫 ； 2京东 */
    private Byte source;

    /**  */
    private String moreData1;

    /**  */
    private String moreData2;
}