package com.co.example.entity.question;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrQuestionOption extends BaseEntity {
    /** id */
    private Long id;

    /** 问题id */
    private Long questionId;

    /** 选项名称 */
    private String name;

    /** 选项类型 1单选 2多选 */
    private Byte type;

    /** 该选项得分 */
    private Float grade;

    /** 选项备注 */
    private String remark;

    /** 应用id */
    private Byte appId;
}