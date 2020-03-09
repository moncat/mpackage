package com.co.example.entity.product.aide;

import com.co.example.entity.enterprise.TBrEnterprisePermission;
import com.co.example.entity.product.TBrEnterprise;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrEnterpriseVo extends TBrEnterprise {
	
	/**
	 * 企业状态
	 */
	private String enterpriseStatus;
	/**
	 * 行业类型
	 */
	private String industryType;
	
	
	//以下字段用于清单内企业tab页的列表
    /** 发证日期 */
    private String startDate;

    /** 有效期至 */
    private String endDate;

    /** 品牌数 */
    private Integer bnum;

    /** 品类数 */
    private Integer cnum;

    /** 产品数 */
    private Integer pnum;
    
    /** 成分数 */
    private Integer inum;
    
    private TBrEnterprisePermission permission;
}