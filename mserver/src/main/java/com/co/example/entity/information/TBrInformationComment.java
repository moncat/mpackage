package com.co.example.entity.information;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrInformationComment extends BaseEntity {
    /** id */
    private Long id;

    /** 资讯id */
    private Long informationId;

    /** 评论内容 */
    private String detail;

    /** 上级id */
    private Long parentId;

    /** 层级 */
    private Integer level;

    /** 点赞数量 */
    private Integer support;

    /** 应用id */
    private Byte appId;
}