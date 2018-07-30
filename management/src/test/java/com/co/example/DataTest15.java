package com.co.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.common.utils.PageReq;
import com.co.example.entity.label.TBrLabel;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.label.aide.TBrLabelQuery;
import com.co.example.entity.label.aide.TBrProductLabelQuery;
import com.co.example.entity.product.TBrAim;
import com.co.example.entity.product.TBrIngredient;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrAimQuery;
import com.co.example.entity.product.aide.TBrIngredientQuery;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.label.TBrLabelService;
import com.co.example.service.label.TBrProductLabelService;
import com.co.example.service.product.TBrAimService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductService;
import com.co.example.utils.BaseDataUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest15 {
	
	@Autowired	
	TBrLabelService  tBrLabelService;
	
	@Autowired	
	TBrProductService  tBrProductService;
	
	@Autowired	
	TBrIngredientService  tBrIngredientService;
	
	@Autowired	
	TBrAimService  tBrAimService;
	
	@Autowired	
	TBrProductLabelService  tBrProductLabelService;
	
	
	static HashMap<String, String> map = Maps.newHashMap();
	{
		map.put("保湿剂","保湿");
		map.put("清洁剂","深层清洁");
		map.put("表面活性剂","深层清洁");
		map.put("增泡剂","深层清洁");
		map.put("抗菌剂","抗痘");
		map.put("去角质","抗痘");
		map.put("美白祛斑","抗氧化,改善暗沉,美白,祛斑,淡斑");
		map.put("抗氧化剂","抗氧化,美白");
		map.put("抗炎剂","抗痘");
		map.put("舒缓抗敏","损伤修复,敏感,抗敏感");
		map.put("收敛剂","平衡水油,提拉紧致,去细纹");
		map.put("摩擦剂","控油平衡");
		map.put("柔润剂","润肤,滋润,柔肤");
	}
	
	
	//根据商品成分给商品标签做匹配,对商品表进行分页循环
	@Test
	public void getData() throws InterruptedException{
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		TBrProductQuery updateQuery = new TBrProductQuery();
		TBrIngredientQuery query = new TBrIngredientQuery();
		TBrLabelQuery tBrLabelQuery = new TBrLabelQuery();
		TBrProductLabelQuery tBrProductLabelQuery = new TBrProductLabelQuery();
		tBrProductQuery.setProductAlias("0");
		PageReq pageReq = new PageReq();
		int pageSize = 30;
		pageReq.setPage(0);
		pageReq.setPageSize(pageSize);
		TBrProduct tBrProduct = null;
		Long productId = null;
		long queryCount = tBrProductService.queryCount(tBrProductQuery);
		HashSet<String> set = Sets.newHashSet();
		String aimName = null;
		while(queryCount>0){
			log.info("queryCount===="+queryCount);;
			Page<TBrProduct> pageList = tBrProductService.queryPageList(tBrProductQuery, pageReq);
			List<TBrProduct> queryList = pageList.getContent();
			int size = queryList.size();
			for (int i=0;i<size ;i++) {
				tBrProduct = queryList.get(i);
				productId = tBrProduct.getId();
				/**
				 * 根据商品id 查询到全部成分 
				 * 根据成分查询出全部目的
				 * 将目的去重
				 * 根据目的匹配到标签
				 * 查询该标签是否已匹配该商品
				 * 未匹配该商品，添加匹配
				 */
				query.setJoinFlg(true);
				query.setProductId(productId);
				List<TBrIngredient> list = tBrIngredientService.queryList(query);
				for (TBrIngredient tBrIngredient : list) {
					TBrAimQuery tBrAimQuery = new TBrAimQuery();
					tBrAimQuery.setIngredientId(tBrIngredient.getId());
					tBrAimQuery.setJoinFlg(true);
					List<TBrAim> tBrAims = tBrAimService.queryList(tBrAimQuery);
					for (TBrAim tBrAim : tBrAims) {
						aimName = tBrAim.getName();
						set.add(aimName);
					}
				}
				for (String str : set) {
					String valueStr = map.get(str);
					if(valueStr!=null){
						String[] values = valueStr.split(",");
						for (String value : values) {
							//根据label名称获得label
							tBrLabelQuery.setName(value);
							TBrLabel queryOne = tBrLabelService.queryOne(tBrLabelQuery);
							//查询产品标签关联表，是否已经关联，如果未关联，则关联
							if(queryOne!=null){
								Long labelId = queryOne.getId();
								tBrProductLabelQuery.setLabelId(labelId);
								tBrProductLabelQuery.setProductId(productId);
								long match = tBrProductLabelService.queryCount(tBrProductLabelQuery);
								if(match ==0){
									//添加到产品标签关联表
									TBrProductLabel tBrProductLabel = new TBrProductLabel();
									tBrProductLabel.setLabelId(labelId);
									tBrProductLabel.setProductId(productId);
									BaseDataUtil.setDefaultData(tBrProductLabel);
									tBrProductLabelService.add(tBrProductLabel);
								}
							}
						}
					}
				}
				updateQuery.setId(productId);
				updateQuery.setProductAlias("1");
				tBrProductService.updateByIdSelective(updateQuery);
			}
			queryCount-=pageSize;
		}
	}
	

	
}




