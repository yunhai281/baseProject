<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>添加菜单</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
</head>
<body>
	<div class="container-fluid"> 
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<input type="hidden" name="parentId" />
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>名称</label>
				<div class="byy-block">
					<input type="text" name="name" style="width:75%;" placeholder="请输菜单名称" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>排序</label>
				<div class="byy-block">
					<input type="text" name="sortNum" style="width:75%;" placeholder="请输菜单编号"/>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">链接</label>
				<div class="byy-block">
					<input type="text" name="url" style="width:75%;" placeholder="请输菜单连接"/>
				</div>
			</div>
            <div class="byy-form-item">
                <label for="" class="byy-label"><span class="requirecls">*</span>是否可用</label>
                <div class="byy-block">
                    <span><input type="radio" class="byy-form-radio" name="available" value="true" title="是" checked="true"></span>
                    <span><input type="radio" class="byy-form-radio" name="available" value="false" title="否"></span>
                </div>
            </div>
			<div class="byy-form-item text-center">
	            <span class="byy-btn" id="submit">提交</span>
	            <span class="byy-btn primary" id="close">关闭</span>
	        </div>
		</form>
	</div>
	<script type="text/javascript"  src="<%=basePath %>plugins/jquery/jquery.1.11.3.min.js"></script>
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.plugin.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/sys/menu/menu.js"></script>
</body>
</html>