package com.co.example.entity.category;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrCategory extends BaseEntity {
    /** id */
    private Long id;

    /** 产品品类名称 */
    private String name;

    /** 品类描述 */
    private String description;

    /** 层级 */
    private Integer level;

    /** 上级id */
    private Long parentId;

    /** 上级名称 */
    private String parentName;

    /** 是否根节点 1是 0不是 */
    private Byte isRoot;

    /** 是否常用 */
    private Byte isChoice;

    /** 更多 */
    private String moreData;
}