<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>添加菜单</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
</head>
<body>
	<div class="container-fluid"> 
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<input type="hidden" name="parentId" />
			<input type="hidden" name="userScopeType" />
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>名称</label>
				<div class="byy-block">
					<input type="text" name="name" style="width:75%;" placeholder="请输角色名称" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">描述</label>
				<div class="byy-block">
					<textarea placeholder="请输入..."  name="remark" style="width:75%;"></textarea>  
				</div>
			</div>
			<div class="byy-form-item text-center">
	            <span class="byy-btn" id="submit">提交</span>
	            <span class="byy-btn primary" id="close">关闭</span>
	        </div>
		</form>
	</div>
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.1.11.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script> 
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.plugin.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/sso/role/role.js"></script>
</body>
</html>