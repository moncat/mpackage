package com.co.example.common.utils;

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
}
