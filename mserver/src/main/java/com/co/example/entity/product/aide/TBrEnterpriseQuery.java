package com.co.example.entity.product.aide;

import com.co.example.entity.product.TBrEnterprise;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrEnterpriseQuery extends TBrEnterprise {
	
	/** 产品id */
	private Long productId;
	
	/** 连接开关 */
	private Boolean joinFlg = false;
	
	/** 备案号模糊查询 */
    private String applySnLike;

    /** 企业名称模糊查询 */
    private String enterpriseNameLike;
	
	
	
}