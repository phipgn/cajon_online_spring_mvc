<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title>Cart Online :: Make Order</title>
<style>
input {
  width: 100%;
}
</style>
</head>
<body>
  <%@include file="nav.jsp"%>
  <br />
  <br />
  <table border="1">
    <tr>
      <th colspan="5"><b>Xem lại giỏ hàng</b></th>
    </tr>
    <tr>
      <th>Hình</th>
      <th>Tên SP</th>
      <th>Đơn giá</th>
      <th>Số lượng</th>
      <th>Thành tiền</th>
    </tr>
    <c:forEach items="${info.cartItems}" var="c">
      <c:set var="totalPrice" value="${totalPrice + c.p.unitPrice * c.quantity}" />
      <c:set var="totalQuantity" value="${totalQuantity + c.quantity}" />
      <tr>
        <td><img class="product-img" src="/cajon_online/images/products/${c.p.image}" /></td>
        <td><b>${c.p.name}</b></td>
        <td class="price">${c.p.unitPrice}</td>
        <td>${c.quantity}</td>
        <td class="price">${c.p.unitPrice * c.quantity}</td>
      </tr>
    </c:forEach>
    <tr>
      <td></td>
      <td></td>
      <td></td>
      <td>${totalQuantity}</td>
      <td class="price">${totalPrice}</td>
    </tr>
  </table>
  <br />
  <form id="formMakeOrder" action="makeOrder" method="post" accept-charset="ISO-8859-1">
    <table border="1">
      <tr>
        <th colspan="2">Thông tin đặt hàng</th>
      </tr>
      <tr>
        <td><b>Tên:</b></td>
        <td><input type="text" id="name" name="name" value="${info.user.name}" /></td>
      </tr>
      <tr>
        <td><b>Địa chỉ:</b></td>
        <td><input type="text" id="address" name="address" value="${info.user.address}" /></td>
      </tr>
      <tr>
        <td><b>Số điện thoại:</b></td>
        <td><input type="text" id="phone" name="phone" value="${info.user.phone}" /></td>
      </tr>
      <tr>
        <td colspan="2"><input type="submit" value="Xác nhận đặt hàng" /></td>
      </tr>
    </table>
  </form>
  <br />


  <script>
			formatMoneyVND();
			$("#phone").verifyInputRealtime();

			$("#formMakeOrder").submit(function(e) {
				var isValidName = $("#name").validateInput(MIN_LEN, ONLY_ALPHABETS);
				var isValidPhone = $("#phone").validateInput(10, ONLY_NUMBERS);
				var isValidAddress = $("#address").validateInput(MIN_LEN, ONLY_ADDRESS);
				
				if (!isValidName || !isValidAddress || !isValidPhone) {
					e.preventDefault();
				}
			});
		</script>
</body>
</html>