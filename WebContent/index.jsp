<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>工具开发环境</title>
<style type="text/css">
.head {
	background-color: rgba(192, 192, 192, 0.6);
	text-align: center;
	color: rgba(16, 64, 0, 1);
	font-size: 30px;
	font-family: Microsoft YaHei;
}

.block {
	background-color: rgba(192, 192, 192, 0.4);
	padding-top: 1px;
	padding-bottom: 1px;
	height: 32px;
}

.name {
	display: inline-block;
	text-align: right;
	height: 100%;
	width: 124px;
}

#errorMsg {
	color: red;
	text-align: right;
}

body {
	width: 1024px;
	margin: 0 auto;
	padding-top: 10%;
}

input {
	width: 892px;
	height: 30px;
	border: none;
	font-size: 20px;
	background-color: rgb(253, 240, 240);
}

input:-webkit-autofill {
	-webkit-box-shadow: 0 0 0px 16px rgb(253, 240, 240) inset;
}

select {
	width: 892px;
	height: 32px;
	border: none;
	font-size: 20px;
	background-color: rgb(253, 240, 240);
}

.select {
	width: 442px;
	height: 32px;
	border: none;
	font-size: 20px;
	background-color: rgb(253, 240, 240);
}

a {
	display: block;
	text-decoration: none;
	color: black;
	text-align: center;
	font-size: 26px;
	background-color: rgba(192, 192, 192, 0.6);
}

a:HOVER {
	background-color: rgba(192, 192, 192, 0.8);
	transition: background-color 0.5s;
}
</style>
<body>
	<div class="head">工具开发环境</div>
	<form id="form">
		<div class="block">
			<div class="name">工具ID：</div>
			<input name="toolID" placeholder="工具ID，用不到可以不填">
		</div>
		<div class="block">
			<div class="name">帮区ID：</div>
			<input name="runningBandID" placeholder="工具运行帮区视图ID，用不到可以不填">
		</div>
		<div class="block">
			<div class="name">有无页面：</div>
			<select name="hasUI">
				<option value="0">无页面</option>
				<option value="1">有页面</option>
			</select>
		</div>
		<div class="block">
			<div class="name">工具操作平台：</div>
			<select name="operationPlatform">
				<option value="-1">无操作平台（无页面）</option>
				<option value="0">PC端工具</option>
				<option value="1">移动端工具</option>
				<option value="2">通用</option>
			</select>
		</div>
		<div class="block">
			<div class="name">用户帐号：</div>
			<input name="userAccount" placeholder="运行工具的用户的帐号，用不到可以不填">
		</div>
		<div class="block">
			<div class="name">用户ID：</div>
			<input name="userID" placeholder="运行工具的用户ID，用不到可以不填">
		</div>
		<div class="block">
			<div class="name">accessToken：</div>
			<input name="accessToken" placeholder="运行工具的用户的登录令牌，用不到可以不填">
		</div>
		<div class="block">
			<div class="name">是否有数据库：</div>
			<select name="hasDB">
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
		</div>
		<div class="block">
			<div class="name">工具运行参数：</div>
			<input name="param" placeholder="工具运行参数，用不到可以不填">
		</div>
		<div class="block">
			<div class="name">返回值类型：</div>
			<select name="hasReturnValue">
				<option value="0">无返回值</option>
				<option value="1">json</option>
				<option value="2">二进制数组（byte[]）</option>
			</select>
		</div>
		<div class="block">
			<div class="name">运发平台：</div>
			<select class="select" name="clientType1">
				<option value="1">web</option>
				<option value="2">app</option>
				<option value="3">微信</option>
			</select> <select class="select" name="clientType2">
				<option value="1">windows</option>
				<option value="2">linux</option>
				<option value="3">android</option>
				<option value="4">IOS</option>
			</select>
		</div>
		<a href="#" onclick="gotoRunTool();">运行</a>
	</form>
	<div id="errorMsg"></div>
	<script>
		function gotoRunTool() {
			var form = document.getElementById("form");
			form.action = '${pageContext.request.contextPath}' + "/gotoRunTool";
			form.method = "post";
			form.target = "_blank";
			form.submit();
		}
	</script>
</body>
</html>
