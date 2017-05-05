<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.boyuyun.com.cn" prefix="byy"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>新增学生</title>
	<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
	<link rel="stylesheet" href="<%=basePath %>plugins/byy/css/byy.css">
	<link rel="stylesheet" href="<%=basePath %>resources/css/common/common.css">
<style type="text/css">
	.byy-form .byy-form-item{
	 clear: none;
	}
	div.byy-form-item{
		float: left;
		width: 45%;
	}
	div.byy-block {
	width: 100%;
	}
	.icon-school-add {
	        background-image: url("<%=basePath%>resources/images/spritesheet.png");
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
			<input type="hidden" name="id" />
			<div class="byy-form-item">
				<label for="" class="byy-label">学号</label>
				<div class="byy-block">
					<input type="text" name="studentNo" style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>密码</label>
				<div class="byy-block">
					<input type="password" name="pwd" style="width: 60%;" placeholder="请输入密码" autocomplete="new-password" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">邮箱</label>
				<div class="byy-block">
					<input type="text" name="email" style="width: 60%;" placeholder="请输入邮箱" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">手机号</label>
				<div class="byy-block">
					<input type="text" name="mobile" style="width: 60%;" placeholder="请输入手机号" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>用户名</label>
				<div class="byy-block">
					<input type="text" name="userName" style="width: 60%;margin-right: 0px;" placeholder="请输入用户名" /><span class="serialNumber" style="line-height:38px;"></span>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>真实姓名</label>
				<div class="byy-block">
					<input type="text" name="realName" style="width: 60%;" placeholder="请输入真实姓名" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">学籍号</label>
				<div class="byy-block">
					<input type="text" name="xueJiNo" style="width: 60%;" placeholder="请输入学籍号" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">所在学校</label>
				<div class="byy-block">
					<input type="hidden" name="schoolId" id="schoolId"/>
					<input type="text" name="schoolName" id="schoolName" style="width: 60%;padding-right: 40px;" placeholder="请选择所在学校" readonly />
					<i class="icon icon-school-add selectSchool" style="cursor: pointer;"></i>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">学段</label>
				<div class="byy-block">
					<select name="stage" id="stage" class="byy-form-select" style="width:60%;">
						<!-- <option value="">请选择...</option> -->
					</select>
					
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">年级</label>
				<div class="byy-block">
					<select name="belongGradeId" id="belongGradeId" class="byy-form-select" style="width:60%;">
						<!-- <option value="">请选择...</option>  -->
					</select>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">班级</label>
				<div class="byy-block">
					<select name="belongClassId" id="belongClassId" class="byy-form-select" style="width:60%;">
						<!-- <option value="">请选择...</option>  -->
					</select>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">性别</label>
				<div class="byy-block">
				<byy:dic dicCode="Gender" cssClass="byy-form-select" id="sex" name="sex" style="width: 60%;"/>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">出生年月</label>
				<div class="byy-block">
					<input type="text" id="birthday" style="width: 60%;" readonly="readonly" name="birthday" placeholder="点击选择生日"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">学习状态</label>
				<div class="byy-block">
					<byy:dic dicCode="StudentStatus" cssClass="byy-form-select" id="status" name="status" style="width: 60%;"/>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">入学方式</label>
				<div class="byy-block">
					<byy:dic dicCode="EntryType" cssClass="byy-form-select" id="entryType" name="entryType" style="width: 60%;"/>
				</div>
			</div>
			
			<div class="byy-form-item">
				<label for="" class="byy-label">就读方式</label>
				<div class="byy-block">
					<byy:dic dicCode="StudyType" cssClass="byy-form-select" id="studyType" name="studyType" style="width: 60%;"/>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">入学日期</label>
				<div class="byy-block">
					<input type="text" id="entryDate" style="width: 60%;" readonly="readonly" name="entryDate"
						placeholder="点击选择入学日期" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">离校日期</label>
				<div class="byy-block">
					<input type="text" id="leavingDate" style="width: 60%;" readonly="readonly" name="leavingDate"
						placeholder="点击选择离校时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">入学成绩</label>
				<div class="byy-block">
					<input type="text" name="entryScore" style="width: 60%;" placeholder="请输入入学成绩" />
				</div>
			</div>
            <div class="byy-form-item text-center" style="width:100%;">
                <span class="byy-btn" id="submit">提交</span>
                <span class="byy-btn primary" id="close">关闭</span>
            </div>
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.1.11.3.min.js"></script>
	<jsp:include page="../../../common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/wdatepicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/user/student/list.js"></script>
</body>
</html>