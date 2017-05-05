<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>新增应用</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
</head>
<body>
	<div class="container-fluid">
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>学科名称</label>
				<div class="byy-block">
					<div class="form-detail" name="name" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>学期总课时</label>
				<div class="byy-block">
					<div class="form-detail" name="hours" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>是否是通用课程:</label>
				<div class="byy-block">
					<div class="form-detail" name="generalCourse" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>是否是主课:</label>
				<div class="byy-block">
					<div class="form-detail" name="mainCourse" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
                <label for="" class="byy-label">课程类型:</label>
                <div class="byy-block">
                    <div class="form-detail" name="typeName" style="width:75%;"></div>
                </div>
            </div>
			<div class="byy-form-item">
                <label for="" class="byy-label">学科领域:</label>
                <div class="byy-block">
                    <div class="form-detail" name="subjectFieldName" style="width:75%;"></div>
                </div>
            </div>
           <div class="byy-form-item">
                <label for="" class="byy-label">学科代码:</label>
                <div class="byy-block">
                    <div class="form-detail" name="subjectCode" style="width:75%;"></div>
                </div>
            </div>                        
           <div class="byy-form-item">
                <label for="" class="byy-label">序号:</label>
                <div class="byy-block">
                    <div class="form-detail" name="sortNum" style="width:75%;"></div>
                </div>
            </div>
		</form>
	</div>
</body>
</html>