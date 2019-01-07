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
									+ "<th style='width: 50px; text-align: center'>查看</th></tr>");
		}
		//全文检索按钮
		$(".fullbutton").click(
				function() {
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
							$(".data").empty();
							if ("" != data) {
								$("#errorInfo").css("display", "none");
								setTableTH();//设置table的头
								$.each(data, function(index, cm) {
									$(".data").append(
											"<tr><td>" + (index + 1) + "</td>"
													+ "<td><a href=\"#\">"
													+ cm.mcName
													+ "</a></td> <td>"
													+ cm.mIngredient
													+ "</td> <td>"
													+ cm.mIndications
													+ "</td><td> </td></tr>");
								});
							} else {
								$("#errorInfo").css("display", "block");
							}
						}
					});
				});
		//普通搜索按钮
		$("#simpleSearch").click(
				function() {
					//普通搜索通过界面从上至下、从左至右的顺序排列的域
					var fields = [ "mcname", "mindications", "mingredient",
							"bookname", "tname" ];
					var values = [ $(".mcname").val(),
							$(".mindications").val(), $(".mingredient").val(),
							$("#bookname").selectpicker('val'),
							$("#tname").selectpicker('val') ];
					var flag = [ true, true, true, true, true ];

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
							$(".data").empty();
							if ("" != data) {
								$("#errorInfo").css("display", "none");
								setTableTH();//设置table的头
								$.each(data, function(index, cm) {
									$(".data").append(
											"<tr><td>" + (index + 1) + "</td>"
													+ "<td><a href=\"#\">"
													+ cm.mcName
													+ "</a></td> <td>"
													+ cm.mIngredient
													+ "</td> <td>"
													+ cm.mIndications
													+ "</td><td> </td></tr>");
								});
							} else {
								$("#errorInfo").css("display", "block");
							}
						}
					});
				});
		//高级搜索按钮
		$("#advancedSearch").click(
				function() {

					var fields = [ "mall", "mall", "mall", "mall" ];
					var values = [ $(".aboveNot").val(), $(".aboveAny").val(),
							$(".aboveComplete").val(), $(".aboveAll").val() ];
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
							$(".data").empty();
							if ("" != data) {
								$("#errorInfo").css("display", "none");
								setTableTH();//设置table的头
								$.each(data, function(index, cm) {
									$(".data").append(
											"<tr><td>" + (index + 1) + "</td>"
													+ "<td><a href=\"*\">"
													+ cm.mcName
													+ "</a></td> <td>"
													+ cm.mIngredient
													+ "</td> <td>"
													+ cm.mIndications
													+ "</td><td> </td></tr>");
								});
							} else {
								$("#errorInfo").css("display", "block");
							}
						}
					});
				});
		//前缀查询
		function setAjaxParm(value, id) {
			$.ajax({
				type : 'post',
				url : 'prefixSearch.action',
				dataType : "json",
				//traditional : true,//可以去掉[],一般传递数组、集合使用
				data : {

					value : value,

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

	})