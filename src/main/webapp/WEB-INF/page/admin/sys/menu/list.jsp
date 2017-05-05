<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>应用管理</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>plugins/tree/css/metroStyle/metroStyle.css" />
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/treeEditor.css">
</head>
<body>
	<div class="container-fluid">
		<div class="row breadcrumb">
			<span class="byy-breadcrumb" separator=">">
				<a>系统管理</a>
                <a><cite>菜单管理</cite></a>
            </span>
		</div>
		<div class="row">
			<div class="col-xs-3" style="padding-left: 5px;">
				<ul id="menutree" class="ztree" style="margin-left:-10px;"></ul>
			</div>
			<div class="col-xs-9">
				<div class="col-lg-9 col-sm-9" style="border-left:1px solid #eee;">
					<form action="" class="byy-form">
					 	<div class="root">
							<div class="byy-form-item">
								<label for="" class="byy-label">名称</label>
								<div class="byy-block">
									<div class="form-detail" name="name" style="width:75%;"></div>
								</div>
							</div>
					    </div> 
						<div class="byy-form-item">
							<label for="" class="byy-label">排序</label>
							<div class="byy-block">
								<div class="form-detail" name="sortNum" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">连接</label>
							<div class="byy-block">
								<div class="form-detail" name="url" style="width:75%;"></div>
							</div>
						</div> 
						<div class="byy-form-item">
							<label for="" class="byy-label">是否可用</label>
							<div class="byy-block">
								<div class="form-detail" name="available" style="width:75%;"></div>
							</div>
						</div>  
					 					
					</form>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/tree/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/common/treeEditor.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/sys/menu/menu.js"></script>
</body>
</html>
