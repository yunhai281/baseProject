<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	<style type="text/css">
		.byy-form .byy-form-item {
			clear: none;
		}
		
		div.byy-form-item {
			float: left;
			width: 45%;
		}
		
		div.byy-block {
			width: 100%;
		}  
	</style>
</head>
<body>
	<div class="container-fluid"> 
		<form action="" class="byy-form">
			<input type="hidden" name="roleId" id="roleId" /> 
			<c:forEach var="ssoApp" items="${list}">
				<div class="byy-form-item ssoAppDiv">
					<div class="byy-inline" style="width: 100%;">
						<div style="width: 50%;float:left">
						    ${ssoApp.name } 
							<input type="hidden" name="applicationId" value=${ssoApp.id } id=${ssoApp.id } /> 
						</div>
						<div style="float:right;width: 50%;">
						    <span class="byy-nav-more"></span>
	                    	<input type="checkbox" class="byy-form-checkbox" name="enabled" value="1" title="启用"   >
                    	</div>
                	</div> 
				</div> 
			</c:forEach>
			<div class="byy-form-item text-center" style="width: 100%;">
	            <span class="byy-btn" id="submit">提交</span> 
	            <span class="byy-btn primary" id="close">关闭</span>
	        </div>
		</form>
	</div>
	<script type="text/javascript"  src="<%=basePath %>plugins/jquery/jquery.1.11.3.min.js"></script>
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.plugin.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/sso/role/role.js"></script>
</body>
</html>