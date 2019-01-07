<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
		
		/* 设置展示表的头行 */
		function setTableTH() {
			$(".data")
					.append(
							"<tr>"
									+ "<th style='width: 56px; text-align: center'>序号</th>"
									+ "<th style='width: 150px; text-align: center'>药品名称</th>"
									+ "<th style='width: 326px; text-align: center'>组成成分</th>"
									+ "<th style='width: 326px; text-align: center'>功能主治</th>"
									+ "<th style='width: 50px; text-align: center'>查看处方</th></tr>");
		}
		//全文检索按钮
		$(".fullbutton").click(
				function() {
					$("#fulltext").css("display", "none");
					$.ajax({
						type : 'post',
						url : 'fullsearch.action',
						dataType : "json",
						data : {
							value : $(".fulltext").val(),
							field : "mall" //将查询域上传	
						},
						success : function(data) {
							/* 先清除所有数据 */
							$("#data").css("display", "block");
							$(".data").empty();
							if ("" != data) {
								$("#errorInfo").css("display", "none");
								setTableTH();//设置table的头
								$.each(data, function(index, cm) {
									$(".data").append(
											"<tr><td>" + (index + 1) + "</td>"
													+ "<td><a href=\"cmdetails.action?mid="+cm.mId+"\">"
													+ cm.mcName
													+ "</a></td> <td>"
													+ cm.mIngredient
													+ "</td> <td>"
													+ cm.mIndications
													+ "</td><td><a href=\"getIndications.action?mid="+cm.mId+"\">查看</a></td></tr>");
								});
							} else {
								$("#data").css("display", "none");
								$("#errorInfo").css("display", "block");
							}
						}
					});
				});
		//普通搜索按钮
		$("#simpleSearch").click(
				function() {
					$("#mcname").css("display", "none");
					$("#mingredient").css("display", "none");
					$("#mindications").css("display", "none");
					//普通搜索通过界面从上至下、从左至右的顺序排列的域
					var fields = [ "mcname", "mindications", "mingredient",
							"bookname", "tname" ];
					var values = [ $(".mcname").val(),
							$(".mindications").val(), $(".mingredient").val(),
							$("#bookname").selectpicker('val'),
							$("#tname").selectpicker('val') ];
					var flag = [ true, true, true, true, true ];
					var checkFlag = [$('.mindicationsA').val(),$('.mcnameA').val(),$('.mingredientA').val()];
					$.ajax({
						type : 'post',
						url : 'multipleSearch.action',
						traditional : true,
						dataType : "json",
						data : {
							fields : fields, //将查询域上传	
							values : values,
							flag : flag,
							checkFlag:checkFlag
						},
						success : function(data) {
							/* 先清除所有数据 */
							$("#data").css("display", "block");
							$(".data").empty();
							if ("" != data) {
								$("#errorInfo").css("display", "none");
								setTableTH();//设置table的头
								$.each(data, function(index, cm) {
									$(".data").append(
											"<tr><td>" + (index + 1) + "</td>"
													+ "<td><a href=\"cmdetails.action?mid="+cm.mId+"\">"
													+ cm.mcName
													+ "</a></td> <td>"
													+ cm.mIngredient
													+ "</td> <td>"
													+ cm.mIndications
													+ "</td><td><a href=\"getIndications.action?mid="+cm.mId+"\">查看</a></td></tr>");
								});
							} else {
								$("#data").css("display", "none");
								$("#errorInfo").css("display", "block");
							}
						}
					});
				});
		//高级搜索按钮
		$("#advancedSearch").click(
				function() {
					$("#aboveNot").css("display", "none");
					$("#aboveAny").css("display", "none");
					$("#aboveAll").css("display", "none");
					$("#aboveComplete").css("display", "none");
					
					var fields = [ "mall", "mall", "mall", "mall" ];
					var values = [ $(".aboveNot").val(), $(".aboveAny").val(),
							$(".aboveAll").val(), $(".aboveComplete").val() ];
					var flag = [ false, true, true, true ];
					$.ajax({
						type : 'post',
						url : 'singleFieldSearch.action',
						dataType : "json",
						traditional : true,
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
								$("#errorInfo").css("display", "none");
								setTableTH();//设置table的头
								$.each(data, function(index, cm) {
									$(".data").append(
											"<tr><td>" + (index + 1) + "</td>"
													+ "<td><a href=\"cmdetails.action?mid="+cm.mId+"\">"
													+ cm.mcName
													+ "</a></td> <td>"
													+ cm.mIngredient
													+ "</td> <td>"
													+ cm.mIndications
													+ "</td><td><a href=\"getIndications.action?mid="+cm.mId+"\">查看</a></td></tr>");
								});
							} else {
								$("#data").css("display", "none");
								$("#errorInfo").css("display", "block");
							}
						}
					});
				});
		//前缀查询
		function setAjaxParm(value, id,field) {
			$.ajax({
				type : 'post',
				url : 'prefixSearch.action',
				dataType : "json",
				//traditional : true,//可以去掉[],一般传递数组、集合使用
				data : {
					field : field,
					value : value

				},
				success : function(data) {

					if ("" != data) {

						$(id).css("display", "block");
						$(id + " ul").empty();
						$.each(data, function(index, str) {
							//往li中写入内容

							$(id + " ul").append("<li>" + str + "</li>");

						});
					} else {
						$(id).css("display", "none");
					}
				}
			});
		}

		/*失去焦点*/
		$(".fulltext").click(function(){
			$("#mcname").css("display", "none");
			$("#mingredient").css("display", "none");
			$("#mindications").css("display", "none");
			$("#aboveAll").css("display", "none");
			$("#aboveNot").css("display", "none");
			$("#aboveComplete").css("display", "none");
			$("#aboveAny").css("display", "none");
		});
		$('.fulltext').bind('input propertychange', function() {
			var id = "#fulltext";
			var value = $('.fulltext').val();
			if (value == "") {
				$("#fulltext").css("display", "none");
			} else {
				setAjaxParm(value, id);
			}
		});
		$("#fulltext ul").on("click", "li", function() {
			var value = $(this).text();
			$(".fulltext").val(value);
			$("#fulltext").css("display", "none");
		});
		
		/*失去焦点*/
		$(".mcname").click(function(){
			$("#fulltext").css("display", "none");
			$("#mingredient").css("display", "none");
			$("#mindications").css("display", "none");
		});
		$('.mcname').bind('input propertychange', function() {
			var id = "#mcname";
			var value = $('.mcname').val();
			if (value == "") {
				$("#mcname").css("display", "none");
			} else {
				setAjaxParm(value, id);
			}
		});
		$("#mcname ul").on("click", "li", function() {
			var value = $(this).text();
			$(".mcname").val(value);
			$("#mcname").css("display", "none");
		});
		
		/*失去焦点*/
		$(".mingredient").click(function(){
			$("#fulltext").css("display", "none");
			$("#mcname").css("display", "none");
			$("#mindications").css("display", "none");
		});
		$('.mingredient').bind('input propertychange', function() {
			var id = "#mingredient";
			var value = $('.mingredient').val();
			if (value == "") {
				$("#mingredient").css("display", "none");
			} else {
				setAjaxParm(value, id);
			}
		});
		$("#mingredient ul").on("click", "li", function() {
			var value = $(this).text();
			$(".mingredient").val(value);
			$("#mingredient").css("display", "none");
		});
		
		/*失去焦点*/
		$(".mindications").click(function(){
			$("#fulltext").css("display", "none");
			$("#mcname").css("display", "none");
			$("#mingredient").css("display", "none");
		});
		$('.mindications').bind('input propertychange', function() {
			var id = "#mindications";
			var value = $('.mindications').val();
			if (value == "") {
				$("#mindications").css("display", "none");
			} else {
				setAjaxParm(value, id);
			}
		});
		$("#mindications ul").on("click", "li", function() {
			var value = $(this).text();
			$(".mindications").val(value);
			$("#mindications").css("display", "none");
		});
		
		/*失去焦点*/
		$(".aboveAll").click(function(){
			$("#aboveNot").css("display", "none");
			$("#aboveAny").css("display", "none");
			$("#aboveComplete").css("display", "none");
			$("#fulltext").css("display", "none");
		});
		$('.aboveAll').bind('input propertychange', function() {
			var id = "#aboveAll";
			var value = $('.aboveAll').val();
			if (value == "") {
				$("#aboveAll").css("display", "none");
			} else {
				setAjaxParm(value, id);
			}
		});
		$("#aboveAll ul").on("click", "li", function() {
			var value = $(this).text();
			$(".aboveAll").val(value);
			$("#aboveAll").css("display", "none");
		});
		
		/*失去焦点*/
		$(".aboveNot").click(function(){
			$("#aboveAll").css("display", "none");
			$("#aboveAny").css("display", "none");
			$("#aboveComplete").css("display", "none");
			$("#fulltext").css("display", "none");
		});
		$('.aboveNot').bind('input propertychange', function() {
			var id = "#aboveNot";
			var value = $('.aboveNot').val();
			if (value == "") {
				$("#aboveNot").css("display", "none");
			} else {
				setAjaxParm(value, id);
			}
		});
		$("#aboveNot ul").on("click", "li", function() {
			var value = $(this).text();
			$(".aboveNot").val(value);
			$("#aboveNot").css("display", "none");
		});
		
		/*失去焦点*/
		$(".aboveAny").click(function(){
			$("#aboveAll").css("display", "none");
			$("#aboveNot").css("display", "none");
			$("#aboveComplete").css("display", "none");
			$("#fulltext").css("display", "none");
		});
		$('.aboveAny').bind('input propertychange', function() {
			var id = "#aboveAny";
			var value = $('.aboveAny').val();
			if (value == "") {
				$("#aboveAny").css("display", "none");
			} else {
				setAjaxParm(value, id);
			}
		});
		$("#aboveAny ul").on("click", "li", function() {
			var value = $(this).text();
			$(".aboveAny").val(value);
			$("#aboveAny").css("display", "none");
		});
		
		/*失去焦点*/
		$(".aboveComplete").click(function(){
			$("#aboveAll").css("display", "none");
			$("#aboveNot").css("display", "none");
			$("#aboveAny").css("display", "none");
			$("#fulltext").css("display", "none");
		});
		$('.aboveComplete').bind('input propertychange', function() {
			var id = "#aboveComplete";
			var value = $('.aboveComplete').val();
			if (value == "") {
				$("#aboveComplete").css("display", "none");
			} else {
				setAjaxParm(value, id);
			}
		});
		$("#aboveComplete ul").on("click", "li", function() {
			var value = $(this).text();
			$(".aboveComplete").val(value);
			$("#aboveComplete").css("display", "none");
		});
		
		$(".bookname").click(function(){
			$("#mcname").css("display", "none");
			$("#mingredient").css("display", "none");
			$("#mindications").css("display", "none");
			$("#fulltext").css("display", "none");
		});
		$(".tname").click(function(){
			$("#mcname").css("display", "none");
			$("#mingredient").css("display", "none");
			$("#mindications").css("display", "none");
			$("#fulltext").css("display", "none");
		});
		$(".mcnameA").click(function(){
			var isChecked = $('.mcnameA').prop('checked'); 
			if(isChecked){
				$('.mcnameA').val(1);
			}else{
				$('.mcnameA').val(0);
			}
		});
		$(".mindicationsA").click(function(){
			var isChecked = $('.mindicationsA').prop('checked'); 
			if(isChecked){
				$('.mindicationsA').val(2);
			}else{
				$('.mindicationsA').val(0);
			}
		});
		$(".mingredientA").click(function(){
			var isChecked = $('.mingredientA').prop('checked'); 
			if(isChecked){
				$('.mingredientA').val(3);
			}else{
				$('.mingredientA').val(0);
			}
		});

	})
