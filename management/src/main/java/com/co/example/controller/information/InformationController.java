package com.co.example.controller.information;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.information.aide.TBrInformationQuery;

@Controller
@RequestMapping("info")
public class InformationController extends  BaseControllerHandler<TBrInformationQuery> {

	
}
