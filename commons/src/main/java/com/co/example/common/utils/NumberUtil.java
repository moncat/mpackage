package com.co.example.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
	
	public static String getNumberFromString(String str) {
		str = str.replace(" ", "");
		String numStr ="";
		String regex = "\\d*[.]\\d*|\\d*";
		
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		while (m.find()) {
			if (!"".equals(m.group())){
				numStr =  m.group();			
			}
		}
		return numStr;
	}
	
	public static void main(String[] args) {
		String sales ="月成交 7.2万笔 评价 51万"; 
		sales = sales.split("笔")[0];
		System.out.println(sales);
//		
		
//		String ss = getNumberFromString("ni h ao23.43ja ja");
//		System.out.println(ss);
	}

}
