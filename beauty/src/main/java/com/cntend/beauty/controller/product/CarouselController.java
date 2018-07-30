package com.cntend.beauty.controller.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseRestControllerHandler;
import com.co.example.entity.product.aide.TBrCarouselQuery;


/**
 * 轮播图展示 不仅限于商品
 * @author zyl
 *
 */
@Controller
@RequestMapping("carousel")
public class CarouselController extends BaseRestControllerHandler<TBrCarouselQuery> {

	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrCarouselQuery query) {
		query.setIsActive(Constant.STATUS_ACTIVE);
		return super.listExt(model, session, request, response, pageReq, query);
	}
	
	

}
