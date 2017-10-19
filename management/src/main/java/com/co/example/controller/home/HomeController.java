package com.co.example.controller.home;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.aide.TAdminVo;
import com.co.example.entity.system.TMenu;
import com.co.example.service.system.TMenuService;
import com.co.example.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Api(value="后台首页控制器")
public class HomeController {

	@Inject
	TMenuService tMenuService;
	
	@ApiOperation(value="后台框架页面", notes="后台框架，包含头部信息，左侧导航，脚部信息")
    @RequestMapping(value = {"/index","/"},method = { RequestMethod.GET, RequestMethod.POST } )
	//该注解可以单个用
	//@ApiImplicitParam(name = "id", value = "其实这是演示api",defaultValue = "0", required = true, dataType = "Long", paramType = "path")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "其实这是演示api",defaultValue = "0", required = true, dataType = "Long", paramType = "path"), })
	//该注解可以单个用
	//@ApiResponse(code = 400, response=String.class, message = "Invalid user supplied")
	@ApiResponses({ @ApiResponse(code = 400, response=String.class ,  message = "Invalid request type") , @ApiResponse(code = 500, message = "server is error") })
	
	
	public String index(
    		@ApiParam(required = true, name = "model", value= "本次用来封装左侧导航菜单") Model model
    		,HttpSession session) throws Exception{
    	TAdmin admin = SessionUtil.getAdmin(session);
    	TAdminVo adminVo = (TAdminVo) admin; 
    	List<TMenu> list = tMenuService.getMenuTree(adminVo.getRoles());
    	model.addAttribute("admin", admin);
    	model.addAttribute("list", list);
        return "common/index";
    }
	@ApiOperation(value="后台右侧主页面", notes="这里是刚打开后台时的默认主页面，包含基本数据的统计和服务器信息")
    @RequestMapping(value = {"/welcome"},method = { RequestMethod.GET, RequestMethod.POST })
    public String welcome(Model model) throws Exception{
    	log.debug("首页主体");
        return "home/welcome";
    }
    
	

}









