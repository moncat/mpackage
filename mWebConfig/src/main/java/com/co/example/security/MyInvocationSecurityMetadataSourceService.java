package com.co.example.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.co.example.entity.system.TRoleRight;
import com.co.example.entity.system.aide.TRoleRightQuery;
import com.co.example.entity.system.aide.TRoleRightVo;
import com.co.example.service.system.TRoleRightService;
import com.google.common.collect.Lists;

/**
 * 角色管理 加载全部角色权限资源
 * @author zyl
 *
 */
@Service(value="mismss")
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private TRoleRightService tRoleRightService;

	private HashMap<String, Collection<ConfigAttribute>> map = null;

	/**
	 * 加载权限表中所有权限
	 */
	public void loadResourceDefine() {
		map = new HashMap<>();
		Collection<ConfigAttribute> array;
		ConfigAttribute cfg;
		TRoleRightVo vo = null;
		String url = null;
		TRoleRightQuery tRoleRightQuery = new TRoleRightQuery();
		tRoleRightQuery.setJoinFlg(true);
		List<TRoleRight> list = tRoleRightService.queryList(tRoleRightQuery);
		for (TRoleRight tRoleRight : list) {
			vo = (TRoleRightVo) tRoleRight;
			url = vo.getUrl();
			array = map.get(url);
			if(CollectionUtils.isEmpty(array)){
				array = Lists.newArrayList();
			}
			//此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。
			//此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
			cfg = new SecurityConfig(vo.getRoleName());
			array.add(cfg);
			// 用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
			map.put(url, array);
		}

	}

	// 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		if (map == null) {
			loadResourceDefine();
		}
		// object 中包含用户请求的request 信息
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		AntPathRequestMatcher matcher;
		String resUrl;
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
			resUrl = iter.next();
			matcher = new AntPathRequestMatcher(resUrl);
			if (matcher.matches(request)) {
				return map.get(resUrl);
			}
		}
		return null;
	}

	/**
	 * 刷新内存中已加载的资源信息 ，解决需要重启才生效的问题
	 * 在添加角色权限关联关系后，用以下语句刷新内存
	 * 	MyInvocationSecurityMetadataSourceService.refreshSecurityMetadataSource(request);
	 */
	public static void refreshSecurityMetadataSource(HttpServletRequest request){
		ApplicationContext ctx =  WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		MyInvocationSecurityMetadataSourceService cs=(MyInvocationSecurityMetadataSourceService)ctx.getBean("mismss",MyInvocationSecurityMetadataSourceService.class);
		cs.loadResourceDefine();
	}
	
	
	
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}