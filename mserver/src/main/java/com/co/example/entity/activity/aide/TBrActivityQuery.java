package com.co.example.entity.activity.aide;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.co.example.entity.activity.TBrActivity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrActivityQuery extends TBrActivity {
	
	private String titleLike;
	
	/** 开始时间 1*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime1;
    /** 开始时间 2*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime2;
    
    /** 截止时间 1*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime1;
    /** 截止时间 2*/
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime2;
    
    private Boolean joinApplyFlg= false;
    
    private Long userId;
    
//    public void setUserId(Long userId) {
//    	this.userId = userId;
//    	this.joinApplyFlg = true;
//    }
    
    private Boolean forUpdate= false;
    
    private Byte reportSatus;
    
}