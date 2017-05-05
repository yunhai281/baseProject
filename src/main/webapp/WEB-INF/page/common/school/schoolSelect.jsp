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
	<link rel="stylesheet" type="text/css" href="<%=basePath %>plugins/byy/css/byy.css" />
	<link rel="stylesheet" href="<%=basePath %>plugins/themes/default/page.css">
<body style="overflow: hidden;">
	<div class="container-fluid noFixedHeaderFlag">
		<div class="row content" style="clear:left;">
			<!--box3-->
            <div class="box4"  >
               <c:choose>
               	<c:when test="${param.showarea eq 'true' }">
               		<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 " style="padding-left:0px;float: left;">
               	</c:when>
               	<c:otherwise>
               		<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8 " style="padding-left:0px;float: left;">
               	</c:otherwise>
               </c:choose>
                  <div class="mlist-rtmenu" style="overflow:hidden;height: 476px;" >
                     <div class="mlist-rtmenu-searchbox">
                        <input type="text" id="search_keyword" class="text select_str" placeholder="请输入学校名称或编号检索" />
                        <input type="button" class="button select_teachers" value="搜索" />
                     </div>
                     <div class="smallwindow">
                        <!--列表-->
	                     <div class="list_tit" style="padding-top:5px;" ></div>
                            <!--列表数据-->
                            <table class="list_table" border="0" cellspacing="0">
                            	<thead>
    	                         <tr class="line_g" height="33px;">
    	                           <th width="50px"><a id="set_all" class="checkbox ico_nocheck"></a></th>
    	                           <th style="width:500px;">名称</th>
                                   <th style="">学校编号</th>
                                 </tr>
                                </thead>
                                <tbody class="list_table_body">
                                </tbody>
                            </table>
                            <div class="page rows " style="margin-top:0px;margin-left:5px;margin-right:5px;height: 50px;" >
                            	<span class="btn btn-default" id="collect_all" style="margin-top:15px;margin-left:5px;">选中所有记录</span>
				      	 		<ul class="pagination pull-right" style="margin-top:5px;"></ul>
					      	</div>
                        </div>
                     <!--smallwindow-->
                  </div>
                 <!-- mlist-rtmenu-->
               </div>
               <div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 " style="padding-left:0px;float: left;">
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
	<jsp:include page="/WEB-INF/page/common/common.jsp"></jsp:include>
	<script src="<%=basePath %>plugins/byy/byy.js"></script> 
	<script src="<%=basePath %>resources/js/common/school/schoolSelect.js"></script> 
	<script type="text/javascript">
		var iptName = '${param.name}';//赋值的input的name或id
		var iptId = '${param.id}';//复制的input的name或id
		var parentName = '${param.parentName}';//来源的framename
		var min = null == '${param.min}' || '' == '${param.min}' ? 0 : parseInt('${param.min}',10);//选择学校的最小数量，默认0
		var max = null == '${param.max}' || '' == '${param.max}' ? 1 : parseInt('${param.max}',10);//选择学校的最大数量，默认1
		var callback = '${param.callback}';//选中后的回调函数，根据parentName进行调用
		var num = '${param.num}';//允许选择的学校数量
		var addIds = '';
		var cfg = {
			iptName : iptName,
			iptId : iptId,
			parentName : parentName,
			min : min,
			max : max,
			callback : callback,
			addIds : addIds,
			num : num
		};
		byy.require(['jquery','win','table','page'],function(){
			byyschool(cfg).start();	
		});
	</script>
</body>
</html>