package com.co.example.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.constant.HttpStatusCode;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.TAdminRole;
import com.co.example.entity.admin.aide.TAdminQuery;
import com.co.example.entity.admin.aide.TAdminRoleQuery;
import com.co.example.service.admin.TAdminLoginService;
import com.co.example.service.admin.TAdminRoleService;
import com.co.example.service.admin.TAdminService;
import com.co.example.utils.SessionUtil;




@Controller
@RequestMapping("/admin")
public class adminController {
	
	@Resource
	private TAdminService tAdminService;
	@Resource
	private TAdminLoginService tAdminLoginService;
	@Resource
	private TAdminRoleService tAdminRoleService;
	
	
	@RequestMapping(value="/list",method = { RequestMethod.GET,RequestMethod.POST})
	public String list(Model model ,PageReq pageReq ,TAdminQuery query){
		//所有用户
		query.setDelFlg(Constant.NO);
		pageReq.setSort(new Sort(Direction.DESC,"t.create_time"));
		Page<TAdmin> page = tAdminService.queryAllInfoList(query, pageReq);
		model.addAttribute("page", page);
		return "admin/list";
	}
	
	
	@RequestMapping(value="/center",method = { RequestMethod.GET,RequestMethod.POST})
	public String center(Model model){
		//用户中心
		return "admin/center";
	}
	
	@RequestMapping(value="/addInit",method = { RequestMethod.GET,RequestMethod.POST})
	public String addInit(Model model){
		return "admin/addInit";
	}

	/**
	 * 编辑用户
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/editInit/{id}/{p}",method = { RequestMethod.GET,RequestMethod.POST})
	public String editInit(Model model, @PathVariable Long id, @PathVariable Long p){
		TAdmin admin = tAdminService.queryById(id);
		TAdminRoleQuery query = new TAdminRoleQuery();
		query.setAdminId(admin.getId());
		List<TAdminRole> adminRoles = tAdminRoleService.queryList(query);
		List<Byte> curRoles = new ArrayList<Byte>();

		for (TAdminRole tAdminRole : adminRoles) {
			curRoles.add(tAdminRole.getRoleId().byteValue());
		}
		
		model.addAttribute("one", admin);
		model.addAttribute("curRoles", curRoles);


		return "admin/editInit";
	}

	
	@ResponseBody
	@RequestMapping(value="/add",method = { RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> add(HttpSession session,TAdminQuery query){
		Map<String,Object> mapResult = new HashMap<String,Object>();
		mapResult = tAdminService.addAdmin(query, null,SessionUtil.getAdminId(session));
		return mapResult;
	}
	
	@ResponseBody
	@RequestMapping(value="/edit",method = { RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> edit(HttpSession session,TAdminQuery query){
		Map<String,Object> mapResult = new HashMap<String,Object>();
		mapResult = tAdminService.edit(query, null,SessionUtil.getAdminId(session));
		return mapResult;
	}

	@ResponseBody
	@RequestMapping(value="/show",method = { RequestMethod.GET,RequestMethod.POST})
	public Map<String,Object> show(HttpSession session,TAdminQuery query){
		Map<String,Object> mapResult = new HashMap<String,Object>();
		TAdmin admin = SessionUtil.getAdmin(session);
		admin.setPassword(null);
		mapResult.put("admin", admin);
		return mapResult;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> delete(HttpSession session,@PathVariable Long id)throws Exception {
		TAdminQuery query = new TAdminQuery();
		query.setDelFlg(Constant.YES);
		query.setId(id);
		tAdminService.updateByIdSelective(query);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/active/{id}", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> active(HttpSession session,@PathVariable Long id)throws Exception {
		TAdminQuery query = new TAdminQuery();
		query.setIsActive(Constant.YES);
		query.setId(id);
		tAdminService.updateByIdSelective(query);			
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/negative/{id}", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> negative(HttpSession session,@PathVariable Long id)throws Exception {
		TAdminQuery query = new TAdminQuery();
		query.setIsActive(Constant.NO);
		query.setId(id);
		tAdminService.updateByIdSelective(query);			
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}
}
