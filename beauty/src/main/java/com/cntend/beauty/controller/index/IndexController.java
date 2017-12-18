package com.cntend.beauty.controller.index;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.service.user.TUserActiveService;
import com.co.example.utils.ClientUtil;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("index")
public class IndexController {
	
	@Resource
	TUserActiveService tUserActiveService;
	
	@ResponseBody
	@RequestMapping(value = "/vcode", method = { RequestMethod.POST })
    public Map<String,Object> vcode(Model model,HttpSession session, HttpServletRequest request,String phoneNum) throws Exception{
		HashMap<String, Object> map = Maps.newHashMap();
		String ip = ClientUtil.getIp(request);
		int flg = tUserActiveService.sendVerifyCodeByPhone(phoneNum,ip);
		map.put("flg", flg);
        return map;
    }
	

	
}
