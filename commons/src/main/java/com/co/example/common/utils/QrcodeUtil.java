package com.co.example.common.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * 二维码
 */
public class QrcodeUtil {
	
	/**
	 * 二维码编码
	 * @param content 编码内容
	 * @param response 编码输出到response
	 */
	public static void encodeQrcode(String content, HttpServletResponse response){
		encodeQrcode(content,response,258,258);
	}
	
	/**
	 * 二维码编码
	 * @param content 编码内容
	 * @param response 编码输出到response
	 * @param width 二维码宽
	 * @param height 二维码高
	 */
	public static void encodeQrcode(String content, HttpServletResponse response,Integer width, Integer height){
		Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 设置字符集编码类型
		BitMatrix bitMatrix;
		try {
			// 生成矩阵
			bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.QR_CODE, width, height, hints);
			// 输出图像 
			MatrixToImageWriter.writeToStream(bitMatrix, "png", response.getOutputStream()); 
		} catch (WriterException e) {
			
		} catch (IOException e) {
			
		}
	}
	

	
	
	
}