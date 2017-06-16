<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common_java.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="../common/common_meta.jsp" %>  
		<%@include file="../common/common_css.jsp"%>
	    <title>错误提示</title>
	</head>
	<body>
	   <jsp:include page="../common/common_header.jsp">
			<jsp:param name="active" value="vote_user"/>
		</jsp:include>
	    <section class="container">
			<div class="page-404 text-c"> 
			  <p class="error-title"><i class="Hui-iconfont">&#xe688;</i> 404</p>  
			  <p class="error-description">不好意思，您没有访问权限或您访问的页面不存在~</p>  
			  <p class="error-info">
				  <c:if test="${isLogin==false}">
				    	  您可以：<a href="${ctx}/loginInit" class="c-primary">&lt;去登录</a><span class="ml-20">|</span>
				  </c:if>
				  <a href="${ctx}" class="c-primary ml-20">去首页 &gt;</a>
			   </p> 
			</div> 
	    </section>
		<%@include file="../common/common_footer.jsp" %> 
		<%@include file="../common/common_js.jsp" %>
	</body>
</html>