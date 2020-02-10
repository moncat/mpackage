package com.co.example.entity.enterprise;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrEnterprisePermission extends BaseEntity {
    /** id */
    private Long id;

    /** 企业表的id，对比获得 */
    private Long eid;

    /** 企业名称 */
    private String enterpriseName;

    /** 许可证id */
    private String permissionId;

    /** 许可项目 */
    private String permissionProject;

    /** 企业住所 */
    private String enterpriseLocal;

    /** 生产地址 */
    private String productingLocal;

    /** 社会信用代码 */
    private String creditId;

    /** 法定代表人 */
    private String personLegal;

    /** 企业负责人 */
    private String personIncharge;

    /** 质量负责人 */
    private String personQuality;

    /** 发证机关 */
    private String licenseOffice;

    /** 签发人 */
    private String licensePerson;

    /** 日常监督管理机构 */
    private String supervisionOffice;

    /** 日常监督管理人员 */
    private String supervisionPerson;

    /** 有效期至 */
    private String endDate;

    /** 发证日期 */
    private String startDate;

    /** 状态 */
    private String status;

    /** 投诉举报电话 */
    private String hotline;

    /** 用于冗余数据 */
    private String moreData1;

    /** 用于冗余数据 */
    private String moreData2;
}