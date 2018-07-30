package com.co.example.controller.system;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.aide.TAdminVo;
import com.co.example.entity.system.TMenu;
import com.co.example.entity.system.aide.TMenuQuery;
import com.co.example.service.system.TMenuService;
import com.co.example.utils.SessionUtil;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("system")
//可以将某个属性放到session中
//@SessionAttributes(value={"names"},types={Integer.class})
public class SystemController extends BaseControllerHandler<TMenu>{

	@Inject
	TMenuService tMenuService;
	
	 //在请求前执行
//	 @ModelAttribute
//	 public void preRun() {
//	    log.debug("Test Pre-Run");
//	 }
	
	
	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TMenu query) {
		log.debug("列表展示");
		TAdmin admin = SessionUtil.getAdmin(session);
    	TAdminVo adminVo = (TAdminVo) admin; 
    	List<TMenu> list = tMenuService.getMenuTree(adminVo.getRoles(),false);
    	model.addAttribute("list", list);
		return true;
	}
	
	@Override
	public void addInitExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		TMenuQuery tMenuQuery = new TMenuQuery();
		tMenuQuery.setParentId(0l);
		List<TMenu> list = tMenuService.queryList(tMenuQuery);
		model.addAttribute("list", list);
	}

	@RequestMapping("icon")
    public String icon() throws Exception{
        return "system/icon";
    }
	

	@Override
	public Boolean addExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TMenu t, PageReq pageReq,Map<String, Object> result) {
		setInfo(t);
		tMenuService.addMenuAndSaPermission(t);
		return true;
	}
	

	@Override
	public void editInitExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TMenu t, Long id) {
		TMenuQuery tMenuQuery = new TMenuQuery();
		tMenuQuery.setParentId(0l);
		List<TMenu> list = tMenuService.queryList(tMenuQuery);
		model.addAttribute("list", list);
	}

	
	@Override
	public Boolean editExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TMenu t, PageReq pageReq,Map<String, Object> result) {
		Long parentId = t.getParentId();
		if(parentId !=t.getId()){
			setInfo(t);
			tMenuService.updateByIdSelective(t);
		}
		return false;
	}

	@Override
	public Boolean deletePhysicsExt(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TMenu t, Long id ,Map<String, Object> result) {
		tMenuService.deleteAll(id);
		return true;
	}

	private void setInfo(TMenu t) {
		Long parentId = t.getParentId();
		if(parentId != null && parentId == 0l){
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









