<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common_java.jsp" %>
<header class="navbar-wrapper" style="height: 70px;">
    <div id="nav-fixed" class="navbar navbar-black navbar-fixed-top" >
        <div class="container cl">
            <a class="logo navbar-logo hidden-xs f-20" href="${ctx}" target="_blank">
          		    首页
            </a>
            <a class="logo navbar-logo-m visible-xs" href="javascript:void(0)">微信助手</a>
            <a aria-hidden="false"  class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
            <nav class="nav navbar-nav nav-collapse" role="navigation" id="Hui-navbar" >
                <ul class="cl">
                    <li class="current"><a href="${ctx}" target="_self">首页</a></li>
                    <li ><a href="#" target="_self">广场</a></li>
                </ul>
            </nav>
        </div>
    </div>
    <!-- 
    <canvas id="canv"></canvas>
     -->
</header>
