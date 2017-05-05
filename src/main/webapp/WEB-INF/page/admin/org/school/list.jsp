<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
	<title>学校管理</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>plugins/tree/css/metroStyle/metroStyle.css" />
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/treeEditor.css">
	<style type="text/css">
		.name{
		    font-size: 12px;
		}
	</style>
</head>

<body>
	<div class="container-fluid">
		<div class="row breadcrumb">
			<span class="byy-breadcrumb" separator=">">
				<a>机构管理</a>
                <a><cite>学校信息</cite></a>
            </span>
            <span class="byy-btn primary small list_import pull-right" style="background-color:#3fbcb4;color:#fff;margin-right: 30px;"><i class="fa fa-sign-in"></i>导入</span>
		</div>
		<div class="row">
			<div class="col-xs-3" style="padding-left: 5px;">
				<div class="byy-form" style="padding:0px;display: block;" >
					 <div class="input-pane mini-search-bar"  style="padding:0px" >
	                    <input type="text" name="name" class="name" placeholder="请输入学校名称" /><label  class="btn_search fa fa-search" ></label>
	                </div>
				</div>
				<ul id="schooldiv" class="ztree" style="margin-left:-10px;"></ul>
			</div>
			<div class="col-xs-9">
				<div  style="border-left:1px solid #eee;">
					<form action="" class="byy-form" id="mainRightForm">
						<div class="byy-form-item">
							<label for="" class="byy-label">名称</label>
							<div class="byy-block">
								<div class="form-detail" name="name" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">学校代码</label>
							<div class="byy-block">
								<div class="form-detail" name="code" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">标识</label>
							<div class="byy-block">
								<div class="form-detail" name="serialNumber" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">建校日期</label>
							<div class="byy-block">
								<div class="form-detail" name="buildDate" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">所属机构</label>
							<div class="byy-block">
								<div class="form-detail" name="governmentName" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">学校类型</label>
							<div class="byy-block">
								<div class="form-detail" name="systemTypeName" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">寄宿类型</label>
							<div class="byy-block">
								<div class="form-detail" name="schoolBoardTypeName" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">驻地类型</label>
							<div class="byy-block">
								<div class="form-detail" name="schoolStationName" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">办学类型</label>
							<div class="byy-block">
								<div class="form-detail" name="schoolTypeName" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">所在地区</label>
							<div class="byy-block">
								<div class="form-detail" name="areaName" style="width:75%;"></div>
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
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/org/school/list.js"></script>
</body>
</html>
