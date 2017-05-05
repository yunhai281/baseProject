<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<title>提示</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<style type="text/css">
	*{
		font-family:"微软雅黑";
	}
	.atable{
		border-collapse:collapse;
		border-color:#999;
	}
	td{padding:5px 10px;}
	warn{color:red;}
	info{color:green;}
	code{
		display:inline-block;
		border-radius:2px;
		background-color: rgb(247, 247, 249);
	    padding: 2px 10px;
	    border-width: 1px;
	    border-style: solid;
	    border-color: #ccc;
	    border-image: initial;
	    font-size:12px;
	    margin-left:5px;
	    margin-right:5px;
	}
	.emptyLine{height:40px;}
	button{padding:2px 10px;cursor:pointer;height:32px;line-height:26px;}
	input{height:25px;line-height:25px;}
</style>
</head>
<body>
<table border="0">
	<tr><th colspan="2" style="font-size:30px;padding-top:10px;padding-bottom:10px;">人员选择框userSelect.jsp</th></tr>
	<tr><td colspan="2">
		<table border="1" class="atable">
			<tr><td colspan="3">路径示例:<code>base + '/roleScope_toUserSelect.do?userType=teacher&callback=teacherRemoveCallback'</code></td></tr>
			<tr><th>参数</th><th>值</th><th>作用</th></tr>
			<tr><td rowspan="5">userType<warn>(必填)</warn></td><td>teacher</td><td>教师选择框</td></tr>
			<tr><td>student</td><td>学生选择框</td></tr>
			<tr><td>parent</td><td>家长选择框</td></tr>
			<tr><td>teacher_student</td><td>用户选择框（学生+教师）</td></tr>
			<tr><td>bureau</td><td>教育局</td></tr> 
			<tr><td>initNames<info>(可选)</info></td><td>父页面已经选中过的input的id</td><td>弹框确认后人员姓名返回至此<code>input</code>（逗号隔开）</td></tr>
			<tr><td>initIds<info>(可选)</info></td><td>父页面已经选中过的input的id</td><td>弹框确认后人员<code>id</code>返回至此<code>input</code>（逗号隔开）</td></tr>
			<tr><td>max<info>(可选)</info></td><td>正整数</td><td>允许选择的最大人数</td></tr>
			<tr><td>min<info>(可选)</info></td><td>正整数</td><td>允许选择的最小人数</td></tr>
			<tr><td>parentName<info>(可选)</info></td><td>父页面className的值</td><td>不传此参数默认接收返回姓名<code>id</code>的<code>iframe</code>为<code>index-frame-target</code>（主页面）多级弹框时请传入需要接收返回值的iframe名</td></tr>
			<tr><td>removeIds<info>(可选)</info></td><td>人员的id逗号隔开</td><td>打开弹框时不可选的人员（复选框失效）</td></tr>
			<tr><td>callback<info>(可选)</info></td><td>js方法名</td><td>执行完之后的回调方法</td></tr>
			<tr><td>roleId<info>(可选)</info></td><td>角色id</td><td></td></tr>
		</table>
	</td></tr>
	<tr>
		<td>教师单项选择框</td>
		<td>
		<input type='text' id='teacherNames' />
		<button onclick='teacherSingle()' >选择</button>
		<input type='text' id='teacherIds' />
		</td>
	</tr>
	<tr>
		<td>教师多项选择框</td>
		<td>
		<input type='text' id='teacherNamesMuti' />
		<button onclick='teacherMuti()' >选择</button>
		<input type='text' id='teacherIdsMuti' />
		<input type='text' id='addTeacherIds' placeholder="默认选中的Id（‘,’隔开）" />
		<input type='text' id='removeTeacherIds' placeholder="不可选的Id（‘,’隔开）" />
		</td>
	</tr>
	<tr>
		<td>教师多项选择框(最多选择3个)</td>
		<td>
		<input type='text' id='teacherNamesMuti3' />
		<button onclick='teacherMuti3()' >选择</button>
		<input type='text' id='teacherIdsMuti3' />
		</td>
	</tr>
	<tr>
		<td>教师多项选择框(最少选择2个)</td>
		<td>
		<input type='text' id='teacherNamesMuti2' />
		<button onclick='teacherMuti2()' >选择</button>
		<input type='text' id='teacherIdsMuti2' />
		</td>
	</tr>
	<tr>
		<td>教师多选(已有选中人员)</td>
		<td>
		<input type='text' id='teacherNamesInit' />
		<button onclick='teacherInit()' >选择</button>
		<input type='text' id='teacherIdsInit' />
		</td>
	</tr>
	<tr>
		<td>教师多选(不能选中人员)</td>
		<td>教士5  540fed39-633f-41b5-a181-527d71e94d73
		<input type='text' id='teacherNamesRemove' />
		<button onclick='teacherRemove()' >选择</button>
		<input type='text' id='teacherIdsRemove' />
		</td>
	</tr>
	<tr style='color:red' >
		<td>学生单项选择框</td>
		<td>
		<input type='text' id='studentNames' />
		<button onclick='studentSingle()' >选择</button>
		<input type='text' id='studentIds' />
		</td>
	</tr> 
	<tr style='color:blue' >
		<td>家长单项选择框</td>
		<td>
		<input type='text' id='parentNames' />
		<button onclick='parentSingle()' >选择</button>
		<input type='text' id='parentIds' />
		</td>
	</tr> 
