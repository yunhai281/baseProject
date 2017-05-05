<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.boyuyun.com.cn" prefix="byy"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
	<title>${byy:param('sysName') }</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
	<link rel="stylesheet" href="<%=basePath %>plugins/font/css/font-awesome.css">
</head>
<body>
	<div class="byy-admin">
		<header>
			<ul class="byy-nav"><!-- ${byy:param('picPath') } -->
				<img src="<%=basePath %>resources/images/logo.png" style="width: 227px;height: 23px;">
				<span class="byy-info pull-right byy-btn byy-btn-menu" style="margin-top:10px;z-index:1000;">
				您好，${sessionScope.user.realName }
				<ul>
					<li class="list_modify" onclick="">修改密码</li>
					<li class="logout" onclick="window.location.href='<%=basePath %>login_logout.do'"><a>退出系统</a></li>
				</ul>
				</span>
			</ul>
		</header>
		<div class="byy-side">
			<div class="byy-side-scroll">
				<ul class="byy-nav byy-nav-tree byy-nav-side">
					<c:forEach var="menu" items="${menu}">
						<li class="byy-nav-item">
							<a href="javascript:;" class="parent">
								${menu.name }
								<span class="byy-nav-more"></span>
							</a>
							<ul class="byy-nav-child">
								<c:if test="${fn:length(menu.child) > 0 }">
									<c:forEach var="child" items="${menu.child }">
										<li tab="main_color" data-url="${child.url }">
											<a href="javascript:;">${child.name }</a>
										</li>
									</c:forEach>
								</c:if>
							</ul>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="byy-body">
			
		</div>
	</div>
	<jsp:include page="../common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script src="<%=basePath %>plugins/highchart/code/highcharts.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/index.js"></script>
</body>
</html>
