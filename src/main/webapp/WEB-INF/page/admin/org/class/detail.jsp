<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>新增班级</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
</head>
<body>
	<div class="container-fluid">
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>班级名称</label>
				<div class="byy-block">
					<div class="form-detail" name="name" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">班级简称</label>
				<div class="byy-block">
					<div class="form-detail" name="shortName" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">所属年级</label>
				<div class="byy-block">
					<div class="form-detail" name="gradeName" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">创建日期</label>
				<div class="byy-block">
					<div class="form-detail" name="createTime" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>开始学年</label>
				<div class="byy-block">
					<div class="form-detail" name="beginYear" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>班级状态</label>
				<div class="byy-block">
					<div class="form-detail" name="statusName" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">状态日期</label>
				<div class="byy-block">
					<div class="form-detail" name="statusTime" style="width:75%;"></div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>