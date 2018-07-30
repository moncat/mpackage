package com.cntend.beauty.controller.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.common.utils.PageReq;
import com.co.example.constant.HttpStatusCode;
import com.co.example.entity.comment.TBrProductCommentStatistics;
import com.co.example.entity.comment.aide.Comment;
import com.co.example.entity.label.TBrLabel;
import com.co.example.entity.label.TBrProductLabel;
import com.co.example.entity.label.aide.TBrLabelQuery;
import com.co.example.entity.label.aide.TBrProductLabelQuery;
import com.co.example.entity.label.aide.TBrProductLabelVo;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.entity.system.TSystemConfig;
import com.co.example.entity.system.aide.TSystemConfigConstant;
import com.co.example.entity.system.aide.TSystemConfigQuery;
import com.co.example.entity.user.TBrUserCollect;
import com.co.example.entity.user.TUser;
import com.co.example.entity.user.aide.TBrUserCollectQuery;
import com.co.example.service.brand.TBrBrandService;
import com.co.example.service.brand.TBrProductBrandService;
import com.co.example.service.comment.TBrProductCommentStatisticsService;
import com.co.example.service.label.TBrLabelService;
import com.co.example.service.label.TBrProductLabelService;
import com.co.example.service.product.TBrAimService;
import com.co.example.service.product.TBrEnterpriseService;
import com.co.example.service.product.TBrIngredientService;
import com.co.example.service.product.TBrProductImageService;
import com.co.example.service.product.TBrProductService;
import com.co.example.service.product.TBrProductSpecService;
import com.co.example.service.system.TSystemConfigService;
import com.co.example.service.user.TBrUserCollectService;
import com.co.example.service.user.TBrUserPlanLabelService;
import com.co.example.utils.SessionUtil;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("product")
public class ProductController {

	@Inject
	TBrIngredientService tBrIngredientService;
	
	@Inject
	TBrProductService tBrProductService;
	
	@Inject
	TBrAimService tBrAimService;
	
	@Inject
	TBrProductImageService tBrProductImageService;
	
	@Inject
	TBrEnterpriseService tBrEnterpriseService;
	
	@Inject
	TBrProductBrandService tBrProductBrandService;
	
	@Inject
	TBrBrandService tBrBrandService;
	
	@Inject
	TBrProductSpecService tBrProductSpecService;
	
	@Inject
	TBrProductCommentStatisticsService tBrProductCommentStatisticsService;
	
	@Inject
	TBrLabelService tBrLabelService;
	
	@Inject
	TBrUserPlanLabelService tBrUserPlanLabelService;
	
	@Inject
	TBrUserCollectService tBrUserCollectService;
	
	@Inject
	TBrProductLabelService tBrProductLabelService;
	
	@Inject
	TSystemConfigService tSystemConfigService;
	
	@Inject
	TBrProductCommentStatisticsService pcss;
	
	String param = "cast(`good` as signed integer)";
	
	
	
	@RequestMapping(value = "init", method = { RequestMethod.GET, RequestMethod.POST })
	public String init(Model model, HttpSession session, TBrProductQuery query) {
		model.addAttribute("query", query);
		//分类,功效
		return "product/init";
	}
	
	 /**   0刚抓取 1匹配不完整  2完整匹配  4特殊符号匹配错误    */
	Byte specialFlg = 2;
	
	@ResponseBody
	@RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String,Object> list(Model model, HttpSession session,PageReq pageReq, TBrProductQuery query) {
		//1 namelike  名称
		//2 namelike2 分类
		//3 功效  功效
		String labels = query.getLabelIds();
		if(labels != null && StringUtils.isNoneBlank(labels)){
			String[] labelIdArr = labels.split("sp");
			query.setLabelIdArr(labelIdArr);
			query.setJoinLabelFlg(true);			
		}
		
		//4 天猫销量
		if(query.getSalesFlg() == true){
			pageReq.setSort(new Sort(Direction.DESC,"t.sales"));
		}
		//5好评
		if(query.getCommentFlg() == true){
			pageReq.setSort(new Sort(Direction.DESC,"t.more_data2+0"));
		}
		query.setDelFlg(specialFlg);
		Map<String,Object> map = Maps.newHashMap();
		log.info("************开始查询******************");
		Page<TBrProduct> page = tBrProductService.queryPageList(query, pageReq);
		log.info("============结束查询==================");
		
//		List<TBrProduct> newContent = Lists.newArrayList();
//		for (TBrProduct tBrProduct : page.getContent()) {
//			tBrProduct = tBrProductService.showOneProduct4Mobile(tBrProduct.getId());
//			tBrProduct = tBrProductService.setLabels(tBrProduct);
//			newContent.add(tBrProduct);
//		}
//		Page<TBrProduct> pageNew = new PageImpl<TBrProduct>(newContent, pageReq,page.getTotalElements());
		map.put("page", page);	
		return map;
	}


	
	@RequestMapping(value = "/show/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String show(Model model,HttpSession session, @PathVariable Long id) {
		TBrProduct one=  tBrProductService.queryById(id);
		model.addAttribute("one", one);
		boolean isLogin = SessionUtil.getIsLogin(session);
		Long collectId  = null;
		TBrUserCollect tBrUserCollect = null;
		if(isLogin){
			Long userId = SessionUtil.getUserId(session);
			TBrUserCollectQuery tBrUserCollectQuery = new TBrUserCollectQuery();
			tBrUserCollectQuery.setCid(id);
			tBrUserCollectQuery.setCreateBy(userId);
			tBrUserCollect = tBrUserCollectService.queryOne(tBrUserCollectQuery);
		}
		//用户是否已收藏
		int collectFlg  = 0;
		if(tBrUserCollect !=null){
			 collectFlg  = 1;
			 collectId = tBrUserCollect.getId();
		}
		//淘宝客代码
		
		TSystemConfigQuery tSystemConfigQuery = new TSystemConfigQuery();
		tSystemConfigQuery.setMapKey(TSystemConfigConstant.TBK_WIDGET);
		TSystemConfig tbkWidget = tSystemConfigService.queryOne(tSystemConfigQuery);
		
		
		model.addAttribute("tbkWidget", tbkWidget);
		model.addAttribute("collectFlg", collectFlg);
		model.addAttribute("collectId", collectId);
		return "product/show";
	}
	
