package com.co.example.entity.label;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrLabel extends BaseEntity {
    /** id */
    private Long id;

    /** 标签名称 */
    private String name;

    /** 标签分类id */
    private Long classId;

    /** 是否被选为常用 */
    private Byte isChoice;

    /** 标签描述 */
    private String description;

    /** 父标签id */
    private Long parentId;

    /** 应用id  1:在首页显示的分类 0肤质特点关联的标签   */
    private Byte appId;
}