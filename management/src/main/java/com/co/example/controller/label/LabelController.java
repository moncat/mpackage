package com.co.example.controller.label;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.co.example.common.utils.PageReq;
import com.co.example.constant.HttpStatusCode;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.label.TBrLabel;
import com.co.example.entity.label.aide.TBrLabelQuery;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.label.TBrLabelService;
import com.co.example.service.product.TBrProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("label")
public class LabelController extends  BaseControllerHandler<TBrLabelQuery> {

	@Autowired
	TBrLabelService  tBrLabelService;
	
	@Autowired
	TBrProductService  tBrProductService;
	
	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrLabelQuery query) {
		List<TBrLabel> list = tBrLabelService.queryList();
		model.addAttribute(QUERY, query);
		model.addAttribute(LIST, list);
		return true;
	}



	@RequestMapping(value = "/detail/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String showMore(Model model, HttpSession session,PageReq pageReq, @PathVariable Long id) {
		TBrLabel one = tBrLabelService.queryById(id);
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinLabelFlg(true);
		tBrProductQuery.setLabelId(one.getId());
		pageReq.setPageSize(10);
		Page<TBrProduct> page = tBrProductService.queryPageList(tBrProductQuery, pageReq);
		model.addAttribute(PAGE, page);
		model.addAttribute(ONE, one);
		return "label/detail";
	}



	@Override
	public Boolean addExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrLabelQuery t, PageReq pageReq, Map<String, Object> result) {
		TBrLabelQuery tBrLabelQuery = new TBrLabelQuery();
		tBrLabelQuery.setName(StringUtils.trim(t.getName()));
		long queryCount = tBrLabelService.queryCount(tBrLabelQuery);
		if(queryCount>0){
			result.put("code", HttpStatusCode.CODE_ERROR);
			result.put("info", "已有该标签，添加失败。");
		}else{
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tBrLabelService.add(t);
			int count = tBrLabelService.addConnect2Product(t);
			log.info(t.getName()+"("+t.getId()+") 成功数量："+count);
			result.put("info", "添加成功！已匹配"+count);
		}
		return true;
	}
	
		
	
	
}
