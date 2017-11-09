package com.co.example.service.product;

import java.util.List;

import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;

import com.co.example.entity.comment.aide.Comment;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.TBrProductSpec;
import com.co.example.entity.spec.TBrSpecKey;
import com.github.moncat.common.service.BaseService;
import us.codecraft.webmagic.proxy.Proxy;

public interface TBrProductSpecService extends BaseService<TBrProductSpec, Long> {
	
	/**
	 * 抓取天猫 京东数据
	 * @param tBrProduct
	 * @param sourceType
	 * @return
	 */
	int addData(TBrProduct tBrProduct, Byte sourceType,List<TBrSpecKey> tbrSpecKeyList,WebDriver chrome);
	
	/**
	 * 使用webmagic 继续爬取
	 * @param doc
	 * @param sourceType
	 * @param tbrSpecKeyList
	 * @return
	 */
	int addDataWM(TBrProduct tBrProduct, Byte sourceType,List<TBrSpecKey> tbrSpecKeyList,WebDriver chrome);
	/**
	 * 实时抓取评论
	 * @param id
	 * @return
	 */
	List<Comment> getComment(Long id);
}