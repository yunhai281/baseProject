<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.boyuyun.com.cn" prefix="byy"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">

<style>

svg {
  font: 10px sans-serif;
}

.y.axis path {
  display: none;
}

.y.axis line {
  stroke: #fff;
  stroke-opacity: .2;
  shape-rendering: crispEdges;
}

.y.axis .zero line {
  stroke: #000;
  stroke-opacity: 1;
}

.title {
  font: 300 78px Helvetica Neue;
  fill: #666;
}

.birthyear,
.age {
  text-anchor: middle;
}

.birthyear {
  fill: #fff;
}

rect {
  fill-opacity: .6;
  fill: #e377c2;
}

rect:first-child {
  fill: #1f77b4;
}

</style>
<!-- <style>
body {
  font: 10px sans-serif;
}
.y.axisRight text {
    fill: orange;
}
.y.axisLeft text {
    fill: steelblue;
}
.axis path,
.axis line {
  fill: none;
  stroke: #000;
  shape-rendering: crispEdges;
}
.bar1 {
  fill: steelblue;
}
.bar2 {
  fill: orange;
}
.x.axis path {
  display: none;
}
</style>-->
<body>
<div id="bar1"></div>
<div id="bar2"></div>
<script type="text/javascript" src="<%=basePath%>resources/js/admin/graphic/d3/d3.v3.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/admin/graphic/d3/view.js"></script>
<script>
</script>
</body>
</html>

