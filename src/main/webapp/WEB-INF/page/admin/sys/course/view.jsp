<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.boyuyun.com.cn" prefix="byy" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>新增学科</title>
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
					<input type="text" name="name" style="width:75%;" placeholder="请输入学科名称" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>学期总课时</label>
				<div class="byy-block">
					<input type="text" name="hours" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>是否是通用课程</label>
                  <div class="byy-block">
                    <span>
                        <input type="radio" class="byy-form-radio" name="generalCourse" title="是" value="true"/>
                    </span>
                    <span>
                        <input type="radio" class="byy-form-radio" name="generalCourse" title="否" value="false">
                    </span>
				 </div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>是否是主课</label>
                  <div class="byy-block">
                    <span>
                        <input type="radio" class="byy-form-radio" name="mainCourse" title="是" value="true"/>
                    </span>
                    <span>
                        <input type="radio" class="byy-form-radio" name="mainCourse" title="否" value="false">
                    </span>
				 </div>
			</div>
			<div class="byy-form-item">
                <label for="" class="byy-label">课程类型</label>
                <div class="byy-block">
					<byy:dic dicCode="CourseType" cssClass="byy-form-select" id="type" name="type" style="width:75%;"/>
                </div>
            </div>
			<div class="byy-form-item">
                <label for="" class="byy-label">学科领域</label>
                <div class="byy-block">
					<byy:dic dicCode="SubjectField" cssClass="byy-form-select" id="subjectField" name="subjectField" style="width:75%;"/>
                </div>
            </div>
            <div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>学科代码</label>
				<div class="byy-block">
					<input type="text" name="subjectCode" style="width:75%;" />
				</div>
			</div>                        
            <div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>序号</label>
				<div class="byy-block">
					<input type="text" name="sortNum" style="width:75%;" />
				</div>
			</div>
            <div class="byy-form-item text-center">
                <span class="byy-btn" id="submit">提交</span>
                <span class="byy-btn primary" id="close">关闭</span>
            </div>
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.1.11.3.min.js"></script>
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/sys/course/list.js"></script>
</body>
</html>