package com.co.example.controller;

import com.co.example.entity.user.TUsers;
import com.co.example.service.user.TUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private TUsersService tUsersService;
    
    @RequestMapping("/user")
    public Map<String, Object> user(Principal puser) {
        TUsers user = tUsersService.queryByLoginName(puser.getName());
        Map<String, Object> userinfo = new HashMap<>();
        userinfo.put("id", user.getUserId());
        userinfo.put("name",user.getUserName());
        userinfo.put("email", user.getEmail());
        userinfo.put("createdate", user.getCreateTime());
        return userinfo;
    }
}
