package com.co.example;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.Lists;

public class PackageTest {

	/**
	 * 从jar获取某包下所有类
	 * 
	 * @param jarPath
	 *                        jar文件路径
	 * @param childPackage
	 *                        是否遍历子包
	 * @return 类的完整名称
	 */
	@ResponseStatus(reason="no reason",value=HttpStatus.BAD_REQUEST)  
	private static List<String> getClassNameByJar(String jarPath, boolean childPackage) {
		List<String> myClassName = new ArrayList<String>();
		String[] jarInfo = jarPath.split("!");
		String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
		String packagePath = jarInfo[1].substring(1);
		try (JarFile jarFile = new JarFile(jarFilePath);) {
			Enumeration<JarEntry> entrys = jarFile.entries();
			while (entrys.hasMoreElements()) {
				JarEntry jarEntry = entrys.nextElement();
				String entryName = jarEntry.getName();
				if (entryName.endsWith(".class")) {
					if (childPackage) {
						if (entryName.startsWith(packagePath)) {
							entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
							myClassName.add(entryName);
						}
					} else {
						int index = entryName.lastIndexOf("/");
						String myPackagePath;
						if (index != -1) {
							myPackagePath = entryName.substring(0, index);
						} else {
							myPackagePath = entryName;
						}
						if (myPackagePath.equals(packagePath)) {
							entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
							myClassName.add(entryName);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return myClassName;
	}

	/**
	 * 从所有jar中搜索该包，并获取该包下所有类
	 * 
	 * @param urls
	 *                        URL集合
	 * @param packagePath
	 *                        包路径
	 * @param childPackage
	 *                        是否遍历子包
	 * @return 类的完整名称
	 */
	private static List<String> getClassNameByJars(String[] urls, String packagePath, boolean childPackage) {
		List<String> myClassName = new ArrayList<String>();
		if (urls != null) {
			for (int i = 0; i < urls.length; i++) {
				String urlPath = urls[i];
				// 不必搜索classes文件夹
				if (urlPath.endsWith("classes/")) {
					continue;
				}
				String jarPath = urlPath + "!" + packagePath;
				myClassName.addAll(getClassNameByJar(jarPath, childPackage));
			}
		}
		return myClassName;
	}
	public static void main(String[] args) {
		List<String> annotationsList = Lists.newArrayList();
		String BasePath="D:/repository/maven/org/springframework";
		List<String> jars = Lists.newArrayList();
	//	jars.add(BasePath+"/spring-context/4.3.8.RELEASE/spring-context-4.3.8.RELEASE.jar");
	//	jars.add(BasePath+"/spring-web/4.3.8.RELEASE/spring-web-4.3.8.RELEASE.jar");
	//	jars.add(BasePath+"/spring-webmvc/4.3.8.RELEASE/spring-webmvc-4.3.8.RELEASE.jar");
	//	jars.add(BasePath+"/spring-beans/4.3.8.RELEASE/spring-beans-4.3.8.RELEASE.jar");
	//	jars.add(BasePath+"/spring-core/4.3.8.RELEASE/spring-core-4.3.8.RELEASE.jar");
		jars.add(BasePath+"/data/spring-data-commons/1.13.3.RELEASE/spring-data-commons-1.13.3.RELEASE.jar");
		
		String[] strArr = new String[jars.size()];
		jars.toArray(strArr);
		String packagePath=" org"; //此处有个空格
		List<String> className = getClassNameByJars(strArr, packagePath, true);
		
		
		for (String cn : className) {
			try {
				Class<?> onwClass = Class.forName(cn);
				if(onwClass !=null){
					
					boolean annotationFlg = onwClass.isAnnotation();
					if(annotationFlg){
						annotationsList.add(cn);
					}
				}
			} catch (Exception e) {
				continue;
			}catch(Error f){
				continue;
			}
		}
		
		System.out.println("********************");
		for (String str : annotationsList){
			System.out.println(str);
		}
		
	}
	
}
