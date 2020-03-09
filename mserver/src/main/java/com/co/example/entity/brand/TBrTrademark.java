package com.co.example.entity.brand;

import com.github.moncat.common.entity.BaseEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=true)
@ToString(callSuper=true)
public class TBrTrademark extends BaseEntity {
    /** id */
    private Long id;

    /** 商标名称 */
    private String name;

    /** 商标图片地址,下载到本地 */
    private String imgUrl;

    /** 申请时间 */
    private Date applyDate;

    /** 注册号 */
    private String registerCode;

    /** 国际分类 */
    private String classify;

    /** 商标状态 */
    private String status;

    /** 详情操作url */
    private String detailUrl;

    /** 企业名称来自于企业表 */
    private String enterpriseName;

    /** 企业id来自于企业表 */
    private Long enterpriseId;
}