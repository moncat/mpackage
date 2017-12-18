package com.co.example;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.example.entity.comment.aide.TBrProductCommentStatisticsQuery;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductImageQuery;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductSpecQuery;
import com.co.example.service.comment.TBrProductCommentStatisticsService;
import com.co.example.service.product.TBrProductImageService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.product.TBrProductSpecService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ManagerApplication.class, webEnvironment = WebEnvironment.MOCK)
public class DataTest11ErrData {
	
	@Autowired
	TBrProductService tBrProductService;
	
	@Autowired
	TBrProductSpecService tBrProductSpecService;
	
	@Autowired	
	TBrProductImageService  tBrProductImageService;
	
	@Autowired	
	TBrProductCommentStatisticsService tBrProductCommentStatisticsService;
	
	
	@Test
	public void getData() throws InterruptedException{
		TBrProductQuery query = new TBrProductQuery();
		query.setUseTmallUrlNotNullFlg(true);
		query.setProductNameLike("&");
		List<TBrProduct> list = tBrProductService.queryList(query);
		int size = list.size();
		TBrProduct tBrProduct = null;
		Long id = 0l;
		TBrProduct pTmp = null;
		for (int i = 0; i < size; i++) {
			tBrProduct = list.get(i);
			id = tBrProduct.getId();
			TBrProductSpecQuery tBrProductSpecQuery = new TBrProductSpecQuery();
			tBrProductSpecQuery.setPid(id);
			int n1 = tBrProductSpecService.delete(tBrProductSpecQuery);
			
			TBrProductImageQuery tBrProductImageQuery = new TBrProductImageQuery();
			tBrProductImageQuery.setProductId(id);
			tBrProductImageQuery.setTmallUrlIsNotNull(true);
			int n2 = tBrProductImageService.delete(tBrProductImageQuery);
			
			pTmp = new TBrProduct();
			pTmp.setId(id);
			pTmp.setTmallPrice(null);
			pTmp.setSales(null);
			pTmp.setTmallUrl("0");
			int n3 = tBrProductService.updateByIdSelective(pTmp);
			
			TBrProductCommentStatisticsQuery tBrProductCommentStatisticsQuery = new TBrProductCommentStatisticsQuery();
			tBrProductCommentStatisticsQuery.setPid(id);
			int n4 = tBrProductCommentStatisticsService.delete(tBrProductCommentStatisticsQuery);
			
			System.out.println(n1+" "+n2+" "+n3+" "+n4+" "+i);
			
			
		}
		
	}
		
}




//delete from t_br_product_spec where pid = '2321903187017728';
//delete  from t_br_product_image where product_id = '2321903187017728' and tmall_url is not null;
//update t_br_product set tmall_price = null ,sales = null ,tmall_url ='0'
// where id='2321903187017728';
//delete from t_br_product_comment_statistics where pid = '2321903187017728';



