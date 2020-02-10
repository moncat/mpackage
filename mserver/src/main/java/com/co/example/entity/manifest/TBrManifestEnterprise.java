package com.co.example.entity.manifest;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrManifestEnterprise extends BaseEntity {
    /** id */
    private Long id;

    /** 企业id */
    private Long enterpriseId;

    /** 详单id */
    private Long manifestId;

    /** 企业名称 */
    private String enterpriseName;

    /** 备案号 */
    private String applySn;

    /** 发证日期 */
    private String startDate;

    /** 有效期至 */
    private String endDate;

    /** 品牌数 */
    private Integer bnum;

    /** 品类数 */
    private Integer cnum;

    /** 产品数 */
    private Integer pnum;

    /** 是否是运营单位 */
    private Byte isBus;

    /** 是否是生产单位 */
    private Byte isProduct;
}