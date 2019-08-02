package com.co.example.controller.export;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.export.aide.TBrExportQuery;

@Controller
@RequestMapping("export")
public class ExportController extends BaseControllerHandler<TBrExportQuery> {

	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrExportQuery query) {
		Sort sort = new Sort(Direction.DESC, "t.create_time");
		pageReq.setSort(sort);
		return super.listExt(model, session, request, response, pageReq, query);
	}

}
