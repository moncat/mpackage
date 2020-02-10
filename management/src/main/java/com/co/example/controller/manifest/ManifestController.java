package com.co.example.controller.manifest;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.manifest.TBrManifest;
import com.co.example.entity.manifest.aide.TBrManifestQuery;
import com.co.example.service.manifest.TBrManifestAuthService;
import com.co.example.service.manifest.TBrManifestService;
import com.co.example.utils.SessionUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("manifest")
public class ManifestController extends BaseControllerHandler<TBrManifestQuery> {
	
	@Inject
	TBrManifestService tBrManifestService;
	
	@Inject
	TBrManifestAuthService tBrManifestAuthService;

	/**
	 * 新增时
	 */
	@Override
	public Boolean addExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrManifestQuery t, PageReq pageReq, Map<String, Object> result) {
		Long adminId = SessionUtil.getAdminId(session);
		return tBrManifestService.addAndAuth(t, adminId);
	}

	
	//TODO
	// 需要开机便去查询并处理数据	
	
	public  void  DoTask() throws Exception  {
		log.info("**DoTask**清单人物消费者程序执行**start**");
		TBrManifestQuery tBrManifestQuery = new TBrManifestQuery();
		tBrManifestQuery.setStatus(Constant.TASK_STATUS_TODO);
		Sort sort = new Sort(Sort.DEFAULT_DIRECTION,"t.create_time");
		List<TBrManifest> mList = tBrManifestService.queryList(tBrManifestQuery,sort);
		if(mList.size()==0){
			Thread.sleep(600000);
			log.info("**DoTask**暂时没有任务睡眠10分钟**");
		}else{
			//从data表读取数据存储到result表中，并更新更新主表的状态
			log.info("清单人物消费者程序执行");
			for (TBrManifest tBrManifest : mList) {
				tBrManifestService.queryManifest(tBrManifest);
			}
		}
	}
	
	@RequestMapping(value = "/show1/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String show1(Model model, HttpSession session, @PathVariable Long id) { 
		return "manifest/show1";
	}
	
	
	
}


