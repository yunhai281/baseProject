<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Highcharts Example</title>
	<!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script> -->
	<script type="text/javascript" src="<%=basePath%>plugins/jquery/jquery.1.11.3.min.js"></script>
</head>
<body>
	<!-- 
	<script src="https://code.highcharts.com/highcharts.js"></script>
	<script src="https://code.highcharts.com/highcharts-more.js"></script>
	<script src="https://code.highcharts.com/modules/exporting.js"></script> -->
	<script src="<%=basePath %>plugins/highchart/code/modules/exporting.js"></script>
	<script type="text/javascript" src="<%=basePath%>resources/js/admin/graphic/highchart/view.js"></script>
	
	<a href="https://www.highcharts.com/demo">echarts参考网站</a>
	
	<!-- 柱状图 -->
    <div id="mainbar" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
    <!-- 折线图 -->
    <div id="mainline" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
    <!-- 饼状图 -->
    <div id="mainpie" style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div>
    <!-- 雷达图 -->
    <div id="mainradar" style="min-width: 310px; max-width: 400px; height: 400px; margin: 0 auto"></div>
</body>
</html>
