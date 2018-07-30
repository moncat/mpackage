package com.co.example.controller.ztree;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.constant.HttpStatusCode;
import com.co.example.entity.ztree.TZtree;
import com.co.example.entity.ztree.aide.TZtreeQuery;
import com.co.example.service.ztree.TZtreeService;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("ztree")
public class ZTreeController {
	
	@Inject
	TZtreeService service;
	
	//初始化树
	@RequestMapping(value = "/init", method = { RequestMethod.GET,RequestMethod.POST })
	public String init(Model model, HttpSession session) {
		return "ztree/init";
	}
	
	
	//展开子节点
	@ResponseBody
	@RequestMapping(value = "/more", method = { RequestMethod.GET,RequestMethod.POST })
	public List<TZtree> more(Model model, HttpSession session,Long id,Long pId) {
		TZtreeQuery query = new TZtreeQuery();
		query.setId(id);
		query.setPid(pId);
		List<TZtree> list = service.queryList(query);
		return list;
	}
	
	//添加
	@ResponseBody
	@RequestMapping(value = "/oper", method = { RequestMethod.GET,RequestMethod.POST })
	public TZtreeQuery oper(Model model, HttpSession session, TZtreeQuery query) {
		if(query.getId() == null){
			service.addNow(query);
		}else{
			service.updateByIdSelective(query);
		}
		return query;
	}
	
	//节点拖动
	@ResponseBody
	@RequestMapping(value = "/drag", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> drag(Model model, HttpSession session, TZtreeQuery query) {
		Map<String, Object> map = Maps.newHashMap();
		service.updateDrag(query);
		map.put("code", HttpStatusCode.CODE_SUCCESS);
		return map;
	}
	
	//节点删除
	@ResponseBody
	@RequestMapping(value = "/delete", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> delete(Model model, HttpSession session, TZtreeQuery query) {
		Map<String, Object> map = Maps.newHashMap();
		service.deleteById(query.getId());
		map.put("code", HttpStatusCode.CODE_SUCCESS);
		return map;
	}
	
	
	
}
