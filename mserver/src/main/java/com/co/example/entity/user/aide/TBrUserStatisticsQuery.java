package com.co.example.entity.user.aide;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.co.example.entity.user.TBrUserStatistics;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TBrUserStatisticsQuery extends TBrUserStatistics {
	
	/**
	 * 截止时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date bigTime;
	
	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date smallTime;
	
}