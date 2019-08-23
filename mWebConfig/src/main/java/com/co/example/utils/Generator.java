package com.co.example.utils;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.co.example.bean.GeInfo;
import com.co.example.entity.label.TBrIngredientLabelClass;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 * @author ZYL 
 */
public class Generator {

	// 修改下面两行
	static String model = "ingredientLabelClass";	
	static Class<TBrIngredientLabelClass> entity = TBrIngredientLabelClass.class;
	
	
	static String basePath="D:/Workspaces2/package/management/src/main/resources/";

	public static void main(String[] args) {  //geData
		// 修改配置文件
//		initData();
		
//		ge(Type.list);	
		
//		ge(Type.add);
//		ge(Type.edit);		
//		ge(Type.show);
	}

	public static void initData() {
		StringBuffer sb = new StringBuffer();
		sb.append("#***标签***字段名***类型***是否必填***最小长度***最大长度***\n");
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(entity);
		for (int i = 0; i < propertyDescriptors.length; i++) {
			String displayName = propertyDescriptors[i].getDisplayName();
			if(!excludeFields.contains(displayName)){
				sb.append("--"+displayName + "--1--true--1--100\n");
			}
		}
		sb.append("#1、文本框 2、下拉框 3、单选 4、复选 5、多行文本 6、日期 7、上传 8、百度UE编辑器\n");
		
		File file = new File("src/main/resources/geData.txt");
		try {
			Files.write(sb.toString().getBytes(), file);
			System.out.println("geData.txt...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static List<GeInfo> readData() {
		File file = new File("src/main/resources/geData.txt");
		List<GeInfo> dataList = Lists.newArrayList();
		GeInfo geInfo = null;
		String[] arr = null;
		try {
			List<String> readLines = Files.readLines(file, StandardCharsets.UTF_8);
			for (String line : readLines) {
				if(!line.startsWith("#")){
//					System.out.println(line);
					line = line.replace(" ", "");
					arr = line.split("--");
					geInfo = new GeInfo();
					geInfo.setLabel(arr[0]);
					geInfo.setField(arr[1]);
					geInfo.setType(arr[2]);
					geInfo.setRequired(arr[3]);
					geInfo.setMinLength(arr[4]);
					geInfo.setMaxLength(arr[5]);
					dataList.add(geInfo);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	static VelocityEngine ve = new VelocityEngine();
	

	static List<String> excludeFields = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("itemOrder");
			add("createTime");
			add("updateTime");
			add("createBy");
			add("updateBy");
			add("isActive");
			add("delFlg");
			add("id");
			add("class");
		}
	};
	
	public enum Type {
		add, edit, list, show;
	}

	
	private static String geModel() {
		String str = entity.toString();
		String[] arr = str.split("\\.");
		String	path = arr[arr.length-2];
		return path;
	}
	
	public static void ge(Type type) {
		if(StringUtils.isEmpty(model)){
			model = geModel();
		}
		List<GeInfo> dataList = readData();
		String html = "";
		String js = "";
		switch (type) {
		case add:
			html += "src/main/resources/velocity/addInit.html.vm";
			js += "src/main/resources/velocity/add.js.vm";
			break;

		case edit:
			html += "src/main/resources/velocity/editInit.html.vm";
			js += "src/main/resources/velocity/edit.js.vm";
			break;

		case list:
			html += "src/main/resources/velocity/list.html.vm";
			js += "src/main/resources/velocity/list.js.vm";
			break;

		case show:
			html += "src/main/resources/velocity/show.html.vm";
			js += "src/main/resources/velocity/show.js.vm";
			break;
		default:
			break;
		}
		Template t = ve.getTemplate(html, "UTF-8");
		VelocityContext context = new VelocityContext();
		context.put("foo", model);
		context.put("dataList", dataList);
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		geFile(type, model, writer, "html");

		Template t2 = ve.getTemplate(js, "UTF-8");
		VelocityContext context2 = new VelocityContext();
		context2.put("foo", model);
		context2.put("dataList", dataList);
		StringWriter writer2 = new StringWriter();
		t2.merge(context2, writer2);
		geFile(type, model, writer2, "js");
	}

	
	
	private static void geFile(Type type, String model, StringWriter writer, String fileType) {
		String html = null, js = null;
		switch (type) {
		case add:
			html = "addInit.html";
			js = "add.js";
			break;

		case edit:
			html = "editInit.html";
			js = "edit.js";
			break;

		case list:
			html = "list.html";
			js = "list.js";
			break;

		case show:
			html = "show.html";
			js = "show.js";
			break;
		default:
			break;
		}

		File file = null;
		if (fileType.equals("html")) {
			String htmlPath = basePath +"templates/"+ model + "/" + html;
			file = new File(htmlPath);
		}
		if (fileType.equals("js")) {
			String jsPath =  basePath +"static/js/"+ model + "/" + js;
			file = new File(jsPath, "");
		}

		File parent = file.getParentFile();
		if (parent != null) {
			parent.mkdirs();
		}
		try {
			String strTmp = writer.toString();
			strTmp=strTmp.replace("$ {", "${");
			Files.write(strTmp.getBytes(), file);
			System.out.println("ge success:"+type+" "+fileType);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
