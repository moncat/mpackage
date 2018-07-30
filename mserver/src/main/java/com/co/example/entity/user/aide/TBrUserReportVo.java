package com.co.example.entity.user.aide;

import java.util.List;

import com.co.example.entity.user.TBrUserReport;
import com.co.example.entity.user.TBrUserReportItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrUserReportVo extends TBrUserReport {
	
	/** 活动标题 */
    private String title;
	
	 /** 用户展示名称 */
    private String userDisplayName;
    
    /**
     * 报告类目列表
     */
    private List<TBrUserReportItem> list;
    
    
}