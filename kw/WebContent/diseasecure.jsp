<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>

<link type="text/css" rel="stylesheet" href="css/common.css" />
<link type="text/css" rel="stylesheet" href="css/bootstrap.min.css" />

<!-- 先加载jq之后才加载js,否则报错(bootstrap) -->
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
<style type="text/css">
.onetitle {
	text-indent: 4px;
}

#twotitle {
	display: none;
	text-indent: 28px;
}

.leftTitle {
	float: left;
}

.rightContent {
	float: left;
	margin-left: 20px;
	width: 660px;
}

.content {
	cursor: pointer;
}

.twotitle a, .onetitle a {
	cursor: pointer;
}
</style>
<script type="text/javascript">
	$(function() {

		/*点击展开事件*/
		$(".onetitle").click(function() {
			$(this).each(function() {
				if ('none' == $(this).find("#twotitle").css("display")) {
					$(this).find("#twotitle").css("display", "block");
				} else {
					$(this).find("#twotitle").css("display", "none");
				}
			});
		});

		$(".twotitle").each(function() {
				$(this).click(function() {
					var url = $(this).find("input").val();
					$.ajax({
								type : 'post',
								url : 'getDiseaseCures.action',
								dataType : "json",
								data : {
									value : url,
										},
								success : function(data) {
										$(".content").css("display","block");
										$(".content").empty();
										$(".details").css("display","none");
												$.each(data,function(index,dc) {
														if(null != dc){
															$(".content").append(
																"<h4><a class=\"titleName\" style=\"text-decoration: none;\">"
																+ dc.threeleveltitle
																+ "</a></h4>"
																+ "<a  style=\"text-decoration: none;color:black;\">"
																+ dc.content
																+ "</a>"
																+ "<input type=\"hidden\" value=\""+ dc.daddress + "\"/><hr style=\"border:1px dashed #D6D6D6\" />");
														
															}
														});
													}
												});
											});
						});
		$(".content").each(function() {
					$(this).click(function() {
												var url = $(this).find(
														".daddress").val();
												var title = $(this).find(
														".titleName").html();
												$.ajax({
															type : 'post',
															url : 'getDiseaseCure.action',
															dataType : "json",
															data : {
																value : url,
																title : title
															},
															success : function(data) {
																$(".content").css("display","none");
																$(".details").css("display","block");
																if(data.threeleveltitle != null && data.content!=null){
																	$(".details")
																		.append(
																				"<h4><a href=\"#\" style=\"text-decoration: none;\">"
																						+ data.threeleveltitle
																						+ "</a></h4>"
																						+ "<a href=\"#\" style=\"text-decoration: none;color:black;\">"
																						+ data.content
																						+ "</a>");
																}

															}
														});
											});
						});
	})
</script>
</head>
<body>
	<div id="container">

		<ul class="nav nav-pills">
			<li><a href="init.action">首页</a></li>
			<li><a href="#">知识科普</a></li>
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

		<div class="leftTitle">
			<div>
				<ul class="nav nav-pills nav-stacked"
					style="width: 200px; margin-top: 20px;">
					<li class="active"><a href="#"> 分科导航</a></li>
					<c:forEach var="onetitle" items="${TITLELIST}">
						<li class="onetitle"><a>${onetitle.getOneleveltitle()}</a>
							<ul class="nav nav-pills nav-stacked" id="twotitle">
								<c:forEach var="twotitle" items="${onetitle.getTwoTitleList()}">
									<li class="twotitle"><a>${twotitle.getTitle()} <input
											type="hidden" value="${twotitle.getTitleAddress()}" />
									</a></li>
								</c:forEach>
							</ul></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="column pre-scrollable" style="height: 628px;">
			<div class="rightContent">
				<c:forEach var="content" items="${CONTENTLIST}">
					<div class="content">
						<h4>
							<a class="titleName" style="text-decoration: none;">${content.getThreeleveltitle()}</a>
						</h4>
						<a style="text-decoration: none; color: black;">${content.getContent()}</a>
						<input class="daddress" type="hidden"
							value="${content.getDaddress()}" />
						<hr style="border: 1px dashed #D6D6D6" />
					</div>
				</c:forEach>
				<div class="details"></div>
			</div>
		</div>
	</div>
</body>
</html>