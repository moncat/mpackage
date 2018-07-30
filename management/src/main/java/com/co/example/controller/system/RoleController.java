package com.co.example.controller.system;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.aide.TAdminQuery;
import com.co.example.entity.admin.aide.TAdminVo;
import com.co.example.entity.system.TMenu;
import com.co.example.entity.system.TRole;
import com.co.example.entity.system.aide.TMenuQuery;
import com.co.example.entity.system.aide.TMenuVo;
import com.co.example.entity.system.aide.TRoleQuery;
import com.co.example.security.MyInvocationSecurityMetadataSourceService;
import com.co.example.service.admin.TAdminRoleService;
import com.co.example.service.admin.TAdminService;
import com.co.example.service.system.TMenuService;
import com.co.example.service.system.TRoleRightService;
import com.co.example.service.system.TRoleService;

@Controller
@RequestMapping("role")
public class RoleController extends BaseControllerHandler<TRoleQuery> {
	
	@Resource
	TRoleService tRoleService;
	
	@Resource
	TRoleRightService tRoleRightService;
	
	@Resource
	TAdminRoleService tAdminRoleService;
	
	@Resource
	TAdminService tAdminService;
	
	@Resource
	TMenuService tMenuService;
	
	//*****角色关联菜单******
	
	@ResponseBody
	@RequestMapping(value = {"/addConn"},method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> addConn(Model model,HttpServletRequest request,Long roleId,@RequestParam(value = "menuIds[]") Long[] menuIds) throws Exception{
		tRoleRightService.addConn(roleId, menuIds);
		//新增关联后，刷新内存
		MyInvocationSecurityMetadataSourceService.refreshSecurityMetadataSource(request);
		Map<String, Object> result = result();
		return result;
	}
	@ResponseBody
	@RequestMapping(value = {"/refresh"},method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> refresh(Model model,HttpServletRequest request) throws Exception{
		MyInvocationSecurityMetadataSourceService.refreshSecurityMetadataSource(request);
		Map<String, Object> result = result();
		return result;
	}
	
	@RequestMapping(value = {"/connInit"},method = { RequestMethod.GET, RequestMethod.POST })
	public String connInit(Model model,Long roleId) throws Exception{
		TRoleQuery tRoleQuery = new TRoleQuery();
		tRoleQuery.setDelFlg(Constant.NO);
		List<TRole> roleList = tRoleService.queryList(tRoleQuery);
		model.addAttribute(LIST, roleList);
		return "role/connInit";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/menuTree", method = { RequestMethod.GET,RequestMethod.POST })
	public Object menuTree(Model model, HttpSession session,Long roleId) {
		TMenuQuery query = new TMenuQuery();
		query.setDelFlg(Constant.NO);
		query.setRoleId(roleId);
		List<TMenu> list = tMenuService.queryList(query);
		for (TMenu tMenu : list) {
			TMenuVo tMenuTmp = (TMenuVo) tMenu;
			tMenuTmp.setUrl(null);
			tMenuTmp.setIcon(null);
			if(null != tMenuTmp.getRoleId()){
				tMenuTmp.setChecked(true);
			}
		}
		return list;
	}
	
	//*****角色关联管理员******
	
	@ResponseBody
	@RequestMapping(value = {"/addAdminConn"},method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> addAdminConn(Model model,HttpServletRequest request,Long roleId,@RequestParam(value = "adminIds[]") Long[] adminIds) throws Exception{
		tAdminRoleService.addAdminConn(roleId, adminIds);
		Map<String, Object> result = result();
		return result;
	}
	
	@RequestMapping(value = {"/connAdminInit"},method = { RequestMethod.GET, RequestMethod.POST })
	public String connAdminInit(Model model,Long roleId) throws Exception{
		TRoleQuery tRoleQuery = new TRoleQuery();
		tRoleQuery.setDelFlg(Constant.NO);
		List<TRole> roleList = tRoleService.queryList(tRoleQuery);
		model.addAttribute(LIST, roleList);
		return "role/connAdminInit";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/adminTree", method = { RequestMethod.GET,RequestMethod.POST })
	public Object adminTree(Model model, HttpSession session,Long roleId) {
		TAdminQuery query = new TAdminQuery();
		query.setDelFlg(Constant.NO);
		query.setRoleId(roleId);
		List<TAdmin> list = tAdminService.queryList(query);
		for (TAdmin admin : list) {
			TAdminVo adminTmp = (TAdminVo) admin;
			if(null != adminTmp.getRoleId()){
				adminTmp.setChecked(true);
			}
		}
		return list;
	}
	
	
}
