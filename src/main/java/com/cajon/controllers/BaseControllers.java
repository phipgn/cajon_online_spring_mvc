package com.cajon.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cajon.daos.Info;
import com.cajon.daos.Product;
import com.cajon.daos.User;
import com.cajon.utils.DBUtil;
import com.cajon.utils.Values;

@Controller
public class BaseControllers extends Values {

	protected DBUtil db = DBUtil.getInstance();
	protected Info info = null;

	protected void updateSession() {
		if (info == null) {
			// no logged in user
			User u = new User(Values.ROLE_GUEST, "Khách", null, Values.ROLE_GUEST);
			info = new Info(u, 0, db.getAllProducts(), null, null, null);
		} else {
			// logged in user
			User u = info.getUser();
			int userId = u.getId();
			info.setProducts(db.getAllProducts());
			info.setSearchInput("");
			if (u.getRoleId() == Values.ROLE_ADMIN) {
				// Admin
				info.setOrders(db.getOrders());
			} else {
				// Normal User
				info.setCartItems(db.getCartItems(userId));
				info.setNumberOfCartItems(db.countCartItems(userId));
			}
		}
	}

	@RequestMapping("/home")
	public ModelAndView goHome() {
		updateSession();
		return new ModelAndView("home", MODEL_NAME, info);
	}

	@RequestMapping("/login_page")
	public String goLogin() {
		return "login";
	}