</script>
<style type="text/css">

/*搜索框6*/
.bar6 {
	/* background: #A3D0C3; */
	float: left;
}

.bar6 input {
	border: 2px solid #7BA7AB;
	border-radius: 5px;
	background: #F9F0DA;
	top: 0;
	right: 0;
}

.bar6 button {
	background: #7BA7AB;
	border-radius: 0 5px 5px 0;
	width: 60px;
	top: 0;
	right: 0;
}

.bar6 button:before {
	content: "搜索";
	font-size: 13px;
	color: #F9F0DA;
}
.mcnameA,.mindicationsA,.mingredientA{
	width:18px;
	height:18px;
	vertical-align:-11px;
	
}
</style>
</head>

<body>
	<div id="container">

		<ul class="nav nav-pills">
			<li class="active"><a href="#">首页</a></li>
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

		<div class="title img">
			<img alt="" src="images/3.png">
		</div>
		<div class="search bar6">
			<div class="titleSearch">
				<input style="width:400px;" class="fulltext" type="text" placeholder="请输入您要搜索的内容...">
				<div id="fulltext">
					<ul>
					</ul>
				</div>
				<button class="fullbutton"></button>
			</div>


		</div>
		<div class="clear"></div>
		<div class="tab">
			<ul id="myTab" class="nav nav-tabs">
				<li class="active"><a href="#search" data-toggle="tab">
						普通搜索</a></li>
				<li><a href="#advanced_search" data-toggle="tab">高级搜索</a></li>

			</ul>
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="search">
					<div style="border: 1px solid #D6D6D6">
						<table class="tab1">
							<tr>
								<td width="150px" height="51px" align="center">药品名称</td>
								<td width="300px"><input name="mcname" class="mcname"
									type="text" />&nbsp;<input type="checkbox"  value="0" class="mcnameA" />精确
									<div id="mcname">
										<ul>
										</ul>
									</div></td>
								<td width="184px" height="51px" style="padding-left: 82px;">功能主治</td>
								<td width="300px"><input name="mindications"
									class="mindications" type="text" />&nbsp;<input type="checkbox"  value="0" class="mindicationsA" />精确
									<div id="mindications">
										<ul>
										</ul>
									</div></td>
							</tr>
							<tr>
								<td class="left-text" align="center">药材组成</td>
								<td><input name="mingredient" class="mingredient"
									type="text" />&nbsp;<input type="checkbox"  value="0" class="mingredientA" />精确
									<div id="mingredient">
										<ul>
										</ul>
									</div></td>
								<td style="padding-left: 82px;">标准来源</td>
								<td width="202px" height="32px;" class="bookname"><select
									class="selectpicker" id="bookname">
										<option>---------------请选择---------------</option>
										<c:forEach var="srType" items="${SRTYPES}">
											<option>${srType.getBookname()}</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td width="150px" height="51px" align="center">药品分类</td>
								<td class="tname"><select class="selectpicker" id="tname">
										<option>---------------请选择---------------</option>
										<c:forEach var="type" items="${TYPES}">
											<option>${type.getTname()}</option>
										</c:forEach>
								</select> <!-- <input type="text" style="border:1px solid #D6D6D6;height:32px"/> -->
								</td>
								<td style="padding-left: 190px;"></td>
								<td>
									<button id="simpleSearch" type="button" class="btn btn-primary"
										style="height: 32px; width: 220px; margin-top: -15px;">搜索</button>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="tab-pane fade" id="advanced_search">
					<div style="border: 1px solid #D6D6D6">
						<table class="tab2">
							<tr>
								<td width="200px" height="51px" align="center">包含以下全部关键字</td>
								<td width="202px"><input class="aboveAll" type="text" />
									<div id="aboveAll">
										<ul>
										</ul>
									</div></td>
								<td width="250px" height="51px" style="padding-left: 100px;">不包含以下关键字</td>
								<td width="202px"><input class="aboveNot" type="text" />
									<div id="aboveNot">
										<ul>
										</ul>
									</div></td>
							</tr>
							<tr>
								<td align="center">包含以下任意一个关键字</td>
								<td><input class="aboveAny" type="text" />
									<div id="aboveAny">
										<ul>
										</ul>
									</div></td>
								<td width="250px" height="51px" style="padding-left: 92px;">包含以下完整关键字</td>
								<td width="202px"><input class="aboveComplete" type="text" />
									<div id="aboveComplete">
										<ul>
										</ul>
									</div></td>
							</tr>

							<tr>
								<td colspan="4" width="220px" height="42px"
									style="padding-left: 350px;"><button id="advancedSearch"
										type="button" class="btn btn-primary"
										style="height: 32px; width: 220px; margin-top: -15px;">搜索</button></td>
							</tr>

						</table>

					</div>
				</div>

			</div>
		</div>
		<div class="showData">
			<div class="column pre-scrollable" id="data" style="height:340px;">
				<table class="data">
					<tr>
						<th style="width: 56px; text-align: center">序号</th>
						<th style="width: 130px; text-align: center">药品名称</th>
						<th style="width: 326px; text-align: center">组成成分</th>
						<th style="width: 326px; text-align: center">功能主治</th>
						<th style="width: 70px; text-align: center">查看处方</th>
					</tr>
					<c:set var="i" value="1" />
					<c:forEach var="cm" items="${LIST}">
						<tr>
							<td class="content">${i}</td>
							<td class="content"><a href="cmdetails.action?mid=${cm.getmId()}">${cm.getMcName()}</a>
							</td>
							<td class="content">${cm.getmIngredient()}</td>
							<td class="content">${cm.getmIndications()}</td>
							<td class="content"><a href="getIndications.action?mid=${cm.getmId()}">查看</a></td>
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
