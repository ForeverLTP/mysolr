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
	height:625px;
}
.pre-scrollable{
	height:625px;
}
.name {
	width: 150px;
	text-align: right;
	font-size: 15px;
	font-weight: bolder;
	padding-right: 5px;
}
table{
	height:355px;
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
			
			
			<div class="column pre-scrollable">
			<div class="img">
				<img src="images/cm/${CM.getmId()}.jpg">
			</div>
			<table>

				<tr>
					<td class="name">药品名称</td>
					<td width="759px;">${CM.getMcName()}</td>
				</tr>
				<tr>
					<td class="name">拼音名</td>
					<td>${CM.getMpName()}</td>
				</tr>
				<tr>
					<td class="name">剂型</td>
					<td>${CM.getmFormulation()}</td>
				</tr>
				<tr>
					<td class="name">组成成分</td>
					<td>${CM.getmIngredient()}</td>
				</tr>
				<tr>
					<td class="name">性状</td>
					<td>${CM.getmTraits()}</td>
				</tr>
				<tr>
					<td class="name">药品类型</td>
					<td>${CM.getTname()}</td>
				</tr>
				<tr>
					<td class="name">功能主治</td>
					<td>${CM.getmIndications()}</td>
				</tr>
				<tr>
					<td class="name">批准文号</td>
					<td>${CM.getApprovalnumber()}</td>
				</tr>
				<tr>
					<td class="name">规格</td>
					<td>${CM.getmSpecification()}</td>
				</tr>
				<tr>
					<td class="name">用法用量</td>
					<td>${CM.getmDosage()}</td>
				</tr>
				<tr>
					<td class="name">不良反应</td>
					<td>${CM.getmNegativereactions()}</td>
				</tr>
				<tr>
					<td class="name">禁忌</td>
					<td>${CM.getmBan()}</td>
				</tr>
				<tr>
					<td class="name">注意事项</td>
					<td>${CM.getmNotice()}</td>
				</tr>
				<tr>
					<td class="name">相互作用</td>
					<td>${CM.getmInteraction()}</td>
				</tr>
				<tr>
					<td class="name">贮藏</td>
					<td>${CM.getmStorage()}</td>
				</tr>
				<tr>
					<td class="name">有效日期</td>
					<td>${CM.getmValidity()}</td>
				</tr>
				<tr>
					<td class="name">包装</td>
					<td>${CM.getmPackage()}</td>
				</tr>
				<tr>
					<td class="name">生产企业</td>
					<td>${CM.getManufacturer()}</td>
				</tr>
			</table>
		</div>
		</div>

	</div>

</body>
</html>