</table>
</body>
<script type="text/javascript"  src="<%=basePath%>plugins/jquery/jquery.1.11.3.min.js"></script>
<script type="text/javascript"  src="<%=basePath%>plugins/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath %>plugins/validator/jquery.validator.js"></script>
<script type="text/javascript" src="<%=basePath %>plugins/jquery/jquery.plugin.js"></script>
<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
<script type="text/javascript">
function teacherSingle(){
	parent.byy.win.open({
		type : 2,
		title : "选择用户",
		frameName : "byy-byywin",
		name : "selectuser",
		shade : .6,
		area : ['900px','580px'],
		content : base + '/roleScope_toUserSelect.do?userType=teacher&callback=teacherSingleCallback&max=1',
		className : "masterWindow"
	});
}
function teacherSingleCallback (ids,names) {
	$('#teacherIds').val(ids);
	$('#teacherNames').val(names);
}
function teacherMuti(){
	parent.byy.win.open({
		type : 2,
		title : "选择用户",
		frameName : "byy-byywin",
		name : "selectuser",
		shade : .6,
		area : ['900px','580px'],
		content : base + '/roleScope_toUserSelect.do?userType=teacher&callback=teacherMutiCallback',
		className : "masterWindow"
	});
}
function teacherMutiCallback (ids,names) {
	$('#teacherIdsMuti').val(ids);
	$('#teacherNamesMuti').val(names);
}

function teacherMuti3(){
	parent.byy.win.open({
		type : 2,
		title : "选择用户",
		frameName : "byy-byywin",
		name : "selectuser",
		shade : .6,
		area : ['900px','580px'],
		content : base + '/roleScope_toUserSelect.do?userType=teacher&callback=teacherMuti3Callback&max=3',
		className : "masterWindow"
	});
}
function teacherMuti3Callback (ids,names) {
	$('#teacherIdsMuti3').val(ids);
	$('#teacherNamesMuti3').val(names);
}
function teacherMuti2(){
	parent.byy.win.open({
		type : 2,
		title : "选择用户",
		frameName : "byy-byywin",
		name : "selectuser",
		shade : .6,
		area : ['900px','580px'],
		content : base + '/roleScope_toUserSelect.do?userType=teacher&callback=teacherMuti2Callback&min=2',
		className : "masterWindow"
	});
}
function teacherMuti2Callback (ids,names) {
	$('#teacherIdsMuti2').val(ids);
	$('#teacherNamesMuti2').val(names);
}
function teacherInit(){
	var initIds= $('#teacherIdsInit').val();
	var initNames= $('#teacherNamesInit').val();
	parent.byy.win.open({
		type : 2,
		title : "选择用户",
		frameName : "byy-byywin",
		name : "selectuser",
		shade : .6,
		area : ['900px','580px'],
		content : base + '/roleScope_toUserSelect.do?userType=teacher&callback=teacherInitCallback&initIds='+initIds+'&initNames='+encodeURI(encodeURI(initNames)),
		className : "masterWindow"
	});
}
function teacherInitCallback (ids,names) {
	$('#teacherIdsInit').val(ids);
	$('#teacherNamesInit').val(names);
}


