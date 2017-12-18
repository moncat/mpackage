package com.co.example.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.user.TBrUserReport;
import com.co.example.entity.user.TBrUserReportItem;
import com.co.example.entity.user.aide.TBrUserReportItemQuery;
import com.co.example.entity.user.aide.TBrUserReportQuery;
import com.co.example.security.MyInvocationSecurityMetadataSourceService;
import com.co.example.service.user.TBrUserReportItemService;
import com.co.example.service.user.TBrUserReportService;

/**
 * 商品试用 后填写报告
 * @author zyl
 *
 */
@Controller
@RequestMapping("report")
public class ReportController extends  BaseControllerHandler<TBrUserReportQuery> {

	@Autowired
	TBrUserReportService tBrUserReportService;
	
	@Autowired
	TBrUserReportItemService tBrUserReportItemService;
	
	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrUserReportQuery query) {
		query.setJoinFlg(true);
		Page<TBrUserReport> page = tBrUserReportService.queryPageList(query, pageReq);
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, page);
		return true;
	}

	@Override
	public Boolean showExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrUserReportQuery t, Long id) {
		TBrUserReportItemQuery itemQuery = new TBrUserReportItemQuery();
		itemQuery.setReportId(id);
		List<TBrUserReportItem> itemList = tBrUserReportItemService.queryList(itemQuery);
		model.addAttribute("itemList",itemList);
		return super.showExt(model, session, request, response, t, id);
	}

	
}
