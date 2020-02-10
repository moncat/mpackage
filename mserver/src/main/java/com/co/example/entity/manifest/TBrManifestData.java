package com.co.example.entity.manifest;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrManifestData extends BaseEntity {
    /** id */
    private Long id;

    /** 清单表id */
    private Long manifestId;

    /** 被关联的id 可能为产品，供应商等      
     */
    private Long connId;

    /** 清单类型：1产品 2品牌 3供应商 4成分 */
    private Byte type;
}