package com.co.example.entity.product.aide;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.co.example.entity.product.TBrProductIngredient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrProductIngredientQuery extends TBrProductIngredient {
	
	@DateTimeFormat(pattern="yyyy-MM")
    private Date timeLmit1;
	
	@DateTimeFormat(pattern="yyyy-MM")
	private Date timeLmit2;
}