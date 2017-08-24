package com.co.example.base.bean;

import lombok.Data;

@Data
public class GeInfo {
	
	/** 对应实体类属性名 */
	private String field;
	
	/** 标签 */
	private String label;
	
	/** 排序 */
	private String order;
	
	/** 类型 1单行文本 2下拉框 3单选 4复选 5 多行文本 6日期 7上传 */
	private String type;
	
	/** 必填 */
	private String required;
	
	/** 最小长度 */
	private String minLength;
	
	/** 最大长度 */
	private String maxLength;
}
