package com.cntend.beauty.controller.user;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseRestControllerHandler;
import com.co.example.entity.user.TBrUserAddress;
import com.co.example.entity.user.aide.TBrUserAddressQuery;
import com.co.example.service.user.TBrUserAddressService;
import com.co.example.utils.SessionUtil;

@Controller
@RequestMapping("address")
public class AddressController extends BaseRestControllerHandler<TBrUserAddressQuery> {

		@Inject
		TBrUserAddressService  tBrUserAddressService;
	
		//收货地址二级页面
		@RequestMapping(value = "/init")
		public String init() {
			return "address/init";
		}

		@Override
		public Boolean listExt(Model model, HttpSession session, HttpServletRequest request,
				HttpServletResponse response, PageReq pageReq, TBrUserAddressQuery query) {
			Long userId = SessionUtil.getUserId(session);
			query.setCreateBy(userId);
			return super.listExt(model, session, request, response, pageReq, query);
		}
	
		
		
		
		
		//列表页面设置默认地址
		@ResponseBody
		@RequestMapping(value = "/here", method = { RequestMethod.POST })
		public Map<String, Object> here(Model model, HttpSession session,Long id) {
			Map<String, Object> result = result();
			Long userId = SessionUtil.getUserId(session);
			tBrUserAddressService.updateDefaultAddress(id,userId);
			return result;
		}
		
		//收货地址添加三级页面
		@RequestMapping(value = "/addInit")
		public String addInit(Model model, HttpSession session) {
			return "address/addInit";
		}
		
		
		
		@Override
		public Boolean addExt(Model model, HttpSession session, HttpServletRequest request,
				HttpServletResponse response, TBrUserAddressQuery t, PageReq pageReq, Map<String, Object> result) {
			Long userId = SessionUtil.getUserId(session);
			t.setUid(userId);
			t.setCreateBy(userId);
			Byte isDefault = t.getIsDefault();
			if(isDefault != null && 1 == isDefault){
				tBrUserAddressService.updateDefaultAddress(null,userId);
			}
			return super.addExt(model, session, request, response, t, pageReq, result);
		}

		//收货地址编辑三级页面
		@RequestMapping(value = "/editInit/{id}")
		public String editInit(Model model, HttpSession session,@PathVariable Long id) {
			TBrUserAddressQuery tBrUserAddressQuery = new TBrUserAddressQuery();
			tBrUserAddressQuery.setUid(SessionUtil.getUserId(session));
			tBrUserAddressQuery.setId(id);
			TBrUserAddress one = tBrUserAddressService.queryOne(tBrUserAddressQuery);
			model.addAttribute(ONE, one);
			return "address/editInit";
		}

		@Override
		public Boolean editExt(Model model, HttpSession session, HttpServletRequest request,
				HttpServletResponse response, TBrUserAddressQuery t, PageReq pageReq, Map<String, Object> result) {
			Long userId = SessionUtil.getUserId(session);
			Byte isDefault = t.getIsDefault();
			if(isDefault != null && 1 == isDefault){
				tBrUserAddressService.updateDefaultAddress(null,userId);
			}
			return super.editExt(model, session, request, response, t, pageReq, result);
		}
		
		
		//数据都从base获取
		//收货地址添加操作
		//收货地址编辑操作 设置为默认
		
	
		
	
	
}
