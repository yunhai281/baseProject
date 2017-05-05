<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
   <div class="container-fluid">
   	<div class="row breadcrumb">
   		<span class="byy-breadcrumb" separator=">">
   			<a>统一登录</a>
   			<a><cite>登录日志</cite></a>
   		</span>
   	</div>
   	<div class="row toolbar byy-form">
   		<div class="byy-inline" >
   			<label for="">用户名: </label>
			<div class="byy-inline">
				<input type="text" name="userName">
			</div>
			<label for="">登录结果: </label>
			<div class="byy-inline">
				<select name="loginResult" id='loginResult' >
					<option value="">请选择... </option>
					<option value="1">登录成功</option>
					<option value="-1">用户名或密码错误</option>
					<option value="-2">没有权限</option>
					<option value="-3">用户被禁用</option>
				</select>
			</div>
			<label for="">登录时间: </label>
			<div class="byy-inline">
				<input type="text" id="bloginTime" readonly="readonly" name="bloginTime" placeholder="点击选择日期"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'eloginTime\')}',dateFmt:'yyyy-MM-dd'})" />
			</div>
			<label for="">-</label>
			<div class="byy-inline">
				<input type="text" id="eloginTime"  readonly="readonly" name="eloginTime" placeholder="点击选择日期"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'bloginTime\')}',dateFmt:'yyyy-MM-dd'})" />
			</div>
   		</div>
		 <span class="byy-btn list_search small"><i class="fa fa-search"></i>查询</span>
		 <span class="byy-btn primary list_reset small"><i class="fa fa-repeat"></i>重置</span>
	</div>
    <div class="row">
    	<table id="dtable" class="byy-table"></table>
    </div>
    <div class="row page">
    	<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 left-btn-group">
    	</div>
    	<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10 pagination pull-right"></div>
    </div>
</div>
<script type="text/javascript" src="<%=basePath%>plugins/wdatepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/admin/sso/log/list.js"></script>
