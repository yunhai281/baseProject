<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.boyuyun.base.util.consts.SysParamKey"%>
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
	<link rel="stylesheet" href="<%=basePath%>plugins/byy/css/webuploader.css">
</head>
<body>
	<div class="container-fluid" > 
	   	<div class="row breadcrumb">
	   		<span class="byy-breadcrumb" separator=">">
	   			<a>系统管理</a>
	   			<a><cite>系统设置</cite></a>
	   		</span>
	   	</div>		
		<form action="" class="byy-form">
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>系统名称</label>
				<div class="byy-block">
					<input type="text" name="<%=SysParamKey.SYS_NAME%>" style="width:45%;" placeholder="请输入系统名称"/>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>版权信息</label>
				<div class="byy-block">
					<input type="text" name="<%=SysParamKey.COPY_RIGHT_INFO%>" style="width:45%;" placeholder="请输入版权信息"/>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">联系电话</label>
				<div class="byy-block">
					<input type="text" name="<%=SysParamKey.TEL%>" style="width:45%;" placeholder="请输入联系电话"/>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">ICP信息</label>
				<div class="byy-block">
					<input type="text" name="<%=SysParamKey.ICP%>" style="width:45%;" placeholder="请输入ICP信息"/>
				</div>
			</div> 			
            <div class="byy-form-item"><label for="" class="byy-label">系统logo</label> 
                <div class="byy-inline">
	                <input type="hidden" id="picPath" name="<%=SysParamKey.PICPATH%>"/>
					<div class=" uploader-list" id="uploadimagefileDiv" >
		                <div class="excel-row" id="" style="border: 1px solid #25c2b7;"> 
		                    <img id="IconThum" src="<%=basePath %>plugins/ueditor/dialogs/attachment/images/image.png" width="227" height="23" >
		                </div>
		                <div>
			                <h4 style="font-weight:100;font-size:14px;">
			                	建议上传图片尺寸227*23
			                </h4>
		             	</div>	
					</div>
				</div>
				    
				<div class="byy-inline"><div class="" style="padding-left:30px;" id="uploadimageDiv">上传按钮</div></div>
            </div>
			<div class="byy-form-item">
				<div class="byy-inline text-center" style="width:60%;">
		            <span class="byy-btn" id="submit">保存</span>
	            </div>
	        </div>
		</form>
	</div>
	<script type="text/javascript"  src="<%=basePath %>plugins/jquery/jquery.1.11.3.min.js"></script>
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.plugin.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/sys/param/param.js"></script>
</body>
</html>