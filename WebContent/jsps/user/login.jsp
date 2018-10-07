<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<style>
body {
	background: url("/goods/images/4.jpg");
}

#login-box {
	width: 430px;
	height: 550px;
	border-radius: 20px;
	border: 3px solid #ffffff;
	margin: 20px auto;
	background-color: rgba(128, 128, 128, 0.50);
	/*opacity: 0.3;*/
}

input {
	background-color: rgba(128, 128, 128, 0.50);
	position: relative;
	left: 50px;
	width: 320px;
	height: 40px;
	border-radius: 20px;
	margin: 10px auto;
	vertical-align: middle;
	text-align: center;
	border: 1px solid #ffffff;
	color: #ffffff;
}

#circle {
	width: 100px;
	height: 100px;
	border-radius: 50%;
	border: 1px solid #ffffff;
	margin: 100px auto;
}

input::-webkit-input-placeholder, textarea::-webkit-input-placeholder {
	color: #ffffff;
}

a {
	color: #ffffff;
}
</style>
<body>
	<h1>${msg}</h1>
	<div id="big-box">
		<div id="login-box">
			<div id="circle"></div>
			<form action='<c:url value='/UserServlet'></c:url>'>
				<input type="hidden" name="method" value="login" />
				<table style="width: 100%">
					<tr>
						<td><span
							class="glyphicon glyphicon-user form-control-feedback"></span><input
							type="text" class="glyphicon glyphicon-user" id="inputname"
							name="username" placeholder="用户名"></td>
					</tr>
					<tr>
						<td><input type="password" id="inputpassword" name="password"
							placeholder="密码"></td>
					</tr>
					<tr>
						<td><input type="submit" value="登入"></td>
					</tr>
					<tr>
						<td><a href="/goods/jsps/user/regist.jsp">注册用户</a></td>
						<td><a href="/goods/jsps/user/pwd.jsp">忘记密码</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>