package com.co.example.controller.label;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.constant.HttpStatusCode;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.entity.label.TBrLabel;
import com.co.example.entity.label.TBrLabelClass;
import com.co.example.entity.label.aide.TBrLabelClassQuery;
import com.co.example.entity.label.aide.TBrLabelQuery;
import com.co.example.entity.label.aide.TBrLabelVo;
import com.co.example.entity.product.TBrProduct;
import com.co.example.entity.product.aide.TBrProductQuery;
import com.co.example.service.label.TBrLabelClassService;
import com.co.example.service.label.TBrLabelService;
import com.co.example.service.product.TBrProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("label")
public class LabelController extends  BaseControllerHandler<TBrLabelQuery> {

	@Autowired
	TBrLabelService  tBrLabelService;
	
	@Autowired
	TBrLabelClassService  tBrLabelClassService;
	
	@Autowired
	TBrProductService  tBrProductService;
	
	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TBrLabelQuery query) {
		List<TBrLabel> list = tBrLabelService.queryList();
		model.addAttribute(QUERY, query);
		model.addAttribute(LIST, list);
		return true;
	}

	
	@RequestMapping(value = "/list2", method = { RequestMethod.GET, RequestMethod.POST })
	public String list2( Model model,HttpSession session,PageReq pageReq,TBrLabelQuery query){
		query.setDelFlg(Constant.NO);	 
		query.setLabelClassJoinFlg(true);
		pageReq.setSort(new Sort(Direction.DESC,"t.create_time"));
		Page<TBrLabel> page = tBrLabelService.queryPageList(query, pageReq);
		model.addAttribute(QUERY, query);
		model.addAttribute(PAGE, page);
		return "label/list2";
	}


	@RequestMapping(value = "/detail/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String showMore(Model model, HttpSession session,PageReq pageReq, @PathVariable Long id) {
		TBrLabel one = tBrLabelService.queryById(id);
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinLabelFlg(true);
		tBrProductQuery.setLabelId(one.getId());
		pageReq.setPageSize(10);
		Page<TBrProduct> page = tBrProductService.queryPageList(tBrProductQuery, pageReq);
		model.addAttribute(PAGE, page);
		model.addAttribute(ONE, one);
		return "label/detail";
	}

	@ResponseBody
	@RequestMapping(value = "/count/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public Map<String, Object> count(Model model,  @PathVariable Long id) {
		TBrLabel one = tBrLabelService.queryById(id);
		TBrProductQuery tBrProductQuery = new TBrProductQuery();
		tBrProductQuery.setJoinLabelFlg(true);
		tBrProductQuery.setLabelId(one.getId()); 
		long num = tBrProductService.queryCount(tBrProductQuery);
		Map<String, Object> result = result();
		result.put("num", num);
		return result;
	}

	
	
	@Override
	public Boolean addExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			TBrLabelQuery t, PageReq pageReq, Map<String, Object> result) {
		TBrLabelQuery tBrLabelQuery = new TBrLabelQuery();
		tBrLabelQuery.setName(StringUtils.trim(t.getName()));
		long queryCount = tBrLabelService.queryCount(tBrLabelQuery);
		if(queryCount>0){
			result.put("code", HttpStatusCode.CODE_ERROR);
			result.put("info", "已有该标签，添加失败。");
		}else{
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//设置appId 为0 默认为标签   为1 默认为分类
			Byte appId = 0;
			t.setAppId(appId);
			tBrLabelService.add(t);
			int count = tBrLabelService.addConnect2Product(t);
			log.info(t.getName()+"("+t.getId()+") 成功数量："+count);
			result.put("info", "添加成功！已匹配"+count);
		}
		return true;
	}
	
	//编辑，新增标签
	@RequestMapping(value = "/addInit2", method = { RequestMethod.GET,RequestMethod.POST })
	public String addInit2(Model model, HttpSession session) throws Exception{
		TBrLabelClassQuery tBrLabelClassQuery = new TBrLabelClassQuery();
		tBrLabelClassQuery.setDelFlg(Constant.NO);
		List<TBrLabelClass> labelClassList = tBrLabelClassService.queryList(tBrLabelClassQuery);
		model.addAttribute("labelClassList", labelClassList);
		return "label/addInit2";
	}
	
	@ResponseBody
	@RequestMapping(value = "/add2", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> add2(Model model, HttpSession session,TBrLabelVo vo,PageReq pageReq) throws Exception{
		Map<String, Object> result = result();
		vo.setIsActive(Constant.STATUS_ACTIVE);
		vo.setDelFlg(Constant.NO);
		vo.setCreateTime(new Date()); 		  
		TBrLabelQuery tBrLabelQuery = new TBrLabelQuery();
		tBrLabelQuery.setName(StringUtils.trim(vo.getName()));
		long queryCount = tBrLabelService.queryCount(tBrLabelQuery);
		if(queryCount>0){
			result.put("code", HttpStatusCode.CODE_ERROR);
			result.put("info", "已有该标签，添加失败。");
		}else{
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//设置appId 为0 默认为标签   为1 默认为分类
			Byte appId = 0;
			vo.setAppId(appId);
			tBrLabelService.add(vo);
//			int count = tBrLabelService.addConnect2Product(vo);
//			log.info(vo.getName()+"("+vo.getId()+") 成功数量："+count);
//			result.put("info", "添加成功！已匹配"+count);
		}
		
		
		result.put("pageNumber", pageReq.getPageNumber());
		return result;
	}
	
	
	@RequestMapping(value = "/editInit2/{id}/{pageNumber}", method = { RequestMethod.GET,RequestMethod.POST })
	public String editInit2(Model model,@PathVariable Long id, @PathVariable Long pageNumber)throws Exception {
		TBrLabelClassQuery tBrLabelClassQuery = new TBrLabelClassQuery();
		tBrLabelClassQuery.setDelFlg(Constant.NO);
		List<TBrLabelClass> labelClassList = tBrLabelClassService.queryList(tBrLabelClassQuery);
		model.addAttribute("labelClassList", labelClassList);
		TBrLabel one = tBrLabelService.queryById(id);
		model.addAttribute(ONE, one);
		model.addAttribute("pageNumber", pageNumber);
		return "label/editInit2";
	}
	
	@ResponseBody
	@RequestMapping(value = "/edit2", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> edit2(Model model, TBrLabelVo vo ,PageReq pageReq,RedirectAttributes redirectAttributes)throws Exception {
		Map<String, Object> result = result();
		vo.setUpdateTime(new Date());
		tBrLabelService.updateByIdSelective(vo);			
		result.put("pageNumber", pageReq.getPageNumber());
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateStatus", method = { RequestMethod.GET,RequestMethod.POST })
	public Map<String,Object> updateStatus(Model model, Long id,Boolean flg)throws Exception {
		Map<String, Object> result = result();
		TBrLabel tBrLabel = new TBrLabel();
		tBrLabel.setId(id);
		if(flg){
			tBrLabel.setIsChoice(Constant.YES);
		}else{
			tBrLabel.setIsChoice(Constant.NO);		
		}
		tBrLabelService.updateByIdSelective(tBrLabel);		
		return result;
	}
}










