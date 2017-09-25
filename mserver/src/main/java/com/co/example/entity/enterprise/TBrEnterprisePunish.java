package com.co.example.entity.enterprise;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrEnterprisePunish extends BaseEntity {
    /** id */
    private Long id;

    /** 企业表Id */
    private Long eid;

    /** 决定日期 */
    private String punishDate;

    /** 决定书文号 */
    private String punishId;

    /** 类型 */
    private String punishType;

    /** 决定机关 */
    private String punishOffice;

    /** 更多数据1 */
    private String moreData1;

    /** 更多数据2 */
    private String moreData2;

    /** 应用id */
    private Byte appId;
}