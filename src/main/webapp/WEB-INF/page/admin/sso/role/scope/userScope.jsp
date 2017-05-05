<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.boyuyun.base.util.consts.UserType" %>
<%@taglib uri="http://www.boyuyun.com.cn" prefix="byy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>添加应用</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath%>plugins/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>plugins/tree/css/metroStyle/metroStyle.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/bootstrap-select/bootstrap-select.min.css"/>
	<style type="text/css">
	.hr-more {
	    height: 25px;
	    width:100px;
	    position: relative; 
	    left: 1%;
	    top: -45px;
	    vertical-align: middle;
	    text-align: center;
	    border-radius: 4px;
	    background-color: #25c2b7; 
	}
	.byy-label {
	    position: relative;
	    float: left;
	    display: block;
	    padding: 9px 15px;
	    width: 114px;
	    font-weight: normal;
	    line-height: 20px;
	    text-align: right;
	}
	</style>
</head>
<body>
	<div class="container-fluid">
		<form action="" class="byy-form">
			<input type="hidden" name="addOrUpdate" value="add"/>
			<input type="hidden" name="roleId" value="${param.roleId}"/>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>设置方式</label>
				<div class="byy-inline">
                    <input type="radio" class="byy-form-radio" name="userScopeType" value="type" title="人员类型" checked=true>
                </div>
                <div class="byy-inline">
                    <input type="radio" class="byy-form-radio" name="userScopeType" value="person" title="指定人员">
                </div>
			</div>
			<div id="userScopeType-type">
				<div class="byy-form-item" id="userType">
					<label for="" class="byy-label"><span class="requirecls"></span>用户身份</label>
					<!-- 
					<div class="byy-block">
						<input type="text" name="name" style="width:75%;" placeholder="请输入应用名称" />
					</div>
					 -->
					 <div class="byy-inline">
	                    <input type="checkbox" class="byy-form-checkbox" name="userTypeAll" value="all" title="全部">
	                </div>
	                <!-- 获取用户身份类别信息 -->
					<%for (UserType item : UserType.values()){if(item.toString().equals("学校管理员")||item.toString().equals("系统管理员")) continue;%>
	                	<div class="byy-inline">
		                    <input type="checkbox" class="byy-form-checkbox" name="userType<%=item.ordinal()%>" value="<%=item.ordinal()%>" title="<%=item%>" onclick="javascript:alert(1);">
		                </div>
		            <%} %>
				</div>
				<div class="byy-form-item" id="userOrg">
					<label for="" class="byy-label"><span class="requirecls"></span>用户机构</label>
					<div class="byy-inline">
	                    <input type="checkbox" class="byy-form-checkbox" name="userOrgAll" value="all" title="全部">
	                </div>
	                <div class="byy-inline">
	                    <input type="checkbox" class="byy-form-checkbox" name="userOrgSchool" value="school" title="学校">
	                </div>
	                <div class="byy-inline">
	                    <input type="checkbox" class="byy-form-checkbox" name="userOrgBureau" value="government" title="教育局">
	                </div>
				</div>
				<div class="byy-form-item" style="display:none;" id="schoolDiv">
					<label for="" class="byy-label"><span class="requirecls"></span>学校</label>
	                <div class="byy-block">
						<input type="hidden" name="schoolIds"/>
						<input type="text" name="schoolNames" style="width:75%;" orgtype="school"/>
					</div>
				</div>
				<div class="byy-form-item" style="display: none;" id="governmentDiv">
					<label for="" class="byy-label"><span class="requirecls"></span>教育局</label>
	                <div class="byy-block">
						<input type="hidden" name="governmentIds"/>
						<input type="text" name="governmentNames" style="width:75%;" orgtype="government"/>
					</div>
				</div>
			</div>
			<div id="userScopeType-person">
				<!-- 获取用户身份类别信息 -->
				<%for (UserType item : UserType.values()){if(item.toString().equals("学校管理员")||item.toString().equals("系统管理员")) continue;%>
                	<div class="byy-form-item">
						<label for="" class="byy-label"><span class="requirecls"></span><%=item%></label>
						<div class="byy-block" style="margin-left:0px;">
							<input type="hidden" name="userIds<%=item.ordinal()%>" userType="<%=item.ordinal()%>"/>
							<input type="text" name="userNames<%=item.ordinal()%>" style="width: 70%;padding-right: 10px;" placeholder="请选择<%=item%>" readonly />
							<i class="icon icon-school-add selectUser" style="cursor: pointer;" userType="<%=item.ordinal()%>"></i>
						</div>
					</div>
	            <%} %>
			</div>
            <div class="byy-form-item text-center">
                <span class="byy-btn" id="submit">提交</span>
                <span class="byy-btn primary" id="close">关闭</span>
            </div>
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.1.11.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/tree/js/jquery.ztree.all-3.5.min.js"></script>
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/sso/role/scope/userScope.js"></script>
</body>
</html>