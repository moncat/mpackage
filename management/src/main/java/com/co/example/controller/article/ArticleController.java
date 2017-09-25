package com.co.example.controller.article;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.co.example.constant.HttpStatusCode;
import com.co.example.controller.BaseControllerHandler;
import com.co.example.common.constant.Constant;
import com.co.example.common.utils.PageReq;
import com.co.example.entity.article.TArticle;
import com.co.example.entity.article.aide.TArticleQuery;
import com.co.example.service.article.TArticleService;

@Controller
@RequestMapping("article")
public class ArticleController extends BaseControllerHandler<TArticle> {
	
	@Inject
	TArticleService tArticleService;
	
	
	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, TArticle query) {
		query.setDelFlg(null);
		return super.listExt(model, session, request, response, pageReq, query);
	}

	@ResponseBody
	@PostMapping("setTop")
	public Map<String, Object> setTop(@RequestParam Long id){
		TArticleQuery tArticleQuery = new TArticleQuery();
		tArticleQuery.setId(id);
		tArticleService.setTop(tArticleQuery);
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
		
	}
	
	@ResponseBody
	@PostMapping("start")
	public Map<String, Object> start(@RequestParam Long id){
		TArticleQuery tArticleQuery = new TArticleQuery();
		tArticleQuery.setId(id);
		tArticleQuery.setDelFlg(Constant.NO);
		tArticleService.updateByIdSelective(tArticleQuery);
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
		
	}
	
	@ResponseBody
	@PostMapping("stop")
	public Map<String, Object> stop(@RequestParam Long id){
		TArticleQuery tArticleQuery = new TArticleQuery();
		tArticleQuery.setId(id);
		tArticleQuery.setDelFlg(Constant.YES);
		tArticleService.updateByIdSelective(tArticleQuery);
		result.put("code", HttpStatusCode.CODE_SUCCESS);
		return result;
	}
	
}









