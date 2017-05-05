<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.boyuyun.com.cn" prefix="byy" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>添加应用</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
	<link rel="stylesheet" href="<%=basePath%>plugins/byy/css/webuploader.css">
	<style type="text/css">
		.file-list{
		    min-height: 188px;
            border: 3px dashed #e3f3f3;
		}
		.file-list-head{
		    height: 36px;
		    border-bottom: 3px dashed #e3f3f3;
		    line-height: 36px;
          
		}
		.file{
			padding-left: 130px;
		    padding-right:180px;
		}
		select[multiple] {
		    height: 150px;
		    display: inherit;
		    width: 50%;
		    border-radius: 0px; 
		}
	</style>	
</head>
<body>
	<div class="container-fluid">
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>应用名称</label>
				<div class="byy-block">
					<input type="text" name="name" style="width:75%;" placeholder="请输入应用名称" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">应用类型</label>
				<div class="byy-block">
					<input type="text" name="applicationType" style="width:75%;" placeholder="请输入应用类型" />
				</div>
			</div>			
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>URL模式</label>
				<div class="byy-block">
					<input type="text" name="serviceid" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>URL</label>
				<div class="byy-block">
					<input type="text" name="url" style="width:75%;" />
				</div>
			</div>			
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>厂商</label>
				<div class="byy-block">
					<input type="text" name="manufacturer" style="width:75%;" />
				</div>
			</div>			
			<div class="byy-form-item">
				<label for="" class="byy-label">描述</label>
                  <div class="byy-block">
                    <div class="show-no">
                        <textarea name="description" placeholder="请输入..." onkeyup="checkLen(this)" maxlength="200" style="width:75%;"></textarea>    
                        <span class="no" style="width:30%;">0/200</span>
                    </div>
				 </div>
			</div>
            <div class="byy-form-item">
                <label for="" class="byy-label">状态</label>
                <div class="byy-inline">
                    <input type="checkbox" class="byy-form-checkbox" name="enabled" checked="checked" value="true" title="启用">
                </div>    
                <div class="byy-inline">
                    <input type="checkbox" class="byy-form-checkbox" name="wicketed" checked="checked" value="true" title="是否打开新窗口">
                </div>                            
            </div>
			<div class="byy-form-item">
				<label for="" class="byy-label">图标</label>
				<div class="byy-inline">
					<input type="hidden" name="filePath" />
					<div class=" uploader-list" id="uploadimagefileDiv" style="padding-left:40px;" >
		                <div class="excel-row" id="" style="border: 1px solid #ccc;"> 
		                    <img id="IconThum" src="<%=basePath %>/plugins/ueditor/dialogs/attachment/images/image.png" width="80" height="80" >
		                </div>
					</div>
				</div>
				<div class="byy-inline">
					<div>
		                <h4 style="font-weight:100;font-size:14px;">
		                	建议上传<br>图片尺寸<br>80*80
		                </h4>
		             </div>	
		         </div>    
				<div class="byy-inline"><div class="" style="padding-left:30px;" id="uploadimageDiv">上传按钮</div></div>
			</div>  
			<div class="byy-form-item">
				<label for="" class="byy-label">属性</label>
				<div class="byy-block">
				   <select name="attributer" id="attributer" multiple="multiple">
				    	<option value="idNo" selected="selected">idNo</option>
				    	<option value="clientPassword" selected="selected">clientPassword</option>
				    	<option value="clientUserId"  selected="selected">clientUserId</option>
				    	<option value="clientUserName"  selected="selected">clientUserName</option>
				    	<option value="mobile"  selected="selected">mobile</option>
				    	<option value="usertype"  selected="selected">usertype</option>
				    	<option value="governmentId"  selected="selected">governmentId</option>
				   </select>
                   <div style="display: inline-block;"><span>按Ctrl或Command键可多选</span></div>
				</div>
			</div>           
            <div class="byy-form-item">
				<label for="" class="byy-label">次序</label>
				<div class="byy-block">
					<input type="text" name="evaluation_order" style="width:75%;" value="0"/>
				</div>
			</div>
            <div class="byy-form-item text-center">
                <span class="byy-btn" id="submit">提交</span>
                <span class="byy-btn primary" id="close">关闭</span>
            </div>
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.1.11.3.min.js"></script>
	<jsp:include page="../../../common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/sso/application/list.js"></script>
</body>
</html>