package com.co.example.common.utils;

import java.io.IOException;

public class Test {
	public static void main(String[] args) {
		try {
			FtpUtil.makeDirecotory("", "192.168.20.179", "a", 21, "a", "bbb", "aa");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
