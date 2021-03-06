<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>教育局人员管理</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
</head>
<body>
	<div class="container-fluid">
		<div class="row breadcrumb">
			<span class="byy-breadcrumb" separator=">">
				<a>用户管理</a>
                <a><cite>教育局人员管理</cite></a>
            </span>
			<span class="byy-btn small pull-right list_import" style="margin-right:10px;" ><i class="fa fa-sign-in"></i>批量导入</span>
            <span class="byy-btn small pull-right batchNo" style="margin-right:10px;" ><i class="fa fa-remove"></i>批量禁用</span>
			<span class="byy-btn small pull-right batchYes"  ><i class="fa fa-check"></i>批量启用</span>
            <span class="byy-btn small pull-right list_add"><i class="fa fa-plus"></i>新增人员</span>
		</div>
		<div class="row toolbar byy-form">
			<div>
				<label for="">姓名：</label>
				<div class="byy-inline">
					<input type="text" name="realName"  />
				</div>
				<span class="byy-btn list_search small"><i class="fa fa-search"></i>查询</span>
				<span class="byy-btn primary list_reset small"><i class="fa fa-repeat"></i>重置</span>
			</div>
		</div>
		<div class="row">
			<table id="dtable" class="byy-table"></table>
		</div>
		<div class="row page">
			<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 left-btn-group">
				<span class="byy-btn primary small list_del"><i class="fa fa-trash"></i>批量删除</span>
			</div>
			<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10 pagination pull-right"></div>
		</div>
	</div>
	<jsp:include page="../../../common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/wdatepicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/user/bureau/list.js"></script>
</body>
</html>