function teacherRemove(){  
var removeId='540fed39-633f-41b5-a181-527d71e94d73';
	parent.byy.win.open({
		type : 2,
		title : "选择用户",
		frameName : "byy-byywin",
		name : "selectuser",
		shade : .6,
		area : ['900px','580px'],
		content : base + '/roleScope_toUserSelect.do?userType=teacher&callback=teacherRemoveCallback&removeIds='+removeId,
		className : "masterWindow"
	});
}
function teacherRemoveCallback (ids,names) {
	$('#teacherIdsRemove').val(ids);
	$('#teacherNamesRemove').val(names);
}

function studentSingle(){
	parent.byy.win.open({
		type : 2,
		title : "选择用户",
		frameName : "byy-byywin",
		name : "selectuser",
		shade : .6,
		area : ['900px','580px'],
		content : base + '/roleScope_toUserSelect.do?userType=student&callback=studentSingleCallback&max=1',
		className : "masterWindow"
	});
}
function studentSingleCallback (ids,names) {
	$('#studentIds').val(ids);
	$('#studentNames').val(names);
}
function studentMuti(){
	parent.byy.win.open({
		type : 2,
		title : "选择用户",
		frameName : "byy-byywin",
		name : "selectuser",
		shade : .6,
		area : ['900px','580px'],
		content : base + '/roleScope_toUserSelect.do?userType=student&callback=studentMutiCallback',
		className : "masterWindow"
	});
}
function studentMutiCallback (ids,names) {
	$('#studentIdsMuti').val(ids);
	$('#studentNamesMuti').val(names);
}

function studentMuti3(){
	parent.byy.win.open({
		type : 2,
		title : "选择用户",
		frameName : "byy-byywin",
		name : "selectuser",
		shade : .6,
		area : ['900px','580px'],
		content : base + '/roleScope_toUserSelect.do?userType=student&callback=studentMuti3Callback&max=3',
		className : "masterWindow"
	});
}
function studentMuti3Callback (ids,names) {
	$('#studentIdsMuti3').val(ids);
	$('#studentNamesMuti3').val(names);
}
function studentMuti2(){
	parent.byy.win.open({
		type : 2,
		title : "选择用户",
		frameName : "byy-byywin",
		name : "selectuser",
		shade : .6,
		area : ['900px','580px'],
		content : base + '/roleScope_toUserSelect.do?userType=student&callback=studentMuti2Callback&min=2',
		className : "masterWindow"
	});
}
function studentMuti2Callback (ids,names) {
	$('#studentIdsMuti2').val(ids);
	$('#studentNamesMuti2').val(names);
}

function parentSingle(){
	parent.byy.win.open({
		type : 2,
		title : "选择用户",
		frameName : "byy-byywin",
		name : "selectuser",
		shade : .6,
		area : ['900px','580px'],
		content : base + '/roleScope_toUserSelect.do?userType=parent&callback=parentSingleCallback&max=1',
		className : "masterWindow"
	});
}
function parentSingleCallback (ids,names) {
	$('#parentIds').val(ids);
	$('#parentNames').val(names);
}
</script>
</html>