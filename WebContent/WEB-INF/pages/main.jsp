<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>TypeManage</title>
<link rel="stylesheet" type="text/css"
	href="${resourceUrl}/css/style.css">

</head>

<body>
	<p id="hasCatagory" style="display:none">暂无可暂时类型！</p>
	<ul class="catagory-list">
	</ul>

	<button id="add">添加</button>

	<div class="catagory-information">
	</div>

	<div class="catagory-modify">
	</div>

	<div class="delete-prompt">
		<p>确定删除 <span id="catagory_span" style="color:red"></span> 类型记录？</p>
		<div class="prompt-buttons">
			<button id="yes">确定</button>
			<button id="no">取消</button>
		</div>
	</div>

	<div class="catagory-add">
		<!-- 这个iframe是为了表单提交时不刷新页面 -->
		<iframe name='frameFile' style="display: none;"></iframe>
		<button id="close-btn"></button>
		<form id="add-form" action="" target="frameFile">
			<label for="catagoryName">类型：</label> <input id="catagory_name"
				name="catagoryName" type="text" placeholder="请输入类型名称"><br>
			<button type="submit">提交</button>
			<!-- <button type="submit" onclick="btnClick()">提交</button> -->
		</form>
	</div>

	<div class="cover"></div>

	<script type="text/javascript">
		var fyToolUrl = '${fyToolUrl}';
	</script>
	<script type="text/javascript"
		src="${resourceUrl}/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="${resourceUrl}/js/main.js"></script>
</body>
</html>
