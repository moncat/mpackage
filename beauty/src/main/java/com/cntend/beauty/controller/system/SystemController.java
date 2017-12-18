package com.cntend.beauty.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("system")
public class SystemController {
	@RequestMapping("test")
    public String icon() throws Exception{
        return "system/test";
    }
}
