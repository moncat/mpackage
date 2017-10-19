package com.co.example.entity.product;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrLog extends BaseEntity {
    /** id */
    private Long id;

    /** 数据来源  1 药监局  2美丽修行 */
    private Byte source;

    /** 访问地址 */
    private String url;

    /** 参数 */
    private String params;

    /** 返回数据数 */
    private Integer count;

    /** 操作状态 */
    private Integer status;

    /** 错误轨迹 */
    private String trace;
}