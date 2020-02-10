package com.co.example.entity.brand.aide;

import lombok.Data;

@Data
public class TBrBrandPo {
    private Long id;
	//品牌名称
    private String tBrBrandName;
    //品牌等级
    private Byte tBrBrandLevel;
	//品类
    private Integer catagoryNum;
	//备案数
    private Integer productNum;
	//生产产品占比
    private Integer curProductNum;
}