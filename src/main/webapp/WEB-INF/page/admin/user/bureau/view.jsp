<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.boyuyun.com.cn" prefix="byy"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>新增人员</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
		<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>plugins/tree/css/metroStyle/metroStyle.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/select2/css/select2.min.css">
<style type="text/css">
	.byy-form .byy-form-item{
	 clear: none;
	}
	div.byy-form-item{
		float: left;
		width: 45%;
	}
	div.byy-block {
	width: 100%;
	}
	
	.select2-container--default .select2-selection--multiple{
		border-radius: 2px;
		margin:0px;
		padding:0px;
		border: 1px solid rgb(230, 230, 230);
	}
	
	.select2-container--default .select2-search--inline .select2-search__field{
		height:28px;
		line-height:28px;
	}
	.select2-container--default.select2-container--focus .select2-selection--multiple{
		border-color: #c9c9c9 !important
	}
	.select2-container--default .select2-selection--multiple .select2-selection__choice{
		margin-top:7px;
	}
</style>
</head>
<body>

	<div class="container-fluid" style="overflow: hidden;">
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>用户名</label>
				<div class="byy-block">
					<input type="text" name="userName" style="width: 60%;" placeholder="请输入用户名" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>真实姓名</label>
				<div class="byy-block">
					<input type="text" name="realName" style="width: 60%;" placeholder="请输入真实姓名" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>密码</label>
				<div class="byy-block">
					<input type="password" name="pwd" style="width: 60%;" placeholder="请输入密码" autocomplete="new-password"/>
				</div>
			</div>
			
			<div class="byy-form-item">
				<label for="" class="byy-label">手机号</label>
				<div class="byy-block">
					<input type="text" name="mobile" style="width: 60%;" placeholder="请输入手机号,例如:135XXXXXXXX" />
				</div>
			</div>
			
			<div class="byy-form-item">
				<label for="" class="byy-label">Email</label>
				<div class="byy-block">
					<input type="text" name="email" style="width: 60%;" placeholder="请输入邮箱" />
				</div>
			</div>
			
			
			
			<div class="byy-form-item">
				<label for="" class="byy-label">性别</label>
				<div class="byy-block">
					<byy:dic dicCode="Gender" cssClass="byy-form-select" id="sex" name="sex" style="width: 60%;" />
				</div>
			</div>
			
			 <div class="byy-form-item">
			 	<label for="" class="byy-label"><span class="requirecls">*</span>所属部门</label>
			 	<div class="byy-block">
			 		<input type="hidden" id="governmentId" name="governmentId" style="width: 60%;" placeholder="请选择部门" />
			 		<input type="text" id="governmentName"  name="governmentName"  style="width: 60%;" placeholder="请选择部门" />
			 		
			 		<div style="padding-top:40px;border: 1px solid #ddd;width: 60%;">
			 			<ul id="governmentdiv" class="ztree selectZtree" style="margin-left:-10px;"></ul>
			 		</div>
				</div>
			</div>
			
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>所属岗位</label>
				<div class="byy-block">
					
					<select id="postId" name="postId"  class="js-example-basic-multiple"  style="width:60%;line-height: 23px;" multiple="multiple" >
						<option value="">请选择</option>
						<c:forEach items="${items }" var="item" varStatus="index">
							<option value="${item.id }">${item.name }</option>
						</c:forEach>
					</select>	
					<input type="hidden" id="postTip"  name="postTip" style="width:0.2px;"/>
				</div>
			</div>
			<table class="byy-table" id="students"></table>
            <div class="byy-form-item text-center">
                <span class="byy-btn" id="submit">提交</span>
                <span class="byy-btn primary" id="close">关闭</span>
            </div>
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.1.11.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/tree/js/jquery.ztree.all-3.5.min.js"></script>
	<jsp:include page="../../../common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/wdatepicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath %>plugins/select2/js/select2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/user/bureau/list.js"></script>

	<script>
		$(function(){
			$('#postId').select2();
		})
		
	</script>
</body>
</html>