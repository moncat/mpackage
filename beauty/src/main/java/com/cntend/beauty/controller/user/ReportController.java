package com.cntend.beauty.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.MultipartFileUploadUtil;
import com.co.example.common.utils.PageReq;
import com.co.example.constant.HttpStatusCode;
import com.co.example.constant.ImageConstant;
import com.co.example.entity.activity.TBrActivity;
import com.co.example.entity.activity.aide.TBrActivityQuery;
import com.co.example.entity.user.TBrUserPlan;
import com.co.example.entity.user.TBrUserPlanItem;
import com.co.example.entity.user.TBrUserReport;
import com.co.example.entity.user.TBrUserReportItem;
import com.co.example.entity.user.TUser;
import com.co.example.entity.user.aide.TBrUserPlanItemQuery;
import com.co.example.entity.user.aide.TBrUserReportItemQuery;
import com.co.example.entity.user.aide.TBrUserReportQuery;
import com.co.example.entity.user.aide.TBrUserReportVo;
import com.co.example.service.activity.TBrActivityService;
import com.co.example.service.user.TBrUserPlanItemService;
import com.co.example.service.user.TBrUserPlanService;
import com.co.example.service.user.TBrUserReportItemService;
import com.co.example.service.user.TBrUserReportService;
import com.co.example.service.user.TUserService;
import com.co.example.utils.SessionUtil;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("report")
public class ReportController  {

	@Autowired
	TBrUserReportService tBrUserReportService;
	
	@Autowired
	TBrUserReportItemService tBrUserReportItemService;
	
	@Autowired
	TUserService tUserService;
	
	@Autowired
	TBrUserPlanService tBrUserPlanService;
	
	@Autowired
	TBrUserPlanItemService tBrUserPlanItemService;
	
	@Autowired
	TBrActivityService tBrActivityService;
	
	
	//活动报告ajax   /report/list  全部列表
	@ResponseBody
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String,Object>  list( Model model,HttpSession session,PageReq pageReq,TBrUserReportQuery query,Boolean myFlg) throws Exception{
		Map<String, Object> result =  new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		pageReq.setSort(new Sort(Direction.DESC,"t.item_order").and(new Sort(Direction.DESC,"t.create_time")));
		query.setDelFlg(Constant.NO);
		if(myFlg){
			query.setCreateBy(SessionUtil.getUserId(session));
		}
		Page<TBrUserReport> page = tBrUserReportService.queryPageList(query, pageReq);
		List<TBrUserReport> content = page.getContent();
		TBrUserReportItemQuery tBrUserReportItemQuery = null;
		Long reportId  = null;
		Long createBy = null;
		Long userId  = null;
		String typeName = null;
		String typeNameAll = "";
		Map<Long, List<TBrUserReportItem>> itemMap = Maps.newHashMap();
		Map<Long, TUser> userMap = Maps.newHashMap();
		Map<Long, String> planMap = Maps.newHashMap();
		
		for (TBrUserReport tBrUserReport : content) {
			reportId = tBrUserReport.getId();
			createBy = tBrUserReport.getCreateBy();

			TUser user = tUserService.queryById(createBy);
			userMap.put(reportId, user);
			
			tBrUserReportItemQuery = new TBrUserReportItemQuery();
			tBrUserReportItemQuery.setReportId(reportId);
			List<TBrUserReportItem> itemList = tBrUserReportItemService.queryList(tBrUserReportItemQuery);
			itemMap.put(reportId, itemList);
			
			userId = user.getId();
			TBrUserPlanItemQuery tBrUserPlanItemQuery = new TBrUserPlanItemQuery();
			TBrUserPlan userPlan = tBrUserPlanService.getUserPlan(userId);
			if(userPlan !=null){
				tBrUserPlanItemQuery.setUserPlanId( userPlan.getId());
				List<TBrUserPlanItem> planItem = tBrUserPlanItemService.queryList(tBrUserPlanItemQuery);
				for (TBrUserPlanItem tBrUserPlanItem : planItem) {
					typeName = tBrUserPlanItem.getTypeName();
					typeNameAll += typeName+" ";
				}
			}
			planMap.put(reportId, typeNameAll);
			typeNameAll ="";
		}
		
		result.put("page", page);
		result.put("itemMap", itemMap);
		result.put("userMap", userMap);
		result.put("planMap", planMap);
		return result;
	}
	
