package com.co.example.service.product;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.co.example.common.utils.PageReq;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.BeVo;
import com.co.example.entity.product.aide.ConfirmVo;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductVo;
import com.github.moncat.common.service.BaseService;

public interface TBrProductService extends BaseService<TBrProduct, Long> {
	
	int addProductFromCFDA(String page,String dateStr);

	int addProductFromCFDA(File file);
	/**
	 * 解析进口非特殊
	 * @param file
	 * @return
	 */
	int addINProductFromCFDA(File file);
	
	void addProductFromBEVOL(int page , int category);

	/**
	 * 保存日志
	 * @param source 来源
	 * @param url 访问url
	 * @param count 返回数量
	 * @param params
	 * @param e
	 */
	void saveLog(Byte source, String url, Integer count, String params, Exception e);
	
	
	void doSomeThing();
	
	/**
	 * 对成分进行统计分析
	 * @param tBrProduct
	 * @param ingredientList
	 * @return
	 */
	TBrProduct getStatisticsInfo(TBrProduct tBrProduct,List<TBrIngredient> ingredientList);
	
	
	/**
	 * 获取仅id 产品名称 的简易结果
	 * @param query
	 * @param pageable
	 * @return
	 */
	Page<TBrProduct> querySimplePageList(TBrProduct query, Pageable pageable);
	
	/**
	 * 仅运营企业的名称（从产品表获得，并去重）
	 * @return
	 */
	List<String> queryOperEnterpriseFromProduct();
	
	int updateByArea(TBrProductQuery query);
	/**
	 * 显示一条商品记录（移动端）
	 * 已弃用，因为服务器配置较低，为了提高显示效率，商品信息采用异步方式获取，此获取全量数据方法弃用
	 * @param id
	 * @return
	 */
	@Deprecated
	TBrProduct showOneProduct4Mobile(Long id);
	
	/**
	 * 根据标签获得商品列表 未登录
	 * 已弃用，未登录则不显示推荐
	 * @see queryByLabel(Long id,Long userId,PageReq pageReq);
	 * @param id
	 * @return
	 */
	@Deprecated
	Page<TBrProduct> queryByLabel(Long id,PageReq pageReq);
	
	
	/**
	 * 根据标签及个人肤质特点获得商品列表
	 * 已弃用，不再使用取交集的方式 ，使用左连接分组的方式
	 * @see queryByLabel(Long id,Long userId,PageReq pageReq);
	 * @param id
	 * @param pageReq
	 * @param userid
	 * @return
	 */
	@Deprecated
	Page<TBrProduct> queryByLabel(Long id,PageReq pageReq,Long userId);
	
	
	/**
	 * 获得更低的价格
	 * @param id 商品Id
	 * @return
	 */
	BigDecimal getCheapPrice(Long id);
	
	/**
	 * 设置商品的标签
	 * @param id
	 */
	TBrProduct setLabels(TBrProduct tBrProduct);
	
	/**
	 * 根据日期获取备案数量
	 * @param days 获取的数
	 * @param type 是否获取注销   1 获取注销  0不获取注销
	 * @return
	 */
	 List<ConfirmVo>  queryConfirmData(String startTime ,String endTime,Integer type);
	
	 
	 List<BeVo>  queryBeData();
	 
	 
	 List<TBrProductVo> queryProductVoListByBrandId(Long id);
	 
	 Page<TBrProductVo> queryProductVoPageByBrandId(Long id ,PageReq pageReq);
	 
	 long queryProductNumByBrandId(Long id);
	 
	 long queryProductNumByRealEnterpriseId(Long id);
	 
	 List<TBrProductVo> queryProductVoListByRealEnterpriseId(Long id);
	 
	 Page<TBrProductVo> queryProductVoPageByRealEnterpriseId(Long id,PageReq pageReq);
	 
	 List<TBrProductVo> queryProductVoListByIngredientId(Long id);
	 
	 void updateStatus(Long id, Long status);
	
}