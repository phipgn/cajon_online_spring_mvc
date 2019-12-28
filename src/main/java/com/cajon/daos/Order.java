package com.cajon.daos;

import java.util.List;

public class Order {
	private int orderId;
	private int userId;
	private String name;
	private String address;
	private String phone;
	private String createdDate;
	private boolean status;
	private List<OrderDetail> orderDetails = null;

	public Order(int orderId, int userId, String name, String address, String phone, String createdDate, boolean status, List<OrderDetail> orderDetails) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.createdDate = createdDate;
		this.status = status;
		this.orderDetails = orderDetails;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

}
