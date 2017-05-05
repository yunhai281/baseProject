<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
	<title>选择学校Demo</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
</head>

<body>
	<div class="container-fluid">
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<div class="byy-form-item">
				<label for="" class="byy-label">选择学校ID(IDs)</label>
				<div class="byy-block">
					<input type="text" name="ids" style="width:75%;" placeholder="选择的学校..." />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">学校名称</label>
				<div class="byy-block">
					<input type="text" name="name" style="width:75%;" />
				</div>
			</div>
            <div class="byy-form-item text-center">
                <span class="byy-btn btn_one" id="submit">选择一个学校</span>
                <span class="byy-btn btn_multi" id="close">选择多个学校</span>
            </div>
            <div class="byy-form-item" style="color: #666;">
            	<label for="" class="byy-label">注意</label>
            	<div class="byy-block warn" style="padding-top: 7px;">
            		1.调用界面比带参数：callback（回调函数，用于选择学校后的返回、处理函数）<br>
            		2.参数num：可选参数，限制选择学校数量
            	</div>
            </div>
		</form>
	</div>
</body>
<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
<script type="text/javascript">
	byy.require(['jquery','win'],function(){
		//1.选择一个学校按钮点击事件
		$(".btn_one").delegate("","click",function(){
			parent.byy.win.open({
				type:2,
				title:"选择学校",
				frameName:"index-frame-target",
				shade:.6,
				area:["1200px","600px"],
				content:base+'/school_toSelect.do?callback=cbstr&num=1',//num可以填具体的限制选择学校数字,该参数为选填，不填的时候，默认为选择多个学校
				className:"masterWindow"
		    });
		});
		//1.选择多个学校按钮点击事件
		$(".btn_multi").delegate("","click",function(){
			parent.byy.win.open({
				type:2,
				title:"选择学校",
				frameName:"index-frame-target",
				shade:.6,
				area:["1200px","600px"],
				content:base+'/school_toSelect.do?callback=cbstr',
				className:"masterWindow"
		    });
		});
	});
	//调用选择学校后callback方法
	function cbstr(ids,names){
		$("input[name=ids]").val(ids);//选择的学校ID
		$("input[name=name]").val(names);//选择的学校
	}
</script>
</html>
