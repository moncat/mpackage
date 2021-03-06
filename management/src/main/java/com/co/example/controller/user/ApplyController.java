package com.co.example.controller.user;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.activity.TBrActivity;
import com.co.example.entity.message.TBrMessage;
import com.co.example.entity.user.TBrUserApply;
import com.co.example.entity.user.aide.TBrUserApplyQuery;
import com.co.example.service.activity.TBrActivityService;
import com.co.example.service.message.TBrMessageService;
import com.co.example.service.user.TBrUserApplyService;
import com.co.example.utils.BaseDataUtil;
import com.co.example.utils.SessionUtil;

/**
 * 用户申请（商品试用）
 * @author zyl
 *
 */
@Controller
@RequestMapping("apply")
public class ApplyController extends  BaseControllerHandler<TBrUserApplyQuery> {

	@Autowired
	TBrUserApplyService tBrUserApplyService;
	
	@Autowired
	TBrMessageService tBrMessageService;
	
	@Autowired
	TBrActivityService tBrActivityService;
	
	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrUserApplyQuery query) {
		query.setJoinActivity(true);
		Page<TBrUserApply> page = tBrUserApplyService.queryPageList(query, pageReq);
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, page);
		return true;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/status/{id}/{flg}", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String, Object> active(HttpSession session,@PathVariable Long id,@PathVariable Byte flg)throws Exception {
		Map<String, Object> result = result();
		TBrUserApply one = tBrUserApplyService.queryById(id);
		one.setIsActive(flg);
		tBrUserApplyService.updateByIdSelective(one);
		Long activityId = one.getActivityId();
		TBrActivity tBrActivity = tBrActivityService.queryById(activityId);
		String title = tBrActivity.getTitle();
		// 发消息 2通过  0拒绝
		if(flg ==2 || flg ==0){
			TBrMessage tBrMessage = new TBrMessage();
			if(flg ==2){
				tBrMessage.setTitle("申请通过");
				tBrMessage.setDetail("恭喜您，你试用活动【"+title+"】申请通过。");
			}else if(flg ==0){
				tBrMessage.setTitle("申请失败");
				tBrMessage.setDetail("很遗憾，你试用活动【"+title+"】申请失败，祝您下次好运。");
			}
			tBrMessage.setCreateBy(SessionUtil.getAdminId(session));
			tBrMessage.setReceiveBy(one.getCreateBy());
			tBrMessage.setReceiveTime(new Date());
			BaseDataUtil.setDefaultData(tBrMessage);
			tBrMessageService.add(tBrMessage);
		}
		return result;
	}
	
}







