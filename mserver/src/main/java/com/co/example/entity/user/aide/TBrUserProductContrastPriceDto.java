package com.co.example.entity.user.aide;

import java.math.BigDecimal;

import lombok.Data;


@Data
public class TBrUserProductContrastPriceDto {
	
	private BigDecimal price;
	
	private BigDecimal percent;
}
