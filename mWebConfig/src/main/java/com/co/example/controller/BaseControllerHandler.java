package com.co.example.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.github.moncat.common.entity.BaseEntity;
import com.co.example.common.utils.PageReq;

/**
 * BaseController默认值，普通Controller将继承该类，并根据需要覆盖相关方法
 * 注意返回的键不要冲突
 * @author zyl
 * @param <T>
 */
public  class BaseControllerHandler<T extends BaseEntity> extends BaseController<T> {

	@Override
	public Boolean listExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			PageReq pageReq, T query) {
		return false;
	}

	@Override
	public void addInitExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
	}

	@Override
	public void editInitExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			T t, Long id) {
	}

	@Override
	public Boolean addExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response, T t,
			PageReq pageReq,Map<String, Object> result) {
		return false;
		
	}

	@Override
	public Boolean editExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response, T t,
			PageReq pageReq,Map<String, Object> result) {
		return false;
	}

	@Override
	public Boolean showExt(Model model, HttpSession session, HttpServletRequest request, HttpServletResponse response,
			T t, Long id) {
		return false;
	}

	@Override
	public Boolean activeExt(HttpSession session, HttpServletRequest request, HttpServletResponse response, T t,
			Long id ,Map<String, Object> result) {
		return false;
	}

	@Override
	public Boolean negativeExt(HttpSession session, HttpServletRequest request, HttpServletResponse response, T t,
			Long id ,Map<String, Object> result) {
		return false;
	}
	
	@Override
	public Boolean deleteLogicExt(HttpSession session, HttpServletRequest request, HttpServletResponse response, T t,
			Long id ,Map<String, Object> result) {
		return false;
	}


	@Override
	public Boolean deletePhysicsExt(HttpSession session, HttpServletRequest request, HttpServletResponse response, T t,
			Long id ,Map<String, Object> result) {
		return false;
	}

	@Override
	public Boolean show4JsonExt(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response, T t, Long id ,Map<String, Object> result) {
		return false;
	}

	
	
}
