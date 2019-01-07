<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link type="text/css" rel="stylesheet" href="css/common.css" />
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet"
	href="css/bootstrap-select.min.css" />
<!-- 先加载jq之后才加载js,否则报错(bootstrap) -->
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<style type="text/css">
.details {
	margin-top: 20px;
	height:580px;
}
.pre-scrollable{
	height:580px;
}
.name {
	width: 100px;
	text-align: right;
	font-size: 15px;
	font-weight: bolder;
	padding-right: 5px;
}

table tr td {
	border: 1px solid #D6D6D6;
	text-align: center;
	padding-top: 5px;
	padding-bottom: 5px;
}

.img {
	text-align: center;
	padding-top: 5px;
	padding-bottom: 5px;
}
</style>
</head>
<body>
	<div id="container">
		<ul class="nav nav-pills">
			<li class="active"><a href="init.action">首页</a></li>
			<li><a href="knowledge.html">知识科普</a></li>
			<li><a href="IndicationsInit.action">处方信息</a></li>
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#">合理用药规则库 <span class="caret"></span></a>
				<ul class="dropdown-menu">
					
					<li><a href="initInteraction.action">相互作用</a></li>
					<li><a href="initSpecial.action">特殊人群用药</a></li>
					
					
				</ul></li>
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#">合理用药知识库 <span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="getInstruction.action">说明书</a></li>
					<li><a href="getLiterature.action">文献知识库</a></li>
					<li><a href="initDiseaseCure.action">疾病诊治标准</a></li>
					<li><a href="indicationsmodel.html">标准处方模板</a></li>
					<li><a href="advice.html">标准医嘱模板</a></li>
				</ul></li>
			<li><a href="#">系统设置</a></li>
		</ul>
		<div class="details">
			<div class="img" style="font-size:16px;font-weight: bolder;">
				${SRD.getCm().getMcName()}
			</div>
			<div class="column pre-scrollable">
			<table>

				<tr>
					<td class="name">标准来源</td>
					<td width="784px">${SRD.getSrType().getBookname()}</td>
				</tr>
				<tr>
					<td class="name">书页号</td>
					<td>${SRD.getSpagenumber()}</td>
				</tr>
				<tr>
					<td class="name">标准编号</td>
					<td>${SRD.getStandardencde()}</td>
				</tr>
				<tr>
					<td class="name">处方</td>
					<td>${SRD.getSprescription()}</td>
				</tr>
				<tr>
					<td class="name">制法</td>
					<td>${SRD.getMake()}</td>
				</tr>
				<tr>
					<td class="name">性状</td>
					<td>${SRD.getCm().getmTraits()}</td>
				</tr>
				<tr>
					<td class="name">鉴别</td>
					<td>${SRD.getIdentify()}</td>
				</tr>
				<tr>
					<td class="name">检查</td>
					<td>${SRD.getScheck()}</td>
				</tr>
			</table>
			</div>
		</div>

	</div>

</body>
</html>