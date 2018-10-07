<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/goods/jsps/css/user/regist.css">
	<script type="text/javascript" src="<c:url value='/jquery/jQuery.js'/>"></script>
   <script type="text/javascript" src="<c:url value='/jsps/js/user/regist.js'/>"></script>
</head>
<body>
<div id="divBody" class="">
    <div id="divTop">
        <span></span>
    </div>
    <div id="divCenter">
        <form action="<c:url value='/UserServlet' />" method="post" id="regist">
        <!-- 发送到UserServlet的regist方法中处理 -->
        <input type="hidden"  name="method" value="regist"/>
            <table>
                <tr>
                    <td>用户名:</td>
                    <td><input type="text" name="loginname" id="inputname"  value="${form.loginname }"></td>	
                    <td><label class="errorclass" id="inputnameError">${errors.loginnameError }</label></td>
                </tr>

                <tr>
                    <td>登入密码:</td>
                    <td><input type="password" name="loginpass" id="inputpsw" value="${form.loginpass }" ></td>
                    <td><label class="errorclass" id="inputpswError">${errors.loginpasswordError }</label></td>
                </tr>
                <tr>
                    <td>确认密码:</td>
                    <td><input type="password" name="reloginpass"  id="inputpsw2" value="${form.reloginpass}"></td>
                    <td><label class="errorclass" id="inputpsw2Error">${errors.loginpassword2Error }</label></td>
                </tr>

                <tr>
                    <td>Email:</td>
                    <td><input type="text" name="email" id="inputmail" value="${form.email }"></td>
                    <td><label class="errorclass" id="inputmailError">${errors.loginemailError }</label></td>
                </tr>

                <tr>
                    <td>图形验证码:</td>
                    <td><input type="text" name="code"  id="inputcode"></td>
                    <td><label class="errorclass" id=inputcodeError>${errors.loginecode}</label></td>
                </tr>

                <tr>
                    <td><img src="/goods/picture" id="pic"></td>
                    <td>  <a href="JavaScript:change()">换一张</a></td>
                </tr>
                <tr>
                  <td><input type="image" src="/goods/images/regist1.jpg" id="registBut"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>