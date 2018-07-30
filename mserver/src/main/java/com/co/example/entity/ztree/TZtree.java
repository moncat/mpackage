package com.co.example.entity.ztree;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TZtree extends BaseEntity {
    /** id */
    private Long id;

    /** 上级id */
    private Long pid;

    /** 用户id */
    private Integer uid;

    /** 是叶子 */
    private Integer isLeaf;

    /** 是分支 */
    private Integer isBranch;

    /** 类型 1m 2c   10end */
    private Integer type;

    /** 类型的条件 */
    private String info;

    /** 名称 */
    private String content;

    /** 应用id */
    private Byte appId;
}