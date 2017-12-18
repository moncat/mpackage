package com.co.example.entity.product.aide;

import com.co.example.entity.product.TBrProductImage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrProductImageQuery extends TBrProductImage {
	
	private Boolean tmallUrlIsNotNull = false;
	
	private Boolean jdUrlIsNotNull = false;
	
}