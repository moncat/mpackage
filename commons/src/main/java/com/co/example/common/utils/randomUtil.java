package com.co.example.common.utils;

public class randomUtil {
	public static String getVCode(){
		Double d = Math.random();
		return  (d+"").substring(2, 8);
	}
	
	public static void main(String[] args) {
		System.out.println(getVCode());
	}
	
}
