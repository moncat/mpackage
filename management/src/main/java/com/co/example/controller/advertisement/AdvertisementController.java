package com.co.example.controller.advertisement;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.co.example.common.utils.MultipartFileUploadUtil;
import com.co.example.constant.ImageConstant;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.advertisement.aide.TBrAdvertisementQuery;

@Controller
@RequestMapping("ad")
public class AdvertisementController extends  BaseControllerHandler<TBrAdvertisementQuery> {
	@ResponseBody
	@RequestMapping(value = "/upload" ,method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> uploadPicture( MultipartFile file ){
		String imagePath = MultipartFileUploadUtil.fileUpload(file, ImageConstant.ADVERTISEMENT);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("picUrl", imagePath);
        result.put("success", 1);
		return result;
	}
}
