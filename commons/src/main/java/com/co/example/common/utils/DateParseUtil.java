package com.co.example.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class DateParseUtil {
	
	public static final DateFormat formartDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final DateFormat formartDate = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final DateFormat formartTime = new SimpleDateFormat("HH:mm:ss");
	
	public static final DateFormat formartWeek = new SimpleDateFormat("E",Locale.CHINA);
	
	/**
	 * 时间转字符串
	 * @param strDate
	 * @param format
	 * @return
	 */
	public static Date parse(String strDate,DateFormat format){
		Date date = null;
		if (StringUtils.isNotBlank(strDate)) {
			try {
				date = format.parse(strDate);
			} catch (ParseException e) {
			}
		} 
		return date;
	}
	
	/**
	 * 字符串转时间 
	 * @param strDateTime 格式:yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date parseDateTime(String strDateTime){
		return parse(strDateTime,formartDateTime);
	}
	
	/**
	 * 字符串转时间  
	 * @param strDate 格式:yyyy-MM-dd
	 * @return
	 */
	public static Date parseDate(String strDate){
		return parse(strDate,formartDate);
	}
	
	/**
	 * 字符串转时间 
	 * @param strTime 格式:HH:mm:ss
	 * @return
	 */
	public static Date parseTime(String strTime){
		return parse(strTime,formartTime);
	}
}
