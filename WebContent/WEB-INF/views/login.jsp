<%@include file="header.jsp"%>
<%@page import="com.cajon.utils.Values"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title>Cajon Online :: Login</title>
<style>
table {
  width: 100%;
}

input {
  width: 100%;
}

input[type='submit'] {
  cursor: pointer;
}
</style>
</head>
<body>
  <div class="absoluteCenter">
    <form action="login" method="post">
      <table border="1">
        <tr>
          <th colspan="2">Chào mừng đến với Cajon World!!!</th>
        </tr>
        <tr>
          <td><b>Tên đăng nhập</b></td>
          <td><input type="text" name="username" /></td>
        </tr>
        <tr>
          <td><b>Mật khẩu</b></td>
          <td><input type="password" name="password" /></td>
        </tr>
        <tr>
          <td colspan="2"><a href="register"><input type="button" value="Tạo tài khoản" /></a></td>
        </tr>
        <tr>
          <td colspan="2"><input type="submit" value="Đăng nhập" /></td>
        </tr>
      </table>
      <div style='text-align: center;'>
        <span class="error">${message}</span><br /> <img src="<%out.print(Values.ROOT_DIR);%>/images/cajon-welcome.jpg" />
      </div>
    </form>
    <a class="nav" href="home">Về trang chủ</a>
  </div>
</body>
</html>