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
			$(".dataLeft")
					.append(
							"<tr>"
									+ "<th style=\"width: 150px; text-align: center\">药品名称</th>"
									+ "<th style=\"width: 250px; text-align: center\">相互作用</th>");
			$(".dataRight")
					.append(
							"<tr>"
									+ "<th style=\"width: 150px; text-align: center\">药品名称</th>"
									+ "<th style=\"width: 300px; text-align: center\">相互作用</th>");
		}
		//相互作用搜索按钮
		$("#InteractionSearch")
				.click(
						function() {
							$("#mcname").css("display", "none");
							var values = $(".mcname").val();
							if("" != values){
							$
									.ajax({
										type : 'post',
										url : 'interactionSearch.action',
										traditional : true,
										dataType : "json",
										data : {
											value : values,
										},
										success : function(data) {
											/* 先清除所有数据 */
											$("#errorInfo").css("display","none");
											$(".dataLeft").empty();
											$(".dataRight").empty();
											setTableTH();//设置table的头
											var flag = 0;//判断右侧有没有数据
											$
													.each(
															data,
															function(index,
																	value) {
																if (null == value.mIndications) {
																	$(
																			".dataLeft")
																			.append(
																					"<tr class=\"hand\">"
																							+ "<td style=\"width: 150px;\">"
																							+ value.mcName
																							+ "</td>"
																							+ "<td class=\"value\" style=\"width: 250px;\">"
																							+ value.mInteraction
																							+ "</td></tr>");
																	$(
																			"table.dataLeft tr:nth-child(2)")
																			.css(
																					"background",
																					"#79C3EB");
																} else {
																	flag = 1;
																	$(
																			".dataRight")
																			.append(
																					"<tr>"
																							+ "<td style=\"width: 150px;\"><a href=\"cmdetails.action?mid="
																							+ value.mId
																							+ "\">"
																							+ value.mcName
																							+ "</a></td>"
																							+ "<td style=\"width: 300px;\">"
																							+ value.mInteraction
																							+ "</td></tr>");

																}
															});
											if (flag == 0) {
												$(".dataRight")
														.append(
																"<tr><td colspan=\"2\" style=\"width:450px;height:41px;\">暂无</td></tr>");
											}
										},
										error:function(){
											
											$(".dataLeft").css("display","none");
											$(".dataRight").css("display","none");
											$("#errorInfo").css("display","block");
										}
									});
							}
						});
		//智能提示
		$('.mcname').bind('input propertychange', function() {
			var value = $('.mcname').val();
			var myReg = /^[\u4e00-\u9fa5]+$/;
			if (value == "" || !myReg.test(value)) {
				$("#mcname").css("display", "none");
			} else {
				$.ajax({
					type : 'post',
					url : 'mcnameSuggest.action',
					dataType : "json",
					//traditional : true,//可以去掉[],一般传递数组、集合使用
					data : {

						value : value,
						field : "mcname"

					},
					success : function(data) {

						if ("" != data) {

							$("#mcname").css("display", "block");
							$("#mcname ul").empty();
							$.each(data, function(index, str) {
								//往li中写入内容

								$("#mcname ul").append("<li>" + str + "</li>");

							});
						} else {
							$("#mcname").css("display", "none");
						}
					}
				});
			}
		});
		$('.mcname1').bind('input propertychange', function() {
			var value = $('.mcname1').val();
			var myReg = /^[\u4e00-\u9fa5]+$/;
			if (value == "" || !myReg.test(value)) {
				$("#mcname1").css("display", "none");
			} else {
				$.ajax({
					type : 'post',
					url : 'mcnameSuggest.action',
					dataType : "json",
					//traditional : true,//可以去掉[],一般传递数组、集合使用
					data : {

						value : value,
						field : "mcname"

					},
					success : function(data) {

						if ("" != data) {

							$("#mcname1").css("display", "block");
							$("#mcname1 ul").empty();
							$.each(data, function(index, str) {
								//往li中写入内容

								$("#mcname1 ul").append("<li>" + str + "</li>");

							});
						} else {
							$("#mcname1").css("display", "none");
						}
					}
				});
			}
		});
		$('.mcname2').bind('input propertychange', function() {
			var value = $('.mcname2').val();
			var myReg = /^[\u4e00-\u9fa5]+$/;
			if (value == "" || !myReg.test(value)) {
				$("#mcname2").css("display", "none");
			} else {
				$.ajax({
					type : 'post',
					url : 'mcnameSuggest.action',
					dataType : "json",
					//traditional : true,//可以去掉[],一般传递数组、集合使用
					data : {

						value : value,
						field : "mcname"

					},
					success : function(data) {

						if ("" != data) {

							$("#mcname2").css("display", "block");
							$("#mcname2 ul").empty();
							$.each(data, function(index, str) {
								//往li中写入内容

								$("#mcname2 ul").append("<li>" + str + "</li>");

							});
						} else {
							$("#mcname2").css("display", "none");
						}
					}
				});
			}
		});
		
		//设置ul选中事件
		$("#mcname ul").on("click", "li", function() {
			var value = $(this).text();
			$(".mcname").val(value);
			$("#mcname").css("display", "none");
		});
		$("#mcname1 ul").on("click", "li", function() {
			var value = $(this).text();
			$(".mcname1").val(value);
			$("#mcname1").css("display", "none");
		});
		$(".mcname1").click(function(){
			$("#mcname2").css("display", "none");
		});
		//设置ul选中事件
		$("#mcname2 ul").on("click", "li", function() {
			var value = $(this).text();
			$(".mcname2").val(value);
			$("#mcname2").css("display", "none");
		});
		$(".mcname2").click(function(){
			$("#mcname1").css("display", "none");
		});
		//难题
		$(".dataLeft").on("click","td",function() {

							$(".dataLeft td").not(this).parent("tr").css(
									"background", "white");
							$(this).parent("tr").css("background", "#79C3EB");
							var value = $(this).parent("tr").find(".value").html();

							/* 局部刷新ajax */
							$.ajax({
										type : 'post',
										url : 'getSuitInteraction.action',
										dataType : "json",
										//traditional : true,//可以去掉[],一般传递数组、集合使用
										data : {
											value : value,
										},
										success : function(data) {
											
											$("#errorInfo").css("display","none");
											$(".dataRight").empty();
											$(".dataRight").append(
															"<tr>"
																	+ "<th style=\"width: 150px; text-align: center\">药品名称</th>"
																	+ "<th style=\"width: 300px; text-align: center\">相互作用</th>");

											$.each(data,function(index, value) {
																$(".dataRight").append(
																				"<tr>"
																						+ "<td style=\"width: 150px;\"><a href=\"cmdetails.action?mid="
																						+ value.mId
																						+ "\">"
																						+ value.mcName
																						+ "</a></td>"
																						+ "<td style=\"width: 300px;\">"
																						+ value.mInteraction
																						+ "</td></tr>");

															});

										},
										error : function() {//返回数据时null时进入error程序
											$(".dataRight").empty();
											$(".dataRight")
													.append(
															"<tr>"
																	+ "<th style=\"width: 150px; text-align: center\">药品名称</th>"
																	+ "<th style=\"width: 300px; text-align: center\">相互作用</th>");
											$(".dataRight")
													.append(
															"<tr><td colspan=\"2\" style=\"width:450px;height:41px;\">暂无</td></tr>");
										}
									});

						})
						
						$(".interactionTab").click(function(){
							$(".dataLeft").css("display","block");
							$(".dataRight").css("display","block");
							$(".hiddenTable").css("display","none");
							$(".pre-scrollable").css("height","518px");
						})
						$(".compareInteraction").click(function(){
							$(".pre-scrollable").css("height","473px");
						});
						$("#compareSearch").click(function(){
							var value1 = $(".mcname1").val();
							var value2 = $(".mcname2").val();
							$.ajax({
								type:'post',
								url:'getCompareInteraction.action',
								dataType:'json',
								data:{
									value1:value1,
									value2:value2
								},
								success:function(data){
									//将left和right表格设置为看不见
									$("#errorInfo").css("display","none");
									$(".dataLeft").css("display","none");
									$(".dataRight").css("display","none");
									$(".hiddenTable").css("display","block");
									$(".hiddenTable").empty();
									var cm1 = null;
									var cm2 = null;
									$.each(data,function(index,cm){
										if(0 == index){
											cm1 = cm;
										}else{
											cm2 = cm;
										}
									});
									$(".hiddenTable").append(
											"<tr><th class=\"title\">药品名称</th>"
											+"<td class=\"hiddenValue\">"+cm1.mcName+"</td>"
											+"<td class=\"hiddenValue\">"+cm2.mcName+"</td></tr>"+
											"<tr><th class=\"title\">功能主治</th>"
											+"<td class=\"hiddenValue\">"+cm1.mIndications+"</td>"
											+"<td class=\"hiddenValue\">"+cm2.mIndications+"</td></tr>"+
											"<tr><th class=\"title\">相互作用</th>"
											+"<td class=\"hiddenValue\">"+cm1.mInteraction+"</td>"
											+"<td class=\"hiddenValue\">"+cm2.mInteraction+"</td></tr>");
								},
								error:function(){
									$("#errorInfo").css("display","none");
									$(".dataLeft").css("display","none");
									$(".dataRight").css("display","none");
									$("#errorInfo").css("display","block");
								}
							})
							
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

.dataLeft th, .dataRight th,.hiddenTable th {
	border: 1px solid #D6D6D6;
	font-size: 14px;
}

.dataLeft tr td, .dataRight tr td,.hiddenTable tr td{
	text-align: center;
	border: 1px solid #D6D6D6;
}

.dataLeft, .dataRight {
	float: left;
}

.dataRight {
	margin-left: 38px;
}

.hand:hover {
	cursor: pointer;
}
.mcname1,.mcname2{
	margin-top:10px;
}
.hiddenTable{
	display:none;
}
.title{
	width: 150px;
	text-align: center;
}
.hiddenValue{
	width: 367px;
	text-align: center;
	padding:3px 3px 3px 3px;
}
</style>


</head>
<body>
	<div id="container">

		<ul class="nav nav-pills">
			<li><a href="init.action">首页</a></li>
			<li><a href="knowledge.html">知识科普</a></li>
			<li><a href="IndicationsInit.action">处方信息</a></li>
			<li class="dropdown"  class="active"><a class="dropdown-toggle"
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
				<li class="active"><a class="interactionTab" href="#interaction" data-toggle="tab">
						药品相互作用</a></li>
				<li><a href="#compareInteraction" class="compareInteraction" data-toggle="tab">对比相互作用</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="interaction">
					<div style="border: 1px solid #D6D6D6">
						<table class="tab1">
							<tr>
								<td width="150px" height="51px" align="center">药品名称</td>
								<td width="220px"><input name="mcname" class="mcname"
									type="text" />
									<div id="mcname">
										<ul>
										</ul>
									</div></td>
								<td width="220px" height="42px" style="padding-left: 150px;"><button
										id="InteractionSearch" type="button" class="btn btn-primary"
										style="height: 32px; width: 220px; margin-top: -15px;">搜索</button></td>
							</tr>

						</table>
					</div>
				</div>
				<div class="tab-pane fade" id="compareInteraction">
					<div style="border: 1px solid #D6D6D6">
						<table class="tab2">
							<tr>
								<td width="150px" height="51px" style="padding-left:50px;">药品名称1</td>
								<td width="202px"><input class="mcname1" type="text" />
									<div id="mcname1">
										<ul>
										</ul>
									</div></td>
								<td width="250px" height="51px" style="padding-left: 150px;">药品名称2</td>
								<td width="202px"><input class="mcname2" type="text" />
									<div id="mcname2">
										<ul>
										</ul>
									</div></td>
							</tr>

							<tr>
								<td colspan="4" width="220px" height="42px"
									style="padding-left: 350px;"><button id="compareSearch"
										type="button" class="btn btn-primary"
										style="height: 32px; width: 220px; margin-top: -15px;">搜索</button></td>
							</tr>

						</table>

					</div>
				</div>
			</div>
		</div>
		<div class="showData">
			<div class="column pre-scrollable" style="height:518px;">
			<table class="dataLeft">
				<tr>
					<th style="width: 150px; text-align: center">药品名称</th>
					<th style="width: 250px; text-align: center">相互作用</th>

				</tr>
				<tr class="hand">
					<td style="width: 150px;">${CHINESEMEDICINE.getMcName()}</td>
					<td class="value" style="width: 250px;">${CHINESEMEDICINE.getmInteraction()}</td>
				</tr>
			</table>
			<table class="dataRight">
				<tr>
					<th style="width: 150px; text-align: center">药品名称</th>
					<th style="width: 300px; text-align: center">相互作用</th>
				</tr>
				<c:if test="${ not empty CMLIST}">
					<c:forEach var="cm" items="${CMLIST}">
						<tr>
							<td style="width: 150px;"><a
								href="cmdetails.action?mid=${cm.getmId()}">${cm.getMcName()}</a></td>
							<td style="width: 300px;">${cm.getmInteraction()}</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty CMLIST}">
					<tr>
						<td colspan="2" style="width: 450px; height: 41px;">暂无</td>
					</tr>
				</c:if>
			</table>
			<table class="hiddenTable"></table>
			<div id="errorInfo">对不起，您搜索的药品不存在。请联系客服。</div>
			</div>
		</div>

	</div>
</body>
</html>