<!--教师选择弹出框 -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<title>选择学校</title>
	<meta charset="utf-8">
	<meta name="contentPath" content="<%=basePath %>" />
	<link rel="stylesheet" type="text/css" href="<%=basePath %>plugins/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath %>plugins/tree/css/metroStyle/metroStyle.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath %>resources/css/common/defaultForm.css" />
	<!--[if lt IE 9]>
	    <script src="<%=basePath%>plugins/html5shiv/html5shiv.js"></script>
	    <script src="<%=basePath%>themes/admin/js/respond.min.js"></script>
	<![endif]-->
	<style>
		html,body,.container-fluid{
			background-color:#fff;
			padding : 0px;
			margin : 0px;
			margin-left:0px;
			overflow:hidden;
		}
		.bannerr{
			background-color: #23bc87;
			*background-color:#2b5d82 !important;
		}
		.bannerr .banner-title{
			color :black;
			font-size:18px;
			margin-top:10px;
			line-height:40px;
			margin-left:10px;
		}
		.content{
			margin-left:1px;
			margin-right:1px;
			padding-top:0px;
		}
		.contentdiv{
			height:25px;
			line-height:25px;
			display:inline-block;
			margin-left:10px;
			margin-top:5px;
			padding-left:10px;
			padding-right:10px;
			cursor:pointer;
		}
		.hast,.notc{
			color:gray;
			
		}
		.footer{
			height:40px;
			position:absolute;
			right:2px;
			bottom:2px;
		}
		.row{
			*margin-left:none;
		}
		#personnelTab{
			*display:inline-block;
			*clear:right;
		}
		.mlist-lfmenu{
			margin-top:5px;
			width:95%;
			border:1px solid #e2e6e5;
		}
		.lfmenu-nav{
			height:30px;
			border-bottom:1px solid #e2e6e5;
			background:#f5f5f5;	
		}
		.lfmenu-nav ul{
			height:26px;
			float:left;
			margin-top:12px;
			padding-left:12px;
		}
		.lfmenu-nav ul li{
			height:26px;
			width:29px;
			float:left;
			display:inline-block;	
		}
		.lfmenu-navico{
			background: url(./images/mlist-menu.png) no-repeat scroll 0% 0%;
			height:26px;
			width:26px;
			display:block;
			margin:0px auto;
			cursor:pointer;
		}
		.lfico1{background-position:-232px 0px;}
		.lfico1-hover{background-position:-232px -29px;}
		.lfico1-disable{background-position:-232px -58px;}
		
		.lfico2{background-position:-87px 0px;}
		.lfico2-hover{background-position:-87px -29px;}
		.lfico2-disable{background-position:-87px -58px;}
		
		.lfico3{background-position:-58px 0px;}
		.lfico3-hover{background-position:-58px -29px;}
		.lfico3-disable{background-position:-58px -58px;}
		
		.lfico4{background-position:-319px 0px;}
		.lfico4-hover{background-position:-319px -29px;}
		.lfico4-disable{background-position:-319px -58px;}
		
		.lfico5{background-position:-348px 0px;}
		.lfico5-hover{background-position:-348px -29px;}
		.lfico5-disable{background-position:-348px -58px;}
		
		.lfico6{background-position:-377px 0px;}
		.lfico6-hover{background-position:-377px -29px;}
		.lfico6-disable{background-position:-377px -58px;}
		
		.lfico7{background-position:-406px 0px;}
		.lfico7-hover{background-position:-406px -29px;}
		.lfico7-disable{background-position:-406px -58px;}
		
		.lfico8{background-position:-435px 0px;}
		.lfico8-hover{background-position:-435px -29px;}
		.lfico8-disable{background-position:-435px -58px;}
		
		
		.sxjg{
			height:90%;
			width:95%;
			margin-left:10px;
			margin-top:5px;
		}
		.mlist-rtmenu{
			margin-top:5px;
			width:100%;
			border:1px solid #e2e6e5;
		}
		.mlist-rtmenu-searchbox{
			width:95%;
			margin:0px auto;
			margin-top:10px;
			height:36px;
		}
		.mlist-rtmenu-searchbox .text{
			width:82%;
			height:34px;
			float:left;
			display:inline-block;
			border-bottom:1px solid #e2e6e5;
			border-left:1px solid #e2e6e5;
			border-top:1px solid #e2e6e5;
			border-right:0px;
		}
		.mlist-rtmenu-searchbox .button{
			background-color:#f4f8f7;
			width:18%;
			height:34px;
			float:left;
			display:inline-block;
			border:1px solid #e2e6e5;
			color:#25ca92;
			font-size:14px;
			font-family : 微软雅黑;
		}
		.radiochose{
			width:95%;
			margin:0px auto;
			margin-top:10px;
			height:30px;
		}
		.radiochose div{
			line-height:30px;
			font-size:12px;
			color:#999999;
		}
		.radiobtn{
			height:15px;
			width:15px;
			float:left;
			margin-right:8px;
			margin-top:8px;
			display:inline-block;
		}
		.list_table{
			width:97%;
			border:1px #dcdcdc solid;
			margin:0 auto;
		}
		
		.list_table th,.list_table td{
			height:18px;
			line-height:18px;
			font-size:12px;
			border-bottom:1px #dcdcdc solid;
			border-right:1px #dcdcdc solid;
			text-align:center;
		}
		.list_table th{	
			font-weight: normal;	
			font-family:"宋体";
			
		}
		.list_table td{
			font-family:"微软雅黑";
		}
		.list_table tr.line_g th{
			background: url(<%=basePath%>plugins/themes/default/img/thbg.png) repeat-x bottom;
			border-bottom:1px #dcdcdc solid;
			border-top:1px #dcdcdc solid;
		}
		.line_gchange{
			background:#f9f9f9;
		}
		.ico_nocheck{
			height:inherit;
			background:url(<%=basePath%>plugins/themes/default/img/ico_checkbox.png) no-repeat left center;
			display:block;
			float:right;
			margin-right:10px;
			padding-left:25px;
			text-decoration:none;
			color:#000;
			margin: 0;
		}
		.ico_checked{
			height:inherit;
			background:url(<%=basePath%>plugins/themes/default/img/ico_checked.png) no-repeat left center;
			display:block;
			float:right;
			margin-right:10px;
			padding-left:25px;
			text-decoration:none;
			color:#000;
			margin: 0;
		}
		.ts_group_checked{
			background-color:#25ca92;
			display:block;
			float:left;
			margin-left:1px;
			width:86px;
			padding-left:1px;
			border:1px #25ca92 solid;
			cursor:pointer;
			text-align: center;
		}
		.ts_group_unchecked{
			background-color:#eee;
			display:block;
			float:left;
			margin-left:1px;
			width:86px;
			padding-left:1px;
			border:1px #ccc solid;
			cursor:pointer;
			text-align: center;
		}
		.selectedTeacher{
			list-style:none;
			width:30%;
			margin-top:8px;
			margin-left:6px;
			border:1px red solid;
			float:left;
			display: block;
			text-align: center;
			vertical-align: center;
			cursor: pointer;
			display:block;
			text-align:center;
			height:35px;
			line-height:35px;
			border:1px #e8e8e8 solid;
			border-radius:3px;
			-moz-border-radius:3px;
			-webkit-border-radius:3px;
			float:left;
			cursor: pointer;
			text-decoration: none;
			border:1px #1bd9aa solid;
			background:url(<%=basePath%>plugins/themes/default/img/role_in.jpg) no-repeat right bottom;
		}
		.userTypeBox {
			display: inline-block;
			margin-top:5px;
			padding-left:5px;
			padding-right:0px;
			cursor:pointer
		}
		.userTypeRight{
			width:5px;
			display: inline-block;
			border-right:1px #dcdcdc solid;
		}
		.userTypeBoxSelected{
			color:#1bdbaa
		}
		.userTypeBoxUnSelected{
			color:#555555
		}
	</style>
	
