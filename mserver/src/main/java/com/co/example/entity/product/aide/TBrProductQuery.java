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
	
	private Boolean brandIsNullFlg = false;
	
	
	/** 连接开关 是否关联  产品与运营企业关联表*/
	private Boolean joinOperEnterpriseFlg = false;
	
	/** 模糊查询*/
	private String productNameLike;
	/** 多个模糊查询*/
	private String productNameLike2;
	
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date greaterThanCreateTime;
	
	 /** 备案日期 */
    private String greaterThanConfirmDate;
    
    /** 使用天猫不为空过滤 */
    private Boolean useTmallUrlNotNullFlg = false;

    /** 使用京东不为空过滤  */
    private Boolean useJdUrlNotNullFlg = false;
    
    /** 1天猫 2京东 */
    private Integer ecType = 0;
    
    /** 品牌id */
	private Long brandId;
	
	/** 连接开关  是否关联品牌*/
	private Boolean joinLabelFlg = false;
	
	/** 标签id */
	private Long labelId;
	
	/** 连接开关  是否关联推荐*/
	private Boolean joinRecommendFlg = false;
	
	
	/** 连接开关  是否设置推荐Id不为空*/
	private Boolean recommendIsNotNullFlg = false;
	
	/** 连接开关  连结统计按照统计进行排序  */
	private Boolean commentFlg = false;
	
	/** 连接开关 按照销量进行排序  */
	private Boolean salesFlg = false;
	
	private Boolean joinCollectFlg = false;
	
	private Long userId ;
		
	/** 2018年4月24日新增 标签id组，接收前台labels */
	private String labelIds;
	
	/** 用于in查询 */
	private String[] labelIdArr;
	
	/** 2019年7月26日添加，用于查询solr数据 */
	private String labelNames;
	
	/** 用于对备案和取消备案查询  confirmDataType 备案类型  ；confirmDataDays查询天数  */
	private int confirmDataType;
	private int confirmDataDays;
	
	//******************************** 2019年8月19日 add *************
	/**
	 * 2019年8月19日 add
	 * 对于 忠良的各种in查询所需要的查询数据
	 */
	private String normal ="";
	private Integer normalType;
	private String startTime;
	private String endTime;
	private String eIds ="";
	private String peIds ="";
	private Long lId;
	private Long bId;
	private Long iId;
	
}



