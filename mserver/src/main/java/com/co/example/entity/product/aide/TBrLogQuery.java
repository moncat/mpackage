package com.co.example.entity.product.aide;

import com.co.example.entity.product.TBrLog;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TBrLogQuery extends TBrLog {
	
	private String paramsLike;
}