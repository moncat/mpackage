package com.co.example.entity.information;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrInformation extends BaseEntity {
    /** id */
    private Long id;

    /** 接口Id */
    private String portId;

    /** 标题 */
    private String title;

    /** 内容详情 */
    private String detail;

    /** 资讯类型 */
    private Integer type;

    /** 资讯来源 */
    private String source;

    /** 应用id */
    private Byte appId;
}