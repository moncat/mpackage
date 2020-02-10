package com.co.example.entity.manifest;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrManifest extends BaseEntity {
    /** id */
    private Long id;

    /** 清单名称 */
    private String name;

    /** 清单类型：1产品 2品牌 3供应商 4成分 */
    private Byte type;

    /** 清单描述 */
    private String description;

    /** 状态：1初始化 2待处理  3处理中 4已完成    因为事务去掉3 */  
    private Byte status;

    /** 应用id  1:在首页显示的分类 0肤质特点关联的标签   */
    private Byte appId;
}