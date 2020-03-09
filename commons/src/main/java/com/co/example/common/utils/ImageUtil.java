package com.co.example.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang3.StringUtils;

import com.github.moncat.common.generator.id.NextId;
import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.google.common.io.Closer;
import com.google.common.io.Files;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageUtil {
	private static  MimetypesFileTypeMap mtftp;

    public ImageUtil(){
        mtftp = new MimetypesFileTypeMap();
        /* 不添加下面的类型会造成误判 详见：http://stackoverflow.com/questions/4855627/java-mimetypesfiletypemap-always-returning-application-octet-stream-on-android-e*/
        mtftp.addMimeTypes("image png tif jpg jpeg bmp");
    }
    
    public static  boolean isImage(File file){
        String mimetype= mtftp.getContentType(file);
        String type = mimetype.split("/")[0];
        return type.equals("image");
    }
    
    
    public static String download(String parentPath, String imgUrl) {
        if(Strings.isNullOrEmpty(imgUrl) || Strings.isNullOrEmpty(parentPath)) {
            return null;
        }
        Closer closer = Closer.create();
        try {
            File imageDir = new File(parentPath);
            if(!imageDir.exists()) {
                imageDir.mkdirs();
            }
            String fileName = NextId.getId()+".jpg";
            File imageFile = new File(imageDir, fileName);
            InputStream in = closer.register(new URL(imgUrl).openStream());
            Files.write(ByteStreams.toByteArray(in), imageFile);
            return fileName;
        } catch(Exception ex) {
            ex.printStackTrace();
            log.error("image download error :"+imgUrl);
            return null;
        } finally {
            try {
                closer.close();
            } catch (IOException e) {
                closer = null;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(download("d:\\", "https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=09cd05db104c510fb1c4e41a50582528/b8389b504fc2d5620bbc0bfeed1190ef76c66c69.jpg"));
    }
 
}
