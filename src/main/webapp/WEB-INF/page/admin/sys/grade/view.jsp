<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.boyuyun.com.cn" prefix="byy"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>新增年级</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
</head>
<body>
	<div class="container-fluid">
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>年级名称</label>
				<div class="byy-block">
					<input type="text" name="name" style="width:75%;" placeholder="请输入年级名称" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>所在学段</label>
				<div class="byy-block">
					<byy:enum className="com.boyuyun.base.util.consts.Stage" pleaseChoose="true" name="stage" id="stage" cssClass="byy-form-select" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>年级简称</label>
				<div class="byy-block">
					<input type="text" name="shortName" style="width:75%;" placeholder="请输入年级简称" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>排序</label>
				<div class="byy-block">
					<input type="text" name="sortNum" style="width:75%;" placeholder="请输入年级排序" />
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
	<script type="text/javascript" src="<%=basePath%>plugins/wdatepicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/sys/grade/list.js"></script>
</body>
</html>