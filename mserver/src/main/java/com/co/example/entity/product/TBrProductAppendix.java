package com.co.example.entity.product;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrProductAppendix extends BaseEntity {
    /** id */
    private Long id;

    /** 产品id */
    private Long productId;

    /** json数据1药监局基础数据 */
    private String jsondata1;

    /** json数据2药监局成分数据 */
    private String jsondata2;

    /** json数据3药监局图片数据 */
    private String jsondata3;

    /** 保留 */
    private String jsondata4;

    /** 保留 */
    private String jsondata5;

    /** 保留 */
    private String jsondata6;
}