	//试用报告查看  三级页面
	//报告详情              
	@RequestMapping(value = "/detail/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String  detail( Model model,@PathVariable Long id) throws Exception{
		TBrUserReport one = tBrUserReportService.queryById(id);
		TBrUserReportItemQuery tBrUserReportItemQuery = new TBrUserReportItemQuery();
		tBrUserReportItemQuery.setReportId(one.getId());
		List<TBrUserReportItem> itemList = tBrUserReportItemService.queryList(tBrUserReportItemQuery);
	
		Long createBy = one.getCreateBy();
		TUser user = tUserService.queryById(createBy);
		
		model.addAttribute("one", one);
		model.addAttribute("itemList", itemList);
		model.addAttribute("user", user);
		return "report/detail";
	}
	
		//试用报告二级页面   我的列表（未填写）
		@RequestMapping(value = "/waitdo", method = { RequestMethod.GET})
		public String  waitdo(Model model,HttpSession session,PageReq pageReq,TBrUserReportQuery query) throws Exception{
			Long userId = SessionUtil.getUserId(session);
			TBrActivityQuery tBrActivityQuery = new TBrActivityQuery();
			tBrActivityQuery.setJoinApplyFlg(true);
			tBrActivityQuery.setUserId(userId);
			tBrActivityQuery.setReportSatus(Constant.STATUS_TWO);
			Page<TBrActivity> page = tBrActivityService.queryPageList(tBrActivityQuery,pageReq);
			model.addAttribute("page", page);
			return "report/waitdo";
		}
	
		//试用报告二级页面   我的列表（已填写）
		@ResponseBody
		@RequestMapping(value = "/done", method = { RequestMethod.POST})
		public Map<String, Object> done(Model model,HttpSession session,PageReq pageReq,TBrUserReportQuery query) throws Exception{
			Map<String, Object> result =  new HashMap<String, Object>();
			Long userId = SessionUtil.getUserId(session);
			TBrActivityQuery tBrActivityQuery = new TBrActivityQuery();
			tBrActivityQuery.setJoinApplyFlg(true);
			tBrActivityQuery.setUserId(userId);
			tBrActivityQuery.setReportSatus(Constant.STATUS_THREE);
			Page<TBrActivity> page = tBrActivityService.queryPageList(tBrActivityQuery,pageReq);
			result.put("page", page);
			return result;
		}
	
		//根据activityid查询并跳转到报告详情
		@RequestMapping(value = "/view", method = { RequestMethod.GET})
		public String  view(Model model,HttpSession session,Long activityId) throws Exception{
			Long createBy = SessionUtil.getUserId(session);
			TBrUserReportQuery tBrUserReportQuery = new TBrUserReportQuery();
			tBrUserReportQuery.setActivityId(activityId);
			tBrUserReportQuery.setCreateBy(createBy);
			TBrUserReport report = tBrUserReportService.queryOne(tBrUserReportQuery);
			Long reportId = report.getId();
			return "redirect:/report/detail/"+reportId;
		}
		
	
		//试用协议二级页面    是否但列，目前定位到注册协议  1
		
		//活动报告  /report/init  我的列表
		@RequestMapping(value = "/init", method = { RequestMethod.GET, RequestMethod.POST })
		public String  init( Model model,HttpSession session){
			return "report/init";
		}
		
		
		//试用报告填写三级页面  addInit
		@RequestMapping(value = "/addInit", method = { RequestMethod.GET, RequestMethod.POST })
		public String  addInit( Model model,HttpSession session,Long activityId){
			model.addAttribute("activityId", activityId);
			return "report/addInit";
		}
		
		//试用报告填写操作     add
		@ResponseBody
		@RequestMapping(value = "/add", method = { RequestMethod.GET, RequestMethod.POST })
		public Map<String, Object>  add( Model model,HttpSession session,TBrUserReportVo vo){
			Map<String, Object> result =  new HashMap<String, Object>();
			result.put("code", HttpStatusCode.CODE_SUCCESS);
			Long userId = SessionUtil.getUserId(session);
			Long reportId = tBrUserReportService.addReport(vo, userId);
			result.put("reportId", reportId);
			return result;
		}
		
		//报告图片上传
		@ResponseBody
		@RequestMapping(value = "/upload" ,method = { RequestMethod.GET, RequestMethod.POST })
		public Map<String, Object> uploadPicture( MultipartFile file ){
			String imagePath = MultipartFileUploadUtil.fileUpload(file, ImageConstant.REPORT);
	        Map<String, Object> result = new HashMap<String, Object>();
	        result.put("picUrl", imagePath);
	        result.put("code", HttpStatusCode.CODE_SUCCESS);
			return result;
		}
		
}
