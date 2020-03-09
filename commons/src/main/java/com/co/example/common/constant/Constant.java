package com.co.example.common.constant;

/**
 * 公共常量
 * @author ZYL
 */
public class Constant {
	/**
	 * 状态有效
	 */
	public static final Byte STATUS_ACTIVE = 1;
	/**
	 * 状态无效
	 */
	public static final Byte STATUS_NOT_ACTIVE = 0;
	
	
	/**
	 * 是
	 */
	public static final Byte YES = 1;
	/**
	 * 否
	 */
	public static final Byte NO = 0;
	
	
	public static final Byte STATUS_ZERO = 0;
	public static final Byte STATUS_ONE = 1;
	public static final Byte STATUS_TWO = 2;
	public static final Byte STATUS_THREE = 3;
	
	
	public static final String  IMAGE_BASE_PATH = "d:/home/images/";
	
	public static final String  EXPORT_BASE_SAVE_PATH = "/home/file/export/";
	
	public static final String  EXPORT_BASE_READ_PATH = "http://114.215.106.58:7001/file/export/";
	
	
	public static final Byte TASK_STATUS_WAITTING = 1;  //等待初始化数据
	public static final Byte TASK_STATUS_TODO = 2;      //已经初始化数据，等待处理
	public static final Byte TASK_STATUS_DOING = 3;     //数据处理中
	public static final Byte TASK_STATUS_DONE = 4;      //数据处理完毕
	
}
