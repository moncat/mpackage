package com.co.example.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtil {
	
	public static final DateFormat formartDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final DateFormat formartDateNumber = new SimpleDateFormat("yyMMdd");
	
	public static final DateFormat formartDateTimeNumber = new SimpleDateFormat("yyMMddHHmmss");
	
	public static final DateFormat formartDate = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final DateFormat formartTime = new SimpleDateFormat("HH:mm:ss");
	
	public static final DateFormat formartWeek = new SimpleDateFormat("E",Locale.CHINA);
	
	/**
	 * 时间转字符串(null转为空字符串)
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date,DateFormat format){
		if (date != null) {
			return format.format(date);
		} else {
			return "";
		}
	}
	
	/**
	 * 时间转字符串 格式:yyyy-MM-dd HH:mm:ss(null转为空字符串)
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date){
		return format(date,formartDateTime);
	}
	
	/**
	 * 时间转字符串 格式:yyyy-MM-dd(null转为空字符串)
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date){
		return format(date,formartDate);
	}
	
	/**
	 * 时间转字符串 格式:HH:mm:ss(null转为空字符串)
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date){
		return format(date,formartTime);
	}
	
	/**
	 * 时间转字符串 格式:E(null转为空字符串)
	 * @param date
	 * @return
	 */
	public static String formatWeek(Date date){
		return format(date,formartWeek);
	}
	
	/**
	 * 时间字符串 格式:yyMMdd
	 * @param date
	 * @return
	 */
	public static String getDateNumber(){
		return format(new Date(),formartDateNumber);
	}
	
	/**
	 * 时间字符串 格式:yyMMddHHmmss
	 * @return
	 */
	public static String getDateTimeNumber(){
		return format(new Date(),formartDateTimeNumber);
	}
}
