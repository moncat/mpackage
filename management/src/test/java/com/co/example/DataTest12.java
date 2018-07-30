package com.co.example;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.common.utils.PageReq;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.TBrProductImage;
import com.co.example.entity.product.aide.TBrProductImageQuery;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.product.TBrProductImageService;
import com.co.example.service.product.TBrProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest12 {
	
	@Autowired
	TBrProductService tBrProductService;
	
	@Autowired	
	TBrProductImageService  tBrProductImageService;
	
	//给商品表做冗余数据 商品图片来自于第二张京东图片
	@Test
	public void getData() throws InterruptedException{
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		Byte over =2;
		Byte overTmp =8;
		Byte source = 3; //4取天猫的图片  3取京东数据  （2018年2月23日改动 ）
		tBrProductQuery.setDelFlg(over);
		TBrProductImageQuery tBrProductImageQuery = new TBrProductImageQuery();
		tBrProductImageQuery.setSource(source);
		PageReq pageReq = new PageReq();
		int pageSize = 30;
		pageReq.setPage(0);
		pageReq.setPageSize(pageSize);
		TBrProduct tBrProduct = null;
		String jdUrl = null;
		Long id = null;
		TBrProductImage image = null;
		TBrProductQuery updateQuery = new TBrProductQuery();
		long queryCount = tBrProductService.queryCount(tBrProductQuery);
		while(queryCount>0){
			System.out.println("last====="+queryCount);
			Page<TBrProduct> pageList = tBrProductService.queryPageList(tBrProductQuery, pageReq);
			List<TBrProduct> queryList = pageList.getContent();
			int size = queryList.size();
			for (int i=0;i<size ;i++) {
				System.out.println("i======================="+i);
				tBrProduct = queryList.get(i);
				id = tBrProduct.getId();
				tBrProductImageQuery.setProductId(id);
				List<TBrProductImage> list = tBrProductImageService.queryList(tBrProductImageQuery);
				if(list.size()>0){
					if(list.size()>1){
						image = list.get(1);
					}
					if(image ==null){
						image = list.get(0);					
					}
					if(image !=null){
						jdUrl = "https://img11.360buyimg.com/n1/"+image.getJdUrl();
						System.out.println("jdUrl"+jdUrl);
					}
					updateQuery.setId(id);
					updateQuery.setDelFlg(overTmp);
					updateQuery.setMoreData1(jdUrl);
					tBrProductService.updateByIdSelective(updateQuery);
				}
			}
			queryCount-=pageSize;
		}
	}
}




