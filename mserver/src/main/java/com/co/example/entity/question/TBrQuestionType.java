package com.co.example.entity.question;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrQuestionType extends BaseEntity {
    /** id */
    private Long id;

    /** 类目名称 */
    private String name;

    /** 层级 */
    private Integer typeLevel;

    /** 上级Id */
    private Long parentId;

    /** 类目最小得分 */
    private Float gradeMin;

    /** 类目最大得分 */
    private Float gradeMax;

    /** 标志  8种标志  16种组合方案 */
    private String flag;

    /** 类目备注 */
    private String remark;

    /** 应用id */
    private Byte appId;
}