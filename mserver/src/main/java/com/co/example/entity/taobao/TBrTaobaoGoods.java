package com.co.example.entity.taobao;

import com.github.moncat.common.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrTaobaoGoods extends BaseEntity {
    /** id */
    private Long id;

    /** 产品名称 */
    private String productName;

    /** 产品分类id */
    private String productSortId;

    /** 产品分类名称 */
    private String productSortName;

    /** 淘宝价格 */
    private BigDecimal taobaoPrice;

    /** 销量 */
    private Integer sales;

    /** 淘宝的url地址 */
    private String taobaoUrl;

    /** 产地 */
    private String producingArea;

    /** 来源：1taobao */
    private Byte source;

    /** 用于冗余数据 */
    private String moreData1;
}