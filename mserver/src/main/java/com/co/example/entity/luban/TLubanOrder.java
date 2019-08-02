package com.co.example.entity.luban;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TLubanOrder extends BaseEntity {
    /** id */
    private Long id;

    /** 接口数据id */
    private Long id2;

    /** 接口数据id */
    private Long orderId;

    /** 用户id */
    private Long userId;

    /** 用户类型 */
    private Integer userType;

    /** 用户名称 */
    private String userName;

    /** 产品id */
    private Long productId;

    /** 店铺id */
    private Long shopId;

    /** 店铺名称 */
    private String shopName;

    /** 发送数量 */
    private Integer postAmount;

    /** 总数量 */
    private Integer totalAmount;

    /** 邮寄码 */
    private Integer postCode;

    /** 邮寄接收人 */
    private String postReceiver;

    /** 邮寄电话密钥 */
    private String postTelSecret;

    /** 物流单号 */
    private String logisticsCode;

    /** 物流时间 */
    private String logisticsTime;

    /** 接口订单创建时间 */
    private String createTime2;

    /** 产品名称 */
    private String productName;

    /** 邮寄电话 */
    private String postTel;

    /** 电话区域 */
    private String telArea;

    /** 应用id */
    private Byte appId;

    /** 更多数据1 */
    private String moreData1;

    /** 更多数据2 */
    private String moreData2;

    /** 更多数据3 */
    private String moreData3;
}