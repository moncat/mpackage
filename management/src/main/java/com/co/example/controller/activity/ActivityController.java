package com.co.example.controller.activity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.activity.aide.TBrActivityQuery;

@Controller
@RequestMapping("activity")
public class ActivityController extends  BaseControllerHandler<TBrActivityQuery> {
	
}
