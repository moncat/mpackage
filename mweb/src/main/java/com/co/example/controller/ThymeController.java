package com.co.example.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.entity.SysUser;

@Controller
public class ThymeController {
	
    	@RequestMapping("index")
        public String index(Model m) {
            List<SysUser> sysUserList = new ArrayList<SysUser>();
            SysUser u1 = new SysUser(11L, "AAAAA", "123456");
            SysUser u2 = new SysUser(22L, "BBBBB", "123456");
            SysUser u3 = new SysUser(33L, "CCCCC", "123456");
            sysUserList.add(u1);
            sysUserList.add(u2);
            sysUserList.add(u3);
            m.addAttribute("sysUserList", sysUserList);
            m.addAttribute("message", sysUserList.hashCode());
            return "sysUser/list";
        }
    
}
