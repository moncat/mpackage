package com.co.example.common.utils;

import java.io.File;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Slf4j
public class TessUtil {
	public static String getStrFromPic(String path) {
		File imageFile = new File(path);
		Tesseract instance = new Tesseract();
		// 将验证码图片的内容识别为字符串
		String result="";
		try {
			result = instance.doOCR(imageFile);
		} catch (TesseractException e) {
			log.info("识别失败，");
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		String result = getStrFromPic("d:\\test.jpg");
		System.out.println("******"+result);
	}
	
}