<body style="overflow: hidden;">
	<div class="container-fluid noFixedHeaderFlag">
		<div class="row content" style="clear:left;">
			<!--box3-->
            <div class="box4"  >
              	<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 " style="padding-left:15px;float: left;">
                  <div class="mlist-lfmenu"  style="overflow:auto;height:475px;" >
                     <div class="lfmenu-nav" >
                     	<c:choose>
                     		<c:when test="${param.selectType eq 'government'}">
	                     		<div class="userTypeBox userTypeBoxSelected" stype="government" url="/government_getList.do" >
		                     	<img src="<%=basePath%>resources/images/ztree-icons/ting.png" height="20" style="margin-top:-4px;">
		                     	<span>教育局</span>
		                     	</div>
                     		</c:when>
	                     	<c:when test="${param.selectType eq 'school'}">
	                     		<div class="userTypeBox userTypeBoxSelected" stype="school" url="school_getList.do" >
		                     	<img src="<%=basePath%>resources/images/ztree-icons/school-icon.gif" height="20" style="margin-top:-4px;" >
		                     	<span>学校</span>
		                     	</div>
                     		</c:when>
                     		<c:otherwise>
                     			<div class="userTypeBox userTypeBoxSelected" stype="government" url="/government_getList.do" >
			                     	<img src="<%=basePath%>resources/images/ztree-icons/ting.png" height="20" style="margin-top:-4px;">
			                     	<span>教育局</span>
			                     	<span class='userTypeRight'>&nbsp;</span>
		                     	</div>
	                     		<div class="userTypeBox userTypeBoxUnSelected" stype="school" url="/school_getList.do" >
			                     	<img src="<%=basePath%>resources/images/ztree-icons/school-icon.gif" height="20" style="margin-top:-4px;" >
			                     	<span>学校</span>
			                     	<span class='userTypeRight'></span>
		                     	</div>
                     		</c:otherwise>
                     	</c:choose>
                     </div>
                     <!--lfmenu-nav-->
                     <div class=" secondmenu" >
                     	<div class="sxjg ztree" id="group_tree_div" style="height:420px;overflow:auto;" ></div>
                     </div>
                  </div>
               </div>
               <div class="col-xs-6 col-sm-6 col-md-6 col-lg-6 " style="padding-left:0px;float: left;">
                  <div class="mlist-rtmenu" style="overflow:auto;height:475px;" >
                      <div class="lfmenu-nav" >
                     	<div id="hasschool" style="font-size:14px;padding-top:5px;margin-left:10px;" >已选择</div>
                     </div>
					 <div style="width:95%;margin:0 auto;margin-top:5px;height:420px;overflow:auto;"  >
					 <ul id="selectedBox" style="width:100%;padding-left:0px;padding-right:0px;" >
					 </ul>
					 </div>
                  </div>
               </div>
            </div>
            <!--box4-->
		</div>
		<div class="row footer text-right">
			<input type="button" class="btn btn-default" style="margin-right:50px;"  id="okbtn" name="okbtn" value="确认" />
		</div>
	<script type="text/javascript"  src="<%=basePath %>plugins/jquery/jquery.1.11.3.min.js"></script>
	<script type="text/javascript"  src="<%=basePath %>plugins/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript" src="<%=basePath %>plugins/jquery/jquery.plugin.js"></script>
	<script type="text/javascript" src="<%=basePath%>plugins/tree/js/jquery.ztree.all-3.5.min.js"></script>
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script src="<%=basePath %>plugins/byy/byy.js"></script> 
	<script src="<%=basePath %>resources/js/common/org_school_select.js"></script> 
	<script type="text/javascript">
	var iptName = '${param.name}';//赋值的input的name或id
	var iptId = '${param.id}';//复制的input的name或id
	var parentName = '${param.parentName}';//来源的framename
	var min = null == '${param.min}' || '' == '${param.min}' ? 0 : parseInt('${param.min}',10);//选择的最小数量，默认0
	var max = null == '${param.max}' || '' == '${param.max}' ? -1 : parseInt('${param.max}',10);//选择的最大数量，默认-1，最大值不做限制
	var callback = '${param.callback}';//选中后的回调函数，根据parentName进行调用
	var num = '${param.num}';//允许选择的数量
	var initIds = '${param.initIds}';//初始化已设置的id
	var initNames = decodeURI('${param.initNames}');//初始化已设置的name
	var removeId = byy.getSearch('removeIds');//不能设置的id
	var roleId = '${param.roleId}';
	var selectType = '${param.selectType}';//选择框类型：学校或者机构
	var cfg = {
		iptName : iptName,
		iptId : iptId,
		parentName : parentName,
		min : min,
		max : max,
		callback : callback,
		initIds : initIds,
		initNames : initNames,
		removeId : removeId,
		num : num,
		roleId :roleId,
		selectType:selectType
	};
	byy.require(['jquery','win','table','page'],function(){
		byyorgschool(cfg).start();	
	});
	</script>
</body>
</html>