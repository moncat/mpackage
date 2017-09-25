package com.co.example.entity.enterprise;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrEnterpriseBase extends BaseEntity {
    /** id */
    private Long id;

    /** 企业表Id */
    private Long eid;

    /** 电话 */
    private String telePhone;

    /** 手机 */
    private String mobilePhone;

    /** 邮箱 */
    private String email;

    /** 网址 */
    private String website;

    /** 邮编 */
    private String postcode;

    /** 传真 */
    private String fax;

    /** 简介 */
    private String intro;

    /** 记录url 以扩展更多数据爬取 */
    private String moreData1;

    /**  */
    private String moreData2;

    /** 备注 */
    private String remark;

    /** 应用id */
    private Byte appId;
}