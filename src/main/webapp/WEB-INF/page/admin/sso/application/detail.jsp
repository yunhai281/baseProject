<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>新增应用</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
</head>
<body>
	<div class="container-fluid">
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>应用名称:</label>
				<div class="byy-block">
					<div class="form-detail" name="name" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">应用类型:</label>
				<div class="byy-block">
					<div class="form-detail" name="applicationType" style="width:75%;"></div>
				</div>
			</div>			
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>URL模式:</label>
				<div class="byy-block">
					<div class="form-detail" name="serviceid" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>URL:</label>
				<div class="byy-block">
					<div class="form-detail" name="url" style="width:75%;"></div>
				</div>
			</div>			
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>厂商:</label>
				<div class="byy-block">
					<div class="form-detail" name="manufacturer" style="width:75%;"></div>
				</div>
			</div>			
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>描述:</label>
				<div class="byy-block">
					<div class="form-detail" name="description" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>启用:</label>
				<div class="byy-block">
					<div class="form-detail" name="enabled" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>是否打开新窗口:</label>
				<div class="byy-block">
					<div class="form-detail" name="wicketed" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">图标:</label>
				<div class="byy-inline">
					<input type="hidden" name="filePath" />
					<div class=" uploader-list" id="uploadimagefileDiv" style="padding-left:40px;" >
		                <div class="excel-row" id=""> 
		                    <img id="IconThum" src="#" width="80" height="80" />
		                </div>							
					</div>
				</div>
			</div>	
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls"></span>属性:</label>
				<div class="byy-block">
					<div class="form-detail" name="attributes" style="width:75%;"></div>
				</div>
			</div>					
           <div class="byy-form-item">
                <label for="" class="byy-label">次序:</label>
                <div class="byy-block">
                    <div class="form-detail" name="evaluation_order" style="width:75%;"></div>
                </div>
            </div>
		</form>
	</div>
	<jsp:include page="../../../common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/sso/application/list.js"></script>	
</body>
</html>