package com.co.example.entity.enterprise;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrEnterpriseManager extends BaseEntity {
    /** id */
    private Long id;

    /** 企业表Id */
    private Long eid;

    /** 姓名 */
    private String name;

    /** 职位 */
    private String post;

    /** 更多数据1 */
    private String moreData1;

    /** 更多数据2 */
    private String moreData2;

    /** 应用id */
    private Byte appId;
}