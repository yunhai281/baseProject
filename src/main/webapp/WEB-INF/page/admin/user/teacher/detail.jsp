<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>查看教师</title>
<jsp:include page="/WEB-INF/page/common/meta.jsp"></jsp:include>
<link rel="stylesheet" href="<%=basePath%>plugins/byy/css/byy.css">
<link rel="stylesheet" href="<%=basePath%>resources/css/common/common.css">
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
</style>
</head>
<body>
	<div class="container-fluid" style="overflow: hidden;">
		<form action="" class="byy-form">
			<input type="hidden" name="id" />
			<div class="byy-form-item">
				<label for="" class="byy-label">教师工号</label>
				<div class="byy-block">
					<div class="form-detail" name="teacherNo" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">手机号</label>
				<div class="byy-block">
					<div class="form-detail" name="mobile" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">邮箱</label>
				<div class="byy-block">
					<div class="form-detail" name="email" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">岗位</label>
				<div class="byy-block">
					<div class="form-detail" name="postTypeName" style="width: 50%"></div>
				</div>
			</div>

			<div class="byy-form-item">
				<label for="" class="byy-label">学科</label>
				<div class="byy-block">
					<div class="form-detail" name="courseListDiv" id="courseListDiv" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">任职状态</label>
				<div class="byy-block">
					<div class="form-detail" name="statusName" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">在职状态</label>
				<div class="byy-block">
					<div class="form-detail" name="workStatusName" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">编制类型</label>
				<div class="byy-block">
					<div class="form-detail" name="compileTypeName" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">所在学校</label>
				<div class="byy-block">
					<div class="form-detail" name="schoolName" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">入职本校时间</label>
				<div class="byy-block">
					<div class="form-detail" name="hireDate" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">骨干教师</label>
				<div class="byy-block">
					<div class="form-detail" name="skeletonTypeName" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>用户名</label>
				<div class="byy-block">
					<div class="form-detail" name="userName" style="width: 50%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>真实姓名</label>
				<div class="byy-block">
					<div class="form-detail" name="realName" style="width: 50%;"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">性别</label>
				<div class="byy-block">
					<div class="form-detail" name="sexName" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">出生年月</label>
				<div class="byy-block">
					<div class="form-detail" name="birthday" id="birthday" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">婚姻状况</label>
				<div class="byy-block">
					<div class="form-detail" name="maritalStatusName" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">职称</label>
				<div class="byy-block">
					<div class="form-detail" name="positionalTitleName" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">毕业时间</label>
				<div class="byy-block">
					<div class="form-detail" name="graduateDate" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">毕业院校</label>
				<div class="byy-block">
					<div class="form-detail" name="graduateSchool" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">学位</label>
				<div class="byy-block">
					<div class="form-detail" name="dgreeName" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">学历</label>
				<div class="byy-block">
					<div class="form-detail" name="educationName" style="width: 50%"></div>
				</div>
			</div>

			<div class="byy-form-item">
				<label for="" class="byy-label">专业</label>
				<div class="byy-block">
					<div class="form-detail" name="profession" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">学历证号</label>
				<div class="byy-block">
					<div class="form-detail" name="educationNo" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">学位证号</label>
				<div class="byy-block">
					<div class="form-detail" name="dgreeNo" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">教师编制</label>
				<div class="byy-block">
					<div class="form-detail" name="hasCompile" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">教师资格证</label>
				<div class="byy-block">
					<div class="form-detail" name="hasTeacherCertificate" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">教师资格证号</label>
				<div class="byy-block">
					<div class="form-detail" name="teacherCertificateNo" style="width: 50%"></div>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">参加工作时间</label>
				<div class="byy-block">
					<div class="form-detail" name="startWorkDate" style="width: 50%"></div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>