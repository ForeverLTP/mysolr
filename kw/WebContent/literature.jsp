<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link type="text/css" rel="stylesheet" href="css/common.css" />
<link type="text/css" rel="stylesheet" href="css/index.css" />
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
<link type="text/css" rel="stylesheet" href="css/font-awesome.min.css" />
<link type="text/css" rel="stylesheet"
	href="css/bootstrap-select.min.css" />
<!-- 先加载jq之后才加载js,否则报错(bootstrap) -->
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<script type="text/javascript" src="js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="js/i18n/defaults-zh_CN.min.js"></script>
<script type="text/javascript">
	$(function() {
		
		function setTableTH() {
			$(".data")
					.append(
							"<tr>"
									+ "<th style=\"width: 56px; text-align: center\">序号</th>"
									+ "<th style=\"width: 852px; text-align: center\">参考文献</th></tr>");
		}
		//特殊人群搜索按钮
		$("#literatureSearch").click(function() {
			
								var values = $(".literature").val();
								if( "" != values){
								$.ajax({
											type : 'post',
											url : 'literatrueSearch.action',
											traditional : true,
											dataType : "json",
											data : {
												value : values,
											},
											success : function(data) {
												/* 先清除所有数据 */
												$(".data").empty();
												if ("" != data) {
													$("#errorInfo").css(
															"display", "none");
													setTableTH();//设置table的头
													$.each(data,function(index,li) {
																		$(".data")
																				.append(
																						"<tr><td>"
																								+ (index + 1)
																								+ "</td>"
																								+ "<td>"+li+ "</td></tr>");
																	});
												} else {
													$("#errorInfo").css(
															"display", "block");
												}
											}
										});
								}
						});
		
	})
</script>
<style type="text/css">
.bookname {
	padding-left: 60px;
	padding-top: 5px;
}

.tab {
	margin-top: 20px;
}
</style>


</head>
<body>
	<div id="container">

		<ul class="nav nav-pills">
			<li><a href="init.action">首页</a></li>
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
		<div class="tab">
			<ul id="myTab" class="nav nav-tabs">
				<li class="active"><a href="#search" data-toggle="tab">
						文献资料</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="search">
					<div style="border: 1px solid #D6D6D6">
						<table class="tab1" style="height:100px;">
							<tr>
								<td width="150px" height="51px" align="center">关键字</td>
								<td width="220px"><input  class="literature"
									type="text" />
								<td colspan="2" width="220px" height="42px"
									style="padding-left: 150px;"><button
										id="literatureSearch" type="button" class="btn btn-primary"
										style="height: 32px; width: 220px; margin-top: -15px;">搜索</button></td>
								
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="showData">
			<div class="column pre-scrollable" style="height:466px;">
				<table class="data">
					<tr>
						<th style="width: 56px; text-align: center">序号</th>
						<th style="width: 852px; text-align: center">参考文献</th>
						
					</tr>
					<c:set var="i" value="1" />
					<c:forEach var="li" items="${LIST}">
						<tr>
							<td class="content">${i}</td>
							<td class="content">${li.getLiterature()}</td>
						</tr>
						<%--变量自增 --%>
						<c:set var="i" value="${i+1}" />
					</c:forEach>

				</table>
			</div>
			<div id="errorInfo">对不起，小编已经努力了。请联系客服。</div>
		</div>

	</div>
</body>
</html>