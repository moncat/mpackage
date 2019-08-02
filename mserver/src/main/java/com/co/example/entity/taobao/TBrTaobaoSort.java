package com.co.example.entity.taobao;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrTaobaoSort extends BaseEntity {
    /** id */
    private Long id;

    /** 分类名称 */
    private String sortName;

    /** 级别 */
    private Integer level;

    /** 用于冗余数据 */
    private String moreData1;
}