package com.cajon.daos;

public class Product {
	private int id;
	private String name;
	private String image;
	private String description;
	private Long unitPrice;

	public Product(int id, String name, String image, String description, Long unitPrice) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.description = description;
		this.unitPrice = unitPrice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Long unitPrice) {
		this.unitPrice = unitPrice;
	}

}