	@RequestMapping("/register")
	public String goRegister() {
		if (info.getUser().getRoleId() != Values.ROLE_GUEST) {
			return "home";
		}
		return "register";
	}

	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User u = db.getUser(username, password);
		if (u == null) {
			return new ModelAndView("login", "message", "Tên đăng nhập hoặc mật khẩu không đúng!!!");
		} else {
			info = new Info();
			info.setUser(u);
			updateSession();
		}
		return new ModelAndView("home", MODEL_NAME, info);
	}

	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
		info = null;
		updateSession();
		return new ModelAndView("home", MODEL_NAME, info);
	}

	/*
	 * CART CONTROLLERS
	 */

	@RequestMapping("/add1CartItem")
	public ModelAndView add1CartItem(@RequestParam("uid") int uid, @RequestParam("pid") int pid) {
		if (info.getUser().getRoleId() != Values.ROLE_USER) {
			return goHome();
		}
		if (info.getCartItems() == null) {
			return goHome();
		}
		db.add1ProductToCart(uid, pid);
		return goCart();
	}

	@RequestMapping("/add1CartItemFromHome")
	public ModelAndView add1CartItemFromHome(@RequestParam("uid") int uid, @RequestParam("pid") int pid) {
		if (info.getUser().getRoleId() != Values.ROLE_USER) {
			return goHome();
		}
		db.add1ProductToCart(uid, pid);
		return goHome();
	}

	@RequestMapping("/remove1CartItem")
	public ModelAndView remove1CartItem(@RequestParam("uid") int uid, @RequestParam("pid") int pid) {
		if (info.getUser().getRoleId() != Values.ROLE_USER) {
			return goHome();
		}
		if (info.getCartItems() == null) {
			return goHome();
		}
		db.remove1ProductFromCart(uid, pid);
		updateSession();
		return new ModelAndView("cart", MODEL_NAME, info);
	}

	@RequestMapping("/removeAllSameCartItems")
	public ModelAndView removeAllSameCartItems(@RequestParam("uid") int uid, @RequestParam("pid") int pid) {
		if (info.getUser().getRoleId() != Values.ROLE_USER) {
			return goHome();
		}
		if (info.getCartItems() == null) {
			return goHome();
		}
		db.deleteCartItem(db.getCartId(uid), pid);
		updateSession();
		if (info.getCartItems() == null) {
			return goHome();
		}
		return new ModelAndView("cart", MODEL_NAME, info);
	}

	@RequestMapping("/cart")
	public ModelAndView goCart() {
		if (info.getUser().getRoleId() != Values.ROLE_USER) {
			return goHome();
		}
		if (info.getCartItems() == null) {
			return goHome();
		}
		updateSession();
		return new ModelAndView("cart", MODEL_NAME, info);
	}

	@RequestMapping("/order")
	public ModelAndView goOrder() {
		if (info.getUser().getRoleId() != Values.ROLE_USER) {
			return goHome();
		}
		if (info.getCartItems() == null) {
			return goHome();
		}
		updateSession();
		return new ModelAndView("order", MODEL_NAME, info);
	}

	@RequestMapping("/makeOrder")
	public ModelAndView makeOrder(HttpServletRequest request, HttpServletResponse response) {
		if (info.getUser() == null) {
			return goHome();
		}
		if (info.getUser().getRoleId() != Values.ROLE_USER) {
			return goHome();
		}
		if (info.getNumberOfCartItems() == 0) {
			return goHome();
		}
		int userId = info.getUser().getId();
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		db.makeOrder(userId, name, address, phone);
		updateSession();
		return new ModelAndView("home", MODEL_NAME, info);
	}

	/*
	 * MANAGE ORDER CONTROLLERS
	 */

	@RequestMapping("/orders")
	public ModelAndView goOrders() {
		if (info.getUser() == null) {
			return goHome();
		}
		if (info.getUser().getRoleId() != Values.ROLE_ADMIN) {
			return goHome();
		}
		return new ModelAndView("orders", MODEL_NAME, info);
	}

	@RequestMapping("/deleteOrder")
	public ModelAndView makeOrder(@RequestParam("orderId") int orderId) {
		if (info.getUser() == null) {
			return goHome();
		}
		if (info.getUser().getRoleId() != Values.ROLE_ADMIN) {
			return goHome();
		}
		db.deleteOrder(orderId);
		updateSession();
		return new ModelAndView("orders", MODEL_NAME, info);
	}

	/*
	 * ADD NEW PRODUCTS
	 */

	@RequestMapping("/addProduct")
	public ModelAndView goAddProduct() {
		if (info == null || info.getUser() == null) {
			return goHome();
		}
		if (info.getUser().getRoleId() != Values.ROLE_ADMIN) {
			return goHome();
		}
		updateSession();
		info.setTempProduct(null);
		return new ModelAndView("add_product", MODEL_NAME, info);
	}

	private String uploadImage(CommonsMultipartFile image, HttpSession session) {
		ServletContext context = session.getServletContext();
		String path = context.getRealPath(UPLOAD_DIRECTORY);
		System.out.println("path=" + path);
		String filename = image.getOriginalFilename();
		String imagePath = path + File.separator + filename;
		System.out.println("imagePath=" + imagePath);
		try {
			// save image file to deployment directory
			byte[] bytes = image.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(imagePath)));
			stream.write(bytes);
			stream.flush();
			stream.close();

			// save image file to original directory
			imagePath = Values.ABSOLUTE_PATH_WIN + filename;
			System.out.println("imagePath=" + imagePath);
			stream = new BufferedOutputStream(new FileOutputStream(new File(imagePath)));
			stream.write(bytes);
			stream.flush();
			stream.close();

			return filename;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@RequestMapping("/uploadImageForEditProduct")
	public ModelAndView uploadImageForEditProduct(@RequestParam CommonsMultipartFile image, HttpSession session) {
		if (info.getUser() == null) {
			return goHome();
		}
		if (info.getUser().getRoleId() != Values.ROLE_ADMIN) {
			return goHome();
		}
		String filename = uploadImage(image, session);
		Product p = info.getTempProduct();
		p.setImage(filename);
		updateSession();
		return new ModelAndView("edit_product", MODEL_NAME, info);
	}

	@RequestMapping("/uploadImageForNewProduct")
	public ModelAndView uploadImageForNewProduct(@RequestParam CommonsMultipartFile image, HttpSession session) {
		if (info.getUser() == null) {
			return goHome();
		}
		if (info.getUser().getRoleId() != Values.ROLE_ADMIN) {
			return goHome();
		}
		String filename = uploadImage(image, session);
		Product p = new Product(0, null, filename, null, null);
		info.setTempProduct(p);
		updateSession();
		return new ModelAndView("add_product", MODEL_NAME, info);
	}

	@RequestMapping("/addNewProduct")
	public ModelAndView addNewProduct(HttpServletRequest request) {
		if (info.getUser() == null) {
			return goHome();
		}
		if (info.getUser().getRoleId() != Values.ROLE_ADMIN) {
			return goHome();
		}
		Product p = info.getTempProduct();

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		Long price = Long.parseLong(request.getParameter("price"));

		p.setName(name);
		p.setDescription(description);
		p.setUnitPrice(price);
		db.addProduct(p);

		updateSession();
		return new ModelAndView("home", MODEL_NAME, info);
	}

	@RequestMapping("/editProduct")
	public ModelAndView goEditProduct(@RequestParam("pid") int pid) {
		if (info.getUser() == null) {
			return goHome();
		}
		if (info.getUser().getRoleId() != Values.ROLE_ADMIN) {
			return goHome();
		}
		Product p = db.getProduct(pid);
		info.setTempProduct(p);
		updateSession();
		return new ModelAndView("edit_product", MODEL_NAME, info);
	}

	@RequestMapping("/editNewProduct")
	public ModelAndView editNewProduct(HttpServletRequest request) {
		if (info.getUser() == null) {
			return goHome();
		}
		if (info.getUser().getRoleId() != Values.ROLE_ADMIN) {
			return goHome();
		}
		int productId = Integer.parseInt(request.getParameter("pid"));
		String name = request.getParameter("name");
		String image = info.getTempProduct().getImage();
		String description = request.getParameter("description");
		String unitPrice = request.getParameter("price");
		db.editProduct(productId, image, name, description, unitPrice);
		updateSession();
		return new ModelAndView("home", MODEL_NAME, info);
	}

	@RequestMapping("/deleteProduct")
	public ModelAndView deleteProduct(@RequestParam("pid") int pid) {
		if (info.getUser() == null) {
			return goHome();
		}
		if (info.getUser().getRoleId() != Values.ROLE_ADMIN) {
			return goHome();
		}
		db.deleteProduct(pid);
		updateSession();
		return new ModelAndView("home", MODEL_NAME, info);
	}

	@RequestMapping("/searchProduct")
	public ModelAndView searchProduct(HttpServletRequest request) {
		String input = request.getParameter("input");
		List<Product> products = db.searchProducts(input);
		updateSession();
		info.setProducts(products);
		info.setSearchInput(input);
		return new ModelAndView("home", MODEL_NAME, info);
	}

	@RequestMapping("/registerAccount")
	public ModelAndView registerAccount(HttpServletRequest request) {
		if (info.getUser() == null) {
			return goHome();
		}
		if (info.getUser().getRoleId() != Values.ROLE_GUEST) {
			return goHome();
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");

		if (db.usernameExisted(username)) {
			return new ModelAndView("register", "message", "Tên đăng nhập đã tồn tại!");
		} else {
			db.insertAccount(username, password, name, address, phone);
		}
		return new ModelAndView("login", "message", "Tạo tài khoản thành công. Mời đăng nhập!");
	}

	@RequestMapping("/editProfile")
	public ModelAndView goEditProfile(HttpServletRequest request) {
		updateSession();
		if (info.getUser() == null) {
			return goHome();
		}
		if (info.getUser().getRoleId() != Values.ROLE_USER) {
			return goHome();
		}
		return new ModelAndView("edit_profile", Values.MODEL_NAME, info);
	}

	@RequestMapping("/editUserProfile")
	public ModelAndView editUserProfile(HttpServletRequest request) {
		if (info.getUser() == null) {
			return goHome();
		}
		if (info.getUser().getRoleId() != Values.ROLE_USER) {
			return goHome();
		}
		String username = request.getParameter("username");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		db.updateAccount(username, name, address, phone);
		info.setUser(db.getUser(username, info.getUser().getPassword()));
		updateSession();
		return new ModelAndView("home", Values.MODEL_NAME, info);
	}
}
