package com.co.example.entity.enterprise;

import com.github.moncat.common.entity.BaseEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrEnterpriseRegister extends BaseEntity {
    /** id */
    private Long id;

    /** 企业表Id */
    private Long eid;

    /** 法人 */
    private String legalPerson;

    /** 注册资本 */
    private String registerCapital;

    /** 企业状态 */
    private String enterpriseStatus;

    /** 工商注册号 */
    private String registerId;

    /** 组织机构代码 */
    private String orgId;

    /** 统一信用代码 */
    private String creditId;

    /** 企业类型 */
    private String enterpriseType;

    /** 纳税人识别号 */
    private String revenueId;

    /** 行业类型 */
    private String industryType;

    /** 营业期限 */
    private String businessTerm;

    /** 注册日期 */
    private Date registerDate;

    /** 核准日期 */
    private Date approveDate;

    /** 登记机关 */
    private String registerAuthority;

    /** 注册地址 */
    private String registerAddress;

    /** 英文名称 */
    private String nameEn;

    /** 经营范围 */
    private String businessScope;

    /** 备注 */
    private String remark;

    /** 应用id */
    private Byte appId;
}