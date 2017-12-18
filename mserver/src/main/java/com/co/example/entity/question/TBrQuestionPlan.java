package com.co.example.entity.question;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrQuestionPlan extends BaseEntity {
    /** id */
    private Long id;

    /** 方案名称 */
    private String name;

    /** 问题类型 16种 1357 1358 1367 1368 1457 1458 1467 1468 2357 2358 2367 2368 2457 2458 2467 2468 */
    private String flags;

    /** 肤质特点 */
    private String skinFeature;

    /** 肤质解决办法 */
    private String skinIdea;

    /** 方案备注 */
    private String remark;

    /** 应用id */
    private Byte appId;
}