package com.cntend.beauty.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.co.example.constant.WechatConstant;

@Controller
@RequestMapping("setting")
public class SettingController  {
	//设置二级页面
	@RequestMapping(value="/init",method = { RequestMethod.GET})
	public String init(Model model,HttpSession session){
		Boolean flg = (Boolean)session.getAttribute(WechatConstant.IS_WECHAT);
		model.addAttribute("flg", flg);
		return "setting/init";
	}
	
	//设置关于我们三级页面
	@RequestMapping(value="/aboutUs",method = { RequestMethod.GET})
	public String aboutUs(Model model,HttpSession session){
		return "setting/aboutUs";
	}
	//设置版本信息三级页面
	@RequestMapping(value="/version",method = { RequestMethod.GET})
	public String version(Model model,HttpSession session){
		return "setting/version";
	}
	//设置用户协议三级页面  转到注册协议
}
