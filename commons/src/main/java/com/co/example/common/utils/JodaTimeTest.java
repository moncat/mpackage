package com.co.example.common.utils;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

public class JodaTimeTest {
	public static void main(String[] args) {
//		DateTime dateTime = new DateTime(2015, 12, 21, 0, 0, 0, 333);// 年,月,日,时,分,秒,毫秒 
//		System.out.println(dateTime);//2015-12-21T00:00:00.333+08:00
//		System.out.println(dateTime.toString("yyyy/MM/dd HH:mm:ss EE"));
//		
//		DateTimeFormatter format = DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss");  
//		DateTime dateTime1 = DateTime.parse("2015-12-21 23:22:45", format); 
//		System.out.println(dateTime1.toString("yyyy/MM/dd HH:mm:ss EE"));
//		
//		DateTime dateTime2 = new DateTime(2016, 1, 1, 0, 0, 0, 0);
//		System.out.println(dateTime2.plusDays(90).toString("E MM/dd/yyyy HH:mm:ss.SSS"));

		LocalDate localDate = new LocalDate();
		LocalDate newYear = localDate.withDayOfYear(1); //一年的第一天
		System.out.println("11111"+newYear.toString());
		
		Days daysToNewYear = daysToNewYear(localDate);
		System.out.println("2222"+daysToNewYear.toString());
		
		
		DateTime dt = new DateTime();  
		//转换成java.util.Date对象  
		Date d1 = new Date(dt.getMillis());  
		Date d2 = dt.toDate(); 
		
		
		//默认设置为日本时间  
		DateTimeZone.setDefault(DateTimeZone.forID("Asia/Tokyo"));  
		DateTime dt1 = new DateTime();  
		System.out.println(dt1.toString("yyyy-MM-dd HH:mm:ss"));

		//伦敦时间  
		DateTime dt2 = new DateTime(DateTimeZone.forID("Europe/London"));
		System.out.println(dt2.toString("yyyy-MM-dd HH:mm:ss"));
		
		DateTime begin = new DateTime("2015-02-01");  
		DateTime end = new DateTime("2016-05-01");  

		//计算区间毫秒数  
		Duration d = new Duration(begin, end);  
		long millis = d.getMillis();  

		//计算区间天数  
		Period p = new Period(begin, end, PeriodType.days());  
		int days = p.getDays();  

		//计算特定日期是否在该区间内  
		Interval interval = new Interval(begin, end);  
		boolean contained = interval.contains(new DateTime("2015-03-01"));
		
		DateTime d3 = new DateTime("2015-10-01"); 
		DateTime d4 = new DateTime("2016-02-01"); 

		//和系统时间比  
		boolean b1 = d3.isAfterNow();  
		boolean b2 = d3.isBeforeNow();  
		boolean b3 = d3.isEqualNow();  

		//和其他日期比  
		boolean f1 = d3.isAfter(d4);  
		boolean f2 = d3.isBefore(d4);  
		boolean f3 = d3.isEqual(d4);
		
		
		
	}
	public static Days daysToNewYear(LocalDate fromDate) {
		LocalDate plusYears = fromDate.plusYears(1);
		LocalDate newYear = plusYears.withDayOfYear(1);//这一年的第一天
		return Days.daysBetween(fromDate, newYear);
	}
}
