package com.co.example.controller.label;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.common.utils.PageReq;
import com.co.example.constant.HttpStatusCode;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.label.aide.TBrLabelClassQuery;
import com.co.example.service.label.TBrLabelClassService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("labelClass")
public class LabelClassController extends BaseControllerHandler<TBrLabelClassQuery> {
	
	@Inject
	TBrLabelClassService tBrLabelClassService;
	
	@Override
	public Boolean addExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrLabelClassQuery t, PageReq pageReq, Map<String, Object> result) {
		TBrLabelClassQuery tBrLabelClassQuery = new TBrLabelClassQuery();
		tBrLabelClassQuery.setName(StringUtils.trim(t.getName()));
		long queryCount = tBrLabelClassService.queryCount(tBrLabelClassQuery);
		if(queryCount>0){
			result.put("code", HttpStatusCode.CODE_ERROR);
			result.put("info", "已有该分类，添加失败。");
		}else{	 
			tBrLabelClassService.add(t);			 
			result.put("info", "添加成功！ " );
		}
		return true;
	}

}
