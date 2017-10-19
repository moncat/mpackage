package com.co.example.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexUtils {
	
	
	public static enum  Regex {
		number("[^0-9]");	
		
		
		private String key;

		private Regex(String key){
			this.key = key;
		}
		
	}
	
	
	public static String getNumber(String source){
		return getData(source , Regex.number);
	}
	
	
	public static String getData(String source , Regex regex){
		Pattern pattern = Pattern.compile(regex.key);
		Matcher matcher = pattern.matcher(source);
		String target = matcher.replaceAll("");
		return target;
	}
	
	
	public static void main(String[] args) {
		System.out.println(getNumber("dasdasd123432dasdas44444"));
		
	}
	
	
}
