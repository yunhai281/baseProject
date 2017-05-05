<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>年级管理</title>
<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/tree/css/metroStyle/metroStyle.css" />
<link rel="stylesheet" href="<%=basePath%>resources/css/common/treeEditor.css">
<style type="text/css">
.name {
	font-size: 12px;
}
</style>
</head>

<body>
	<div class="container-fluid">
		<div class="row breadcrumb">
			<span class="byy-breadcrumb" separator=">">
				<a>机构管理</a>
				<a><cite>年级信息</cite></a>
			</span>
		</div>
		<div class="row">
			<div class="col-xs-3" style="padding-left: 5px;">
				<div class="byy-form" style="padding:0px;display: block;" >
					 <div class="input-pane mini-search-bar"  style="padding:0px" >
	                    <input type="text" name="name" class="name" placeholder="请输入学校名称" /><label  class="btn_search fa fa-search" ></label>
	                </div>
				</div>
				<ul id="schooldiv" class="ztree" style="margin-left: -10px;"></ul>
			</div>
			<div class="col-xs-9">
				<div id="right_content"  style="border-left: 1px solid #eee;">请点击左侧选择学校</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/tree/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/common/treeEditor.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/org/grade/list.js"></script>
</body>
</html>
