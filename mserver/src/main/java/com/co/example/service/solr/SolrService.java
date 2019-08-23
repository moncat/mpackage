package com.co.example.service.solr;

import java.util.List;
import java.util.Map;

import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.product.aide.TBrProductSolr;

public interface SolrService {
    Boolean syncProducts(List<TBrProductSolr> tBrProductSolrList);
        
    Map<String,Object> queryProductSolr(TBrProductQuery query,int start,int rows);
    
    Map<String,Object> querySolr(TBrProductQuery query,int start,int rows);
    
    Boolean deleteProducts();
    
    Boolean deleteProductById(Long id);
    
}