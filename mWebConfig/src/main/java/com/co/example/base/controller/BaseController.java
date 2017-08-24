package com.co.example.base.controller;

import java.util.Date;
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
import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.user.TUsers;
import com.github.moncat.common.entity.BaseEntity;
import com.github.moncat.common.service.BaseService;
import com.google.common.collect.Maps;

import lombok.SneakyThrows;


public abstract class  BaseController<T extends BaseEntity> {
	
	//返回一个对象时用one
	public static final String ONE = "one";
	
	//返回多个对象时用List
	public static final String LIST = "list";
	
	//返回分页对象时用page
	public static final String PAGE = "page";
	
	//查询对象
	public static final String QUERY = "query";
	
	//请求路径
	private String returnPath;
	//用户
	protected TUsers tUser; 
	//用户id
	protected Long userId; 
	//异步返回
	protected Map<String, Object> result = Maps.newHashMap();
	
	@SneakyThrows(Exception.class)
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list( Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,PageReq pageReq,T query) throws Exception{
		pageReq.setSort(new Sort(Direction.DESC,"t.item_order").and(new Sort(Direction.DESC,"t.create_time")));
		query.setDelFlg(Constant.NO);
		BaseService<T,Long> service = getService(request, query);
		Boolean flg = listExt(model, session, request, response, pageReq,query);
		if(!flg){
			Page<T> page = service.queryPageList(query, pageReq);
			model.addAttribute(QUERY, query);
			model.addAttribute(PAGE, page);
		}
		return returnPath+"/list";
	}
	/**
	 * 扩展列表查询，返回特殊业务的查询结果,并确定是否更改默认查询结果
	 * @return  false 不更改当前查询  ；  true 改变当前查询
	 */
	abstract public Boolean listExt( Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,PageReq pageReq,T query);
	
	
	
	
	@SneakyThrows(Exception.class)
	@RequestMapping(value = "/addInit", method = { RequestMethod.GET,RequestMethod.POST })
	public String addInit(Model model, HttpSession session,	HttpServletRequest request, HttpServletResponse response) throws Exception{
		addInitExt(model, session, request, response);
		return returnPath+"/addInit";
	}
	/**
	 * 或许还有更多的初始化数据
	 */
	abstract public void addInitExt(Model model, HttpSession session,	HttpServletRequest request, HttpServletResponse response);
	
	
	
	
	@SneakyThrows(Exception.class)
	@RequestMapping(value = "/editInit/{id}/{pageNumber}", method = { RequestMethod.GET,RequestMethod.POST })
	public String editInit( Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Long id, @PathVariable Long pageNumber)throws Exception {
		BaseService<T,Long> service = getService(request, t);
		t =  service.queryById(id);
		editInitExt(model, session, request, response, t,id);
		model.addAttribute(ONE, t);
		model.addAttribute("pageNumber", pageNumber);
		return returnPath+"/editInit";
	}
	/**
	 * 或许还有更多的初始化数据
	 */
	abstract public void editInitExt(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,Long id);

		
	
	
	@SneakyThrows(Exception.class)
	@ResponseBody
	@RequestMapping(value = "/add", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> add(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,PageReq pageReq,RedirectAttributes redirectAttributes)throws Exception {
		BaseService<T,Long> service = getService(request, t);
		t.setDelFlg(Constant.NO);
		t.setCreateTime(new Date());
		t.setUpdateTime(t.getCreateTime());
		Boolean flg = addExt(model, session, request, response, t,pageReq);
		if(!flg){
			service.add(t);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageNumber", pageReq.getPageNumber());
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}

	/**
	 * 扩展新增,在新增前改变一下数据,并确定是否更改默认数据
	 */
	abstract public Boolean addExt(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,PageReq pageReq);
	
	
	@SneakyThrows(Exception.class)
	@ResponseBody
	@RequestMapping(value = "/edit", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> edit(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,PageReq pageReq,RedirectAttributes redirectAttributes)throws Exception {
		BaseService<T,Long> service = getService(request, t);
		t.setUpdateTime(new Date());
		Boolean flg = editExt(model, session, request, response, t, pageReq);
		if(!flg){
			service.updateByIdSelective(t);			
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("pageNumber", pageReq.getPageNumber());
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}
	/**
	 * 扩展编辑,在编辑前改变一下数据,并确定是否更改默认数据
	 */
	abstract public Boolean editExt(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,PageReq pageReq);
	
	
	
	

	@SneakyThrows(Exception.class)
	@ResponseBody
	@RequestMapping(value = "/show4Json/{id}", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> show4Json(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Long id)throws Exception {
		BaseService<T,Long> service = getService(request, t);
		Boolean flg = show4JsonExt(model, session, request, response, t, id);
		if(!flg){
			t = service.queryById(id);
			result.put("show4Json", t);
		}
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}
	/**
	 * 扩展show的数据，返回特殊show数据,并确定是否更改默认数据
	 * false 不更改 ；  true 更改
	 */
	abstract public Boolean show4JsonExt(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Long id);

	
	@SneakyThrows(Exception.class)
	@RequestMapping(value = "/show/{id}", method = { RequestMethod.GET,RequestMethod.POST })
	public String show(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Long id)throws Exception {
		BaseService<T,Long> service = getService(request, t);
		Boolean flg = showExt(model, session, request, response, t, id);
		if(!flg){
			t = service.queryById(id);
			model.addAttribute(ONE, t);			
		}
		return returnPath+"/show";
	}
	/**
	 * 扩展show的数据，返回特殊show数据,并确定是否更改默认数据
	 * false 不更改 ；  true 更改
	 */
	abstract public Boolean showExt(Model model,HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Long id);

	
	/**
	 * 恢复
	 */
	@SneakyThrows(Exception.class)
	@ResponseBody
	@RequestMapping(value = "/active/{id}", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> active(HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Long id)throws Exception {
		BaseService<T,Long> service = getService(request, t);
		t.setIsActive(Constant.YES);
		Boolean flg = activeExt(session, request, response, t, id);
		if(!flg){
			service.updateByIdSelective(t);			
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}
	/**
	 * 恢复，扩展过滤条件
	 */
	abstract public Boolean activeExt(HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Long id);
	
	/**
	 * 失效
	 */
	@SneakyThrows(Exception.class)
	@ResponseBody
	@RequestMapping(value = "/negative/{id}", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> negative(HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Long id)throws Exception {
		BaseService<T,Long> service = getService(request, t);
		t.setIsActive(Constant.NO);
		Boolean flg = negativeExt(session, request, response, t, id);
		if(!flg){
			service.updateByIdSelective(t);			
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}
	/**
	 * 失效，扩展过滤条件
	 */
	abstract public Boolean negativeExt(HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Long id);
	
	
	@SneakyThrows(Exception.class)
	@ResponseBody
	@RequestMapping(value = "/deletel/{id}", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> deleteLogic(HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Long id)throws Exception {
		BaseService<T,Long> service = getService(request, t);
		t.setDelFlg(Constant.YES);
		Boolean flg = deleteLogicExt(session, request, response, t, id);
		if(!flg){
			service.updateByIdSelective(t);			
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}
	/**
	 * 逻辑删除，扩展过滤条件,并确定是否更改默认删除操作
	 */
	abstract public Boolean deleteLogicExt(HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Long id);
	
	
	@SneakyThrows(Exception.class)
	@ResponseBody
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> deletePhysics(HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Long id)throws Exception {
		BaseService<T,Long> service = getService(request, t);
		Boolean flg = deletePhysicsExt(session, request, response, t, id);
		if(!flg){
			service.deleteById(id);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}
	/**
	 * 物理删除，扩展过滤条件,并确定是否更改默认删除操作
	 */
	abstract public Boolean deletePhysicsExt(HttpSession session,HttpServletRequest request,HttpServletResponse response,T t,@PathVariable Long id);

	@SneakyThrows(Exception.class)
	@SuppressWarnings("unchecked")
	private BaseService<T, Long> getService(HttpServletRequest request,T query) throws Exception{
		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		BaseService<T,Long> service = (BaseService<T,Long>) ac.getBean(query.getClass().getSimpleName().replace("Query", "")+"ServiceImpl");
		return service;
	}
	
	@SneakyThrows(Exception.class)
	public void setReturnPath(String returnPath)throws Exception {
		this.returnPath = returnPath;
	}


}
