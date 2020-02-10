package com.co.example.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	public static String xxlParse(String str){
		if(StringUtils.isBlank(str)){
			str=null;
		}
		if(StringUtils.contains(str,"<script>")){
			str=str.replace("<script>", "");
			str=str.replace("</script>", "");
		}
		return str;
	}
	
	public static boolean isChinese(String str) {
		String regEx = "[\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		boolean flg = false;
		if (matcher.find())
			flg = true;

		return flg;
	}
	
	public static String trimStrAll(String str) {
		if(StringUtils.isBlank(str)){
			return null;
		}else{
			str =str.trim();
		} 
		return str;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(isChinese("11qweqwe3w好342dew1"));
	}
}
