package com.co.example.common.utils;

import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * @Title: VelocityUtil.java
 * @Package com.kjj.mobile.util
 * @Description: 
 * @author ZYLORG
 * @date 2016年10月24日 上午9:38:29
 * @copyright Beijing KJJ Electronic commerce Co., LTD
 * @version V1.0   
 */
public class VelocityUtil {
	
	private final static  VelocityEngine ve =new VelocityEngine();
	
    static{
    	
	    ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
	    ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
	    ve.init();
    }
    
    /**
     * 通过Velocity模板拼接
     * @param vmpath 模板路径
     * @param map  数据
     * @param sb   待拼接StringBuffer
     */
	public static StringBuffer create(String vmpath, Map<String,Object> map,StringBuffer sb) {
		Template t = ve.getTemplate(vmpath,"utf-8"); 
		VelocityContext context = new VelocityContext();
		if(MapUtils.isNotEmpty(map)){
			for (String key : map.keySet()) {
				context.put(key,map.get(key)); 
			}
		}
		StringWriter writer = new StringWriter();
		t.merge(context, writer); 
		sb.append(writer.getBuffer());
		return sb;
	}
	
}

