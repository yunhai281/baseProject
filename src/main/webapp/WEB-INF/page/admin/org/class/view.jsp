<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.boyuyun.com.cn" prefix="byy"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>新增班级</title>
<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
<link rel="stylesheet" href="<%=basePath%>plugins/byy/css/byy.css">
<link rel="stylesheet" href="<%=basePath%>resources/css/common/common.css">
</head>
<body>
	<div class="container-fluid">
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>班级名称</label>
				<div class="byy-block">
					<input type="text" name="name" style="width: 75%;" placeholder="请输入班级名称" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">班级简称</label>
				<div class="byy-block">
					<input type="text" name="shortName" style="width: 75%;" placeholder="请输入班级简称" />
				</div>
			</div>

			<div class="byy-form-item">
				<label for="" class="byy-label">创建日期</label>
				<div class="byy-block">
					<input type="text" id="createTime" style="width: 75%;" readonly="readonly" name="createTime" placeholder="点击选择创建日期"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>开始学年</label>
				<div class="byy-block">
					<input type="text" name="beginYear" style="width: 75%;" placeholder="请输入开始学年" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>班级状态</label>
				<div class="byy-block">
					<byy:dic dicCode="ClassStatus" cssClass="byy-form-select" id="status" name="status" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">状态日期</label>
				<div class="byy-block">
					<input type="text" id="statusTime" style="width: 75%;" readonly="readonly" name="statusTime" placeholder="点击选择状态日期"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				</div>
			</div>
			<input name="gradeId" type="hidden" value="${param.gradeId}"/>
			<div class="byy-form-item text-center">
				<span class="byy-btn" id="submit">提交</span>
				<span class="byy-btn primary" id="close">关闭</span>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		var global_gradeId = "${param.gradeId}";
	</script>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.1.11.3.min.js"></script>
	<jsp:include page="../../../common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/wdatepicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/org/class/class.js"></script>
</body>
</html>