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

	
	/** 备案时间限制  */
	@Field
    private String confirmDate;	

	/**
	 * 多个标签搜索 ,暂时去掉
	 */
    @Field
    @Deprecated
    private String labelNames;
	
     
    
    /**
     * 多种成分搜索
     */
    @Field
    private String ingredients;
    
    /**
     * 多种品牌搜索
     */
    @Field
    private String brands;
    
    /**
     * 企业id
     */
    @Field
    private String beid;
    
    /**
     * 生产企业ids
     */
    @Field
    private String peids;
    
    /**
     * 成分ids
     */
    @Field
    private String iids;
    
    /**
     * 标签ids
     */
    @Field
    private String lids;
    
    /**
     * 品牌ids
     */
    @Field
    private String bids;


}