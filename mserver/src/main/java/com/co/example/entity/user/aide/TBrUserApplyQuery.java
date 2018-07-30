package com.co.example.entity.user.aide;

import com.co.example.entity.user.TBrUserApply;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class TBrUserApplyQuery extends TBrUserApply {
	
	private Boolean joinActivity = false;
	
	private String titleLike;
	
	private String userDisplayNameLike;
	 
}