	@ResponseBody
	@RequestMapping(value = "/showAjax", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> showAjax(Model model,HttpSession session, Long id) {
		HashMap<String, Object> map = Maps.newHashMap();
		TBrProduct one=  tBrProductService.queryById(id);
		map.put("one", one);
		map.put("code", HttpStatusCode.CODE_SUCCESS);
		return map;
	}
	
	/**
	 * 获取某个商品的标签
	 * @param model
	 * @param session
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/showLabelAjax", method = { RequestMethod.GET, RequestMethod.POST })
	public HashMap<String, Object> showLabelAjax(Model model,HttpSession session, Long id) {
		HashMap<String, Object> map = Maps.newHashMap();
		TBrProductLabelQuery query = new TBrProductLabelQuery();
		query.setJoinLabelFlg(true);
		query.setProductId(id);
		List<TBrProductLabel> list = tBrProductLabelService.queryList(query);
		String labels="";
		TBrProductLabelVo vo = null;
		for (TBrProductLabel tBrProductLabel : list) {
			vo= (TBrProductLabelVo) tBrProductLabel;
			labels+=vo.getLabelName()+" ";
		}
		map.put("labels", labels);
		map.put("code", HttpStatusCode.CODE_SUCCESS);
		return map;
	}
	
	
	//JD评论
	@ResponseBody
	@RequestMapping(value = "/jdComment", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> jdComment(Model model,HttpSession session,Long id)throws Exception {
		Map<String, Object> result =  new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		List<Comment> commentList = tBrProductSpecService.getJdComments(id);
		result.put("commentList", commentList);
		return result;
	}
	
	//Tmall评论
	@ResponseBody
	@RequestMapping(value = "/tmallComment", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> tmallComment(Model model,HttpSession session,Long id)throws Exception {
		Map<String, Object> result =  new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		List<Comment> commentList = tBrProductSpecService.getTmallComments(id);
		result.put("commentList", commentList);
		return result;
	}
	
	
	//产品推荐 --要显示的标签列表（tab页）
	@ResponseBody
	@RequestMapping(value = "/label", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> label(Model model,HttpSession session,Byte flg)throws Exception {
		Map<String, Object> result =  new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		TBrLabelQuery tBrLabelQuery = new TBrLabelQuery();
		tBrLabelQuery.setAppId(flg);
		List<TBrLabel> labelList = tBrLabelService.queryList(tBrLabelQuery);
		result.put("list", labelList);
		return result;
	}
	
	
	//产品推荐 --根据标签推荐
	@ResponseBody
	@RequestMapping(value = "/recommend", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> recommend(Model model,HttpSession session, Long id ,PageReq pageReq)throws Exception {
		Map<String, Object> result =  new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_ERROR);
		Long userId  = SessionUtil.getUserId(session);
		Page<TBrProductLabel> page = tBrProductLabelService.queryByLabel(id, userId, pageReq);
		if(page != null){
			result.put("code", HttpStatusCode.CODE_SUCCESS);
		}
		result.put("page", page);
		return result;
	}


	//商品模糊匹配 返回json 给前端 awesomplete框架
	@ResponseBody
	@RequestMapping(value = "/search", method = { RequestMethod.GET,RequestMethod.POST })
	public List<TBrProduct> search(Model model,HttpSession session,String key)throws Exception {
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setProductNameLike(key);
		List<TBrProduct> queryList = tBrProductService.queryList(tBrProductQuery);
		return queryList;
	}
	
	//用户商品匹配
	@ResponseBody
	@RequestMapping(value="match")
	public Map<String, Object> match(HttpSession session,Long productId){
		TUser user = SessionUtil.getUser(session);
		Map<String, Object> map = null;
		if(user !=null){
			Long userId = user.getId();
			map = tBrUserPlanLabelService.getMatching(userId, productId);
		}else{
			map = Maps.newHashMap();
			map.put("type", 0);
			map.put("info", "请登录后查看。");
		}
		return  map;
	}
	
	@ResponseBody
	@RequestMapping(value = "/commentStatistics", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> CommentStatistics(Model model,HttpSession session, Long id)throws Exception {
		Map<String, Object> result =  new HashMap<String, Object>();
		result.put("code", HttpStatusCode.CODE_ERROR);
		TBrProductCommentStatistics tBrProductCommentStatistics = new TBrProductCommentStatistics();
		tBrProductCommentStatistics.setPid(id);
		TBrProductCommentStatistics one = pcss.queryOne(tBrProductCommentStatistics);
		String tmallNumAll = one.getTmallNumAll();
		result.put("tmallNumAll", tmallNumAll);
		return result;
	}
}


