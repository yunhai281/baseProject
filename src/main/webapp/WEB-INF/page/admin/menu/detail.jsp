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
				<label for="" class="byy-label"><span class="requirecls">*</span>应用名称</label>
				<div class="byy-block">
					<div class="form-detail" name="name" style="width:75%;"></div>
					<!-- <input type="text" name="name" style="width:75%;" placeholder="请输入应用名称" /> -->
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>应用编号</label>
				<div class="byy-block">
					<div class="form-detail" name="code" style="width:75%;"></div>
					<!-- <input type="text" name="code" style="width:75%;" /> -->
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>应用图标</label>
				<div class="byy-block">
					<!-- <input type="text" name="icon" style="width:75%;" /> -->
					<div class="form-detail" name="icon" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>开发者</label>
				<div class="byy-block">
					<!-- <input type="text" name="developer" style="width:75%;" /> -->
					<div class="form-detail" name="developer" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
                <label for="" class="byy-label">是否免费</label>
                <div class="byy-block">
                    <span>
                        <input type="radio" class="byy-form-radio" name="free" title="是" value="true"/>
                    </span>
                    <span>
                        <input type="radio" class="byy-form-radio" name="free" title="否" value="false">
                    </span>
                </div>
            </div>
           <div class="byy-form-item">
                <label for="" class="byy-label">是否支持适用</label>
                <div class="byy-block">
                    <span>
                        <input type="radio" class="byy-form-radio" name="trial" title="是" value="true">    
                    </span>
                    <span>
                        <input type="radio" class="byy-form-radio" name="trial" title="否" value="false">    
                    </span>
                </div>
            </div>
            <div class="byy-form-item">
                <label for="" class="byy-label">是否应用列表显示</label>
                <div class="byy-block">
                    <span>
                        <input type="radio" class="byy-form-radio" name="enable" title="是" value="true">    
                    </span>
                    <span>
                        <input type="radio" class="byy-form-radio" name="enable" title="否" value="false">    
                    </span>
                </div>
            </div>
            <div class="byy-form-item">
				<label for="" class="byy-label">当前版本</label>
				<div class="byy-block">
					<!-- <input type="text" name="version" style="width:75%;" /> -->
					<div class="form-detail" name="version" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">发布时间</label>
				<div class="byy-block">
					<!-- <input type="text" id="releaseTime" style="width:75%;" readonly="readonly" name="releaseTime" placeholder="点击选择发布时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d}'})" /> -->
					<div class="form-detail" name="releaseTime" style="width:75%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">所属分类</label>
				<div class="byy-block">
					<div class="form-detail" name="type" style="width:75%;"></div>
					<%-- <select name="type" id="type" class="byy-form-select" style="width:75%;">
                           <option value="">请选择...</option>
						<%for(AppType appType : AppType.values()){ %>
							<option value="<%=appType%>"><%=appType%></option>
						<%} %>
                       </select> --%>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">应用介绍</label>
                <div class="byy-block">
	                <div class="show-no">
	                    <!-- <textarea name="description" style="width:75%;" placeholder="请输入..."></textarea> -->
	                    <div class="form-detail" name="description" style="width:75%;"></div>    
	                </div>
             	</div>
            </div>
		</form>
	</div>
</body>
</html>