package com.co.example.entity.user;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrUserStatisticsMonth extends BaseEntity {
    /** id */
    private Long id;

    /** 年月信息 */
    private String month;

    /** 操作类型 1注册 2测试 3咨询 */
    private Integer type;

    /** 数量 */
    private Integer count;

    /** 应用id */
    private Byte appId;
}