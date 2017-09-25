package com.co.example.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

public class CsrfSecurityRequestMatcher implements RequestMatcher {
    protected Log log = LogFactory.getLog(getClass());
    private Pattern allowedMethods = Pattern
            .compile("^(GET|HEAD|TRACE|OPTIONS)$");

    @Override
    public boolean matches(HttpServletRequest request) {
    	//此处添加了 一些可以不拦截的请求路径
        if (execludeUrls != null && execludeUrls.size() > 0) {
            String servletPath = request.getServletPath();
            for (String url : execludeUrls) {
                if (servletPath.contains(url)) {
                    log.info("++++"+servletPath);
                    return false;
                }
            }
        }
        //默认 拦截模式和本设置一致，GET|HEAD|TRACE|OPTIONS 等都需要拦截
        return !allowedMethods.matcher(request.getMethod()).matches();
    }

    /**
     * 需要排除的url列表
     */
    private List<String> execludeUrls;

    public List<String> getExecludeUrls() {
        return execludeUrls;
    }

    public void setExecludeUrls(List<String> execludeUrls) {
        this.execludeUrls = execludeUrls;
    }
}