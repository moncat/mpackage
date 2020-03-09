package com.co.example.interceptor;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.co.example.common.constant.Constant;
import com.co.example.constant.SessionConstant;
import com.co.example.entity.admin.TAdmin;
import com.co.example.entity.admin.TBrSession;
import com.co.example.entity.admin.aide.AdminSession;
import com.co.example.entity.admin.aide.TBrSessionQuery;
import com.co.example.service.admin.TBrSessionService;
import com.co.example.utils.BaseDataUtil;
import com.co.example.utils.ClientUtil;
import com.co.example.utils.SessionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 配合 baseController使用的请求路径拦截器
 * 
 * @author zyl
 *
 */
@Slf4j
public class SessionInterceptor extends HandlerInterceptorAdapter {

	@Inject
	TBrSessionService tBrSessionService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg) throws Exception {
		HttpSession session = request.getSession();
		AdminSession adminSession = (AdminSession) session.getAttribute(SessionConstant.SESSION_ADMIN);
		if (null == adminSession) {
			response.sendRedirect("/login");
			return false;
		}
		if (null != adminSession) {
			TAdmin admin = SessionUtil.getAdmin(session);
			Long adminId = admin.getId();
			Byte memberType = admin.getMemberType();
			String sessionId = session.getId();
			TBrSession tBrSession = new TBrSession();
			tBrSession.setAdminId(adminId);
			tBrSession.setLoginSessionId(sessionId);
			tBrSession.setDelFlg(Constant.YES);
			long kickNum = tBrSessionService.queryCount(tBrSession);
			if(kickNum>0){
				log.info("***limit***已经被踢出");
				loginOut(response, session);
			}else{
				tBrSession.setDelFlg(Constant.NO);
				long has = tBrSessionService.queryCount(tBrSession);
				if(has>0){
//					log.info("***limit***没有被踢出");					
				}else{
					log.info("***limit***无此记录，添加");					
					String ip = ClientUtil.getIp(request);
					if(memberType != null){
						TBrSessionQuery tBrSessionQuery = new TBrSessionQuery();
						tBrSessionQuery.setAdminId(adminId);
						tBrSessionQuery.setDelFlg(Constant.NO);
						List<TBrSession> list = tBrSessionService.queryList(tBrSessionQuery,
								new Sort(Direction.DESC, "t.update_time"));
						int size = list.size();
						if (size>0 && memberType.equals(Constant.STATUS_ONE)) { 
							log.info("***limit***普通用户");
							for (int i = 0; i < size; i++) {
								updateSession(list.get(i).getId());
							}
						}
						if (size>=5 && memberType.equals(Constant.STATUS_TWO)) { 
							log.info("***limit***付费用户");
							for (int i = 4; i < size; i++) {
								updateSession(list.get(i).getId());
							}
						}
					}
					addSession(sessionId, adminId,ip);
				}
			}
		}
		return true;
	}

	private void loginOut(HttpServletResponse response, HttpSession session) throws IOException {
		session.removeAttribute(SessionConstant.SESSION_ADMIN);
		session.setAttribute("flo",1);
		response.sendRedirect("/login");
	}

	private void updateSession(Long id) {
		TBrSession query4up = new TBrSession();
		query4up.setId(id);
		query4up.setDelFlg(Constant.YES);
		tBrSessionService.updateByIdSelective(query4up);
	}

	private void addSession(String sessionId, Long adminId,String ip) {
		TBrSession tBrSession = new TBrSession();
		BaseDataUtil.setDefaultData(tBrSession);
		tBrSession.setLoginIp(ip);
		tBrSession.setAdminId(adminId);
		tBrSession.setUpdateTime(new Date());
		tBrSession.setLoginSessionId(sessionId);
		tBrSessionService.add(tBrSession);
	}

}
