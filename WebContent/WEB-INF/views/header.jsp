<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="shortcut icon" type="image/png" href="<c:url value="/images/favicon.png"/>" />
<script src="<c:url value="/lib/jquery-3.4.1.js"/>"></script>
<script src="<c:url value="/lib/money.js"/>"></script>
<script src="<c:url value="/lib/validate.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
.error {
  color: red; font-weight: bold; display: block; margin-top: 3px;
}

.absoluteCenter {
  width: 300px; height: 200px; position: absolute; top: 40%; left: 50%; margin: -100px 0 0 -150px;
  background-color: white; padding: 15px; text-align: center;
}

.nav {
  font-weight: bold; color: brown;
}

.description {
  font-size: 12px; text-align: left; width: 300px;
}

body,table,tr,td,input,button {
  font-family: Arial; font-size: 13px;
}

button,input[type="submit"],input[type="button"] {
  cursor: pointer; font-weight: bold;
}

input[readonly='readonly'] {
  background: #ccc;
  border:1px solid #333;
}

table {
  border-collapse: collapse; width: 800px;
}

#table-orders {
  width: 800px;
}

th,td {
  padding: 5px; text-align: center;
}

th {
  height: 40px; background: #ddd;
}

.out-of-stock {
  font-weight: bold; color: red;
}

.in-stock {
  font-weight: bold; color: green;
}

.product-desc {
  font-style: italic; font-size: 12px; width: 200px; display: block;
}

.product-img {
  width: 50px;
}

.price,.unit-price {
  color: brown; font-weight: bold;
}
</style>