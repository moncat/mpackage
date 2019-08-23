package com.co.example.entity.product.aide;

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
}