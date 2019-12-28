<%@include file="header.jsp"%>
<%@page import="com.cajon.utils.Values"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title>Cajon Online :: Create new account</title>
<style>
table {
  width: 100%;
}
</style>
</head>
<body>
  <div class="absoluteCenter">
    <form id="formReigsterAccount" action="registerAccount" method="post" accept-charset="ISO-8859-1">
      <table border="1">
        <tr>
          <th colspan="2">Nhập thông tin tài khoản</th>
        </tr>
        <tr>
          <td><b>Tên đăng nhập (*)</b></td>
          <td><input type="text" id="username" name="username" /><span class="error" id="error-message">${message}</span></td>
        </tr>
        <tr>
          <td><b>Mật khẩu (*)</b></td>
          <td><input type="password" id="password" name="password" /></td>
        </tr>
<!--         <tr> -->
<!--           <td><b>Nhập lại mật khẩu (*)</b></td> -->
<!--           <td><input type="password" id="password2" name="password2" /></td> -->
<!--         </tr> -->
        <tr>
          <td><b>Họ tên (*)</b></td>
          <td><input type="text" id="name" name="name" /></td>
        </tr>
        <tr>
          <td><b>Địa chỉ (*)</b></td>
          <td><input type="text" id="address" name="address" /></td>
        </tr>
        <tr>
          <td><b>Số điện thoại (*)</b></td>
          <td><input type="text" id="phone" name="phone" /></td>
        </tr>
        <tr>
          <td colspan="2"><input type="submit" value="Tạo tài khoản" /></td>
        </tr>
      </table>
    </form>
    <a class="nav" href="home">Về trang chủ</a>
  </div>
  <script>
			$(document).ready(function() {
				$("#phone").verifyInputRealtime();
				
				$("#formReigsterAccount").submit(function(e) {					
					var isValidUsername = $("#username").validateInput(MIN_LEN, ONLY_NUMBERS_ALPHABETS);
					var isValidPassword = $("#password").validateInput(MIN_LEN, ONLY_NUMBERS_ALPHABETS);
					var isValidName = $("#name").validateInput(MIN_LEN, ONLY_ALPHABETS);
					var isValidPhone = $("#phone").validateInput(MIN_LEN, ONLY_NUMBERS);
					var isValidAddress = $("#address").validateInput(MIN_LEN, ONLY_ADDRESS);
					
					if (!isValidUsername || !isValidPassword || !isValidName || !isValidAddress || !isValidPhone) {
						e.preventDefault();
					}
					
				});
			});
		</script>
</body>
</html>