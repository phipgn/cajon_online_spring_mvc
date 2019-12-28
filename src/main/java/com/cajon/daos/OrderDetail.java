package com.cajon.daos;

public class OrderDetail {
	private int orderId;
	private int productId;
	private String productName;
	private String image;
	private int quantity;
	private Long unitPrice;

	public OrderDetail(int orderId, int productId, String productName, String image, int quantity, Long unitPrice) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.productName = productName;
		this.image = image;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Long unitPrice) {
		this.unitPrice = unitPrice;
	}

}
