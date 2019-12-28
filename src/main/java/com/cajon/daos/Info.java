package com.cajon.daos;

import java.util.List;

public class Info {
	private User user;
	private int numberOfCartItems;
	private List<Product> products = null;
	private List<CartItem> cartItems = null;
	private List<Order> orders = null;
	private Product tempProduct; // for add/edit product
	private String searchInput = "";

	public Info() {
	}

	public Info(User user, int numberOfCartItems, List<Product> products, List<CartItem> cartItems, List<Order> orders,
			Product tempProduct) {
		super();
		this.user = user;
		this.numberOfCartItems = numberOfCartItems;
		this.products = products;
		this.cartItems = cartItems;
		this.orders = orders;
		this.tempProduct = tempProduct;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getNumberOfCartItems() {
		return numberOfCartItems;
	}

	public void setNumberOfCartItems(int numberOfCartItems) {
		this.numberOfCartItems = numberOfCartItems;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Product getTempProduct() {
		return tempProduct;
	}

	public void setTempProduct(Product tempProduct) {
		this.tempProduct = tempProduct;
	}

	public String getSearchInput() {
		return searchInput;
	}

	public void setSearchInput(String searchInput) {
		this.searchInput = searchInput;
	}

}
