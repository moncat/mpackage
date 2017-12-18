package com.co.example.entity.user;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrUserStatistics extends BaseEntity {
    /** id */
    private Long id;

    /** 用户id */
    private Long uid;

    /** 操作类型 1注册 2测试 3咨询 */
    private Integer type;

    /** 应用id */
    private Byte appId;
}