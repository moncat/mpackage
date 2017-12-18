package com.co.example.common.utils;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.co.example.common.constant.Constant;
import com.github.moncat.common.generator.id.NextId;

/**
 * @Description: 多部件文件上传
 * @author ZYL
 * @version V1.0   
 */
public class MultipartFileUploadUtil {
	
	/**
	 * @param file 多部件文件上传 
	 * @param imageDir 文件要上传的文件夹位置
	 * @return  返回文件路径
	 * @see     com.kjj.commserver.constant
	 */
	public static String fileUpload(MultipartFile file, String imageDir) {
		return fileUpload(file, Constant.IMAGE_BASE_PATH, imageDir);
	}
	/**
	 * @param file 多部件文件上传 
	 * @param BasePath 绝对路径
	 * @param imageDir 文件要上传的文件夹位置
	 * @return  返回文件路径
	 */
	public static String fileUpload(MultipartFile file, String BasePath,String imageDir) {
		if (!file.isEmpty()) {  
			String contentType = file.getContentType();
			String fileType=contentType.substring(contentType.lastIndexOf("/")+1);
			if(fileType.endsWith("jpeg")){
				fileType="jpg";
			}
			String fileName = NextId.getId()+"."+fileType;
            try {  
    			File fileTarget = new  File(BasePath+imageDir+fileName);
			    File parent = fileTarget.getParentFile();
			    if (parent != null) {
			       parent.mkdirs();
			    }
    			file.transferTo(fileTarget); 
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            return imageDir+fileName;
        }
		return null;
	}
}

