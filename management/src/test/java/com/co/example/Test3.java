package com.co.example;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import com.github.moncat.common.generator.id.NextId;

public class Test3 {
	
	public static void main(String[] args) {
			m2();
	}
	
	static void m1(){
//		String s="&rdquo;你好afsdfsd;f;&divide;sf&middot;sadf&#39;";;
//		String ss=StringEscapeUtils.unescapeHtml4(s);
//		System.out.println(ss);
		String str=" aaa bbb  保湿 去           角质 滋养肌肤 滋润";
		str = str.replace(" ","");
		str = str.replace(" ","");
//		String target = str.replace(" ", "");
		System.out.println(str);
	}
	static void m2(){
		for (int i = 0; i < 42; i++) {
			System.out.println(NextId.getId());
		}
	}
}
