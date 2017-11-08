package com.co.example.entity.product.aide;

import com.co.example.entity.product.TBrProductEnterprise;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrProductEnterpriseQuery extends TBrProductEnterprise {
	
	/** 是否关联产品表 */
	private Boolean joinProductFlg = false;
}