<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<div class="container-fluid">
	<div class="row breadcrumb">
		<span class="byy-breadcrumb" separator=">">
               <a><cite id="gradeName">${school.name}</cite></a>
         </span>
         <span class="byy-btn small pull-right list_add"><i class="fa fa-plus"></i>新增班级</span>
	</div>
	<div class="row toolbar byy-form">
		<div>
			<label for="">班级名称：</label>
			<div class="byy-inline">
				<input type="text" name="shortName"  />
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
<script type="text/javascript">
	var global_gradeId = "${param.gradeId}";
</script>
<script type="text/javascript" src="<%=basePath%>plugins/wdatepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/admin/org/class/class.js"></script>
