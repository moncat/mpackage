package com.co.example.entity.user;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrUserPlan extends BaseEntity {
    /** id */
    private Long id;

    /** 方案id */
    private Long planId;

    /** 方案类型（冗余数据） 16种 1357 1358 1367 1368 1457 1458 1467 1468 2357 2358 2367 2368 2457 2458 2467 2468 */
    private Byte type;

    /** 应用id */
    private Byte appId;
}