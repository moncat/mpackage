package com.cntend.beauty.controller.advertisement;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseRestControllerHandler;
import com.co.example.entity.advertisement.TBrAdvertisement;
import com.co.example.entity.advertisement.aide.TBrAdvertisementQuery;
import com.co.example.service.advertisement.TBrAdvertisementService;

@Controller
@RequestMapping("ad")
public class AdvertisementController extends  BaseRestControllerHandler<TBrAdvertisementQuery> {
	
	@Inject
	TBrAdvertisementService tBrAdvertisementService;
	
	@ResponseBody
	@RequestMapping(value = "/init", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> init() {
		Map<String, Object> result = result();
		TBrAdvertisementQuery tBrAdvertisementQuery = new TBrAdvertisementQuery();
		tBrAdvertisementQuery.setIsActive(Constant.YES);
		PageReq  pageReq = new PageReq();
		pageReq.setPage(1);
		pageReq.setPageSize(1);
		pageReq.setSort(new Sort(Direction.DESC,"t.create_time"));
		Page<TBrAdvertisement> page = tBrAdvertisementService.queryPageList(tBrAdvertisementQuery, pageReq);
		if(page.getSize()>0){
			result.put(ONE, page.getContent().get(0));
		}
		return result;
	}
	
}







