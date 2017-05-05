<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
   <div class="container-fluid">
   	<div class="row breadcrumb">
   		<span class="byy-breadcrumb" separator=">">
   			<a>系统管理</a>
   			<a><cite>操作日志</cite></a>
   		</span>
   	</div>
   	<div class="row toolbar byy-form">
   		<div class="byy-inline" >
   			<label for="">关键词: </label>
			<div class="byy-inline">
				<input type="text" name="operate">
			</div>
			<label for="">操作时间: </label>
			<div class="byy-inline">
				<input type="text" id="boperateTime" readonly="readonly" name="boperateTime" placeholder="点击选择日期"
					onclick="WdatePicker({maxDate:'#F{$dp.$D(\'eoperateTime\')}',dateFmt:'yyyy-MM-dd'})" />
			</div>
			<label for="">-</label>
			<div class="byy-inline">
				<input type="text" id="eoperateTime"  readonly="readonly" name="eoperateTime" placeholder="点击选择日期"
					onclick="WdatePicker({minDate:'#F{$dp.$D(\'boperateTime\')}',dateFmt:'yyyy-MM-dd'})" />
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
<script type="text/javascript" src="<%=basePath %>resources/js/admin/sys/operateLog/list.js"></script>
