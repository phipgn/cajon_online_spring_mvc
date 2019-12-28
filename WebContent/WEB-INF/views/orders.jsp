<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title>Cajon Online :: Manage Orders</title>
</head>
<body>
  <%@include file="nav.jsp"%>
  <br />
  <br />
  <table id="table-orders" border="1">
    <tr>
      <th colspan="6">Danh sách đơn hàng</th>
    </tr>
    <tr>
      <th>#</th>
      <th>Tên khách hàng</th>
      <th>Địa chỉ</th>
      <th>Số điện thoại</th>
      <th>Ngày đặt</th>
    </tr>
    <c:forEach items="${info.orders}" var="o" varStatus="loop">
      <tr>
        <td>${o.orderId}</td>
        <td><b>${o.name}</b></td>
        <td>${o.address}</td>
        <td>${o.phone}</td>
        <td>${o.createdDate}</td>
      </tr>
      <tr>
        <td colspan="6">
          <table border="1" style="width: 100%">
            <tr>
              <th>Hình</th>
              <th>Tên hàng</th>
              <th>Đơn giá</th>
              <th>Số lượng</th>
              <th>Thành tiền</th>
            </tr>
            <c:forEach items="${o.orderDetails}" var="item">
              <c:set var="totalPrice" value="${totalPrice + (item.unitPrice * item.quantity)}" />
              <c:set var="totalQuantity" value="${totalQuantity + item.quantity}" />
              <tr>
                <td><img style='width: 20px;' src='<% out.print(Values.ROOT_DIR); %>/images/products/${item.image}' /></td>
                <td>${item.productName}</td>
                <td class="price">${item.unitPrice}</td>
                <td>${item.quantity}</td>
                <td class="price">${item.unitPrice * item.quantity}</td>
              </tr>
            </c:forEach>
            <tr>
              <td></td>
              <td></td>
              <td></td>
              <td>${totalQuantity}</td>
              <td class="price">${totalPrice}</td>
              <c:set var="totalPrice" value="0" />
              <c:set var="totalQuantity" value="0" />
            </tr>
          </table>
        </td>
      </tr>
    </c:forEach>
  </table>

  <script type="text/javascript">
			formatMoneyVND();
		</script>
</body>
</html>