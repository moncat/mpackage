package com.co.example.controller.twitter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.moncat.common.generator.id.NextId;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("twitter")
public class TwitterController {
	
	@ResponseBody
	@RequestMapping(value = "/getId", method = { RequestMethod.GET, RequestMethod.POST })
	public Long getId() {
		return NextId.getId();
	}
}









