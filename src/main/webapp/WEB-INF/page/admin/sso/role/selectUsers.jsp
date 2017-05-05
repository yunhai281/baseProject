<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

	String roleId = (String)request.getAttribute("roleId");
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>教育局人员管理</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
</head>
<body>
	<div class="container-fluid">
		
		<div class="row toolbar byy-form">
			<div>
				<label for="">姓名：</label>
				<div class="byy-inline">
					<input type="text" name="realName"  />
				</div>
				<span class="byy-btn list_search"><i class="fa fa-search"></i>查询</span>
				<span class="byy-btn primary list_reset"><i class="fa fa-repeat"></i>重置</span>
			</div>
		</div>
		
		<div class="row">
			<table id="dtable" class="byy-table"></table>
		</div>
		<div class="row page">
			
			<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10 pagination pull-right"></div>
		</div>
	</div>
	
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/sso/role/roleUsers.js"></script>
	<script>
		menu.config.roleId = '<%=roleId%>';
	</script>
</body>
</html>
