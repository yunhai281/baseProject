<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>地域管理</title>
    
    <link rel="stylesheet" type="text/css" href="<%=basePath %>plugins/tree/css/metroStyle/metroStyle.css" />
  </head>
  
  <body>
	<div class="container-fluid">
		<div class="row breadcrumb">
			<span class="byy-breadcrumb" separator=">">
				<a>基础信息管理</a>
                <a><cite>地域管理</cite></a>
            </span>
		</div>
		<div class="row">
		
			<div class="col-xs-3" style="padding-left:5px;">
				<ul id="areatree" class="ztree" style="margin-left:-10px;"></ul>
			</div>
			<div class="col-xs-9" style="border-left:1px solid #eee;">
				<form class="byy-form">
					<div class="byy-form-item">
						<label class="byy-label">名称</label>
						<div class="byy-block">
							<div class="form-detail" name="name"></div>
						</div>
					</div>
					<div class="byy-form-item">
						<label class="byy-label">短名称</label>
						<div class="byy-block">
							<div class="form-detail" name="shortName"></div>
						</div>
					</div>
					<div class="byy-form-item">
						<label class="byy-label">完整名称</label>
						<div class="byy-block">
							<div class="form-detail" name="mergerName"></div>
						</div>
					</div>
					<div class="byy-form-item">
						<label class="byy-label">区域等级</label>
						<div class="byy-block">
							<div class="form-detail" name="levelType"></div>
						</div>
					</div>
					<div class="byy-form-item">
						<label class="byy-label">区号</label>
						<div class="byy-block">
							<div class="form-detail" name="code"></div>
						</div>
					</div>
					<div class="byy-form-item">
						<label class="byy-label">邮编</label>
						<div class="byy-block">
							<div class="form-detail" name="zip"></div>
						</div>
					</div>
					<div class="byy-form-item">
						<label class="byy-label">是否可用</label>
						<div class="byy-block">
							<div class="form-detail" name="available"></div>
							
						</div>
					</div>
					<div class="byy-form-item">
						<label class="byy-label">经度</label>
						<div class="byy-block">
							<div class="form-detail" name="longitude"></div>
						</div>
					</div>
					<div class="byy-form-item">
						<label class="byy-label">纬度</label>
						<div class="byy-block">
							<div class="form-detail" name="dimension"></div>
						</div>
					</div>
					<div class="byy-form-item">
						<label class="byy-label">拼音</label>
						<div class="byy-block">
							<div class="form-detail" name="pinyin"></div>
						</div>
					</div>
					<div class="byy-form-item">
						<label class="byy-label">首字母</label>
						<div class="byy-block">
							<div class="form-detail" name="firstLetter"></div>
						</div>
					</div>
					<div class="byy-form-item">
						<label class="byy-label">上级</label>
						<div class="byy-block">
							<div class="form-detail" name="parentName"></div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/tree/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>resources/js/admin/sys/area/area.js"></script>   
  </body>
</html>