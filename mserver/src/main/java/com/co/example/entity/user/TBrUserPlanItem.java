package com.co.example.entity.user;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrUserPlanItem extends BaseEntity {
    /** id */
    private Long id;

    /** 方案id */
    private Long userPlanId;

    /** 问题类型id */
    private Long typeId;

    /** 类目名称 */
    private String typeName;

    /** 标志  8种标志  16种组合方案 */
    private String flag;

    /** 该套题总分,可以用于进度条样式展示 */
    private Float gradeCount;

    /** 应用id */
    private Byte appId;
}