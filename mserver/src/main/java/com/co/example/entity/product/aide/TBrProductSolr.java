package com.co.example.entity.product.aide;

import org.apache.solr.client.solrj.beans.Field;

import lombok.Data;

/**
 * solr bean
 * @author zyl
 *
 */
@Data
public class TBrProductSolr {
    /** id */ 

	@Field
    private Long id;

    /** 产品名称 */
	@Field
    private String productName;

    /** 备案号 */
	@Field
    private String applySn;

    /** 企业名称 */
	@Field
    private String enterpriseName;

	/** 时间  */
	@Field
    private String confirmDate;
	
    @Field
    private String labelNames;
	
 
	
	

}