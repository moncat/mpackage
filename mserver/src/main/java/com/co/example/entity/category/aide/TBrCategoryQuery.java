package com.co.example.entity.category.aide;

import com.co.example.entity.category.TBrCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrCategoryQuery extends TBrCategory {
	
	private String nameLike;
}