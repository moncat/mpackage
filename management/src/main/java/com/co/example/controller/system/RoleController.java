package com.co.example.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.base.controller.BaseControllerHandler;
import com.co.example.entity.system.aide.TRoleQuery;

@Controller
@RequestMapping("role")
public class RoleController extends BaseControllerHandler<TRoleQuery> {

}
