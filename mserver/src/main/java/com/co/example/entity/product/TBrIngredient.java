package com.co.example.entity.product;

import com.github.moncat.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrIngredient extends BaseEntity {
    /** id */
    private Long id;

    /** 成分名 */
    private String name;

    /** 抓取的id */
    private String dataId;

    /** 安全风险级别 */
    private String securityRisks;

    /** 活性成分1有 0无 */
    private Byte activeIngredient;

    /** 致痘风险 1有  0无 */
    private Byte blainRisk;

    /** 别名 */
    private String alias;

    /** 英文名 */
    private String nameEn;

    /** cas号 */
    private String casNum;

    /** 来源  1 药监局 ； 2美丽修行 */
    private Byte resource;

    /** 描述 */
    private String description;
}