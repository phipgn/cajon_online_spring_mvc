package com.cajon.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.cajon.daos.CartItem;
import com.cajon.daos.Order;
import com.cajon.daos.OrderDetail;
import com.cajon.daos.Product;
import com.cajon.daos.User;

public class DBUtil {

	private static DBUtil dbUtil = null;
	private Connection conn;

	private DBUtil() {
	}

	public static DBUtil getInstance() {
		System.out.println("Get DB instace!!!");
		if (dbUtil == null)
			dbUtil = new DBUtil();
		return dbUtil;
	}

	private void disconnect() {
		try {
			conn.close();
			// System.out.println("Database disconnected!!!");
		} catch (Exception e) {
			System.out.println("Error disconnecting database!!! Detail: " + e);
		}
	}

	private void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cajon_online", "root", "");
			// System.out.println("Database connected!!!");
		} catch (Exception e) {
			System.out.println("Error connecting to database!!! Detail: " + e);
		}
	}

	public User getUser(String username, String password) {
		try {
			connect();
			Statement stmt = conn.createStatement();
			String sql = "select * from users where username = '" + username + "' and password = '" + password + "'";
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				System.out.println("Incorrect username or password!!!");
				return null;
			} else {
				int id = rs.getInt(1);
				String u = rs.getString(2);
				String p = rs.getString(3);
				String name = rs.getString(4);
				String address = rs.getString(5);
				String phone = rs.getString(6);
				int role = rs.getInt(7);
				if (role == Values.ROLE_USER) {
					return new User(id, u, p, role, name, address, phone);
				} 
				return new User(id, u, p, role);
			}
		} catch (Exception ex) {
			System.out.println("Error querying user from database!!!");
		} finally {
			disconnect();
		}
		return null;
	}

	public List<Product> getAllProducts() {
		List<Product> products = null;
		try {
			connect();
			Statement stmt = conn.createStatement();
			String sql = "select * from products";
			ResultSet rs = stmt.executeQuery(sql);
			products = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String image = rs.getString(3);
				String description = rs.getString(4);
				Long unitPrice = rs.getLong(5);
				Product p = new Product(id, name, image, description, unitPrice);
				products.add(p);
			}
		} catch (Exception ex) {
			System.out.println("Error querying user from database!!!");
		} finally {
			disconnect();
		}
		return products;
	}

	public void addProduct(Product p) {
		try {
			connect();
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO products(name, image, description, unit_price) VALUES('" + p.getName() + "','" + p.getImage() + "','" + p.getDescription() + "','" + p.getUnitPrice() + "')";
			System.out.println("sql=" + sql);
			stmt.executeUpdate(sql);
		} catch (Exception ex) {
			System.out.println("Error adding a product to database!!! " + ex);
		} finally {
			disconnect();
		}
	}

	public void deleteProduct(int productId) {
		try {
			connect();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM products WHERE id=" + productId);
			stmt.executeUpdate("DELETE FROM cart_detail WHERE product_id=" + productId);
		} catch (Exception ex) {
			System.out.println("Error adding a product to database!!! " + ex);
		} finally {
			disconnect();
		}
	}

	public int getCartId(int userId) {
		try {
			connect();
			Statement stmt = conn.createStatement();
			String sql = "SELECT id FROM carts WHERE user_id=" + userId;
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				int cartId = rs.getInt(1);
				return cartId;
			}
		} catch (Exception ex) {
			System.out.println("Error getting cart ID!!!");
		} finally {
			disconnect();
		}
		return 0;
	}

	private boolean thisUserHasACartYet(int userId) {
		try {
			connect();
			Statement stmt = conn.createStatement();
			String sql = "select * from carts where user_id = " + userId;
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				System.out.println("User #" + userId + " does not have a cart yet!!!");
				return false;
			}
		} catch (Exception ex) {
			System.out.println("Error checking cart for user!!!");
		} finally {
			disconnect();
		}
		return true;
	}

	private void createCartForUser(int userId) {
		try {
			connect();
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO carts(user_id) VALUES ('" + userId + "')";
			System.out.println("sql=" + sql);
			stmt.executeUpdate(sql);
		} catch (Exception ex) {
			System.out.println("Error creating a new cart for user!!! " + ex);
		} finally {
			disconnect();
		}
	}

	private int hasThisProductBeenAddedToCart(int productId, int cartId) {
		try {
			connect();
			Statement stmt = conn.createStatement();
			String sql = "select quantity from cart_detail where cart_id = '" + cartId + "' and product_id = '" + productId + "'";
			System.out.println("sql=" + sql);
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				int quantity = rs.getInt(1);
				return quantity;
			}
		} catch (Exception ex) {
			System.out.println("Error checking cart for user!!! " + ex);
		} finally {
			disconnect();
		}
		return 0;
	}

	public boolean add1ProductToCart(int userId, int productId) {
		if (!thisUserHasACartYet(userId)) {
			createCartForUser(userId);
		}

		int cartId = getCartId(userId);

		try {
			connect();
			Statement stmt = conn.createStatement();

			// check if this cart already has this product
			String sql = "";
			int quantity = hasThisProductBeenAddedToCart(productId, cartId);
			if (quantity == 0) {
				sql = "INSERT INTO cart_detail(cart_id, product_id, quantity) VALUES ('" + cartId + "', '" + productId + "', '" + (++quantity) + "')";
			} else {
				sql = "UPDATE cart_detail SET quantity=" + (++quantity) + " WHERE cart_id=" + cartId + " AND product_id=" + productId;
			}

			System.out.println("sql=" + sql);
			stmt.executeUpdate(sql);
			return true;
		} catch (Exception ex) {
			System.out.println("Error adding a product to cart!!! " + ex);
			return false;
		} finally {
			disconnect();
		}
	}

	public void deleteCartItem(int cartId, int productId) {
		try {
			connect();
			Statement stmt = conn.createStatement();
			String sql = "DELETE FROM cart_detail WHERE product_id=" + productId + " AND cart_id=" + cartId;
			System.out.println("sql=" + sql);
			stmt.executeUpdate(sql);
		} catch (Exception ex) {
			System.out.println("Error deleting a cart item!!! " + ex);
		} finally {
			disconnect();
		}
	}

	public boolean remove1ProductFromCart(int userId, int productId) {
		if (!thisUserHasACartYet(userId)) {
			createCartForUser(userId);
		}

		int cartId = getCartId(userId);

		try {
			connect();
			Statement stmt = conn.createStatement();

			// check if this cart already has this product
			String sql = "";
			int quantity = hasThisProductBeenAddedToCart(productId, cartId);
			quantity--;
			if (quantity == 0) {
				// remove product from cart
				deleteCartItem(cartId, productId);
			} else {
				sql = "UPDATE cart_detail SET quantity=" + quantity + " WHERE cart_id=" + cartId + " AND product_id=" + productId;
			}

			System.out.println("sql=" + sql);
			stmt.executeUpdate(sql);
			return true;
		} catch (Exception ex) {
			System.out.println("Remove product #" + productId + " from cart: " + ex);
			return false;
		} finally {
			disconnect();
		}
	}

	public int countCartItems(int userId) {
		if (thisUserHasACartYet(userId)) {
			int cartId = getCartId(userId);
			try {
				connect();
				Statement stmt = conn.createStatement();

				// check if this cart already has this product
				String sql = "SELECT quantity FROM cart_detail WHERE cart_id=" + cartId;
				System.out.println("sql=" + sql);
				ResultSet rs = stmt.executeQuery(sql);
				int count = 0;
				while (rs.next()) {
					count += rs.getInt(1);
					System.out.println(rs.getInt(1));
				}
				if (count == 0) {
					System.out.println("No product items in the cart of user #" + userId);
				}
				System.out.println("count=" + count);
				return count;
			} catch (Exception ex) {
				System.out.println("Error counting cart items!!! " + ex);
			} finally {
				disconnect();
			}
		}
		return 0;
	}

	public Product getProduct(int productId) {
		try {
			connect();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM products WHERE id=" + productId;
			System.out.println("[getProduct] sql=" + sql);
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				return null;
			}
			int id = rs.getInt(1);
			String name = rs.getString(2);
			String image = rs.getString(3);
			String description = rs.getString(4);
			Long unitPrice = rs.getLong(5);
			Product p = new Product(id, name, image, description, unitPrice);
			return p;
		} catch (Exception ex) {
			System.out.println("Error getting product #" + productId + ": " + ex);
		} finally {
			disconnect();
		}
		return null;
	}

	@SuppressWarnings("unused")
	private String getProductName(int productId) {
		Product p = getProduct(productId);
		return p.getName();
	}

	@SuppressWarnings("unused")
	private String getProductImage(int productId) {
		Product p = getProduct(productId);
		return p.getImage();
	}

	private Long getProductUnitPrice(int productId) {
		Product p = getProduct(productId);
		return p.getUnitPrice();
	}

	public void editProduct(int productId, String image, String name, String description, String unitPrice) {

		System.out.println("name=" + name);
		try {
			connect();
			Statement stmt = conn.createStatement();
			String sql = "UPDATE products SET name='" + name + "', image='" + image + "', description='" + description + "', unit_price=" + Long.parseLong(unitPrice) + " WHERE id=" + productId;
			System.out.println("sql=" + sql);
			stmt.executeUpdate(sql);
		} catch (Exception ex) {
			System.out.println("Error updating product " + ex);
		} finally {
			disconnect();
		}
	}

	public List<CartItem> getCartItems(int userId) {
		if (!thisUserHasACartYet(userId)) {
			return null;
		} else {
			if (countCartItems(userId) == 0) {
				return null;
			} else {
				List<CartItem> cartItems = new ArrayList<>();
				int cartId = getCartId(userId);
				try {
					connect();
					Statement stmt = conn.createStatement();
					String sql = "SELECT * FROM cart_detail WHERE cart_id=" + cartId;
					System.out.println("sql=" + sql);
					ResultSet rs = stmt.executeQuery(sql);
					while (rs.next()) {
						int productId = rs.getInt(3);
						int quantity = rs.getInt(4);
						Product p = getProduct(productId);
						CartItem cartItem = new CartItem(p, quantity);
						cartItems.add(cartItem);
					}
					return cartItems;
				} catch (Exception ex) {
					System.out.println("Error adding a product to cart!!! " + ex);
				} finally {
					disconnect();
				}
			}
		}
		return null;
	}

	private String getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	private void insertOrderDetail(int orderId, int productId, String productName, String productImage, int quantity, Long unitPrice) {
		try {
			connect();
			Statement stmt = conn.createStatement();
			String sql = "INSERT INTO order_detail(order_id, product_id, product_name, product_image, quantity, unit_price) VALUES('" + orderId + "','" + productId + "', '" + productName + "', '" + productImage + "'," + quantity + "," + unitPrice + ")";
			System.out.println("sql=" + sql);
			stmt.executeUpdate(sql);
		} catch (Exception ex) {
			System.out.println("Error inserting order detail!!!" + ex);
		} finally {
			disconnect();
		}
	}

	private void insertOrderDetails(int cartId, int orderId) {
		try {
			connect();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM cart_detail WHERE cart_id=" + cartId;
			System.out.println("sql=" + sql);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int productId = rs.getInt(3);
				Product p = getProduct(productId);
				String productName = p.getName();
				String productImage = p.getImage();
				int quantity = rs.getInt(4);
				Long unitPrice = getProductUnitPrice(productId);
				insertOrderDetail(orderId, productId, productName, productImage, quantity, unitPrice);
			}
		} catch (Exception ex) {
			System.out.println("Error getting unit price!!!" + ex);
		} finally {
			disconnect();
		}
	}

	public void makeOrder(int userId, String name, String address, String phone) {
		int cartId = getCartId(userId);
		try {
			connect();
			Statement stmt = conn.createStatement();

			String sql = "";
			// create new order
			sql = "INSERT INTO orders(user_id, name, address, phone, created_date, status) VALUES(" + userId + ", '" + name + "', '" + address + "', '" + phone + "', '" + getCurrentDate() + "', 0)";
			System.out.println("sql=" + sql);
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			int orderId = 0;
			if (rs.next()) {
				orderId = rs.getInt(1);
			}

			// insert order details
			insertOrderDetails(cartId, orderId);

			// empty cart for user
			sql = "DELETE FROM cart_detail WHERE cart_id=" + cartId;
			System.out.println("sql=" + sql);
			stmt.executeUpdate(sql);
		} catch (Exception ex) {
			System.out.println("Error placing order!!! " + ex);
		} finally {
			disconnect();
		}

	}

	private List<OrderDetail> getOrderDetails(int orderId) {
		try {
			connect();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM order_detail WHERE order_id=" + orderId;
			ResultSet rs = stmt.executeQuery(sql);
			List<OrderDetail> orderItems = new ArrayList<>();
			while (rs.next()) {
				int productId = rs.getInt(3);
				Product p = getProduct(productId);
				if (p == null) {
					System.out.println("Product #" + productId + " not found! The product might be deleted!");
				}
				String productName = rs.getString(4);
				String productImage = rs.getString(5);
				int quantity = rs.getInt(6);
				Long unitPrice = rs.getLong(7);
				OrderDetail orderItem = new OrderDetail(orderId, productId, productName, productImage, quantity, unitPrice);
				orderItems.add(orderItem);
			}
			return orderItems;
		} catch (Exception ex) {
			System.out.println("Error getting order detail!!!" + ex);
		} finally {
			disconnect();
		}
		return null;
	}

	public List<Order> getOrders() {
		List<Order> orders = new ArrayList<>();
		try {
			connect();
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM orders ORDER BY created_date DESC";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int orderId = rs.getInt(1);
				int userId = rs.getInt(2);
				String name = rs.getString(3);
				String address = rs.getString(4);
				String phone = rs.getString(5);
				String createdDate = rs.getString(6);
				boolean status = rs.getBoolean(7);
				List<OrderDetail> orderDetails = getOrderDetails(orderId);
				Order order = new Order(orderId, userId, name, address, phone, createdDate, status, orderDetails);
				orders.add(order);
			}
			return orders;
		} catch (Exception ex) {
			System.out.println("Error getting orders!!!");
		} finally {
			disconnect();
		}
		return null;
	}

	public void deleteOrder(int orderId) {
		try {
			connect();
			Statement stmt = conn.createStatement();

			String sql = "DELETE FROM order_detail WHERE order_id=" + orderId;
			System.out.println("sql=" + sql);
			stmt.executeUpdate(sql);

			sql = "DELETE FROM orders WHERE id=" + orderId;
			System.out.println("sql=" + sql);
			stmt.executeUpdate(sql);
		} catch (Exception ex) {
			System.out.println("Error deleting order!!!" + ex);
		} finally {
			disconnect();
		}
	}

	public List<Product> searchProducts(String input) {
		try {
			connect();
			List<Product> products = null;

			String sql = "SELECT * FROM products WHERE name LIKE '%" + input + "%'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			products = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String image = rs.getString(3);
				String description = rs.getString(4);
				Long unitPrice = rs.getLong(5);
				Product p = new Product(id, name, image, description, unitPrice);
				products.add(p);
			}
			if (products.isEmpty()) {
				return null;
			}
			return products;
		} catch (Exception ex) {
			System.out.println("Error searching for product name '" + input + "': " + ex);
		} finally {
			disconnect();
		}
		return null;
	}

	public void insertAccount(String username, String password, String name, String address, String phone) {
		try {
			connect();
			String sql = "INSERT INTO users(username, password, name, address, phone, role_id) VALUES('" + username + "', '" + password + "', '" + name + "', '" + address + "', '" + phone + "', 1)";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception ex) {
			System.out.println("Error inserting new account!!!" + ex);
		} finally {
			disconnect();
		}
	}

	public void updateAccount(String username, String name, String address, String phone) {
		try {
			connect();
			String sql = "UPDATE users SET name='" + name + "', address='" + address + "', phone='" + phone + "' WHERE username='" + username + "'";
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception ex) {
			System.out.println("Error inserting new account!!!" + ex);
		} finally {
			disconnect();
		}
	}
	
	public boolean usernameExisted(String username) {
		try {
			connect();
			String sql = "SELECT * FROM users WHERE username='" + username + "'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				return true;
			}
		} catch (Exception ex) {
			System.out.println("Error inserting new account!!!" + ex);
		} finally {
			disconnect();
		}
		return false;
	}
}
