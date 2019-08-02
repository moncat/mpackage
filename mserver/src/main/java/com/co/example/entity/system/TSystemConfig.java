package com.co.example.entity.system;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TSystemConfig extends BaseEntity {
    /** 主键自增id */
    private Long id;

    /** 标题 */
    private String title;

    /** 配置键 */
    private String mapKey;

    /** 配置值 */
    private String mapValue;

    /** 配置对象 0 全部;  1 pc端;  2 app端;  因本系统只有app端，该字段暂时保留 */
    private String target;

    /** 备注 */
    private String mark;
}