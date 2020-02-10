package com.co.example.dao.product;

import java.util.List;

import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.BeVo;
import com.co.example.entity.product.aide.ConfirmVo;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.github.moncat.common.dao.BaseDao;

public interface TBrProductDao extends BaseDao<TBrProduct, Long> {

	List<String> selectOperEnterpriseFromProduct();

	int updateByArea(TBrProductQuery query);

	List<ConfirmVo> selectConfirmData(String startTime, String endTime, int type);

	List<BeVo> selectBeData();
}