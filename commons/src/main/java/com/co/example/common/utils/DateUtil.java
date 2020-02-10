package com.co.example.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.Lists;

public class DateUtil {
	
	public static final DateFormat formartDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static final DateFormat formartDate = new SimpleDateFormat("yyyy-MM-dd");
	
	public static final DateFormat formartTime = new SimpleDateFormat("HH:mm:ss");
	
	public static final DateFormat formartWeek = new SimpleDateFormat("E",Locale.CHINA);
	

	/**
	 * 获取当天的时刻
	 * @param time 格式:HH:mm:ss
	 * @return
	 */
	public static Date getDateByTime(Date time){
		Calendar calTime = Calendar.getInstance();
		calTime.setTime(time);
		Calendar calNow = Calendar.getInstance();
		calNow.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
		calNow.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
		calNow.set(Calendar.SECOND, calTime.get(Calendar.SECOND));
		calNow.set(Calendar.MILLISECOND, 0);
        return calNow.getTime();
	}
	
	/**
	 * 获取两个时间的间隔 单位:毫秒
	 * @param time
	 * @return
	 */
	public static long getTimeInterval(Date time,Date time2){
		return time.getTime() - time2.getTime();
	}
	
	
	/**
	 * 时间比较，小于当前时间则返回true
	 * @param TargetTime  需要比较的时间
	 * @return
	 */
	public static Boolean isLessThanCurrentTime(Date targetTime){
		return  targetTime.before(new Date());
	}
	
	/**
	 * 
	 * @param dateTime 要修改的时间
	 * @param modifyType  要修改的类型（年，月，日）
	 * @param modifyCount 正数为加，负数为减
	 * @return Date
	 */
	public static Date modifyDate(Date dateTime,int modifyType,int modifyCount){
		Calendar ca=Calendar.getInstance();
		ca.setTime(dateTime);
		ca.add(modifyType, modifyCount);
		return ca.getTime();
	}
	
	/**
	 * 根据日期取得星期几,周一为2，类推，周日为1
	 * @param date
	 * @return
	 */
	public static String getWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		return String.valueOf(cal.get(Calendar.DAY_OF_WEEK));
	}
	
	public static void main(String args[]) {
//		System.out.println(getWeek(new Date()));
//		
//		Date time1 = new Date();
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Date time2 = new Date();
//		System.out.println("两个时间相差:" + (time2.getTime() - time1.getTime()));
		
//		Calendar cal = Calendar.getInstance();
//		Date aa = new Date();
//		Calendar calendar = new GregorianCalendar(aa.getYear() + 1900, aa.getMonth(), aa.getDate(),aa.getHours(),aa.getMinutes(),aa.getSeconds());
//		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
//		Date date = calendar.getTime();
//		System.out.println(df.format(date));
		
//		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//		Calendar calendar = new GregorianCalendar(2016, 6, 28,18,0,0);
//		Date date = calendar.getTime();
//		System.out.println(df.format(date));
		
//		System.out.println(getDays(2));
//		System.out.println(getIntervalDays("2019-08-18","2019-08-21"));
//		System.out.println( getMonthOnYear());
//		System.out.println( getMonthsOnYear());
//		System.out.println( getMonthsOnYear(formartDateTime));
		System.out.println(  getDateMonthsOnYear());
		System.out.println("*************");
		
	}
	
	public static Date getDateByCalendar(){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        return date;
	}
	
	public static String  getDays(int num){
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, num);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date = calendar.getTime();
        String str = DateFormatUtil.format(date, DateFormatUtil.formartDate);
        return str;
	}
	
	public static String  getDays(String startDateStr, int num){
		Calendar calendar = Calendar.getInstance();
		Date startDate = DateParseUtil.parseDate(startDateStr);
		calendar.setTime(startDate);
		calendar.add(Calendar.DAY_OF_YEAR, num);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime();
		String str = DateFormatUtil.format(date, DateFormatUtil.formartDate);
		return str;
	}
	
 
	public static int  getIntervalDays(String start,String end){
		Date date1=DateParseUtil.parseDate(start);
		Date date2=DateParseUtil.parseDate(end);	
        long tmp = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 *24);
        return Integer.parseInt(String.valueOf(tmp)); 
	}
	
	
	public static List<String>  getMonths(){
		String months="一月,二月,三月,四月,五月,六月,七月,八月,九月,十月,十一月,十二月"; 
		Calendar calendar = Calendar.getInstance();
		int m = calendar.get(Calendar.MONTH)+1;
		List<String> list = Lists.newArrayList();
		String[] split = months.split(",");
		for (int i = m; i < 12; i++) {
			String str = split[i];
			list.add(str);			
		}
		for (int i =0; i < m; i++) {
			String str = split[i];
			list.add(str);			
		}
        return list; 
	}
	
	public static String  getMonthOnYear(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		calendar.add(Calendar.MONTH, 1);
		return DateFormatUtil.getYearMonth(calendar.getTime());
	}
	
	public static List<String>  getMonthsOnYear(){
		return getMonthsOnYear(DateFormatUtil.formartYearMonth);
	}
	
	public static List<String>  getMonthsOnYear(DateFormat dateFormat){
		List<String> list = Lists.newArrayList();
		String str = "";
		for (int i =-11; i < 1; i++) {		
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, i);
			str = DateFormatUtil.format(calendar.getTime(),dateFormat);
			list.add(str);			
		}
		return list;
	}
	
	// 2019-09-01 00:00:00
	public static List<Date>  getDateMonthsOnYear(){
		List<Date> list = Lists.newArrayList();
		for (int i =-12; i < 1; i++) {		
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, i);
			calendar.set(Calendar.DAY_OF_MONTH,1);
			calendar.set(Calendar.HOUR_OF_DAY,0);
			calendar.set(Calendar.MINUTE,0);
			calendar.set(Calendar.SECOND,0);
			list.add(calendar.getTime());			
		}
		return list;
	}
	

	
	
}
