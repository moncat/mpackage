package com.co.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import com.co.example.common.utils.HttpClientUtils;
import com.co.example.common.utils.HttpUtils;
import com.github.moncat.common.generator.id.NextId;

public class Test3 {
	
	public static void main(String[] args) {
			m3();
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
	static void m3(){
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx607adf094642e51e&secret=31fcf4832b460f982957ea9b554e0520"
				+ "&code=011cJIxW1gBE6T0tCSwW1lGyxW1cJIxs"
				+ "&grant_type=authorization_code";
//		String postData = HttpUtils.postData(url);
		int postData = 0;
		try {
			postData = HttpClientUtils.doPost(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(postData);
	
	}
	
	
	
}
