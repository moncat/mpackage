package com.co.example.entity.export;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrExport extends BaseEntity {
    /** id */
    private Long id;

    /** 数据来源 */
    private String source;

    /** 过滤条件 */
    private String filters;

    /** 下载链接 */
    private String url;

    /** 应用id  1:在首页显示的分类 0肤质特点关联的标签   */
    private Byte appId;
}