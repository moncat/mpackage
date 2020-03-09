package com.co.example.entity.order;

import com.github.moncat.common.entity.BaseEntity;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrOrder extends BaseEntity {
    /** id */
    private Long id;

    /** admin_id */
    private Long adminId;

    /** 会员产品名称eg   1、免费版 2、高级版 3、豪华版 */
    private String roleName;

    /** 会员产品级别标识eg 1、免费版 2、高级版 3、豪华版 */
    private Byte roleId;

    /** 订单金额 */
    private BigDecimal money;

    /** 购买时长 */
    private String duration;

    /** 是否支付 1,支付 0未支付 */
    private Byte isPay;

    /** 是否打折 1,打折 0未打折 */
    private Byte isDiscount;

    /** 打折后的金额 */
    private BigDecimal moneyDiscount;

    /** 支付方式 1,支付宝 2微信 */
    private Byte payWay;
}