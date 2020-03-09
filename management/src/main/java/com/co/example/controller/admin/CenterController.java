package com.co.example.controller.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.co.example.common.utils.Base64ImageUtil;
import com.co.example.common.utils.MultipartFileUploadUtil;
import com.co.example.constant.HttpStatusCode;
import com.co.example.constant.ImageConstant;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.aide.TAdminQuery;
import com.co.example.entity.order.TBrOrder;
import com.co.example.entity.order.aide.TBrOrderQuery;
import com.co.example.service.admin.TAdminService;
import com.co.example.service.order.TBrOrderService;
import com.co.example.utils.SessionUtil;

@Controller
@RequestMapping("/center")
public class CenterController {
	
	@Resource
	TAdminService tAdminService;
	@Resource
	TBrOrderService tBrOrderService;
	
	@RequestMapping(value="/center",method = { RequestMethod.GET})
	public String center(Model model,HttpSession session){
		//用户中心
		Long adminId = SessionUtil.getAdminId(session);
		TAdmin admin = tAdminService.queryById(adminId);
		admin.setPassword(null);
		TBrOrderQuery tBrOrderQuery = new TBrOrderQuery();
		tBrOrderQuery.setAdminId(adminId);
		List<TBrOrder> list = tBrOrderService.queryList(tBrOrderQuery);
		model.addAttribute("one", admin);
		model.addAttribute("list", list);
		return "center/center";
	}
	
	@RequestMapping(value="/test",method = { RequestMethod.GET})
	public String test(Model model,HttpSession session){
		return "center/test";
	}
	
	@ResponseBody
	@RequestMapping(value = "/upload" ,method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> uploadPicture( MultipartFile file ,Long id) throws IllegalStateException, IOException{
		Map<String, Object> result = new HashMap<String, Object>();
		String imagePath = MultipartFileUploadUtil.fileUpload(file, ImageConstant.HEAD_IMAGE);
		TAdminQuery tAdminQuery = new TAdminQuery();
		tAdminQuery.setId(id);
		tAdminQuery.setHeadImg(imagePath);
		tAdminService.updateByIdSelective(tAdminQuery);
        result.put("picUrl", imagePath);
        result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}
}












