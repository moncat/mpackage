package com.co.example.controller.manifest;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.aide.TAdminQuery;
import com.co.example.entity.manifest.TBrManifest;
import com.co.example.entity.manifest.TBrManifestAuth;
import com.co.example.entity.manifest.TBrManifestResult;
import com.co.example.entity.manifest.aide.TBrManifestAuthQuery;
import com.co.example.entity.manifest.aide.TBrManifestAuthVo;
import com.co.example.entity.manifest.aide.TBrManifestQuery;
import com.co.example.entity.manifest.aide.TBrManifestResultQuery;
import com.co.example.service.admin.TAdminService;
import com.co.example.service.manifest.TBrManifestAuthService;
import com.co.example.service.manifest.TBrManifestDataService;
import com.co.example.service.manifest.TBrManifestResultService;
import com.co.example.service.manifest.TBrManifestService;
import com.co.example.utils.JsonUtil;
import com.co.example.utils.SessionUtil;
import com.github.moncat.common.generator.id.NextId;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("manifestAuth")
public class ManifestAuthController extends BaseControllerHandler<TBrManifestAuthQuery> {

	@Inject
	TBrManifestAuthService tBrManifestAuthService;
	@Inject
	TBrManifestService tBrManifestService;
	@Inject
	TBrManifestDataService tBrManifestDataService;
	@Inject
	TBrManifestResultService tBrManifestResultService;
	@Inject
	TAdminService tAdminService;

