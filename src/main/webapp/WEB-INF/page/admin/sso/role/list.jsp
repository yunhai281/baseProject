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
				<a>统一登录</a>
                <a><cite>角色管理</cite></a>
            </span>
		</div>
		<div class="row">
			<div class="col-xs-3" style="padding-left: 5px;">
				<div class="byy-form" style="padding:0px;display: block;" >
					 <div class="input-pane mini-search-bar"  style="padding:0px" >
	                    <input type="text" name="name" class="name" placeholder="请输入角色名称" /><label  class="btn_search fa fa-search" ></label>
	                </div>
				</div>
				<ul id="menutree" class="ztree" style="margin-left:-10px;"></ul>
			</div>
			<div class="col-xs-9">
				<div class="col-lg-9 col-sm-9" style="border-left:1px solid #eee;">
					<form action="" class="byy-form" id="mainRightForm">
					 	<div class="root">
							<div class="byy-form-item">
								<label for="" class="byy-label">名称</label>
								<div class="byy-block">
									<div class="form-detail" name="name" style="width:75%;"></div>
								</div>
							</div>
					    </div> 
						<div class="byy-form-item">
							<label for="" class="byy-label">描述</label>
							<div class="byy-block">
								<div class="form-detail" name="remark" style="width:75%;height: inherit;min-height: 38px;"></div>
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
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/sso/role/role.js"></script>
</body>
</html>
