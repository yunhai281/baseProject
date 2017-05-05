<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.boyuyun.com.cn" prefix="byy"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title>新增教师</title>
<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
<link rel="stylesheet" href="<%=basePath%>plugins/byy/css/byy.css">
<link rel="stylesheet" href="<%=basePath%>resources/css/common/common.css">
<link rel="stylesheet" href="<%=basePath%>plugins/byy/css/webuploader.css">
	<style type="text/css">
		.byy-form .byy-form-item {
			clear: none;
		}
		
		div.byy-form-item {
			float: left;
			width: 45%;
		}
		
		div.byy-block {
			width: 100%;
		} 
		select[multiple] {
		    height: auto;
		    display: inherit;
		    width: 60%;
		    border-radius: 0px; 
		}
		.icon-school-add {
	        background-image: url(../resources/images/spritesheet.png);
		    background-repeat: no-repeat;
		    display: block;
		    width: 16px;
		    height: 16px;
		    background-position: -31px -57px;
	        cursor: pointer;
		    position: absolute;
		    top: 11px;
		    right: 150px;
		    color: #c8c8c8;
		    font-size: 17px;
		}
	</style>
</head>
<body>
	<div class="container-fluid" style="overflow: hidden;">
		<form action="" class="byy-form">
		 	<div class="row tipdiv">
				<span><b>温馨提示:</b></span><br>
				<span>
					<span>为提高数据导入的准确性，请认真查看以下提示：</span><br>
					<span>1.用户名、姓名、所属部门、岗位列为必填项，且用户名不能重复；</span><br> 
				</span>  
			</div>
			<div class="byy-form-item">
				<div class="text-center">
					<h3>1.点击模版下载</h3>
				</div>
				<div class="text-center" style="margin-top: 10px;">
					<h3><a href="<%=basePath %>/bureau_exportTemplateExcel.do" title="点击下载模版文件" target="_blank" style="color: #25c2b7;font-size: 20px;">模版</a></h3>
				</div>
			</div>
			<div class="byy-form-item">
				<div class="text-center">
					<h3>2.选择文件上传</h3>
				</div>
				<input type="hidden" name="filename" />
				<input type="hidden" name="filePath" />
				<div class="text-center" id="uploadimageDiv">
					<%-- <img class="uploadimage" title="点击选择文件上传"  style="cursor:pointer;" src="<%=basePath %>plugins/ueditor/dialogs/attachment/images/image.png" alt="" /> --%>
				</div>
				<div class="text-center" id="uploadimagefileDiv" style="display:none;" >
					<img class="uploadimage"  style="cursor:pointer;" src="<%=basePath %>plugins/ueditor/dialogs/attachment/images/excel.png" alt="" />
				</div>
				<div class="text-center" style=" padding:0px;" id="delDiv"> 
					<span class="importName" > </span><br/>
					<span class="byy-btn mini text-center importdel" > 删除文件</span>
				</div>
			</div>
			
			<div class="byy-form-item text-center" style="clear: both;width: 100%;" >
				<span class="byy-btn importsubmit" id="importsubmit">提交</span> <span class="byy-btn primary importcancel" id="close">关闭</span>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.1.11.3.min.js"></script>
	<jsp:include page="../../../common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/wdatepicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/user/bureau/list.js"></script>
</body>
</html>