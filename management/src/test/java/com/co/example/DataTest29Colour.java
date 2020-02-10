package com.co.example;

import java.util.Random;


/**
 * 随机计算浅色
 * @author zyl
 *
 */
public class DataTest29Colour {

	public static void main(String[] args) {
		for (int i = 0; i < 40; i++) {
			System.out.println("<p><span style='background-color:#" + randomHexStr(6) + ";'>AAAAAAAAAAAAAAAAAA</span></p>");
		}
	}

	public static String randomHexStr(int len) {
		try {
			StringBuffer result = new StringBuffer();
			for (int i = 0; i < len; i++) {
				result.append(Integer.toHexString(new Random().nextInt(6)+8));
			}
			return result.toString().toUpperCase();
		} catch (Exception e) {
			return "00CCCC";
		}
	}
	
	
}
