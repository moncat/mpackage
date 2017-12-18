package com.cntend.beauty.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.co.example.controller.BaseRestControllerHandler;
import com.co.example.entity.user.aide.TBrUserCollectQuery;
import com.co.example.service.user.TBrUserCollectService;

@Controller
@RequestMapping("collect")
public class CollectController extends BaseRestControllerHandler<TBrUserCollectQuery> {

	@Autowired
	TBrUserCollectService  tBrUserCollectService;	

	
	
}
