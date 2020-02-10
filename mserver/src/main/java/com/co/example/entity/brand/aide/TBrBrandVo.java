package com.co.example.entity.brand.aide;

import com.co.example.entity.brand.TBrBrand;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrBrandVo extends TBrBrand {
	

    /** 供应商数 */
    private Integer eenum;

    /** 品类数 */
    private Integer cnum;

    /** 产品备案数 */
    private Integer pnum;
}