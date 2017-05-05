<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
	<title>管理机构</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>plugins/tree/css/metroStyle/metroStyle.css" />
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/treeEditor.css">
	
</head>

<body>
	<div class="container-fluid">
		<div class="row breadcrumb"  >
			<span class="byy-breadcrumb" separator=">">
				<a>机构管理</a>
                <a><cite>机构信息</cite></a>
            </span>
            <span class="byy-btn small list_import pull-right" style="line-height:28px;height:28px;background-color:#3fbcb4;color:#fff;margin-top:2px;margin-right: 30px;"><i class="fa fa-sign-in"></i>导入</span>
		</div>
		<div class="row">
			<div class="col-xs-3" style="padding-left: 5px;">
				<div class="byy-form" style="padding:0px" >
					 <div class="input-pane mini-search-bar"  style="padding:0px" >
	                    <input type="text" name="name" class="name" placeholder="请输入机构名称" /><label  class="btn_search fa fa-search" ></label>
	                </div>
				</div>
				<ul id="governmentdiv" class="ztree" style="margin-left:-10px;"></ul>
			</div>
			<div class="col-xs-9">
				<div style="border-left:1px solid #e6e6e6;">
					<form action="" class="byy-form" id="mainRightForm" >
						<div class="byy-form-item">
							<label for="" class="byy-label">名称</label>
							<div class="byy-block">
								<div class="form-detail" name="name" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">简称</label>
							<div class="byy-block">
								<div class="form-detail" name="shortName" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">机构号码</label>
							<div class="byy-block">
								<div class="form-detail" name="code" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">机构等级</label>
							<div class="byy-block">
								<div class="form-detail" name="levelType" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">所属地域</label>
							<div class="byy-block">
								<div class="form-detail" name="areaName" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">上级机构</label>
							<div class="byy-block">
								<div class="form-detail" name="parentName" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">排序</label>
							<div class="byy-block">
								<div class="form-detail" name="seq" style="width:75%;"></div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/tree/js/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/common/treeEditor.js"></script><!-- 树结构右键绑定事件 -->
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/org/government/list.js"></script>
</body>
</html>
