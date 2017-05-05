<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新增节点</title>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">  
  </head>
  
<body>
	<div class="container-fluid">
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<input type="hidden" name="levelType" value="<%=request.getParameter("levelType") %>">
		<% String levelType = request.getParameter("levelType");
		if(levelType == "2" || levelType.equals("2")){
		 %>	
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>字典项名称</label>
				<div class="byy-block">
					<input type="text" name="name" style="width:75%;" placeholder="请输入字典表名称" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>编码</label>
				<div class="byy-block">
					<input type="text" name="code" style="width:75%;" />
				</div>
			</div>
            <div class="byy-form-item">
                <label for="" class="byy-label"><span class="requirecls">*</span>是否可编辑</label>
                <div class="byy-block">
                    <sqan><input type="radio" class="byy-form-radio" name="editable" value="true" title="是" checked="true"></sqan>
                    <sqan><input type="radio" class="byy-form-radio" name="editable" value="false" title="否"></sqan>
                </div>
            </div>			
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>是否允许学校自定义</label>
				<div class="byy-block">
                    <sqan><input type="radio" class="byy-form-radio" name="schooldiy" value="true" title="是"></sqan>
                   <sqan><input type="radio" class="byy-form-radio" name="schooldiy" value="false" title="否" checked="true"></sqan>
				</div>
			</div>			
            <div class="byy-form-item">
                <label for="" class="byy-label">描述信息</label>
                <div class="byy-block">
                    <div class="show-no">
                        <textarea name="remark" placeholder="请输入..." style="width:75%;"></textarea>    
                        <span class="no" style="width:33%;">0/200</span>
                    </div>
                </div>
            </div>			
		<%}else if(levelType == "3" || levelType.equals("3")){
		%>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>字典值名称</label>
				<div class="byy-block">
					<input type="text" name="name" style="width:75%;" placeholder="请输入字典值名称" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>编码值</label>
				<div class="byy-block">
					<input type="text" name="value" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>数字值</label>
				<div class="byy-block">
					<input type="text" name="num" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>序号</label>
				<div class="byy-block">
					<input type="text" name="sortNum" style="width:75%;" />
				</div>
			</div>
            <div class="byy-form-item">
                <label for="" class="byy-label">描述信息</label>
                <div class="byy-block">
                    <div class="show-no">
                        <textarea name="remark" placeholder="请输入..." style="width:75%;"></textarea>    
                        <span class="no" style="width:33%;">0/200</span>
                    </div>
                </div>
            </div>			
			<input type="hidden" name="dictionaryId" />
			<input type="hidden" name="dictionaryName" readonly style="width:75%;" />
			
		<% } %>				
            <div class="byy-form-item text-center">
                <span class="byy-btn" id="submit">提交</span>
                <span class="byy-btn primary" id="close">关闭</span>
            </div>
		</form>
	</div>
	<script type="text/javascript"  src="<%=basePath %>plugins/jquery/jquery.1.11.3.min.js"></script>
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.plugin.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/sys/dictionary/list.js"></script>
</body>
</html>
