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
import com.co.example.entity.comment.TBrProductCommentStatistics;
import com.co.example.entity.comment.aide.TBrProductCommentStatisticsQuery;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.TBrProductImage;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.comment.TBrProductCommentStatisticsService;
import com.co.example.service.product.TBrProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest13 {
	
	@Autowired
	TBrProductService tBrProductService;
	
	@Autowired	
	TBrProductCommentStatisticsService  tBrProductCommentStatisticsService;
	
	//给商品表做冗余数据          好评度来自于京东好评度
	@Test
	public void getData() throws InterruptedException{
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		Byte over =2;
		Byte overTmp =8;

		tBrProductQuery.setDelFlg(over);
		TBrProductCommentStatisticsQuery pcsQuery = new TBrProductCommentStatisticsQuery();
		PageReq pageReq = new PageReq();
		int pageSize = 30;
		pageReq.setPage(0);
		pageReq.setPageSize(pageSize);
		TBrProduct tBrProduct = null;
		Long id = null;
		String moreData2 = null;
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
				
				pcsQuery.setPid(id);
				TBrProductCommentStatistics queryOne = tBrProductCommentStatisticsService.queryOne(pcsQuery);
				if(queryOne !=null){
					moreData2 = queryOne.getJdNumGood();
					moreData2 = moreData2.replace("+", "").replace(".", "").replace("万", "0000");
					updateQuery.setId(id);
					updateQuery.setDelFlg(overTmp);
					updateQuery.setMoreData2(moreData2);
					tBrProductService.updateByIdSelective(updateQuery);
				}
			}
			queryCount-=pageSize;
		}
	}
}




