package com.co.example.controller.enterprise;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.enterprise.aide.TBrEnterprisePermissionQuery;

@Controller
@RequestMapping("enterprisepermission")
public class EnterprisePermissionController extends BaseControllerHandler<TBrEnterprisePermissionQuery> {

}
