package com.co.example.common.utils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *  List按照指定字段排序工具类
 *
 * @param <T>
 */

public class ListSortUtil {
	/**
	 * @param targetList 目标排序List
	 * @param sortField 排序字段(实体类属性名)
	 * @param sortMode 排序方式（asc or  desc）
	 */
	public  static void sort(List<?> targetList, final String sortField, final String sortMode) {
		Collections.sort(targetList, new Comparator<Object>() {
			public int compare(Object obj1, Object obj2) { 
				int retVal = 0;
				try {
					//首字母转大写
					String newStr=sortField.substring(0, 1).toUpperCase()+sortField.replaceFirst("\\w",""); 
					String methodStr="get"+newStr;
					
					Method method1 = obj1.getClass().getMethod(methodStr);
					Method method2 = obj2.getClass().getMethod(methodStr);
					if (sortMode != null && "desc".equals(sortMode)) {
						retVal = method2.invoke(obj2).toString().compareTo(method1.invoke(obj1).toString()); // 倒序
					} else {
						retVal = method1.invoke(obj1).toString().compareTo(method2.invoke(obj2).toString()); // 正序
					}
				} catch (Exception e) {
					throw new RuntimeException();
				}
				return retVal;
			}
		});
	}
	
}
