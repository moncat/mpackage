package com.co.example.entity.product.aide;

import java.math.BigDecimal;
import java.util.List;

import com.co.example.entity.label.TBrIngredientLabel;
import com.co.example.entity.product.TBrAim;
import com.co.example.entity.product.TBrIngredient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrIngredientVo extends TBrIngredient {
	
	/** 成分目的组 */
	private List<TBrAim> tBrAims;
	
	private String  safeColour;
	
	
	//用于成分详单
    /** 使用目的 */
    private String aims;

    /** 产品数 */
    private Integer pnum;
    /** 品牌数 */
    private Integer bnum;
    /** 企业数 */
    private Integer eenum;

    /** 淘宝销量 */
    private Integer taobaoSale;

    /** 淘宝营业额 */
    private BigDecimal taobaoTurnover;
    
    
    //给成分列表添加标签名称列
    private List<TBrIngredientLabel> labelList;
}



