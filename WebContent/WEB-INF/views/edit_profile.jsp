<%@include file="header.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cajon Online :: Edit profile</title>
<style>
table {
  width: 100%;
}
</style>
</head>
<body>
  <div class="absoluteCenter">
    <form id="formEditProfile" action="editUserProfile" method="post" accept-charset="ISO-8859-1">
      <table border="1">
        <tr>
          <th colspan="2">Cập nhật thông tin tài khoản</th>
        </tr>
        <tr>
          <td><b>Tên đăng nhập</b></td>
          <td><input readonly="readonly" type="text" id="username" name="username" value="${info.user.username}" /></td>
        </tr>
        <tr>
          <td><b>Họ tên (*)</b></td>
          <td><input type="text" id="name" name="name" value="${info.user.name}" /></td>
        </tr>
        <tr>
          <td><b>Địa chỉ (*)</b></td>
          <td><input type="text" id="address" name="address" value="${info.user.address}" /></td>
        </tr>
        <tr>
          <td><b>Số điện thoại (*)</b></td>
          <td><input type="text" id="phone" name="phone" value="${info.user.phone}" /></td>
        </tr>
        <tr>
          <td colspan="2"><input type="submit" value="Cập nhật tài khoản" /></td>
        </tr>
      </table>
    </form>
    <a class="nav" href="home">Về trang chủ</a>
  </div>
  <script>
			$(document).ready(function() {
				$("#phone").verifyInputRealtime();				
				$("#formEditProfile").submit(function(e) {
					var name = $("#name").val();
					var address = $("#address").val();
					var phone = $("#phone").val();

					var isValidName = $("#name").validateInput(MIN_LEN, ONLY_ALPHABETS);
					var isValidPhone = $("#phone").validateInput(MIN_LEN, ONLY_NUMBERS);
					var isValidAddress = $("#address").validateInput(MIN_LEN, ONLY_ADDRESS);
					
					if (!isValidName || !isValidAddress || !isValidPhone) {
						e.preventDefault();
					}
					
				});
			});

		</script>
</body>
</html>