package com.co.example.entity.user.aide;

import com.co.example.entity.user.TBrUserReport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrUserReportQuery extends TBrUserReport {
	
	private Boolean joinFlg = false;
	
	
	private String nameLike;
	
	private String titleLike;
	
	private String userDisplayNameLike;
	
}