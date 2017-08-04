package com.co.example;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class PackageUtils {

	private static final Logger LOG = LoggerFactory.getLogger(PackageUtils.class);
	private static final String fileSeparator = System.getProperty("file.separator");

	/**
	 * 获取某包下（包括该包的所有子包）所有类
	 * 
	 * @param packageName
	 *                        包名
	 * @return 类的完整名称
	 */
	public static List<String> getClassName(String packageName) {
		return getClassName(packageName, true);
	}

	/**
	 * 获取某包下所有类
	 * 
	 * @param packageName
	 *                        包名
	 * @param childPackage
	 *                        是否遍历子包
	 * @return 类的完整名称
	 */
	public static List<String> getClassName(String packageName, boolean childPackage) {
		List<String> fileNames = null;

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		String packagePath = packageName.replace(".", "/");
		URL url = loader.getResource(packagePath);
		LOG.info("package url: {}", url);
		if (url != null) {
			String type = url.getProtocol();
			if (type.equals("file")) {
				fileNames = getClassNameByFile(url.getPath(), null, childPackage);
			} else if (type.equals("jar")) {
				fileNames = getClassNameByJar(url.getPath(), childPackage);
			}
		} else {
			//fileNames = getClassNameByJars(((URLClassLoader) loader).getURLs(), packagePath, childPackage);
		}
		return fileNames;
	}

	/**
	 * 从项目文件获取某包下所有类
	 * 
	 * @param filePath
	 *                        文件路径
	 * @param className
	 *                        类名集合
	 * @param childPackage
	 *                        是否遍历子包
	 * @return 类的完整名称
	 */
	private static List<String> getClassNameByFile(String filePath, List<String> className, boolean childPackage) {
		List<String> myClassName = new ArrayList<String>();
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		for (File childFile : childFiles) {
			if (childFile.isDirectory()) {
				if (childPackage) {
					myClassName.addAll(getClassNameByFile(childFile.getPath(), myClassName, childPackage));
				}
			} else {
				final String classesSeparator = "classes" + fileSeparator;
				String childFilePath = childFile.getPath();
				if (childFilePath.endsWith(".class")) {
					System.out.println(childFilePath);
					int index = childFilePath.lastIndexOf(classesSeparator);
					if (index != -1) {
						childFilePath = childFilePath.substring(index + classesSeparator.length(),
								childFilePath.length() - 6);
						childFilePath = childFilePath.replace(fileSeparator, ".");
						myClassName.add(childFilePath);
					}
				}
			}
		}

		return myClassName;
	}

	/**
	 * 从jar获取某包下所有类
	 * 
	 * @param jarPath
	 *                        jar文件路径
	 * @param childPackage
	 *                        是否遍历子包
	 * @return 类的完整名称
	 */
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
		jars.add(BasePath+"/spring-context/4.3.8.RELEASE/spring-context-4.3.8.RELEASE.jar");
		String[] strArr = new String[jars.size()];
		jars.toArray(strArr);
		List<String> className = getClassNameByJars(strArr, " org", true);
		
		for (String cn : className) {
			try {
				Class<?> onwClass = Class.forName(cn);
				if(onwClass !=null){
					Annotation[] annotations = onwClass.getAnnotations();
					if(annotations!=null && annotations.length>0){
						annotationsList.add("**"+cn);
					}
				}
			} catch (ClassNotFoundException e) {
				continue;
			}catch(NoClassDefFoundError f){
				continue;
			}
		}
		
		for (String str : annotationsList){
			System.out.println(str);
		}
		
	}
	
}
