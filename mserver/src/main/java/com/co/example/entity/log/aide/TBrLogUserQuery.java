package com.co.example.entity.log.aide;

import com.co.example.entity.log.TBrLogUser;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class TBrLogUserQuery extends TBrLogUser {
	
	private String userNameLike;
	
	private String operTypeLike;
	
	private String moduleLike;
	
	private String detailLike;
}