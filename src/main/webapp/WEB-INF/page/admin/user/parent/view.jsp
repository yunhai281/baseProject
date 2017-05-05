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
<title>新增家长</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath%>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath%>resources/css/common/common.css">
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
	.select2 {
		margin-right:5px;
	}
	.select2-container--default .select2-selection--single{
		border-radius: 2px;
		border: 1px solid rgb(230, 230, 230);
	}
	.select2-container .select2-selection--single {
		height:38px;
		line-height:38px;
	}
	.select2-container--classic .select2-selection--single:focus{
		border-color: #c9c9c9 !important
	}
	#studentDiv{
		margin-top:20px;
	}
	.byy-blockSelect{
		position: relative;
	} 
</style>
</head>
<body>
	<script>
		var relationIds = [];
		var relationNames = [];
	</script>		
					
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
				<label for="" class="byy-label">Email</label>
				<div class="byy-block">
					<input type="text" name="email" style="width: 60%;" placeholder="请输入邮箱" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">手机号</label>
				<div class="byy-block">
					<input type="text" name="mobile" style="width: 60%;" placeholder="请输入手机号,例如:135XXXXXXXX" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">性别</label>
				<div class="byy-block">
					<byy:dic dicCode="Gender" cssClass="byy-form-select" id="sex" name="sex" style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">工作单位</label>
				<div class="byy-block">
					<input type="text" name="work" style="width: 60%;" placeholder="例如:东华博育云有限公司" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">学历</label>
				<div class="byy-block">
					<byy:dic dicCode="Education" cssClass="byy-form-select" id="education" name="education" style="width: 60%;" />
				</div>
			</div>			
			<div id="studentDivs" style="float:left;width:100%;" >
				<div id="row0">
					<div class="byy-form-item" style="width: 17%;padding-right: 5px;"> 
						<div class="byy-blockSelect">
							<input type="hidden" name="schoolId" />
							<input type="text" name="schoolName"  placeholder="请选择所在学校" readonly />
							<i class="icon icon-school-add selectSchool" style="cursor: pointer;right: 7px;"></i>
						</div>
					</div>
					<div class="byy-form-item" style="width: 14%;padding-right: 5px;"> 
					
						<div class="byy-blockSelect">
							<select name="stage" id="stage" class="byy-form-select" >
								<option value="">请选择学段</option>
							</select>
							
						</div>
					</div>
					<div class="byy-form-item" style="width: 14%;padding-right: 5px;"> 
						<div class="byy-blockSelect">
							<select name="grade" id="grade" class="byy-form-select">
								<option value="">请选择年级</option> 
							</select>
						</div>
					</div>
					<div class="byy-form-item" style="width: 14%;padding-right: 5px;"> 
						<div class="byy-blockSelect" >
							<select name="classes" id="classes" class="byy-form-select" >
								<option value="">请选择班级</option> 
							</select>
						</div>
					</div>
					<div class="byy-form-item" style="width: 14%;padding-right: 5px;"> 
						<div class="byy-blockSelect">
							<select name="studentId"  class="byy-form-select" >
								<option value="">请选择学生</option> 
							</select>
						</div>
					</div>
					<div class="byy-form-item" style="width: 14%;padding-right: 5px;"> 
						<div class="byy-block" style="position: relative;margin-left: 0px;">
							<byy:dic dicCode="Relation" cssClass="byy-form-select" name="relation" />
						</div>
					</div>
					<div class="byy-form-item" style="width: 7%;"> 
						<div class="byy-blockSelect handlerRow">
							<span class="byy-btn mini add_student"  style="margin:8px 0px;"><i class="fa fa-plus"></i>添加</span>	
							 
						</div>
					</div>
				</div>
				<%-- <div id="studentDiv" class="col-sm-12 col-lg-12 col-md-12 col-xs-12 ">
					<div class="col-sm-2" style="height:40px;">
						<input type="hidden" name="schoolId" id="schoolId"/>
						<input type="text" name="schoolName" id="schoolName" style="" placeholder="请选择所在学校" readonly />
						<i class="icon icon-school-add selectSchool" style="left:110px;cursor: pointer;"></i>
					</div>
					<div class="col-sm-2">
						<select style="width:107px;display:block;margin-left:13px;"> 
							<option selected>小学</option>
							<option>初中</option>
							<option>高中</option>
						</select>
					</div>
					<div class="col-sm-2">
						<select style="width:120px;display:block" id="grade" name="grade"> 
							<option value="">请选择年级</option>
						</select>
					</div>
					<div class="col-sm-2">
						<select style="width:120px;display:block"  id="classes" name="classes"> 
							<option value="">请选择班级</option>
						</select>
					</div>
					<div class="col-sm-2">
						<select class="js-example-basic-single" id="student" name="student" style="width:120px;line-height: 23px; display:block"> 
							<option value="">请选择学生</option>
						</select>
					</div>
					<div class="col-sm-1" style="width:80px;">
						<select id="parentRelation" name="parentRelation" style="width:80px;display:block" >
							<option value="">请选择</option>
							<c:forEach items="${relations }" var="item" varStatus="index">
								<script>
									relationIds.push('${item.id }');
									relationNames.push('${item.name }');
								</script>
								<option value="${item.id }">${item.name }</option>
							</c:forEach>
						</select>	
					</div>
					<div class="col-sm-1" style="width:40px;margin-left:10px;">
						<span class="byy-btn mini add_student"  style="margin:8px 0px;"><i class="fa fa-plus"></i>添加</span>					
					</div>
				</div> --%>
			</div>
			<div  class="col-sm-12 col-lg-12 col-md-12 col-xs-12 col-sm-offset-5" style="margin-top:50px;">
	                <span class="byy-btn" id="submit">提交</span>
	                <span class="byy-btn primary" id="close">关闭</span>
			</div>
           
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.1.11.3.min.js"></script>
	<jsp:include page="../../../common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/wdatepicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath %>plugins/select2/js/select2.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>plugins/select2/js/i18n/zh-CN.js"></script>
	
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/user/parent/list.js"></script>
	
	<script>
	</script>
</body>
</html>