package com.co.example;

import org.apache.commons.lang3.StringEscapeUtils;

public class Test3 {
	
	public static void main(String[] args) {
		
		String s="&rdquo;你好afsdfsd;f;&divide;sf&middot;sadf&#39;";;
		String ss=StringEscapeUtils.unescapeHtml4(s);
		System.out.println(ss);
	}
}
