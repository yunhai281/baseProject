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
				<label for="" class="byy-label"><span class="requirecls">*</span>教师工号</label>
				<div class="byy-block">
					<input type="text" name="teacherNo" style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>手机号</label>
				<div class="byy-block">
					<input type="text" name="mobile" style="width: 60%;" placeholder="请输入手机号" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>邮箱</label>
				<div class="byy-block">
					<input type="text" name="email" style="width: 60%;" placeholder="请输入邮箱" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>密码</label>
				<div class="byy-block">
					<input type="password" name="pwd" style="width: 60%;" placeholder="请输入密码" autocomplete="new-password"/>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>岗位</label>
				<div class="byy-block" id="postTypeDiv">
					<byy:dic dicCode="TeacherPostType" cssClass="byy-form-select" id="postType" name="postType" style="width: 60%;" />
				</div>
			</div>
			
			<div class="byy-form-item">
				<label for="" class="byy-label">学科</label>
				<div class="byy-block">
                   <select name="courseList" id="courseList" multiple="multiple" ></select>
                   <div style="display: inline-block;"><span>按Ctrl或Command键可多选</span></div>
                </div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">任职状态</label>
				<div class="byy-block">
					<byy:dic dicCode="TeacherStatus" cssClass="byy-form-select" id="status" name="status" style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>在职状态</label>
				<div class="byy-block">
					<byy:dic dicCode="WorkStatus" cssClass="byy-form-select" id="workStatus" name="workStatus" style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">编制类型</label>
				<div class="byy-block">
					<byy:dic dicCode="TeacherCompileType" cssClass="byy-form-select" id="compileType" name="compileType"
						style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>所在学校</label>
				<div class="byy-block">
					<input type="hidden" name="schoolId" id="schoolId"/>
					<input type="text" name="schoolName" id="schoolName" style="width: 60%;padding-right: 40px;" placeholder="请选择所在学校" readonly />
					<i class="icon icon-school-add selectSchool" style="cursor: pointer;"></i>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label"><span class="requirecls">*</span>入职本校时间</label>
				<div class="byy-block">
					<input type="text" id="hireDate" style="width: 60%;" readonly="readonly" name="hireDate" placeholder="点击选择入职时间"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">骨干教师</label>
				<div class="byy-block">
					<byy:dic dicCode="SkeletonType" cssClass="byy-form-select" id="skeletonType" name="skeletonType" style="width: 60%;" />
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
				<label for="" class="byy-label"><span class="requirecls">*</span>性别</label>
				<div class="byy-block">
					<byy:dic dicCode="Gender" cssClass="byy-form-select" id="sex" name="sex" style="width: 60%;" />
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
				<label for="" class="byy-label">婚姻状况</label>
				<div class="byy-block">
					<byy:dic dicCode="MaritalStatus" cssClass="byy-form-select" id="maritalStatus" name="maritalStatus"
						style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">职称</label>
				<div class="byy-block">
					<byy:dic dicCode="PositionalTitle" cssClass="byy-form-select" id="positionalTitle" name="positionalTitle" style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">毕业时间</label>
				<div class="byy-block">
					<input type="text" id="graduateDate" style="width: 60%;" readonly="readonly" name="graduateDate"
						placeholder="点击选择毕业时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">毕业院校</label>
				<div class="byy-block">
					<input type="text" name="graduateSchool" style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">学位</label>
				<div class="byy-block">
					<byy:dic dicCode="Dgree" cssClass="byy-form-select" id="dgree" name="dgree" style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">学历</label>
				<div class="byy-block">
					<byy:dic dicCode="Education" cssClass="byy-form-select" id="education" name="education" style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">专业</label>
				<div class="byy-block">
					<input type="text" name="profession" style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">学历证号</label>
				<div class="byy-block">
					<input type="text" name="educationNo" style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">学位证号</label>
				<div class="byy-block">
					<input type="text" name="dgreeNo" style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">教师编制</label>
				<div class="byy-block">
					<span> <input type="radio" class="byy-form-radio" name="hasCompile" title="是" value="true" />
					</span> <span> <input type="radio" class="byy-form-radio" name="hasCompile" title="否" value="false">
					</span>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">教师资格证</label>
				<div class="byy-block">
					<span> <input type="radio" class="byy-form-radio" name="hasTeacherCertificate" title="是" value="true" />
					</span> <span> <input type="radio" class="byy-form-radio" name="hasTeacherCertificate" title="否" value="false">
					</span>
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">教师资格证号</label>
				<div class="byy-block">
					<input type="text" name="teacherCertificateNo" style="width: 60%;" />
				</div>
			</div>
			<div class="byy-form-item">
				<label for="" class="byy-label">参加工作时间</label>
				<div class="byy-block">
					<input type="text" id="startWorkDate" style="width: 60%;" readonly="readonly" name="startWorkDate"
						placeholder="点击选择参加工作时间" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				</div>
			</div>
			<div class="byy-form-item text-center" style="clear: both;width:100%;">
				<span class="byy-btn" id="submit">提交</span> <span class="byy-btn primary" id="close">关闭</span>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.1.11.3.min.js"></script>
	<jsp:include page="../../../common/common.jsp"></jsp:include>
	<script type="text/javascript" src="<%=basePath%>plugins/byy/byy.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/wdatepicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/validator/jquery.validator.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/user/teacher/list.js"></script>
</body>
</html>