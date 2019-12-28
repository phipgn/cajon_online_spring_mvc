<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.cajon.daos.Info"%>
<%@page import="com.cajon.utils.Values"%>
<%
	session = request.getSession();
	Info info = (Info) request.getAttribute(Values.MODEL_NAME);

	String infoUsername = info.getUser().getUsername();
	String infoUserRole = info.getUser().getRole();
	int infoRoleId = info.getUser().getRoleId();
	int infoUserId = info.getUser().getId();
	int infoNoCartItems = info.getNumberOfCartItems();
    int infoNoOrders = info.getOrders() == null ? 0 : info.getOrders().size();

	out.print("<a class='nav' href='home'>Trang chủ</a> :: Xin chào <b>" + infoUsername + "</b> (" + infoUserRole + ")");
	switch (infoRoleId) {
	case 0: { // guest
		out.print(" :: <a class='nav' href='login_page'>Đăng nhập</a> :: <a class='nav' href='register'>Tạo tài khoản</a>");
		break;
	}
	case 1: { // user
		out.print(" :: <a class='nav' href='editProfile'>Cập nhật thông tin</a> :: <a class='nav' href='cart'>Giỏ hàng (<span id='number_of_cart_items'>" + infoNoCartItems + "</span>)</a> :: <a class='nav' href='logout'>Đăng xuất</a>");
		break;
	}
	case 2: { // admin
		out.print(" :: <a class='nav' href='logout'>Đăng xuất</a> :: <a class='nav' href='orders'>Quản lý đơn hàng (" + infoNoOrders + ")</a> :: <a class='nav' href='addProduct'>Thêm sản phẩm</a>");
		break;
	}
	default: {
		break;
	}
	}
%>