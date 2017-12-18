package com.cntend.beauty.controller.region;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.entity.region.TBrRegion;
import com.co.example.service.region.TBrRegionService;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("region")
public class regionController {
	
	@Resource
	TBrRegionService tBrRegionService;
	
	@ResponseBody
	@RequestMapping("detail")
    public Map<String,Object> region(Model model,HttpSession session, String id) throws Exception{
		HashMap<String, Object> map = Maps.newHashMap();
		List<TBrRegion> list = tBrRegionService.getRegionListByParentRegionId(id);
		map.put("list", list);
        return map;
    }
}
