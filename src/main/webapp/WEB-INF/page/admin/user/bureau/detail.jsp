<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>查看</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
<style type="text/css">
	.byy-form .byy-form-item{
	 clear: none;
	}
	div.byy-form-item{
		float: left;
		width: 45%;
	}
	div.byy-block {
	width: 100%;
	}
</style>
</head>
<body>
	<div class="container-fluid" style="overflow: hidden;">
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>用户名</label>
				<div class="byy-block">
					<div class="form-detail" name="userName" style="width: 50%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>真实姓名</label>
				<div class="byy-block">
					<div class="form-detail" name="realName" style="width: 50%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">Email</label>
				<div class="byy-block">
					<div class="form-detail" name="email" style="width: 50%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">手机号</label>
				<div class="byy-block">
					<div class="form-detail" name="mobile" style="width: 50%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">性别</label>
				<div class="byy-block">
					<div class="form-detail" name="sexName" style="width: 50%;"></div>
				</div>
			</div>
			
			<div class="byy-form-item">
				<label for="" class="byy-label">部门</label>
				<div class="byy-block">
					<div class="form-detail" name="governmentName" style="width: 50%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">岗位</label>
				<div class="byy-block">
					<div class="form-detail" name="postName" style="width: 50%;"></div>
				</div>
			</div>
			
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.1.11.3.min.js"></script>
	<jsp:include page="../../../common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/wdatepicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/user/student/list.js"></script>
</body>
</html>