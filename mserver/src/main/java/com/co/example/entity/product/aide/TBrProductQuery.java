package com.co.example.entity.product.aide;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.co.example.entity.product.TBrProduct;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrProductQuery extends TBrProduct {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 成分id */
	private Long ingredientId;
	
	/** 连接开关 是否关联成分 */
	private Boolean joinFlg = false;
	
	/** 连接开关  是否关联品牌*/
	private Boolean joinBrandFlg = false;
	
	
	/** 连接开关 是否关联  产品与运营企业关联表*/
	private Boolean joinOperEnterpriseFlg = false;
	
	private String productNameLike;
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date greaterThanCreateTime;
	
	 /** 备案日期 */
    private String greaterThanConfirmDate;
    
    
    
    
    
	
}