package com.co.example.service.taobao;

import com.co.example.entity.taobao.TBrTaobaoGoods;
import com.github.moncat.common.service.BaseService;

public interface TBrTaobaoGoodsService extends BaseService<TBrTaobaoGoods, Long> {
	
	void grapTaobaoSort();
	
	void grapTmallSort();
	
	void grapTaobaoGoods();
	
	void grapTmallGoods();
	
	void grapTmallGoodsSells();
	
}