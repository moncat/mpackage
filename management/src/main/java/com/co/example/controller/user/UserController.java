package com.co.example.controller.user;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.user.TBrUserAddress;
import com.co.example.entity.user.aide.TBrUserAddressQuery;
import com.co.example.entity.user.aide.TUserQuery;
import com.co.example.service.user.TBrUserAddressService;

@Controller
@RequestMapping("user")
public class UserController extends  BaseControllerHandler<TUserQuery> {

	@Resource
	TBrUserAddressService tBrUserAddressService;
	
	
	@Override
	public Boolean showExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TUserQuery t, Long id) {
		TBrUserAddressQuery tBrUserAddressQuery = new TBrUserAddressQuery();
		tBrUserAddressQuery.setUid(id);
		List<TBrUserAddress> addressList = tBrUserAddressService.queryList(tBrUserAddressQuery);
		model.addAttribute("addressList", addressList);
		return super.showExt(model, session, request, response, t, id);
	}

	
	
	
}
