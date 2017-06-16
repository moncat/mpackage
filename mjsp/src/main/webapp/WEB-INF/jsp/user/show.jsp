
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../common/common_java.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@include file="../common/common_meta.jsp" %>  
	<%@include file="../common/common_css.jsp"%>
    <title>show</title>
</head>
<body>
   <jsp:include page="../common/common_header.jsp">
		<jsp:param name="active" value="user_show"/>
	</jsp:include>
    <section class="container">
        <div class="responsive">
            <div class="row cl">
                <!-- right -->
                <div class="col-md-12">
                     <!--panel 2 start-->
                    <div class="panel shadow panel-default radius mt-20">
                        <div class="panel-body">
                            <h4>show</h4>
                            <div class="line"></div>
                           
                        </div>
                    </div>
                    <!--panel 2 end-->
                </div>
            </div>
        </div>
    </section>
<%@include file="../common/common_footer.jsp" %> 
<%@include file="../common/common_js.jsp" %>
</body>
</html>