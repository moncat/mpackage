package com.co.example.entity.contact;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TContact extends BaseEntity {
    /** 主键 */
    private Long id;

    /** 公司名称 */
    private String companyName;

    /** 公司主页 */
    private String companyUrl;

    /** 公司地址 */
    private String address;

    /** 位置地图 */
    private String map;

    /** 联系人 */
    private String linkman;

    /** 电话 */
    private String telephone;

    /** 电邮 */
    private String email;

    /** 传真 */
    private String fax;

    /** 邮编 */
    private Integer postcode;

    /** 是否有效  1有效 0无效 */
    private Byte isActive;
}