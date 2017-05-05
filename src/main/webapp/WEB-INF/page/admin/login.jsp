<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="false"%>
<%@ taglib uri="http://www.boyuyun.com.cn" prefix="byy"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    if(session.getAttribute("user")!=null) {
        response.sendRedirect(request.getContextPath()+"/admin_index.do");
    }
%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>${byy:param('sysName') } - 登录</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
<!--[if lt IE 9]>
	<script src="<%=basePath%>plugins/html5shiv/html5shiv.js"></script>
	<script src="<%=basePath%>plugins/html5shiv/respond.min.js"></script>
<![endif]-->
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css" />
	<link rel="stylesheet" href="<%=basePath %>plugins/themes/default/page.css" />
	<link rel="stylesheet" href="<%=basePath %>plugins/themes/default/login.css" />
</head>
<body>
	<div class="body-wrap">
		<div class="main-color1 body-header">
			<div class="header-wrap"><!-- ${byy:param('picPath') } -->
				<a href="<%=basePath%>login.do"><img src="<%=basePath %>resources/images/logo.png" alt="" /></a>
			</div>
		</div>
		<div class="content-wrap">
			<div class="content-container">
				<form action="" class="byy-form">
					<div class="container-fluid">
						<p class="legend">系统登录</p>
						<hr />
						<input type="text" name="username" autocomplete="off" placeholder="用户名"/>
						<input type="password" name="password" autocomplete="off" placeholder="登录密码"/>
						<span class="byy-btn large warm" id="submit">立即登录</span>
					</div>
				</form>
			</div>
		</div>
		<div class="light-color4 light-color2 body-footer">
			<div class="footer-wrap">
				<p>${byy:param('copyRightInfo') } ${byy:param('icp') }</p>
			</div>
		</div>
		<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.min.js"></script>
	</div>
</body>
</html>
<jsp:include page="../common/common.jsp"></jsp:include>
<script type="text/javascript" src="<%=basePath%>resources/js/admin/login.js"></script>