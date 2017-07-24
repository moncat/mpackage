package com.co.example.controller.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.co.example.base.constant.HttpStatusCode;
import com.co.example.base.controller.BaseController;
import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.system.TMenu;
import com.co.example.entity.system.aide.TMenuQuery;
import com.co.example.service.system.TMenuService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("system")
//可以将某个属性放到session中
//@SessionAttributes(value={"names"},types={Integer.class})
public class SystemController extends BaseController<TMenu>{

	@Inject
	TMenuService tMenuService;
	
	 //在请求前执行
//	 @ModelAttribute
//	 public void preRun() {
//	    log.debug("Test Pre-Run");
//	 }
	
	@Override
    @RequestMapping("list")
    public String list(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TMenu query) throws Exception{
    	log.debug("列表展示");
    	List<TMenu> list = tMenuService.getMenuTree();
    	model.addAttribute("list", list);
        return "system/list";
    }

	@Override
	public String addInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TMenuQuery tMenuQuery = new TMenuQuery();
		tMenuQuery.setParentId(0);
		List<TMenu> list = tMenuService.queryList(tMenuQuery);
		model.addAttribute("list", list);
		return super.addInit(model, session, request, response);
	}
    
	@RequestMapping("icon")
    public String icon() throws Exception{
        return "system/icon";
    }

	@Override
	public String add(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TMenu t, PageReq pageReq, RedirectAttributes redirectAttributes) throws Exception {
		t.setCreateTime(new Date());
		t.setDelFlg(Constant.NO);
		setInfo(t);
		return super.add(model, session, request, response, t, pageReq, redirectAttributes);
	}

	@Override
	public String editInit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TMenu t,@PathVariable  Integer id,@PathVariable  Integer pageNumber) throws Exception {
		TMenuQuery tMenuQuery = new TMenuQuery();
		tMenuQuery.setParentId(0);
		List<TMenu> list = tMenuService.queryList(tMenuQuery);
		model.addAttribute("list", list);
		return super.editInit(model, session, request, response, t, id, pageNumber);
	}

	@Override
	public String edit(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TMenu t, PageReq pageReq, RedirectAttributes redirectAttributes) throws Exception {
		Integer parentId = t.getParentId();
		if(parentId ==t.getId()){
			return "system/list";
		}
		setInfo(t);
		tMenuService.updateByIdSelective(t);
		return "system/list";
	}

	@Override
	public Map<String, Object> delete(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TMenu t, @PathVariable Integer id) throws Exception {
		tMenuService.deleteAll(id);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}
	
	private void setInfo(TMenu t) {
		Integer parentId = t.getParentId();
		if(parentId != null && parentId == 0){
			Integer flg = 1;
			t.setIsRoot(flg.byteValue());
			t.setLevel(1);
		}else{
			Integer flg = 0;
			t.setIsRoot(flg.byteValue());
			t.setLevel(2);
		}
	}
}









