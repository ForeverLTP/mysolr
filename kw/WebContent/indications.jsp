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
									+ "<th style=\"width: 150px; text-align: center\">药品名称</th>"
									+ "<th style=\"width: 300px; text-align: center\">药品来源</th><th style=\"width: 50px; text-align: center\">页码</th>"
									+ "<th style=\"width: 352px; text-align: center\">处方</th></tr>");
		}
		//处方搜索按钮
		$("#IndicationsSearch")
				.click(
						function() {
							$("#mcname").css("display", "none");
							
							//普通搜索通过界面从上至下、从左至右的顺序排列的域
							var fields = [ "mcname", "bookname" ];
							var values = [ $(".mcname").val(),
									$("#bookname").selectpicker('val'), ];
							var flag = [ true, true ];
							var flag1 = true;
							var flag2 = true;
							if ($(".mcname").val() == "") {
								flag1 = false;
							}
							if ($("#bookname").selectpicker('val') == "---------------请选择---------------") {
								flag2 = false;
							}
							if (flag1 == true || flag2 == true) {
								$.ajax({
											type : 'post',
											url : 'multipleSearch.action',
											traditional : true,
											dataType : "json",
											data : {
												fields : fields, //将查询域上传	
												values : values,
												flag : flag
											},
											success : function(data) {
												/* 先清除所有数据 */
												$("#data").css("display", "block");
												$(".data").empty();
												if ("" != data) {
													$("#errorInfo").css(
															"display", "none");
													setTableTH();//设置table的头
													$
															.each(
																	data,
																	function(
																			index,
																			cm) {
																		$(
																				".data")
																				.append(
																						"<tr><td>"
																								+ (index + 1)
																								+ "</td>"
																								+ "<td><a href=\"findSRDetails.action?mid="+cm.mId+"\">"
																								+ cm.mcName
																								+ "</a></td> <td>"
																								+ cm.bookname
																								+ "</td> <td>"
																								+ cm.sSource.spagenumber
																								+ "</td><td>"
																								+ cm.sSource.sprescription
																								+ "</td></tr>");
																	});
												} else {
													$("#data").css("display", "none");
													$("#errorInfo").css(
															"display", "block");
												}
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
		//设置ul选中事件
		
		$("#mcname ul").on("click", "li", function() {
			var value = $(this).text();
			$(".mcname").val(value);
			$("#mcname").css("display", "none");
		});
		
		/*清除ul*/
		$(".bookname").click(function(){
			$("#mcname").css("display", "none");
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
			<li class="active"><a href="#">处方信息</a></li>
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
						处方搜索</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="search">
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
								<td style="padding-left: 162px;">标准来源</td>
								<td width="220px" height="32px" class="bookname"><select
									class="selectpicker" id="bookname">
										<option>---------------请选择---------------</option>
										<c:forEach var="srType" items="${SRTYPES}">
											<option>${srType.getBookname()}</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td colspan="4" width="220px" height="42px"
									style="padding-left: 350px;"><button
										id="IndicationsSearch" type="button" class="btn btn-primary"
										style="height: 32px; width: 220px; margin-top: -15px;">搜索</button></td>
							</tr>

						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="showData" style="height:473px;">
			<div class="column pre-scrollable" id="data" style="height:473px;">
				<table class="data">
					<tr>
						<th style="width: 56px; text-align: center">序号</th>
						<th style="width: 150px; text-align: center">药品名称</th>
						<th style="width: 300px; text-align: center">药品来源</th>
						<th style="width: 50px; text-align: center">页码</th>
						<th style="width: 352px; text-align: center">处方</th>
					</tr>
					<c:set var="i" value="1" />
					<c:forEach var="cm" items="${LIST}">
						<tr>
							<td class="content">${i}</td>
							<td class="content"><a href="findSRDetails.action?mid=${cm.getmId()}">${cm.getMcName()}</a></td>
							<td class="content">${cm.getBookname()}</td>
							<td class="content">${cm.getsSource().getSpagenumber()}</td>
							<td class="content">${cm.getsSource().getSprescription()}</td>
						</tr>
						<%--变量自增 --%>
						<c:set var="i" value="${i+1}" />
					</c:forEach>

				</table>
			</div>
			<div id="errorInfo">对不起，您搜索的药品不存在。请联系客服。</div>
		</div>

	</div>
</body>
</html>