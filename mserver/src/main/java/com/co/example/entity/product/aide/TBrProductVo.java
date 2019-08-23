package com.co.example.entity.product.aide;

import java.util.List;

import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.product.TBrEnterprise;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.TBrProductImage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrProductVo extends TBrProduct {
	
	/**
	 * 成分列表
	 */
	private List<TBrIngredient> tBrIngredients;
	
	/**
	 * 香精成分
	 */
	private List<String> essenceList;
	
	/**
	 * 防腐剂成分
	 */
	private List<String> corrosionList;
	
	/**
	 * 风险成分
	 */
	private List<String> riskList;
	
	/**
	 *  清洁成分 
	 */
	private List<String> cleanList;
	
	/**
	 *  表面活性成分 
	 */
	private List<String> activeList;
	
	/**
	 * 为移动端展示一张天猫图片
	 */
	private TBrProductImage image;
	
	/**
	 * 被推荐Id
	 */
	private Long recommendId;
	
	/**
	 * 商品标签
	 */
	private List<TBrProductLabel> labels;
	
	
	/**
	 * 品牌名称
	 */
	private String brandName;
	
	/**
	 * 生产企业列表
	 */
	private List<TBrEnterprise>  enterpriseList;
	
}