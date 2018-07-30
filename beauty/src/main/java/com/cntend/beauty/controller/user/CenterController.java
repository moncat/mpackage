package com.cntend.beauty.controller.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.co.example.common.utils.Base64ImageUtil;
import com.co.example.common.utils.MultipartFileUploadUtil;
import com.co.example.constant.HttpStatusCode;
import com.co.example.constant.ImageConstant;
import com.co.example.constant.SessionConstant;
import com.co.example.controller.BaseRestControllerHandler;
import com.co.example.entity.user.TUser;
import com.co.example.entity.user.aide.TUserQuery;
import com.co.example.entity.user.aide.UserSession;
import com.co.example.service.user.TUserService;
import com.co.example.utils.ClientUtil;
import com.co.example.utils.SessionUtil;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("center")
public class CenterController extends BaseRestControllerHandler<TUserQuery> {

	@Autowired
	TUserService  tUserService;	
	
	//我的信息一级页面
	@RequestMapping(value="/mine",method = { RequestMethod.GET})
	public String mine(Model model,HttpSession session){
		TUser user = SessionUtil.getUser(session);
		TUser tUser = tUserService.queryById(user.getId());
		String displayName = tUser.getDisplayName();
		if(StringUtils.isEmpty(displayName)){
			tUser.setDisplayName(tUser.getLoginName());
		}
		model.addAttribute("user", tUser);
		return "center/mine";
	}
	
	//个人中心二级页面
	@RequestMapping(value="/init",method = { RequestMethod.GET})
	public String init(Model model,HttpSession session){
		TUser user = SessionUtil.getUser(session);
		TUser tUser = tUserService.queryById(user.getId());
		String displayName = tUser.getDisplayName();
		if(StringUtils.isEmpty(displayName)){
			tUser.setDisplayName(tUser.getLoginName());
		}
		model.addAttribute("user", tUser);
		return "center/init";
	}
	
	//个人信息ajax 用与商品试用页面个人信息展示
	@ResponseBody
	@RequestMapping(value="/initAjax",method = { RequestMethod.GET})
	public Map<String, Object>  initAjax(Model model,HttpSession session){
		Map<String, Object> map = Maps.newHashMap();
		TUser user = SessionUtil.getUser(session);
		TUser tUser = tUserService.queryById(user.getId());
		map.put("user", tUser);
		return map;
	}
	
	//编辑个人信息  用于个人中心和试用申请时填写
	@RequestMapping(value="/editInfo",method = { RequestMethod.POST})
	public String editInfo(Model model,HttpSession session,TUserQuery query){
		
		tUserService.updateByIdSelective(query);
		TUser tUser = tUserService.queryById(query.getId());
		UserSession userSession = SessionUtil.getUserSession(session);
		userSession.setUser(tUser);
		session.setAttribute(SessionConstant.SESSION_USER, userSession);
		return "redirect:/center/mine";
	}
	
		
	
	
	
	//个人中心修改手机号三级页面
	@RequestMapping(value="/phone",method = { RequestMethod.GET})
	public String phone(Model model,HttpSession session){
		return "center/phone";
	}
	
	//上传头像操作
	/**
	 * 使用框架上传，取代直接上传
	 * @param file
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @see uploadPicture
	 */
	@Deprecated
	@ResponseBody
	@RequestMapping(value = "/uploadOld" ,method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> uploadPictureOld( MultipartFile file ) throws IllegalStateException, IOException{
		Map<String, Object> result = new HashMap<String, Object>();
//		 result.put("code", HttpStatusCode.CODE_ERROR);
//		File newFile = new File("tmp.jpg");
//		file.transferTo(newFile);
//		if(ImageUtil.isImage(newFile)){
//			 result.put("desc", "请上传图片");
//			return result;
//		};
//		if(1024<file.getSize()){
//			 result.put("desc", "图片大小过大");
//			return result;
//		};
		String imagePath = MultipartFileUploadUtil.fileUpload(file, ImageConstant.HEAD_IMAGE);
        result.put("picUrl", imagePath);
        result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}
	
	//上传头像操作
	//使用框架上传，传输base64编码数据
	@ResponseBody
	@RequestMapping(value = "/upload" ,method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> uploadPicture( String  imgSrc ) throws IllegalStateException, IOException{
		Map<String, Object> result = new HashMap<String, Object>();
		String imagePath = Base64ImageUtil.GenerateImage( ImageConstant.HEAD_IMAGE, imgSrc);
		result.put("picUrl", imagePath);
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}
	
	

	
	//个人中心修改密码三级页面
	@RequestMapping(value="/reset",method = { RequestMethod.GET})
	public String reset(Model model,HttpSession session){
		return "center/reset";
	}
	
	//修改密码操作
	@ResponseBody
	@RequestMapping(value="/updatePwd",method = { RequestMethod.POST})
	public Map<String, Object> updatePwd(HttpServletRequest request,HttpServletResponse response,HttpSession session,String password1,String password2){
		UserSession userSession = (UserSession)session.getAttribute(SessionConstant.SESSION_USER);
		String phoneNum = userSession.getUser().getLoginName();
		Map<String, Object> map = Maps.newHashMap();
		String ip = ClientUtil.getIp(request);
		String vcode = (String) session.getAttribute("verifyVCode");
		Boolean flg = tUserService.updatePwd(ip,phoneNum, password1, password2,vcode);
		if(flg){
			session.removeAttribute("phoneNum");
			session.removeAttribute("verifyVCode");
			map.put("code", HttpStatusCode.CODE_SUCCESS);
		}else{
			map.put("code", HttpStatusCode.CODE_ERROR);
			map.put("desc", "两次输入的密码不一致！");
		}
		return map;
	}
	
	
	
	public static void main(String[] args) {
		String a="1,2,3";
		String[] arr = a.split(",");
		for (String string : arr) {
			System.out.println("aaa"+string);
		}
	}
}









