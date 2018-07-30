package com.co.example.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.system.aide.TSystemConfigQuery;

@Controller
@RequestMapping("config")
public class ConfigController extends BaseControllerHandler<TSystemConfigQuery> {

}
