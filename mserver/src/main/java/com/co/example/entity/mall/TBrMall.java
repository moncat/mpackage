package com.co.example.entity.mall;

import com.github.moncat.common.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrMall extends BaseEntity {
    /** id */
    private Long id;

    /** 产品id */
    private Long productId;

    /** 商城url 每月抓取数据时，记得做冗余*/
    private String url; 

    /** 本月抓取时的价格  每月抓取数据时，记得做冗余*/
    private BigDecimal price;

    /** 本月抓取时的销量 每月抓取数据时，记得做冗余 */
    private Integer sale;

    /** 本月抓取时的销售额 */
    private BigDecimal money;

    /** 更多数据1，用于抓取数据时产品新需求 */
    private String moreData1;

    /** 更多数据2，用于抓取数据时产品新需求 */
    private String moreData2;
}