package com.co.example.entity.product;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrEnterprise extends BaseEntity {
    /** id */
    private Long id;

    /** 备案号 */
    private String applySn;

    /** 企业名称 */
    private String enterpriseName;

    /** 产地 */
    private String producingArea;

    /** 备注 */
    private String remark;

    /** 是否是运营单位 */
    private Byte isBus;

    /** 是否是生产单位 */
    private Byte isProduct;

    /** 是否选择为常用 */
    private Byte isChoice;
}