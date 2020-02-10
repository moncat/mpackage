package com.co.example.entity.manifest;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrManifestResult extends BaseEntity {
    /** id */
    private Long id;

    /** 清单表id */
    private Long manifestId;

    /** 数据基本类型 1产品2品牌3供应商4成分 */
    private Byte type;

    /** 数据类型key：eg 1产品清单的tab1饼图，详见字典表或静态变量 
     * @see com.co.example.constant#ManifestConstant
     * */
    private Integer keyId;

    /** 将各个不同节点数据以json方式保存 */
    private String jsondata;
}