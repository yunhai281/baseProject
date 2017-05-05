<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>字典管理</title>
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
				<a>基础信息管理</a>
                <a><cite>字典管理</cite></a>
            </span>
		</div>
		<div class="row">
			<div class="col-xs-3" style="padding-left: 5px;">
				<div class="byy-form" style="padding:0px" >
					<div class="input-pane mini-search-bar"  style="padding:0px" >
						<input type="text" name="name" class="name" placeholder="字典项名称/编码"/><label  class="btn_search fa fa-search" ></label>
	                </div>
				</div>
				<ul id="dictionary" class="ztree" style="margin-left:-10px;"></ul>
			</div>
			<div class="col-xs-9">
				<div class="col-lg-9 col-sm-9" style="border-left:1px solid #eee;">
					<form action="" class="byy-form">
					 <div class="root">
						<div class="byy-form-item">
							<label for="" class="byy-label">名称</label>
							<div class="byy-block">
								<div class="form-detail" name="rootname" style="width:75%;"></div>
							</div>
						</div>
					 </div>
					 <div class="dic">	
						<div class="byy-form-item">
							<label for="" class="byy-label">字典项名称</label>
							<div class="byy-block">
								<div class="form-detail" name="name" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">编码值</label>
							<div class="byy-block">
								<div class="form-detail" name="code" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">描述信息</label>
							<div class="byy-block">
								<div class="form-detail" name="remark" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">是否可编辑</label>
							<div class="byy-block">
								<div class="form-detail" name="editable" formatter="main.formatterTrue" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">是否允许学校自定义</label>
							<div class="byy-block">
								<div class="form-detail" name="schooldiy" formatter="main.formatterTrue" style="width:75%;"></div>
							</div>
						</div>
					</div>
					 <div class="dicI">	
						<div class="byy-form-item">
							<label for="" class="byy-label">字典值名称</label>
							<div class="byy-block">
								<div class="form-detail" name="name" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">编码值</label>
							<div class="byy-block">
								<div class="form-detail" name="value" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">数字值</label>
							<div class="byy-block">
								<div class="form-detail" name="num" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">序号</label>
							<div class="byy-block">
								<div class="form-detail" name="sortNum" style="width:75%;"></div>
							</div>
						</div>
						<div class="byy-form-item">
							<label for="" class="byy-label">描述信息</label>
							<div class="byy-block">
								<div class="form-detail" name="remark" style="width:75%;"></div>
							</div>
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
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/sys/dictionary/list.js"></script>
</body>
</html>
