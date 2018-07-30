package com.co.example.entity.product;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrRecommend extends BaseEntity {
    /** id */
    private Long id;

    /** 推荐类型 1产品 2资讯 */
    private Byte type;

    /** 推荐id(目前为商品id或资讯id) */
    private Long cid;

    /** 商品名称 或 资讯名称 （冗余字段，方便查询） */
    private String name;

    /** 应用id */
    private Byte appId;
}