package com.co.example.service.solr;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductSolr;

public interface SolrService {
    Boolean syncProducts(List<TBrProductSolr> tBrProductSolrList);
    
    Boolean syncOne(TBrProductSolr tBrProductSolr);
    
    Integer syncOne(TBrProduct tBrProduct,Boolean check,Long status);
    
    //从数据库对象转到solr对象
    TBrProductSolr getSolrBean(TBrProduct tBrProduct);
    
    @Deprecated  
    Map<String,Object> queryProductSolr(TBrProductQuery query,int start,int rows);
    @Deprecated
    Map<String,Object> querySolr(TBrProductQuery query,int start,int rows);
    
    Map<String,Object> querySolr2(TBrProductQuery query,int start,int rows);
    
    List<Long> querySolr3(TBrProductQuery query,int start,int rows);
    
    Boolean deleteProducts();
    
    Boolean deleteProductById(Long id);
    
    int updateByIdSelective(String id,String fieldName,Object fieldValue);

	void updateInBatch(String id,Map<String, String> maps);

	HashSet<String> queryByIds(HashSet<String> ids);
	
	TBrProductSolr queryById(Long id);
		
	Long queryCount();

    
}