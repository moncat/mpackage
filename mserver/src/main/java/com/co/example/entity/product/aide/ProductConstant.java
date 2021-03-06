package com.co.example.entity.product.aide;

public class ProductConstant {
	
//	public static String CFDA_PRODUCT = "http://125.35.6.80:8080/ftba";
	public static String CFDA_PRODUCT = "http://125.35.6.80:8181/ftban";
	public static String BEVOL_PRODUCT = "https://www.bevol.cn";
	
	//药监局产品url
	public static final String CFDA_PRODUCT_URL =CFDA_PRODUCT+"/itownet/fwAction.do?method=getBaNewInfoPage" ;
	
	//药监局产品成分url
	public static final String CFDA_INGREDIENT_URL =CFDA_PRODUCT+"/itownet/fwAction.do?method=getBaInfo" ;
	        					
	
	//药监局产品图片url
	public static final String CFDA_PRODUCT_IMAGE_URL =CFDA_PRODUCT+"/itownet/fwAction.do?method=getAttachmentCpbz" ;
								
	//药监局产品图片预览
	public static final String CFDA_PRODUCT_IMAGE_SHOW =CFDA_PRODUCT+"/itownet/_static/common/FlexPaper_2.0.2/yl2015.jsp?fileId=@1" ;
	
	//药监局产品图片查看
	public static final String CFDA_PRODUCT_IMAGE_DOWNLOAD =CFDA_PRODUCT+"/itownet/download.do?method=downloadFile&fid=@1&ssid=@2" ;

	//美丽修行产品url
	public static final String BEVOL_PRODUCT_URL = "https://api.bevol.cn/search/goods/index3";
	//美丽修行产品成分url
	public static final String BEVOL_INGREDIENT_URL = BEVOL_PRODUCT+"/product/";
	/**
	 * 数据来源 1 药监局 ， 2美丽修行  ，3京东 ，4天猫
	 */
	public static final Byte PRODUCT_SOURCE_CFDA = 1;
	
	/**
	 * 数据来源 1 药监局 ， 2美丽修行  ，3京东 ，4天猫
	 */
	public static final Byte PRODUCT_SOURCE_BEVOL = 2;
	
	/**
	 * 数据来源 1 药监局 ， 2美丽修行  ，3京东 ，4天猫
	 */
	public static final Byte PRODUCT_SOURCE_JD = 3;
	
	/**
	 * 数据来源 1 药监局 ， 2美丽修行  ，3京东 ，4天猫
	 */
	public static final Byte PRODUCT_SOURCE_TMALL = 4;
	
	/**
	 * 产品来源  1 国产  2进口
	 */
	public static final String PRODUCT_APPLYTYPE_DOMESTIC = "1";
	/**
	 * 产品来源  1 国产  2进口
	 */
	public static final String PRODUCT_APPLYTYPE_IMPORT = "2";
	
	/**
	 * 备案是否注销   1是 2否
	 */
	public static final String PRODUCT_ISOFF_YES = "1";
	/**
	 * 备案是否注销   1是 2否
	 */
	public static final String PRODUCT_ISOFF_NO = "0";
	

	/**
	 * 填充0， eg：安全等级
	 */
	public static final Byte ZERO_BYTE = 0;

	
	
	/**
	 * 图片类型 1平面图 2立体图 3商标证，授权书  4其他未确定  5技术要求
	 */
	public static final Byte IMAGETYPE_PLANE = 1;
	public static final Byte IMAGETYPE_CUBE = 2;
	public static final Byte IMAGETYPE_BRAND = 3;
	public static final Byte IMAGETYPE_OTHER = 4;
	public static final Byte IMAGETYPE_TECHNICAL = 5;
	
	/**
	 * 京东产品查询url
	 */
	public static final String JD_PRODUCT_SEARCH_URL = "https://search.jd.com/Search?enc=utf-8&keyword=";
	
	/**
	 * 天猫产品查询url
	 */
	public static final String TMALL_PRODUCT_SEARCH_URL = "https://list.tmall.com/search_product.htm?q=";
	
	//类型转变时，需要更新数据 TODO 抓取其他 进口非特殊json格式不同。
	public static final String CFDA_FILE_PATH = "/home/file/cfda/domesticNonspecial/";
	public static final String CFDA_OVER_FILE_PATH = "/home/file/cfda/domesticNonspecial/over/";
//	public static final String CFDA_FILE_PATH = "d:/home/file/cfda/";
//	public static final String CFDA_OVER_FILE_PATH = "d:/home/file/cfda/over/";
	
	public static final String CFDA_IN_PATH = "/home/file/cfda/importNonspecial/";
	public static final String CFDA_OVER_IN_PATH = "/home/file/cfda/importNonspecial/over/";
	
	
}











