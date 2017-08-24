package com.co.example.entity.product.aide;

public class ProductConstant {
	
	public static String CFDA_PRODUCT = "http://125.35.6.80:8080/ftba";
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
	 * 数据来源 1 药监局 ； 2美丽修行
	 */
	public static final Byte PRODUCT_SOURCE_CFDA = 1;
	/**
	 * 数据来源 1 药监局 ； 2美丽修行
	 */
	public static final Byte PRODUCT_SOURCE_BEVOL = 2;
	
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
	 * 图片类型 1平面图 2立体图 3商标证，授权书 
	 */
	public static final Byte IMAGETYPE_PLANE = 1;
	public static final Byte IMAGETYPE_CUBE = 2;
	public static final Byte IMAGETYPE_BRAND = 3;
	
	
}
