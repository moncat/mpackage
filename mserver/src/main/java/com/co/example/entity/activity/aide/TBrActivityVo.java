package com.co.example.entity.activity.aide;

import com.co.example.entity.activity.TBrActivity;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class TBrActivityVo extends TBrActivity {
	
	/**
	 * 申领人id
	 */
	private Long applyUserId;
	
}