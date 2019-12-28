package com.cajon.daos;

import com.cajon.utils.Values;

public class User {
	private int id;
	private String username;
	private String password;
	private int roleId;
	private String role;
	private String name;
	private String address;
	private String phone;

	public User(int id, String username, String password, int role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roleId = role;
		this.role = Values.ROLES[roleId];
	}

	public User(int id, String username, String password, int roleId, String name, String address, String phone) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roleId = roleId;
		this.role = Values.ROLES[roleId];
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRole(int role) {
		this.roleId = role;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

}
