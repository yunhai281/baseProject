<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.boyuyun.com.cn" prefix="byy"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
	<title>新增机构</title>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>plugins/tree/css/metroStyle/metroStyle.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>plugins/bootstrap-select/bootstrap-select.min.css"/>
</head>
<body>
	<div class="container-fluid">
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<input type="hidden" name="parentId" />
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>名称</label>
				<div class="byy-block">
					<input type="text" name="name" style="width:75%;" placeholder="请输入学校名称" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>简称</label>
				<div class="byy-block">
					<input type="text" name="shortName" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">标识</label>
				<div class="byy-block">
					<input type="text" name="serialNumber" style="width:75%;" placeholder="请输入学校标识,若没有填写则默认学校的简拼或全拼"  />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span><a href="http://www.nacao.org.cn/publish/main/index.html" target="_blank">学校代码</a></label>
				<div class="byy-block">
					<input type="text" name="code" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">所属地域</label>
				<div class="byy-block areadiv">
					<select name="areadiv-province" id="" class="byy-form-select province" style="width:25%;" msg="请选择省份" ><option value="">请选择省份</option></select>
					<span></span><!-- 这个标签没任何意义，只是用来隔开select，否则不会正常显示 -->
					<select name="areadiv-city" id="" class="byy-form-select" style="width:25%;" msg="请选择市"><option value="">请选择市</option></select>
					<span></span><!-- 这个标签没任何意义，只是用来隔开select，否则不会正常显示 -->
					<select name="areadiv-district" id="" class="byy-form-select" style="width:25%;" msg="请选择县(区)" ><option value="">请选择县(区)</option></select>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>所属机构</label>
				<div class="byy-block">
					<input type="hidden" name="governmentId" />
					<input type="text" name="governmentName" readonly style="width:75%;" />
					<ul id="governmentdiv" class="ztree" style="display:none;position:absolute;z-index:9999;background-color:#eee;"></ul>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">驻地类型</label>
				<div class="byy-block">
					<byy:dic dicCode="SchoolStation" cssClass="byy-form-select" id="schoolStation" name="schoolStation" style="width: 75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">办学类型</label>
				<div class="byy-block">
					<byy:dic dicCode="SchoolType" cssClass="byy-form-select" id="schoolType" name="schoolType" style="width: 75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">学校类型</label>
				<div class="byy-block">
					<byy:dic dicCode="SchoolSystemType" cssClass="byy-form-select" id="systemType" name="systemType" style="width: 75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">寄宿类型</label>
				<div class="byy-block">
					<byy:dic dicCode="SchoolBoardType" cssClass="byy-form-select" id="schoolBoardType" name="schoolBoardType" style="width: 75%;" />
				</div>
			</div>
            <div class="byy-form-item">
				<label for="" class="byy-label">校长</label>
				<div class="byy-block">
					<input type="text" name="headmaster" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">建校日期</label>
				<div class="byy-block">
					<input type="text" id="buildDate" style="width:75%;" readonly="readonly" name="buildDate" placeholder="点击选择建校时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">校庆日</label>
				<div class="byy-block">
					<input type="text" id="decorationDay" style="width:75%;" readonly="readonly" name="decorationDay" placeholder="点击选择日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">地址</label>
				<div class="byy-block">
					<input type="text" name="address" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">邮编</label>
				<div class="byy-block">
					<input type="text" name="zip" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">法人登记号</label>
				<div class="byy-block">
					<input type="text" name="legaNo" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">电话</label>
				<div class="byy-block">
					<input type="text" name="tel" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">传真</label>
				<div class="byy-block">
					<input type="text" name="fax" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">邮箱</label>
				<div class="byy-block">
					<input type="text" name="email" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">管理员联系方式</label>
				<div class="byy-block">
					<input type="text" name="adminMobile" style="width:75%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">总校</label>
				<div class="byy-block">
					<input type="text" name="parentName" style="width:75%;" readonly />
					<!-- <select name="parentId" class="byy-form-select" style="width:75%;">
						<option value="">请选择...</option>
					</select> -->
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">简介</label>
                <div class="byy-block">
	                <div class="show-no">
	                    <textarea name="description" style="width:75%;" placeholder="请输入..."></textarea>    
	                </div>
             	</div>
            </div>
            <div class="byy-form-item text-center">
                <span class="byy-btn" id="submit">提交</span>
                <span class="byy-btn primary" id="close">关闭</span>
            </div>
		</form>
	</div>
	<script type="text/javascript"  src="<%=basePath %>plugins/jquery/jquery.1.11.3.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/tree/js/jquery.ztree.all-3.5.min.js"></script>
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/wdatepicker/WdatePicker.js"></script>
	<script src="<%=basePath%>plugins/bootstrap-select/bootstrap-select.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.plugin.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/org/school/list.js"></script>
	<script type="text/javascript">
	$(function(){
		if(typeof window.event == 'undefined') {  
             document.onkeypress = function(e) {  
                var type = e.target.localName.toLowerCase();  
                var code = e.keyCode;  
                if ((code != 8 && code != 13) ||  
                    (type == 'input' && code != 13 ) ||  
                    (type == 'textarea') ||  
                    (type == 'submit' && code == 13)) {  
                    return true;  
                } else {  
                    return false ;  
                }  
            }  
        }  
        })
	</script>
</body>
</html>