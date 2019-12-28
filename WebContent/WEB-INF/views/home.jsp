<%@include file="header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<head>
<title>Cajon Online :: Home</title>
</head>
<body>
  <%@include file="nav.jsp"%>
  <br />
  <br />
  <form action="searchProduct" id="formSearch" method="post" accept-charset="ISO-8859-1">
  <b>Tìm kiếm sản phẩm:</b> <input type="text" id="searchInput" name="input" value="${info.searchInput}" />
  </form>
  <c:if test="${not empty info.searchInput}" >
  	Kết quả tìm kiếm cho từ khoá: <b>${info.searchInput}</b> | <a href='home'>Hiện tất cả sản phẩm</a>
  	<br />
  </c:if>
  <br />
  <table border="1">
    <c:forEach items="${info.products}" var="p">
      <tr>
        <td><img class="product-img" src="<% out.print(Values.ROOT_DIR); %>/images/products/${p.image}" /></td>
        <td><b>${p.name}</b></td>
        <td class="description">${p.description}</td>
        <td><span class='price'>${p.unitPrice}</span></td>
        <td><c:choose>
            <c:when test="${info.user.roleId==0}">
            Đăng nhập để đặt hàng.
          </c:when>
            <c:when test="${info.user.roleId==1}">
              <a href="<% out.print(Values.ROOT_DIR); %>/add1CartItemFromHome?uid=<% out.print(infoUserId); %>&pid=${p.id}"><button>Thêm vào giỏ</button></a>
            </c:when>
            <c:when test="${info.user.roleId==2}">
              <a href="<% out.print(Values.ROOT_DIR); %>/editProduct?pid=${p.id}"><button>Sửa</button></a>
              <br /><br />
              <a href="<% out.print(Values.ROOT_DIR); %>/deleteProduct?pid=${p.id}"><button>Xóa</button></a>
            </c:when>
          </c:choose></td>
      </tr>
    </c:forEach>
  </table>
  <script>
			$(document).ready(function() {
				
				formatMoneyVND();
				$("#formSearch").submit(function(e) {
					var isValidSearch = $("#searchInput").validateInput(1, ONLY_NUMBERS_ALPHABETS);
					if (!isValidSearch) {
						e.preventDefault();
					}
				});
			});
		</script>
</body>
</html>