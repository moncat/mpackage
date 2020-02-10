package com.co.example.service.brand;

import java.util.List;

import org.jsoup.nodes.Element;

import com.co.example.entity.brand.TBrBrand;
import com.co.example.entity.brand.aide.TBrBrandVo;
import com.github.moncat.common.service.BaseService;

public interface TBrBrandService extends BaseService<TBrBrand, Long> {

	/**
	 * 1 从太平洋女性网抓取品牌数据
	 */
	int addBrandFromPclady(Element eachBrand);
	
	/**
	 * 2 从YOKA时尚网抓取数据
	 */
	public int addBrandFromYOKA(Element eachBrand);
	
	
	/**
	 * 3从Onlylady女人志时尚网抓取数据
	 */
	public int addBrandFromONLYLADY(Element eachBrand);
	
	/**
	 * 4从腾讯女性时尚网抓取数据
	 */
	public int addBrandFromQQLADY(Element eachBrand);
	
	
	/**
	 * 5从39化妆品库抓取数据
	 */
	public int addBrandFrom39(Element eachBrand);
	
	/**
	 * 6从凤凰时尚化妆品库抓取数据
	 */
	public int addBrandFromIfeng(Element eachBrand);
	
	/**
	 * 7从瑞丽网抓取数据
	 */
	public int addBrandFromRAYLI(Element eachBrand);
	
	/**
	 * 8从网易女人抓取数据
	 */
	public int addBrandFrom163LADY(Element eachBrand);
	
	/**
	 * 品牌名称列表（根据名称长度倒序） 
	 */
	List<TBrBrand> queryByNameLength();
	
	/**
	 * 品牌名称列表（根据英文名称长度倒序） 
	 */
	List<TBrBrand> queryByNameEnLength();
	
	
	int addConnect2Product(TBrBrand tBrBrand);
	
	

	TBrBrandVo queryByProductId(Long id);
	
	void addTBrEnterpriseBrandByBrand(TBrBrand brand);
}



