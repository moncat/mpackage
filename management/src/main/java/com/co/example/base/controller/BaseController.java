package com.co.example.base.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.co.example.base.constant.HttpStatusCode;
import com.co.example.common.service.BaseService;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.user.TUsers;


public abstract class  BaseController<T> {
	
	//请求路径
	private String returnPath;
	//用户
	protected TUsers tUser; 
	//用户id
	protected Integer userId; 

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list( Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,PageReq pageReq,T query) throws Exception{
		pageReq.setSort(new Sort(Direction.DESC,"t.create_time"));
		BaseService<T, Integer> service = getService(request, query);
		Page<T> page = service.queryPageList(query, pageReq);
		model.addAttribute("page", page);
		return returnPath+"/list";
	}
	
	@RequestMapping(value = "/addInit", method = { RequestMethod.GET,RequestMethod.POST })
	public String addInit(Model model, HttpSession session,	HttpServletRequest request, HttpServletResponse response) throws Exception{
		return returnPath+"/addInit";
	}

	@RequestMapping(value = "/editInit/{id}/{pageNumber}", method = { RequestMethod.GET,RequestMethod.POST })
	public String editInit( Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Integer id, @PathVariable Integer pageNumber)throws Exception {
		BaseService<T, Integer> service = getService(request, t);
		t = (T) service.queryById(id);
		model.addAttribute("tOne", t);
		model.addAttribute("pageNumber", pageNumber);
		return returnPath+"/editInit";
	}

	@RequestMapping(value = "/add", method = { RequestMethod.GET,RequestMethod.POST })
	public String add(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,PageReq pageReq,RedirectAttributes redirectAttributes)throws Exception {
		BaseService<T, Integer> service = getService(request, t);
		service.add(t);
		redirectAttributes.addAttribute("pageNumber", pageReq.getPageNumber());
		return "redirect:"+returnPath+"/list";
	}
	
	@RequestMapping(value = "/edit", method = { RequestMethod.GET,RequestMethod.POST })
	public String edit(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,PageReq pageReq,RedirectAttributes redirectAttributes)throws Exception {
		BaseService<T, Integer> service = getService(request, t);
		service.updateByIdSelective(t);
		redirectAttributes.addAttribute("pageNumber", pageReq.getPageNumber());
		return returnPath+"/show";
	}
	
	@RequestMapping(value = "/show/{id}", method = { RequestMethod.GET,RequestMethod.POST })
	public String show(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Integer id)throws Exception {
		BaseService<T, Integer> service = getService(request, t);
		t = service.queryById(id);
		model.addAttribute("tOne", t);
		return returnPath+"/show";
	}

	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> delete(HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Integer id)throws Exception {
		BaseService<T, Integer> service = getService(request, t);
		service.deleteById(id);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}

	
	@SuppressWarnings("unchecked")
	private BaseService<T, Integer> getService(HttpServletRequest request,T query) throws Exception{
		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		BaseService<T,Integer> service = (BaseService<T,Integer>) ac.getBean(query.getClass().getSimpleName()+"ServiceImpl");
		return service;
	}

	public void setReturnPath(String returnPath)throws Exception {
		this.returnPath = returnPath;
	}

	
}
