<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title>Cajon Online :: Cart</title>
</head>
<body>
  <%@include file="nav.jsp"%>
  <br />
  <br />
  <br />
  <table border="1">
    <tr>
      <th colspan="6">Giỏ hàng</th>
    </tr>
    <tr>
      <th>Hình</th>
      <th>Tên SP</th>
      <th>Đơn giá</th>
      <th>Số lượng</th>
      <th>Thành tiền</th>
      <th>Thao tác</th>
    </tr>
    <c:forEach items="${info.cartItems}" var="c">
      <c:set var="totalPrice" value="${totalPrice + c.p.unitPrice * c.quantity}" />
      <c:set var="totalQuantity" value="${totalQuantity + c.quantity}" />
      <tr>
        <td><img class="product-img" src="<% out.print(Values.ROOT_DIR); %>/images/products/${c.p.image}" /></td>
        <td><b>${c.p.name}</b></td>
        <td class="price">${c.p.unitPrice}</td>
        <td><a href="<% out.print(Values.ROOT_DIR); %>/remove1CartItem?uid=<% out.print(infoUserId); %>&pid=${c.p.id}"><button>-</button></a> ${c.quantity} <a href="/cajon_online/add1CartItem?uid=<% out.print(infoUserId); %>&pid=${c.p.id}"><button>+</button></a></td>
        <td class="price"><c:out value="${c.p.unitPrice * c.quantity}" /></td>
        <td><a href="<% out.print(Values.ROOT_DIR); %>/removeAllSameCartItems?uid=<% out.print(infoUserId); %>&pid=${c.p.id}"><button>Xóa</button></a></td>
      </tr>
    </c:forEach>
    <tr>
      <td></td>
      <td></td>
      <td></td>
      <td>${totalQuantity}</td>
      <td class="price">${totalPrice}</td>
      <td><a href="order"><button id="btnMakeOrder">Đặt hàng</button></a></td>
    </tr>
  </table>

  <script>
			$(document).ready(function() {
				formatMoneyVND();
			});
		</script>

</body>
</html>