	/**
	 * 我的清单页面 TODO 关联清单表
	 */
	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrManifestAuthQuery query) {
		Long adminId = SessionUtil.getAdminId(session);
		// 需要展示的我的关注列表，然后根据进行类型在我的清单里区分
		query.setUsingBy(adminId);
		query.setJoinManifestFlg(true);
		query.setDelFlg(Constant.NO);
		List<TBrManifestAuthVo> queryList = tBrManifestAuthService.queryList(query);
		for (TBrManifestAuthVo one : queryList) {
			one.setStatus(one.getManifestStatus());
			int keyId = one.getType().intValue() * 100;
			TBrManifestResultQuery tBrManifestResultQuery = new TBrManifestResultQuery();
			tBrManifestResultQuery.setManifestId(one.getManifestId());
			tBrManifestResultQuery.setKeyId(keyId);
			TBrManifestResult manifestResult = tBrManifestResultService.queryOne(tBrManifestResultQuery);
			if (manifestResult != null) {
				one.setResult(manifestResult);
				try {
					one.setResultJson(JsonUtil.toKvList(manifestResult.getJsondata()));
				} catch (Exception e) {
					e.printStackTrace();
					log.info("result解析报错：" + manifestResult.getJsondata());
				}
			} else {
				one.setResultJson(Lists.newArrayList());
			}
			Long createBy = one.getCreateBy();
			TAdmin creator = tAdminService.queryById(createBy);
			one.setAdminName(creator.getDisplayName());
		}
		model.addAttribute(LIST, queryList);
		return true;
	}

	/**
	 * 每个列表页面下拉框列表，根据分类进行过滤，且仅仅暂时状态为waitting需要初始化数据的的选项
	 * 
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/option", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> option(Model model, HttpSession session, byte type) throws Exception {
		Map<String, Object> result = result();
		Long uId = SessionUtil.getAdminId(session);
		TBrManifestAuthQuery query = new TBrManifestAuthQuery();
		query.setJoinManifestFlg(true);
		query.setUsingBy(uId);
//		query.setStatus(Constant.TASK_STATUS_WAITTING);
		query.setDelFlg(Constant.NO);
		query.setType(type);
		List<TBrManifestAuth> queryList = tBrManifestAuthService.queryList(query);
		result.put(LIST, queryList);
		return result;
	}

	
	@RequestMapping(value = "/option2", method = { RequestMethod.GET, RequestMethod.POST })
	public String option2(Model model, HttpSession session, byte type) throws Exception {
		Long uId = SessionUtil.getAdminId(session);
		TBrManifestAuthQuery query = new TBrManifestAuthQuery();
		query.setJoinManifestFlg(true);
		query.setUsingBy(uId);
		query.setDelFlg(Constant.NO);
		query.setType(type);
		List<TBrManifestAuth> queryList = tBrManifestAuthService.queryList(query);
		model.addAttribute(LIST, queryList);
		return "manifestAuth/option";
	}
	
	/**
	 * 我的工作台清单
	 * 
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> index(Model model, HttpSession session) throws Exception {
		Map<String, Object> result = result();
		TBrManifestAuthQuery query = new TBrManifestAuthQuery();
		Long adminId = SessionUtil.getAdminId(session);
		query.setUsingBy(adminId);
		query.setJoinManifestFlg(true);
		query.setDelFlg(Constant.NO);
		query.setIsTop(Constant.YES);
		List<TBrManifestAuthVo> queryList = tBrManifestAuthService.queryList(query);
		for (TBrManifestAuthVo one : queryList) {
			int keyId = one.getType().intValue() * 100;
			TBrManifestResultQuery tBrManifestResultQuery = new TBrManifestResultQuery();
			tBrManifestResultQuery.setManifestId(one.getManifestId());
			tBrManifestResultQuery.setKeyId(keyId);
			TBrManifestResult manifestResult = tBrManifestResultService.queryOne(tBrManifestResultQuery);
			one.setResult(manifestResult);
		}
		result.put(LIST, queryList);
		return result;
	}

	@RequestMapping(value = "/shareInit", method = { RequestMethod.GET, RequestMethod.POST })
	public String shareInit(Model model, HttpSession session, Long id) throws Exception {
		model.addAttribute("id", id);
		return "manifestAuth/share";
	}

	@ResponseBody
	@RequestMapping(value = "/shareTo", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> shareTo(Model model, HttpSession session, String nameLike) {
		Map<String, Object> result = result();
		Long adminId = SessionUtil.getAdminId(session);
		TAdminQuery tAdminQuery = new TAdminQuery();
		tAdminQuery.setDelFlg(Constant.NO);
		tAdminQuery.setNameLike(nameLike);
		List<TAdmin> queryList = tAdminService.queryList(tAdminQuery);
		List<TAdmin> list = queryList.stream().filter(s -> s.getId() != adminId).collect(Collectors.toList());
		result.put(LIST, list);
		return result;
	}

	/**
	 * 分享 将此清单给其他人也创建一条数据
	 */
	@ResponseBody
	@RequestMapping(value = "/share", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> share(Model model, HttpSession session, Long id,
			@RequestParam(value = "usingIds[]") Long[] usingIds) throws Exception {
		Map<String, Object> result = result();
		TBrManifestAuthQuery tBrManifestAuthQuery = null;
		tBrManifestAuthQuery = new TBrManifestAuthQuery();
		tBrManifestAuthQuery.setId(id);
		TBrManifestAuth one = tBrManifestAuthService.queryOne(tBrManifestAuthQuery);
		for (Long usingId : usingIds) {
			tBrManifestAuthQuery = new TBrManifestAuthQuery();
			tBrManifestAuthQuery.setManifestId(one.getManifestId());
			tBrManifestAuthQuery.setUsingBy(usingId);
			TBrManifestAuth queryOne = tBrManifestAuthService.queryOne(tBrManifestAuthQuery);
			if (queryOne == null) {
				one.setCreateTime(new Date());
				one.setDelFlg(Constant.NO);
				one.setIsActive(Constant.YES);
				one.setUsingBy(usingId);
				one.setId(NextId.getId());
				tBrManifestAuthService.add(one);
			} else {
				log.info("share-该清单已分享");
			}
		}
		return result;
	}

	/**
	 * 新增一条授权， 关联产品或其他数据到清单。此处要传递type
	 */
	@Override
	public Boolean addExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrManifestAuthQuery t, PageReq pageReq, Map<String, Object> result) {
		Long adminId = SessionUtil.getAdminId(session);
		t.setUsingBy(adminId);
		t.setCreateBy(adminId);
		// 更新清单表状态为已经初始化数据待处理
		Long manifestId = t.getManifestId();
		TBrManifestQuery tBrManifestQuery = new TBrManifestQuery();
		tBrManifestQuery.setId(manifestId);
		tBrManifestQuery.setStatus(Constant.TASK_STATUS_TODO);
		tBrManifestService.updateByIdSelective(tBrManifestQuery);
		return super.addExt(model, session, request, response, t, pageReq, result);
	}

	// 放到工作台上
	@ResponseBody
	@RequestMapping(value = "/top", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> top(Model model, HttpSession session, Long id, int flg) {
		Map<String, Object> result = result();
		TBrManifestAuthQuery tBrManifestAuthQuery = new TBrManifestAuthQuery();
		tBrManifestAuthQuery.setId(id);
		if (flg == 1) {
			tBrManifestAuthQuery.setIsTop(Constant.YES);
		} else {
			tBrManifestAuthQuery.setIsTop(Constant.NO);
		}
		tBrManifestAuthService.updateByIdSelective(tBrManifestAuthQuery);
		return result;
	}

	//删除优化，处理删除逻辑。未分享删除，或分享人同时删除时  删除主清单。
	@Override
	public Boolean deleteLogicExt(HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrManifestAuthQuery t, Long id, Map<String, Object> result) {
		
		TBrManifestAuth auth = tBrManifestAuthService.queryById(id);
		Long manifestId = auth.getManifestId();
		TBrManifestAuthQuery tmpQuery = new TBrManifestAuthQuery();
		tmpQuery.setManifestId(manifestId);
		tmpQuery.setDelFlg(Constant.NO);
		long queryCount = tBrManifestAuthService.queryCount(tmpQuery);
		if(queryCount >1){
			log.info("该清单已经分享多人，其他人未删除，该主清单不作废");
		}else{
			log.info("该清单已经无人使用，作废，不必再计算");
			TBrManifest tBrManifest = new TBrManifest();
			tBrManifest.setId(manifestId);
			tBrManifest.setDelFlg(Constant.YES);
			tBrManifestService.updateByIdSelective(tBrManifest);
		}
		return false;
	}

	@RequestMapping(value = "/pList/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String pList(Model model, HttpSession session, @PathVariable Long id) {
		return "manifestAuth/pList";
	}
	

	@RequestMapping(value = "/bList/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String bList(Model model, HttpSession session, @PathVariable Long id) {
		return "manifestAuth/bList";
	}

	@RequestMapping(value = "/eList/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String eList(Model model, HttpSession session, @PathVariable Long id) {
		return "manifestAuth/eList";
	}

	@RequestMapping(value = "/iList/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String iList(Model model, HttpSession session, @PathVariable Long id) {
		return "manifestAuth/iList";
	}

	// 获取数据
	@ResponseBody
	@RequestMapping(value = "/result/{manifestId}/{type}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> result(Model model, HttpSession session, @PathVariable Long manifestId,
			@PathVariable Byte type) {
		Map<String, Object> result = result();
		TBrManifestResultQuery query = new TBrManifestResultQuery();
		query.setManifestId(manifestId);
		query.setType(type);
		List<TBrManifestResult> list = tBrManifestResultService.queryList(query);
		result.put("list", list);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/todo/{manifestId}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> todo(Model model, HttpSession session, @PathVariable Long manifestId) {
		Map<String, Object> result = result();
		//更新主表状态，数据已经初始化，准备加载
		TBrManifest tBrManifest = new TBrManifest();
		tBrManifest.setId(manifestId);
		tBrManifest.setStatus(Constant.TASK_STATUS_TODO);	
		tBrManifestService.updateByIdSelective(tBrManifest);
		
		//更新授权表状态，需要批量更新， 包含哪些未有数据，便已分享的情况
		TBrManifestAuthQuery tBrManifestAuthQuery = new TBrManifestAuthQuery();
		tBrManifestAuthQuery.setManifestId(manifestId);
		List<TBrManifestAuth> authList = tBrManifestAuthService.queryList(tBrManifestAuthQuery);
		for (TBrManifestAuth old : authList) {
			TBrManifestAuth tmp = new TBrManifestAuth();
			tmp.setId(old.getId());
			tmp.setStatus(Constant.TASK_STATUS_TODO);
			tBrManifestAuthService.updateByIdSelective(tmp);
		}
		return result;
	}
	

}
