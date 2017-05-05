<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
	<title>新增机构</title>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
</head>
<body>
	<div class="container-fluid">
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>名称</label>
				<div class="byy-block">
					<input type="text" name="name" style="width:75%;" placeholder="请输入机构名称" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>简称</label>
				<div class="byy-block">
					<input type="text" name="shortName" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><a href="http://www.nacao.org.cn/publish/main/index.html" target="_blank">机构号码</a></label>
				<div class="byy-block">
					<input type="text" name="code" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>机构等级</label>
				<div class="byy-block">
					<select name="levelType" id="type" class="byy-form-select" style="width:75%;">
						<option value="">请选择...</option>
                        <option value="1">部</option>
						<option value="2">厅</option>
						<option value="3">局</option>
						<option value="4">科室</option>
					</select>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>所属地域</label>
				<div class="byy-block areadiv">
					<select name="areadiv-province" id="" class="byy-form-select province" style="width:25%;" msg="请选择省份" ><option value="">请选择省份</option></select>
					<span></span><!-- 这个标签没任何意义，只是用来隔开select，否则不会正常显示 -->
					<select name="areadiv-city" id="" class="byy-form-select" style="width:25%;" msg="请选择市"><option value="">请选择市</option></select>
					<span></span><!-- 这个标签没任何意义，只是用来隔开select，否则不会正常显示 -->
					<select name="areadivdistrict" id="" class="byy-form-select" style="width:25%;" msg="请选择县(区)" ><option value="">请选择县(区)</option></select>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">上级机构</label>
				<div class="byy-block">
					<input type="hidden" name="parentId" />
					<input type="text" name="parent.name" readonly style="width:75%;" />
				</div>
			</div>
            <div class="byy-form-item">
				<label for="" class="byy-label">排序</label>
				<div class="byy-block">
					<input type="text" name="seq" style="width:75%;" />
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
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/org/government/list.js"></script>
</body>
</html>